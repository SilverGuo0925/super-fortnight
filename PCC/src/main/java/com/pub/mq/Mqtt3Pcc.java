package com.pub.mq;

//import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.*;
//import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
//import org.eclipse.paho.client.mqttv3.MqttCallback;
//import org.eclipse.paho.client.mqttv3.MqttClient;
//import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
//import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
//import com.pub.mq.Mqtt3;

//import org.codehaus.jackson.map.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.google.gson.Gson;
import com.pub.external.ManualResetEvent;
import com.pub.pojo.fact.*;
//import com.pub.pojo.para.*;
import com.sample.*;

public class Mqtt3Pcc implements InterestingEvent {

	static final String BROKER_URL = "tcp://192.3.20.141:1883";

	static final String CLIENTID = "PCC";
	static final boolean CLEAN = true;
	static final boolean QUITEMODE = false;
	static final String USERNAME = "wgn_silver";
	static final String PASSWORD = "123456";

	static final String[] subTopics = new String[] { "UpdateRes", "SelectRes", "InterestingPush" };
	static final String[] pubTopics = new String[] { "Update", "Select", "Interesting" };
	static final String[] preTagNames = new String[] { "JEBS-DPSET-MAIN-1", "JEBS-DP-Main-1", "JEBS-SP-Main-1" };
	static final String[] pumpTagNames = new String[] { "JEBS-P1-L*T", "JEBS-P1-Status", "JEBS-P1-Trip",
			"JEBS-P1-SPEED" };

	private Mqtt3 client;
	private int qos;
	ObjectMapper mapper;
	private ManualResetEvent eventHandle = null;

	private SelectPoint selectPt;
	private SelectResPoint selectResPt;
	private UpdatePoint updatePt;
	private UpdateResPoint updateResPt;
	public int mode;
	private int index;
	private boolean m_bSelect, m_bUpdate;
	// private Data dataReqMode;

	public PreStart preStart;
	public ArrayList<Double> pressures;
	public ArrayList<Pump> pumps;
	private ReceiveEvent recvEvt;

	public Mqtt3Pcc(ReceiveEvent event) throws MqttException {

		client = new Mqtt3(BROKER_URL, CLIENTID, CLEAN, QUITEMODE, USERNAME, PASSWORD, this);
		recvEvt = event;
		eventHandle = new ManualResetEvent(false);

		for (String topic : subTopics)
			client.subscribe(topic, qos);
		// client.subscribe("SelectRes", qos);
		//
		// m_bPS = false;
		// m_bIO = false;

		// preStart = new PreStart();
		// dataPoints = new ArrayList<>();
		// Save the event object for later use.

		qos = 0;
		index = 0;
		mapper = new ObjectMapper();

		// select
		selectPt = new SelectPoint();
		selectPt.clientID = CLIENTID;
		selectPt.table = "AnalogBase";
		selectPt.tagName = "JEBS-Pump-Mode";
		selectPt.column = "Value";
		// select res
		selectResPt = new SelectResPoint();
		selectResPt.clientID = CLIENTID;

		// pressure
		pressures = new ArrayList<>();
		pumps = new ArrayList<>();

		// update
		updatePt = new UpdatePoint();
		updatePt.clientID = CLIENTID;

		// update res
		updateResPt = new UpdateResPoint();
		updateResPt.clientID = CLIENTID;
	}

	public void copy(SelectResPoint srcPt, SelectResPoint destPt) {
		destPt.clientID = srcPt.clientID;
		destPt.index = srcPt.index;
		destPt.value = srcPt.value;
	}

	public void interestingEvent(String topic, MqttMessage message) {
		// transfer message to datapoint
		String msg = new String(message.getPayload());
		switch (topic) {
		case "SelectRes":

			try {
				SelectResPoint srPt = mapper.readValue(msg, SelectResPoint.class);

				if (selectResPt.index == srPt.index) {
					copy(srPt, selectResPt);
					m_bSelect = true;

				}

			} catch (Throwable t) {
				t.printStackTrace();
			}
			eventHandle.set();
			break;

		case "UpdateRes":
			try {
				UpdateResPoint updatePt = mapper.readValue(msg, UpdateResPoint.class);

				if (updateResPt.index == updatePt.index) {
					if (updatePt.value == true)
						m_bUpdate = true;
				}

			} catch (Throwable t) {
				t.printStackTrace();
			}
			eventHandle.set();
			break;
		case "InterestingPush":
			try {
				SubResPoint subResPt = mapper.readValue(msg, SubResPoint.class);
				recvEvt.receiveEvent(subResPt);

			} catch (Throwable t) {
				t.printStackTrace();
			}
			break;
		}

	}

	public boolean updatePt(String table, String tagName, String column, double value) {

		if (index > 100)
			index = 0;
		else
			index += 1;

		try {
			m_bUpdate = false;
			eventHandle.reset();
			updatePt.index = index;
			updateResPt.index = updatePt.index;
			updatePt.table = table;
			updatePt.tagName = tagName;
			updatePt.column = column;
			updatePt.value =Double.toString(value);
			String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(updatePt);
			byte[] jsonInBytes = jsonInString.getBytes();

			client.publish("Update", qos, jsonInBytes);
			//TraceOut(jsonInString);
			eventHandle.waitOne(5000, TimeUnit.MILLISECONDS);

			return m_bUpdate;

		} catch (MqttException e) {
			e.printStackTrace();
			return false;
		} catch (Throwable e) {
			e.printStackTrace();
			return false;
		}

	}

	public boolean update(Pump pump) {
		String table = "AnalogBase";
		String tagName = "JEBS-P" + pump.pumpNo + "-SPEED-SET";
		String column = "Value";
		double value = pump.cmdSpeed;
		boolean bRet = updatePt(table, tagName, column, value);
		return bRet;
	}

	public boolean selectPt(String table, String tagName, String column) {

		if (index > 100)
			index = 0;
		else
			index += 1;

		try {
			m_bSelect = false;
			eventHandle.reset();
			selectPt.index = index;
			selectResPt.index = selectPt.index;
			selectPt.table = table;
			selectPt.tagName = tagName;
			selectPt.column = column;

			String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(selectPt);
			byte[] jsonInBytes = jsonInString.getBytes();
			client.publish("Select", qos, jsonInBytes);
			//TraceOut(jsonInString);
			eventHandle.waitOne(5000, TimeUnit.MILLISECONDS);

			return m_bSelect;

		} catch (MqttException e) {
			e.printStackTrace();
			return false;
		} catch (Throwable e) {
			e.printStackTrace();
			return false;
		}

	}

	public boolean subscribe() {

		try {

			SubPoint subPt = new SubPoint();
			subPt.clientID = CLIENTID;
			subPt.data = new ArrayList<>();

			// add mode
			subPt.data.add(new SinglePoint("AnalogBase", "JEBS-Pump-Mode", "Value"));

			// add pressure
			for (int i = 0; i < preTagNames.length; i++) {
				subPt.data.add(new SinglePoint("AnalogBase", preTagNames[i], "Value"));
			}

			// add pump
			for (int pumpNo = 0; pumpNo < 6; pumpNo++) {
				for (int input = 0; input < 4; input++) {
					if (input < 3) {
						String tagName = pumpTagNames[input].substring(0, 6) + (pumpNo + 1)
								+ pumpTagNames[input].substring(7);
						subPt.data.add(new SinglePoint("DigitalBase", tagName, "Status"));
					} else {
						String tagName = pumpTagNames[input].substring(0, 6) + (pumpNo + 1)
								+ pumpTagNames[input].substring(7);
						subPt.data.add(new SinglePoint("AnalogBase", tagName, "Value"));
					}
				}
			}
			// to json string
			String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(subPt);
			byte[] jsonInBytes = jsonInString.getBytes();
			//TraceOut(jsonInString);
			client.publish("Interesting", qos, jsonInBytes);

			return true;
		} catch (MqttException e) {
			e.printStackTrace();
			return false;
		} catch (Throwable e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean request(Data data) {

		// dataReqMode = data;
		boolean bRet = false;
		switch (data) {
		case Mode:
			bRet = selectPt("AnalogBase", "JEBS-Pump-Mode", "Value");
			if (bRet)
				mode = (int) selectResPt.value;
			break;
		case Pressure:
			for (int i = 0; i < preTagNames.length; i++) {
				bRet = selectPt("AnalogBase", preTagNames[i], "Value");
				if (bRet)
					pressures.add(new Double(selectResPt.value));
				else
					break;
			}
			break;
		case Pump:
			for (int pumpNo = 0; pumpNo < 6; pumpNo++) {
				pumps.add(new Pump(pumpNo));
				Pump pump = pumps.get(pumpNo);
				// pmode
				for (int input = 0; input < 4; input++) {
					if (input < 3) {
						String tagName = pumpTagNames[input].substring(0, 6) + (pumpNo + 1)
								+ pumpTagNames[input].substring(7);
						bRet = selectPt("DigitalBase", tagName, "Status");
						if (bRet) {

							if (input == 0) {
								if (selectResPt.value == 0) {
									pump.mode = PMode.Local;
								} else
									pump.mode = PMode.Auto;
							} else if (input == 1) {
								if (selectResPt.value == 0) {
									pump.running = false;
								} else
									pump.running = true;
							} else if (input == 2) {
								if (selectResPt.value == 0) {
									pump.trip = false;
								} else
									pump.trip = true;
							}

						} else
							break;
					} else {
						String tagName = pumpTagNames[input].substring(0, 6) + (pumpNo + 1)
								+ pumpTagNames[input].substring(7);
						bRet = selectPt("AnalogBase", tagName, "Value");
						if (bRet) {
							pump.speed = selectResPt.value;
						} else
							break;

					}
				}
				if (!bRet)
					break;
			}
		default:
			break;
		}

		return bRet;
	}

//	public void TraceIn(String str) {
//		System.out.println("[" + System.currentTimeMillis() + "]" + "Rev: " + str);
//	}
//
//	public void TraceOut(String str) {
//		System.out.println("[" + System.currentTimeMillis() + "]" + "Sent: " + str);
//	}

}
