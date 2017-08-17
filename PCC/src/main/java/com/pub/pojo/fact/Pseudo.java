package com.pub.pojo.fact;

public class Pseudo {

	public int puiNextAction;
	public int puiDPInputOpt;
	
	public Pseudo(){
		puiNextAction=1;
		puiDPInputOpt=1;
	}
	public int getPUINextAction(){
		return puiNextAction;
	}
	
	public void setPUINextAction(int puiNA){
		this.puiNextAction=puiNA;
	}
	
	public int getPUIDPInputOpt(){
		return puiDPInputOpt;
	}
	
	public void setPUIDPInputOpt(int puiDPIO){
		this.puiDPInputOpt=puiDPIO;
	}
}
