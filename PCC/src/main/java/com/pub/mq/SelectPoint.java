package com.pub.mq;

public class SelectPoint {

    public String clientID;
    public int index;
    public String table;
    public String tagName;
    public String column;

    
    public String getClientID() {
        return clientID;
    }

//    public void setClientID(String ClientID) {
//        this.ClientID = ClientID;
//    }
//    
    public int getIndex() {
        return index;
    }

//    public void setIndex(int Index) {
//        this.Index = Index;
//    }
//    
    public String getTable() {
        return table;
    }

//    public void setTable(String Table) {
//        this.Table = Table;
//    }
    public String getTagName() {
        return tagName;
    }

//    public void setTagName(String TagName) {
//        this.TagName = TagName;
//    }
    public String getColumn() {
        return column;
    }

//    public void setColumn(String Column) {
//        this.Column = Column;
//    }
//    
//    @Override
//    public String toString() {
//       return "DataPoint: { Name:" + getName() + " Value:" +getValue()+" Date:"+getDate()+" }";
//    }
}
