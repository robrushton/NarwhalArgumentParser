package edu.jsu.mcis;

import java.io.*;

public class PositionalArgumentException extends RuntimeException {
    public PositionalArgumentException(String msg){
        super(msg);
    }
}
