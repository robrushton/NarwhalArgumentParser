
package edu.jsu.mcis;

public class RequiredNamedArgumentNotGivenException extends RuntimeException{
    public RequiredNamedArgumentNotGivenException(String msg){
        super(msg);
    }
}
