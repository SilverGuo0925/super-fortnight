package com.pub.pojo.fact;

public class PreStart {
	public double A1;
	public double A2;
	public double N_Start;
	public double N_Rated;
	public double dpTol;
	public double RH;
	public int maxRunPump;
	public double accelRate;
	public double decelRate;
	public int leadPump;
	public double dutyCycle;
	public double logInterval;
	public double expResTime;
	public int filterValue;
	public double reqTP;
	public double tpDump;

	public PreStart() {
		A1 = 29;// m
		A2 = 17.5;
		N_Start = 370;// rpm
		N_Rated = 740;
		dpTol = 0.5;// m
		RH = 1;//m
		maxRunPump = 3;
		accelRate = 0;
		decelRate = 0;
		leadPump = 1;
		dutyCycle = 24;// hours
		logInterval = 1;// min
		expResTime = 5;// min
		filterValue = 10;
		reqTP = 40;
		tpDump = 0.2;
	}

	public double getA1() {
		return A1;
	}

	public void setA1(double value) {
		this.A1 = value;
	}

	public double getA2() {
		return A2;
	}

	public void setA2(double value) {
		this.A2 = value;
	}

	public double getN_Start() {
		return N_Start;
	}

	public void setN_Start(double value) {
		this.N_Start = value;
	}

	public double getN_Rated() {
		return N_Rated;
	}

	public void setN_Rated(double value) {
		this.N_Rated = value;
	}

	public double getDPTolerance() {
		return dpTol;
	}

	public void setDPTolerance(double value) {
		this.dpTol = value;
	}

	public double getRH() {
		return RH;
	}

	public void setRH(double value) {
		this.RH = value;
	}

	public int getMaxRunPump() {
		return maxRunPump;
	}

	public void setMaxRunPump(int value) {
		this.maxRunPump = value;
	}

	public double getAccelRate() {
		return accelRate;
	}

	public void setAccelRate(double value) {
		this.accelRate = value;
	}

	public double getDecelRate() {
		return decelRate;
	}

	public void setDecelRate(double value) {
		this.decelRate = value;
	}

	public int getLeadPump() {
		return leadPump;
	}

	public void setLeadPump(int value) {
		this.leadPump = value;
	}

	public double getDutyCycleInterval() {
		return dutyCycle;
	}

	public void setDutyCycleInterval(double value) {
		this.dutyCycle = value;
	}

	public double getLogInterval() {
		return logInterval;
	}

	public void setLogInterval(double value) {
		this.logInterval = value;
	}

	public double getExpResTime() {
		return expResTime;
	}

	public void setExpResTime(double value) {
		this.expResTime = value;
	}

	public int getFilterValue() {
		return filterValue;
	}

	public void setFilterValue(int value) {
		this.filterValue = value;
	}

	public double getReqTP() {
		return reqTP;
	}

	public void setReqTP(double value) {
		this.reqTP = value;
	}

	public double getTPDumping() {
		return tpDump;
	}

	public void setTPDumping(double value) {
		this.tpDump = value;
	}

}