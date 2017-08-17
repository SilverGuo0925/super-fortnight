package com.pub.pojo.para;

public class Basic {
	public double A3;
	public double A4;
	public double H;
	public double Nmax;
	public double Nmin;
  //  public String preFiredRule;
 //   public int dutyPumpNo;
	public boolean isPSUpdated;
	public boolean isPumpModeUpdated;
	public boolean isPumpLeadDutyOffline;
	public Basic() {
		A3=0;
		A4=0;
		H=0;
		Nmax=0;
		Nmin=0;
	//	preFiredRule="";
	}

	public double getA3() {
		return A3;
	}

	public void setA3(double value) {
		this.A3 = value;
	}

	public double getA4() {
		return A4;
	}

	public void setA4(double value) {
		this.A4 = value;
	}

	public double getH() {
		return H;
	}

	public void setH(double value) {
		this.H = value;
	}

	public double getNmax() {
		return Nmax;
	}

	public void setNmax(double value) {
		this.Nmax = value;
	}

	public double getNmin() {
		return Nmin;
	}

	public void setNmin(double value) {
		this.Nmin = value;
	}

	public boolean getIsPSUpdated() {
		return isPSUpdated;
	}

	public void setIsPSUpdated(boolean bUpdated) {
		this.isPSUpdated = bUpdated;
	}
	public boolean getIsPumpModeUpdated() {
		return isPumpModeUpdated;
	}

	public void setIsPumpModeUpdated(boolean bUpdated) {
		this.isPumpModeUpdated = bUpdated;
	}
	
	public boolean getIsPumpLeadDutyOffline() {
		return isPumpLeadDutyOffline;
	}

	public void setIsPumpLeadDutyOffline(boolean bUpdated) {
		this.isPumpLeadDutyOffline = bUpdated;
	}
	
//	public int getDutyPumpNo() {
//		return dutyPumpNo;
//	}
//
//	public void setDutyPumpNo(int dutyPumpNo) {
//		this.dutyPumpNo =dutyPumpNo;
//	}
}
