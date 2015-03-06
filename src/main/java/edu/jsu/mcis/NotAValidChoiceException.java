
package edu.jsu.mcis;

public class NotAValidChoiceException extends RuntimeException{
    public NotAValidChoiceException(String msg){
        super(msg);
    }
}
