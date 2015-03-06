package edu.jsu.mcis;

import java.io.*;

public class InvalidNamedArgumentException extends RuntimeException {
        public InvalidNamedArgumentException(String msg){
            super(msg);
        }
    }
