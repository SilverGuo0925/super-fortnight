package com.sample;

//import java.util.*;
//import org.kie.api.KieServices;
//import org.kie.api.runtime.KieContainer;
//import org.kie.api.runtime.KieSession;
//
//import com.pub.pojo.para.DataPoint;
//import com.pub.pojo.para.*;
//import java.util.HashMap;
//import java.util.Map;

//import java.io.PrintStream;
//import java.io.FileOutputStream;
/**
 * This is a sample class to launch a rule.
 */
public  class EntryPoint  {
	
	public static final void main(String[] args) {
	
		try {
//			PrintStream out = new PrintStream(new FileOutputStream("C:\\WillowLynx\\scada\\project\\errlog\\app\\pcc_log.txt"));
//			System.setOut(out);
//			
			
			Drools drools =new Drools();
			drools.run();
	
		} catch (Throwable t) {
			t.printStackTrace();
		}

	}

}
