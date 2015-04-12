package edu.jsu.mcis;


import java.util.*;
import edu.jsu.mcis.ArgumentParser.Datatype;

/**
 *
 * @author Kane
 * @param <T>
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
     * @return
     */
    public List<String> getRestrictions() {
        return restrictions;
    }
    
    /**
     *
     * @param n
     */
    public void setName(String n) {
        name = n;
    }
    
    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }
    
    /**
     *
     * @return
     */
    public Datatype getDataType() {
        return dataType;
    }

    /**
     *
     * @param d
     */
    public void setDataType(Datatype d) {
        dataType = d;
    }
    
    /**
     *
     * @return
     */
    public String getDefaultValue() {
        return defaultValue;
    }
    
    /**
     *
     * @param s
     */
    public void setDefaultValue(String s) {
        defaultValue = s;
    }
}
