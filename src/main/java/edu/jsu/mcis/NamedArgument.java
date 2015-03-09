
package edu.jsu.mcis;

public class NamedArgument extends Argument{
    public String nickname;
    public boolean required;
    public boolean wasEntered;
    public NamedArgument() {
        nickname = "";
        required = false;
        wasEntered = false;
    }
}
