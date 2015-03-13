package edu.jsu.mcis;


import java.util.*;
import javax.xml.bind.annotation.*;
import edu.jsu.mcis.ArgumentParser.Datatype;

@XmlRootElement(name = "arguments")
public class Argument <T>{
    protected String name;
    protected List<Argument> argument;
    protected String value;
    protected Datatype dataType;
    protected List<T> restrictions = new ArrayList<T>();
    
    public Argument(){
    	value = "";
        name = "";
    }
    
    public void setArgument(List<Argument> myArgs) {
        this.argument = myArgs;
    }
    
    public List<Argument> getArguments(){
    	return argument;
    }
    
    public boolean checkRestrictions(String checkValue){
        for (T s : restrictions) {
            String str = s.toString();
            if (str.equals(checkValue)) {
                return true;
            }
        }
        return false;	
    }
    
    public List<T> getRestrictions() {
        return restrictions;
    }
    
    public void setName(String n) {
        name = n;
    }
    
    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String v) {
        value = v;
    }
    
    public Datatype getDataType() {
        return dataType;
    }

    public void setDataType(Datatype d) {
        dataType = d;
    }
}
