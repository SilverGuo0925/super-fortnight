package com.sample;

import com.pub.mq.Data;

public interface DroolsService {
	 // This is just a regular method so it can return something or
    // take arguments if you like.
    public boolean move(int nPumpNo);
  //  public boolean move(int nPumpNo,double dSpeed);
    public boolean isDataUpdated(Data data);
    public boolean isDataUpdated(Data data,double offset);
    public void updateData(Data data);
}
