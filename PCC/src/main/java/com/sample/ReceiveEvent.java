package com.sample;

import com.pub.mq.*;
public interface ReceiveEvent {
	 // This is just a regular method so it can return something or
    // take arguments if you like.
    public void receiveEvent (SubResPoint subResPt);
}
