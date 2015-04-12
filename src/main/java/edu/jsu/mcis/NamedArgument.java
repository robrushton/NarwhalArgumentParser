
package edu.jsu.mcis;

import edu.jsu.mcis.ArgumentParser.Datatype;

/**
 *
 * @author Narwhalians
 */
public class NamedArgument extends Argument{
    private String nickname;
    private boolean required;
    private int group;
    private String value;
    public NamedArgument() {
        nickname = "";
        required = false;
        dataType = Datatype.STRING;
        group = 0;
    }
    
    /**
     *
     * @param v the value of the Named Argument
     */
    public void setValue(String v) {
        value = v;
    }
    
    /**
     *
     * @return the value of the Named Argument
     */
    public String getValue() {
        return value;
    }
    
    /**
     *
     * @param n the nickname of the Named Argument
     */
    public void setNickname(String n) {
        nickname = n;
    }
    
    /**
     *
     * @return the nickname of the Named Argument
     */
    public String getNickname() {
        return nickname;
    }

    /**
     *
     * @return whether the Named Argument is required
     */
    public boolean getRequired() {
        return required;
    }

    /**
     *
     * @param r set whether the Named Argument is required
     */
    public void setRequired(boolean r) {
        required = r;
    }
    
    /**
     *
     * @param n set the mutually exclusive group number of the Named Argument
     */
    public void setGroup(int n) {
        group = n;
    }
    
    /**
     *
     * @return The mutually exclusive group number of the Named Argument
     */
    public int getGroup() {
        return group;
    }
}
