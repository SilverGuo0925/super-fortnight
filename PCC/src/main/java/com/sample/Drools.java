package com.sample;

//import java.util.*;
//import java.util.concurrent.TimeUnit;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.EntryPoint;

import com.pub.mq.*;
import com.pub.pojo.para.*;
import com.pub.pojo.fact.*;
import com.pub.model.*;
//import com.pub.external.ManualResetEvent;
//import org.eclipse.paho.client.mqttv3.MqttException;

public class Drools implements ReceiveEvent, DroolsService {

	PreStart preStart;
	String[] names;
	Basic basic;

	// 6pumps
	Pump[] pumps, pumpsMir;
	Pressure[] pressures, pressuresMir;
	Mode mode, modeMir;
	Mqtt3Pcc socket;
	// Pipeline
	Pipeline pipeline;
	// session
	KieSession session;
	private EntryPoint initStream;
	// private EntryPoint updateStream;
	// event
	// private ManualResetEvent eventHandle = null;

	public boolean m_bMode, m_bPressure, m_bPumpMode,m_bPumpRunning,m_bPumpTrip,m_bPumpSpeed;
	public boolean m_bDP, m_bPD, m_bPS;

	public Drools() {
		// preStart instance
		preStart = new PreStart();
		basic = new Basic();
		// name2DataPoint instance
		names = new String[] { "DP", "PD", "PS", "P1", "P2", "P3", "P4", "P5", "P6" };

		// eventHandle = new ManualResetEvent(false);

		// interesting data
		pumps = new Pump[6];
		pressures = new Pressure[3];
		for (int i = 0; i < pumps.length; i++) {
			pumps[i] = new Pump(i + 1);
		}

		for (int i = 0; i < pressures.length; i++) {
			pressures[i] = new Pressure(names[i]);

		}
		mode = new Mode();

		// interesting data Mirror
		m_bMode = false;
		m_bPressure = false;
		m_bPumpMode = false;
		m_bPumpRunning=false;
		m_bPumpTrip=false;
		m_bPumpSpeed=false;

		pumpsMir = new Pump[6];
		pressuresMir = new Pressure[3];
		for (int i = 0; i < pumpsMir.length; i++) {
			pumpsMir[i] = new Pump(i + 1);
		}

		for (int i = 0; i < pressuresMir.length; i++) {
			pressuresMir[i] = new Pressure(names[i]);

		}
		modeMir = new Mode();

	}

	public boolean initFacts() {

		try { // 1st step get PreStart parameter
				// ArrayList<Double> tempDP = new ArrayList<>();

			socket = new Mqtt3Pcc(this);

			// mode
			boolean bRet = socket.request(Data.Mode);
			if (!bRet) {
				System.out.println("socket.Request(Mode): " + bRet);
				return false;
			}
			mode.opMode = socket.mode;
			modeMir.opMode = mode.opMode;
			// pressure
			bRet = socket.request(Data.Pressure);
			if (!bRet) {
				System.out.println("socket.Request(Pressure): " + bRet);
				return false;
			}
			for (int i = 0; i < pressures.length; i++) {
				pressures[i].value = (double) socket.pressures.get(i);
				pressuresMir[i].value = pressures[i].value;
			}
			// // temp testing
			//
			// pressures[0].value = 30;
			// pressures[1].value =30;
			// pressures[2].value =5;
			//
			//
			// pump
			bRet = socket.request(Data.Pump);
			if (!bRet) {
				System.out.println("socket.Request(Pump): " + bRet);
				return false;
			}

			for (int pumpNo = 0; pumpNo < 6; pumpNo++) {
				Pump pump = socket.pumps.get(pumpNo);
				// pmode
				for (int input = 0; input < 4; input++) {
					if (input == 0) {
						pumps[pumpNo].mode = pump.mode;
						pumpsMir[pumpNo].mode = pumps[pumpNo].mode;
					} else if (input == 1) {
						pumps[pumpNo].running = pump.running;
						pumpsMir[pumpNo].running = pumps[pumpNo].running;
					} else if (input == 2) {
						pumps[pumpNo].trip = pump.trip;
						pumpsMir[pumpNo].trip = pumps[pumpNo].trip;
					} else {
						pumps[pumpNo].speed = pump.speed;
						pumpsMir[pumpNo].speed = pumps[pumpNo].speed;
					}
				}
			}

			pipeline = new Pipeline(pumps);
			// Pseudo points

			// subscribe points
			bRet = socket.subscribe();
			if (!bRet) {
				System.out.println("socket.subscribe(): " + bRet);
				return false;
			}
			return true;
		} catch (Throwable t) {
			t.printStackTrace();
			return false;
		}

	}

	public void receiveEvent(SubResPoint subResPt) {
		// Wow! Something really interesting must have occurred!
		// Do something...
		switch (subResPt.tagName) {
		case "JEBS-Pump-Mode":
			m_bMode = true;
			modeMir.opMode = (int) subResPt.value;
			break;
		case "JEBS-DPSET-MAIN-1":
			m_bDP = true;
			pressuresMir[0].value = subResPt.value;
			break;
		case "JEBS-DP-Main-1":
			m_bPD = true;
			pressuresMir[1].value = subResPt.value;
			break;
		case "JEBS-SP-Main-1":
			m_bPS = true;
			pressuresMir[2].value = subResPt.value;
			break;
		default:
			String str = subResPt.tagName;
			if (str.contains("JEBS-P")) {
				String aa = str.substring(6, 7);
				int pumpNo = Integer.parseInt(aa);
				int index = pumpNo > 0 ? pumpNo - 1 : 0;
				str = str.substring(0, 6) + str.substring(7);
				switch (str) {
				case "JEBS-P-L*T":
					m_bPumpMode = true;
					if (subResPt.value == 0)
						pumpsMir[index].mode = PMode.Local;
					else
						pumpsMir[index].mode = PMode.Auto;
					break;
				case "JEBS-P-Status":
					m_bPumpRunning = true;
					if (subResPt.value == 0)
						pumpsMir[index].running = false;
					else
						pumpsMir[index].running = true;
					break;
				case "JEBS-P-Trip":
					m_bPumpTrip = true;
					if (subResPt.value == 0)
						pumpsMir[index].trip = false;
					else
						pumpsMir[index].trip = true;
					break;

				case "JEBS-P-SPEED":
					m_bPumpSpeed = true;
					pumpsMir[index].speed = subResPt.value;
					break;
				}
			}

			break;

		}

	}

	public boolean isDataUpdated(Data data) {
		boolean m_bUpdated = false;
		switch (data) {
		case Mode:
			m_bUpdated = m_bMode;
			break;
		case DP:
			m_bUpdated = m_bDP;
			break;
		case PD:
			m_bUpdated = m_bPD;
			break;
		case PS:
			m_bUpdated = m_bPS;
			break;
		case PumpMode:
			m_bUpdated = m_bPumpMode;
			break;
		case PumpRunning:
			m_bUpdated = m_bPumpRunning;
			break;
		case PumpTrip:
			m_bUpdated = m_bPumpTrip;
			break;
		case PumpSpeed:
			m_bUpdated = m_bPumpSpeed;
			break;
		default:
			break;
		}
		return m_bUpdated;
	}

	public boolean isDataUpdated(Data data, double offset) {

		boolean m_bUpdated = false;
		switch (data) {
		case PS:
			if (Math.abs(pressuresMir[2].value - pressures[2].value) >= offset)
				m_bUpdated = m_bPS;
			break;
		default:
			break;

		}
		return m_bUpdated;
	}

	public void updateData(Data data) {

		switch (data) {
		case Mode:
			mode.opMode = modeMir.opMode;
			m_bMode = false;
			break;
		case DP:
			pressures[0].value = pressuresMir[0].value;
			m_bDP = false;
			break;
		case PD:
			pressures[1].value = pressuresMir[1].value;
			m_bPD = false;
			break;
		case PS:
			pressures[2].value = pressuresMir[2].value;
			m_bPS = false;
			break;
		case PumpMode:
			for (int i = 0; i < pumps.length; i++) {
				pumps[i].mode = pumpsMir[i].mode;
				if(pumps[i].mode ==PMode.Local)
					pumps[i].setOffline(true);
			}
			m_bPumpMode = false;
			break;
		case PumpRunning:
			for (int i = 0; i < pumps.length; i++) {
				pumps[i].running = pumpsMir[i].running;
			}
			m_bPumpRunning = false;
			break;
		case PumpTrip:
			for (int i = 0; i < pumps.length; i++) {
				pumps[i].trip = pumpsMir[i].trip;
			}
			m_bPumpTrip = false;
			break;
		case PumpSpeed:
			for (int i = 0; i < pumps.length; i++) {
				pumps[i].speed = pumpsMir[i].speed;
			}
			m_bPumpSpeed = false;
			break;
		default:
			break;
		}
	}

	public boolean move(int nPumpNo) {
		try {

			int index = nPumpNo > 0 ? nPumpNo - 1 : 0;
			// simulate speed equal to cmdspeed - only for test
			// pumps[index].speed = pumps[index].cmdSpeed;
			socket.update(pumps[index]);
			while (true) {

				if (pumpsMir[index].speed == pumps[index].cmdSpeed) {
					System.out.println("pumpsMir[" + index + "].speed " + pumpsMir[index].speed);
					return true;
				}
				Thread.sleep(100);
			}

		} catch (Throwable t) {
			t.printStackTrace();
			return false;
		}
	}

	// public boolean move(int nPumpNo, double dSpeed) {
	// try {
	//
	// int no = nPumpNo > 0 ? nPumpNo - 1 : 0;
	// pumps[no].cmdSpeed = dSpeed;
	// // send message to rtdb
	//
	// // wait pump speeding done
	// return move(nPumpNo);
	//
	// } catch (Throwable t) {
	// t.printStackTrace();
	// return false;
	// }
	// }

//	public boolean move(Pump pump) {
//		return socket.update(pump);
//	}

	public void run() {
		try {
			// initialization
			boolean bRet = initFacts();
			if (!bRet) {
				return;
			}
			// init Session
			this.session = createSession();
			this.initStream = this.session.getEntryPoint("Init stream");
			// ajustStream = session.getEntryPoint("Ajust stream");
			InitTick it = new InitTick();
			it.timestamp = System.currentTimeMillis();
			initStream.insert(it);
			this.session.getAgenda().getAgendaGroup("evaluation").setFocus();
			this.session.fireAllRules();

			// int a=3;

			// create a thread
			// (new Thread(new MonitorPD())).start();

		} catch (Throwable t) {
			t.printStackTrace();
		}

	}

	// private void trace(String text) {
	// System.out.println("[" + System.currentTimeMillis() + "]" + text);
	// }

	private KieSession createSession() {
		KieServices ks = KieServices.Factory.get();
		KieContainer kc = ks.getKieClasspathContainer();
		session = kc.newKieSession("ksession-rules");

		session.setGlobal("services", this);

		// for (Pressure pressure : pressures) {
		// session.insert(pressure);
		// }
		// for (Pump pump : pumps) {
		// session.insert(pump);
		// }
		for (int i = 0; i < pressures.length; i++)
			session.insert(pressures[i]);

		session.insert(pipeline);
		session.insert(new Pseudo());

		session.insert(preStart);
		session.insert(basic);
		session.insert(new Timestamp());

		session.fireAllRules();
		return session;
	}
}
