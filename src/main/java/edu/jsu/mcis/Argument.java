package edu.jsu.mcis;


import java.util.*;
import javax.xml.bind.annotation.*;
import edu.jsu.mcis.ArgumentParser.Datatype;

@XmlRootElement(name = "arguments")
public class Argument {
    protected String name;
    protected List<Argument> argument;
    protected String value;
    protected Datatype dataType;
    protected List<String> restrictions = new ArrayList<String>();
    
    public Argument(){
    	value = "";
        name = "";
    }
    
    public void setArgument(List<Argument> myArgs) {
        this.argument = myArgs;
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
            
    public List<Argument> getArguments(){
    	return argument;
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
