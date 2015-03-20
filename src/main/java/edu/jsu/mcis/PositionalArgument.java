
package edu.jsu.mcis;

import java.util.*;
import javax.xml.bind.annotation.XmlRootElement;


public class PositionalArgument extends Argument{
    private String description;
    private int numberOfValues;
    private List<String> value;
    
    public PositionalArgument() {
        description = "";
        numberOfValues = 1;
        value = new ArrayList<>();
    }
    
    public String getDescription() {
        return description;
    }
    public void setDescription(String d) {
        description = d;
    }
    
    public int getNumberOfValues() {
        return numberOfValues;
    }
    public void setNumberOfValues(int n) {
        numberOfValues = n;
    }
    
    public List<String> getValueList() {
        return value;
    }
    
    public String getValue(int n) {
        return value.get(n);
    }

    public void setValue(String v) {
        value.add(v);
    }
}
