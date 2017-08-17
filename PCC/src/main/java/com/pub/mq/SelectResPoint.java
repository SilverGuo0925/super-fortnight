package com.pub.mq;

public class SelectResPoint {

    public String clientID;
    public int index;
    public double value;

    
    public String getClientID() {
        return clientID;
    }

    public void setClientID(String ClientID) {
        this.clientID = ClientID;
    }
    
    public int getIndex() {
        return index;
    }

    public void setIndex(int Index) {
        this.index = Index;
    }
    
    public double getValue() {
        return value;
    }

    public void setValue(double Value) {
        this.value = Value;
    }
  
//    @Override
//    public String toString() {
//       return "DataPoint: { Name:" + getName() + " Value:" +getValue()+" Date:"+getDate()+" }";
//    }
}
