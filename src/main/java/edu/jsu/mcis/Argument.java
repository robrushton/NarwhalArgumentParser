package edu.jsu.mcis;


import java.util.*;
import edu.jsu.mcis.ArgumentParser.Datatype;

/**
 *
 * @author Narwhalians
 */
public class Argument <T>{
    protected String name;
    protected Datatype dataType;
    protected List<String> restrictions;
    protected String defaultValue;
    
    public Argument(){
        name = "";
        defaultValue = "";
        restrictions = new ArrayList<>();
    }
    
    /**
     *
     * @return  A list of Strings containing the restrictions
     */
    public List<String> getRestrictions() {
        return restrictions;
    }
    
    /**
     *
     * @param name the name of the Argument
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     *
     * @return the name of the Argument
     */
    public String getName() {
        return name;
    }
    
    /**
     *
     * @return the data type
     */
    public Datatype getDataType() {
        return dataType;
    }

    /**
     *
     * @param d the data type of the Argument
     */
    public void setDataType(Datatype d) {
        dataType = d;
    }
    
    /**
     *
     * @return the default value
     */
    public String getDefaultValue() {
        return defaultValue;
    }
    
    /**
     *
     * @param s the default value of the Argument
     */
    public void setDefaultValue(String s) {
        defaultValue = s;
    }
}
