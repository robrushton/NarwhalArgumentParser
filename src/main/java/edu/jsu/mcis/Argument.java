package edu.jsu.mcis;

import java.util.List;
import javax.xml.bind.annotation.*;
import edu.jsu.mcis.ArgumentParser.Datatype;

@XmlRootElement(name = "arguments")
public class Argument {
    private String name;
    //private Arguments as;
    public List<Argument> argument;
	
    protected String value;
    protected Datatype dataType;
    
    public Argument(){
    	value = "";
        name = "";
    }
    
    //public Arguments getArguments(){
    //	return as;
    //}
    
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

