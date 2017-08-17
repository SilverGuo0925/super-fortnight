package com.pub.pojo.fact;

import java.util.Objects;

public class Pump {
	public int pumpNo;
	public PMode mode;
	public boolean running;
	public boolean trip;
	public double speed;
	public double cmdSpeed;
	public boolean cmdStart;
	public boolean cmdStop;
	public boolean lead;
	public boolean duty;
    public boolean offline;
    
	public Pump(int pumpNo) {
		this.pumpNo = pumpNo;
		mode = PMode.Auto;
		lead = false;
		duty = false;
		trip = false;
		running = false;
		speed = 0;
		cmdSpeed = 0;
		cmdStart = false;
		cmdStop = false;
		offline=false;
	}

	public int getPumpNo() {
		return pumpNo;
	}

	public void setPumpNo(int pumpNo) {
		this.pumpNo = pumpNo;
	}

	public PMode getPMode() {
		return mode;
	}

	public void setPMode(PMode mode) {
		this.mode = mode;
	}

	public boolean getRun() {
		return running;
	}

	public void setRun(boolean run) {
		this.running = run;
	}

	public boolean getTrip() {
		return trip;
	}

	public void setTrip(boolean trip) {
		this.trip = trip;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getCmdSpeed() {
		return cmdSpeed;
	}

	public void setCmdSpeed(double cmdSpeed) {
		this.cmdSpeed = cmdSpeed;
	}

	public boolean getCmdRun() {
		return cmdStart;
	}

	public void setCmdRun(boolean cmdRun) {
		this.cmdStart = cmdRun;
	}

	public boolean getCmdStop() {
		return cmdStop;
	}

	public void setCmdStop(boolean cmdRun) {
		this.cmdStop = cmdRun;
	}

	public boolean getLead() {
		return lead;
	}

	public void setLead(boolean lead) {
		this.lead = lead;
	}

	public boolean getDuty() {
		return duty;
	}

	public void setDuty(boolean duty) {
		this.duty = duty;
	}

	public boolean getOffline() {
		return offline;
	}

	public void setOffline(boolean offline) {
		this.offline = offline;
	}

	public boolean isOffline() {
		if (trip)
			return true;
		else if (mode == PMode.Local)
			return true;
		else
			return false;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Pump pump = (Pump) o;

		if (pumpNo != pump.pumpNo) {
			return false;
		}
		if (mode != pump.mode) {
			return false;
		}
		if (running != pump.running) {
			return false;
		}
		if (trip != pump.trip) {
			return false;
		}
		if (speed != pump.speed) {
			return false;
		}
		if (cmdSpeed != pump.cmdSpeed) {
			return false;
		}
		if (cmdStart != pump.cmdStart) {
			return false;
		}
		if (cmdStop != pump.cmdStop) {
			return false;
		}
		if (lead != pump.lead) {
			return false;
		}
		if (duty != pump.duty) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(pumpNo, mode, running, trip, speed, cmdSpeed, cmdStart, cmdStop, lead, duty);
	}
}
