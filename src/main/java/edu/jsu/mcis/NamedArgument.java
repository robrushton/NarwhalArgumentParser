
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
    
    /** Sets the value of the Named Argument
     *
     * @param v the value of the Named Argument
     */
    public void setValue(String v) {
        value = v;
    }
    
    /** Gets the value of the Named Argument
     *
     * @return the value of the Named Argument
     */
    public String getValue() {
        return value;
    }
    
    /** Sets the nickname of the Named Argument
     *
     * @param n the nickname of the Named Argument
     */
    public void setNickname(String n) {
        nickname = n;
    }
    
    /** Gets the nickname of the Named Argument
     *
     * @return the nickname of the Named Argument
     */
    public String getNickname() {
        return nickname;
    }

    /** Gets the whether the Named Argument is required or not
     *
     * @return whether the Named Argument is required
     */
    public boolean getRequired() {
        return required;
    }

    /** Sets the whether the Named Argument is required or not
     *
     * @param r set whether the Named Argument is required
     */
    public void setRequired(boolean r) {
        required = r;
    }
    
    /** Set the group number of the Named Argument
     *
     * @param n set the mutually exclusive group number of the Named Argument
     */
    public void setGroup(int n) {
        group = n;
    }
    
    /** Get the group number of the Named Argument
     *
     * @return The mutually exclusive group number of the Named Argument
     */
    public int getGroup() {
        return group;
    }
}
