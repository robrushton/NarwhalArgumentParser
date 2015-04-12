
package edu.jsu.mcis;

import edu.jsu.mcis.ArgumentParser.Datatype;

/**
 *
 * @author Kane
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
     * @param v
     */
    public void setValue(String v) {
        value = v;
    }
    
    /**
     *
     * @return
     */
    public String getValue() {
        return value;
    }
    
    /**
     *
     * @param n
     */
    public void setNickname(String n) {
        nickname = n;
    }
    
    /**
     *
     * @return
     */
    public String getNickname() {
        return nickname;
    }

    /**
     *
     * @return
     */
    public boolean getRequired() {
        return required;
    }

    /**
     *
     * @param r
     */
    public void setRequired(boolean r) {
        required = r;
    }
    
    /**
     *
     * @param n
     */
    public void setGroup(int n) {
        group = n;
    }
    
    /**
     *
     * @return
     */
    public int getGroup() {
        return group;
    }
}
