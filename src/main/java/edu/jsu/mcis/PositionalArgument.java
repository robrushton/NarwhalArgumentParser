
package edu.jsu.mcis;


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
