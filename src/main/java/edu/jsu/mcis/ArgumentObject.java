package edu.jsu.mcis;

import java.util.*;

public class ArgumentObject {
    
    private String description;
    private int intValue;
    private float floatValue;
    private String stringValue;
    private boolean booleanValue;
    
    
    private void ArgumentObject() {
        description = null;
        //intValue;
        //floatValue;
        stringValue = null;
        //booleanValue;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String n) {
        description = n;
    }
    
    public int getIntValue() {
        return intValue;
    }
    
    public void setIntValue(int n) {
        intValue = n;
    }
    
    public float getFloatValue() {
        return floatValue;
    }
    
    public void setDescription(float n) {
        floatValue = n;
    }
    
    public String getStringValue() {
        return stringValue;
    }
    
    public void setStringValue(String n) {
        stringValue = n;
    }
    
    public boolean getBooleanValue() {
        return booleanValue;
    }
    
    public void setBooleanValue(boolean n) {
        booleanValue = n;
    }
}