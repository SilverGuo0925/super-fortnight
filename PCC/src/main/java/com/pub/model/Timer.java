package com.pub.model;

import java.util.Date;
public class Timer
{
    private Date startTime;
   // private Date stopTime;
    private boolean m_bRunning = false;
    public boolean isBegin()
    {
        return m_bRunning;
    }
    public void Begin()
    {
        this.startTime = new Date();
        this.m_bRunning = true;
    }
    public void Reset()
    {   
    	//this.stopTime =new Date();
        this.m_bRunning = false;
    }
    // elaspsed time in milliseconds
    public long GetElapsedTime()
    {
        long dbTotalTime = 0;
        if (m_bRunning)
        {
            long interval;
            Date temp=new Date();
            interval    = temp.getTime() - startTime.getTime();
            dbTotalTime = interval;
        }
        return dbTotalTime;
    }
    
}
