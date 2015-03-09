
package edu.jsu.mcis;

import edu.jsu.mcis.ArgumentParser.Datatype;

public class PositionalArgument extends Argument{
    public Datatype dataType;
    public String myDescription;
    public PositionalArgument() {
        myDescription = "";
    }
}
