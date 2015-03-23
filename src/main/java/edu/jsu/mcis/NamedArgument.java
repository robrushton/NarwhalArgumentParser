
package edu.jsu.mcis;

import edu.jsu.mcis.ArgumentParser.Datatype;


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
    
    public void setValue(String v) {
        value = v;
    }
    
    public String getValue() {
        return value;
    }
    
    public void setNickname(String n) {
        nickname = n;
    }
    
    public String getNickname() {
        return nickname;
    }

    public boolean getRequired() {
        return required;
    }

    public void setRequired(boolean r) {
        required = r;
    }
    
    public void setGroup(int n) {
        group = n;
    }
    
    public int getGroup() {
        return group;
    }
}
