
package edu.jsu.mcis;

import java.util.*;

/**
 *
 * @author Narwhalians
 */
public class PositionalArgument extends Argument{
    private String description;
    private int numberOfValues;
    private List<String> value;
    
    public PositionalArgument() {
        description = "";
        numberOfValues = 1;
        value = new ArrayList<>();
    }
    
    /** Get the description of the Positional Argument
     *
     * @return Positional Argument description
     */
    public String getDescription() {
        return description;
    }

    /** Set the description of the Named Argument
     *
     * @param d Positional Argument description
     */
    public void setDescription(String d) {
        description = d;
    }
    
    /** Get the arity of the Positional Argument
     *
     * @return the arity
     */
    public int getNumberOfValues() {
        return numberOfValues;
    }

    /** Set the arity of the Positional Argument
     *
     * @param n number of values
     */
    public void setNumberOfValues(int n) {
        numberOfValues = n;
    }
    
    /** Get the value of the Positional Argument as a list of strings 
     *
     * @return the value as a list of strings
     */
    public List<String> getValueListAsString() {
        return value;
    }
    
    /** Get the value of the Positional Argument as a list of the proper data type.
     *  If the list of values size is equal to 1 then returns it as a single value of the proper data type.
     *
     * @return value as a list or single value of proper data type
     */
    public <T> List<T> getValueList() {
        if (dataType == ArgumentParser.Datatype.STRING) {
            return (List<T>) value;
        } else if (dataType == ArgumentParser.Datatype.INT) {
            List<Integer> returnList = new ArrayList<>();
            for (String s : value) {
                returnList.add(new Integer(s));
            }
            return (List<T>) returnList;
        } else if (dataType == ArgumentParser.Datatype.FLOAT) {
            List<Float> returnList = new ArrayList<>();
            for (String s : value) {
                returnList.add(new Float(s));
            }
            return (List<T>) returnList;
        } else {
            List<Boolean> returnList = new ArrayList<>();
            for (String s : value) {
                returnList.add(Boolean.valueOf(s));
            }
            return (List<T>) returnList;
        }
    }
    
    /** returns a specific value at a particular index
     *
     * @param n index of value
     * @return value at index
     */
    public String getValue(int n) {
        return value.get(n);
    }

    /** add a value to the list of values in Positional Argument
     *
     * @param v value to add
     */
    public void setValue(String v) {
        value.add(v);
    }
}
