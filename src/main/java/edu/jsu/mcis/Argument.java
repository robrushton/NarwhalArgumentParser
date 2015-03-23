package edu.jsu.mcis;


import java.util.*;
import edu.jsu.mcis.ArgumentParser.Datatype;

public class Argument <T>{
    protected String name;
    protected List<Argument> argument;
    protected Datatype dataType;
    protected List<String> restrictions = new ArrayList<String>();
    protected String defaultValue;
    
    public Argument(){
        name = "";
        defaultValue = "";
    }
    
    public void setArgument(List<Argument> myArgs) {
        this.argument = myArgs;
    }
    
    public List<Argument> getArguments(){
    	return argument;
    }
    
    public boolean checkRestrictions(String checkValue){
        for (String s : restrictions) {
            if (s.equals(checkValue)) {
                return true;
            }
        }
        return false;	
    }
    
    public List<String> getRestrictions() {
        return restrictions;
    }
    
    public void setName(String n) {
        name = n;
    }
    
    public String getName() {
        return name;
    }
    
    public Datatype getDataType() {
        return dataType;
    }

    public void setDataType(Datatype d) {
        dataType = d;
    }
    
    public String getDefaultValue() {
        return defaultValue;
    }
    
    public void setDefaultValue(String s) {
        defaultValue = s;
    }
}
