package com.pub.pojo.fact;

import com.pub.pojo.fact.Pump;

public class Pipeline {
	public Pump[] pumps;
    //public boolean bIs
	
	public Pipeline(Pump[] pumps) {
		this.pumps = pumps;
	}
	public Pump[] getPumps(){
		return this.pumps ;
	}
	
//	public Pump nextIndexPump(Pump pump){
//		
//	}
	public int getNextOnlinePumpNo(int pumpNo){
		
		for(int i=0;i<pumps.length ;i++){
		    pumpNo=pumpNo==pumps.length?1:pumpNo+1;
		    if(pumps[pumpNo-1].offline ==false)
		    	return pumpNo;
		}
		return -1;
	}
	public int getPrevOnlinePumpNo(int pumpNo){
		
		for(int i=0;i<pumps.length ;i++){
		    pumpNo=pumpNo==1?pumps.length:pumpNo-1;
		    if(pumps[pumpNo-1].offline ==false)
		    	return pumpNo;
		}
		return -1;
	}
    public int getOnlinePumpNo(int pumpNo){
		
		for(int i=0;i<pumps.length ;i++){
		    if(pumps[pumpNo-1].offline ==false)
		    	return pumpNo;
		    
		    pumpNo=pumpNo==pumps.length?1:pumpNo+1;
		}
		return -1;
	}
}
