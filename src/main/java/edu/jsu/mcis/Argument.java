package edu.jsu.mcis;

import java.util.*;
import javax.xml.bind.annotation.*;
import edu.jsu.mcis.ArgumentParser.Datatype;

@XmlRootElement(name = "arguments")
public class Argument {
	
	public List<Argument> argument;
    protected String value;
    protected Datatype dataType;
    public String name;
	public List<Object> restrictions = new ArrayList<Object>();
	
    public Argument() {
        value = "";
    }
	
	public boolean checkRestrictions(){
		for (int i = 0; i < restrictions.size(); i++){
			if (restrictions.get(i) == value){
				return true;
			}
		}
		this.value = "";
		return false;	
	}
    public List<Argument> getArguments(){
    	return argument;
    }
    
    public Argument(List<Argument> myArgs) {
        this.argument = myArgs;
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