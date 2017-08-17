package com.pub.pojo.fact;

public class Timestamp {

	public long currentTimeMillis;
	
	public long getCurrentTimeMillis(){
	//	currentTimeMillis=System.currentTimeMillis();
		return this.currentTimeMillis ;
	}
public void setCurrentTimeMillis(long CurrentTimeMillis){
	this.currentTimeMillis =CurrentTimeMillis;
}
}
