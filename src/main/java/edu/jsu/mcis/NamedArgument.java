
package edu.jsu.mcis;

import edu.jsu.mcis.ArgumentParser.Datatype;
import javax.xml.bind.annotation.XmlRootElement;


public class NamedArgument extends Argument{
    private String defaultValue;
    private String nickname;
    private boolean required;
    private boolean wasEntered;
    public NamedArgument() {
        nickname = "";
        required = false;
        wasEntered = false;
        dataType = Datatype.STRING;
    }
    
    public void setDefaultValue(String d) {
        defaultValue = d;
    }
    
    public String getDefaultValue() {
        return defaultValue;
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

    public boolean getWasEntered() {
        return wasEntered;
    }

    public void setWasEntered(boolean w) {
        wasEntered = w;
    }
}
