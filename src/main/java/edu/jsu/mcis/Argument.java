
package edu.jsu.mcis;

import edu.jsu.mcis.ArgumentParser.Datatype;

public class Argument {
    protected String value;
    protected Datatype dataType;
    public Argument() {
        value = "";
    }

    public String getValue() {
        return value;
    }

    public void setValue(String v) {
        value = v;
    }

    public Datatype getDataType() {
        return dataType;
    }

    public void setDataType(Datatype d) {
        dataType = d;
    }
}
