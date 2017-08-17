package com.pub.pojo.fact;

import java.util.Date;

//import java.beans.PropertyChangeListener;
//import java.beans.PropertyChangeSupport;

public class Pressure {
 public String name;
 public double value;
 public Date srcTime;
 
 //private final PropertyChangeSupport changes  = new PropertyChangeSupport( this );

 public Pressure(String name){
	 this.name=name;
	 value=0;
	 srcTime=new Date();
 }
 
  public String getName(){
	 return this.name;
 }
 
 public void setName(String name){
	 this.name=name;
//	 String oldValue = this.name;
//     this.name=name;
//     this.changes.firePropertyChange( "name",
//                                      oldValue,
//                                      name );
 }
 
  public double getValue(){
	 return this.value ;
 }

 public void setValue(double newValue){
	 this.value =newValue;
//	  double oldValue = this.value;
//      this.value=newValue;
//      this.changes.firePropertyChange( "value",
//                                       oldValue,
//                                       newValue );
 }
 
 public Date getSrcTime(){
	 return this.srcTime ;
 }
 public void setSrcTime(Date srcTime){
	 this.srcTime=srcTime;
	 
//	 Date oldValue = this.srcTime;
//     this.srcTime =srcTime;
//     this.changes.firePropertyChange( "srcTime",
//                                      oldValue,
//                                      srcTime );
 }
 

}
