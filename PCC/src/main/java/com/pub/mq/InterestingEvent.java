package com.pub.mq;

//import com.pub.pojo.para.DataPoint;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public interface InterestingEvent {
	 // This is just a regular method so it can return something or
    // take arguments if you like.
    public void interestingEvent (String topic,MqttMessage message);
}
