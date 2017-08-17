package com.pub.mq;

import java.util.*;

public class SubPoint {

	public String clientID;

	public ArrayList<SinglePoint> data;

}

class SinglePoint {
	public String table;
	public String tagName;
	public String column;

	public SinglePoint(String table, String tagName, String column) {
		this.table = table;
		this.tagName = tagName;
		this.column = column;

	}
}


