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
    
    /**  Returns a list of all restrictions the Argument object contains
     *
     * @return  A list of Strings containing the restrictions
     */
    public List<String> getRestrictions() {
        return restrictions;
    }
    
    /** Sets the name of the Argument object
     *
     * @param name the name of the Argument
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /** Gets the name of the Argument object
     *
     * @return the name of the Argument
     */
    public String getName() {
        return name;
    }
    
    /** Gets the data type of the Argument object
     *
     * @return the data type
     */
    public Datatype getDataType() {
        return dataType;
    }

    /** sets the data type of the Argument object
     *
     * @param d the data type of the Argument
     */
    public void setDataType(Datatype d) {
        dataType = d;
    }
    
    /** gets the default value of the Argument object
     *
     * @return the default value
     */
    public String getDefaultValue() {
        return defaultValue;
    }
    
    /**  sets the default value of the Argument object
     *
     * @param s the default value of the Argument
     */
    public void setDefaultValue(String s) {
        defaultValue = s;
    }
}
