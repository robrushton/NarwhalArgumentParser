
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
    
    /**
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param d
     */
    public void setDescription(String d) {
        description = d;
    }
    
    /**
     *
     * @return
     */
    public int getNumberOfValues() {
        return numberOfValues;
    }

    /**
     *
     * @param n
     */
    public void setNumberOfValues(int n) {
        numberOfValues = n;
    }
    
    /**
     *
     * @return
     */
    public List<String> getValueListAsString() {
        return value;
    }
    
    /**
     *
     * @param <T>
     * @return
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
    
    /**
     *
     * @param n
     * @return
     */
    public String getValue(int n) {
        return value.get(n);
    }

    /**
     *
     * @param v
     */
    public void setValue(String v) {
        value.add(v);
    }
}
