
package edu.jsu.mcis;

import edu.jsu.mcis.ArgumentParser.Datatype;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class NamedArgument extends Argument{
    private String nickname;
    private boolean required;
    private boolean wasEntered;
    public NamedArgument() {
        nickname = "";
        required = false;
        wasEntered = false;
        dataType = Datatype.STRING;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String n) {
        nickname = n;
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
