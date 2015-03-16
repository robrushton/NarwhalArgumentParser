
package edu.jsu.mcis;

import javax.xml.bind.annotation.XmlRootElement;


public class PositionalArgument extends Argument{
    private String description;
    public PositionalArgument() {
        description = "";
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String d) {
        description = d;
    }
}
