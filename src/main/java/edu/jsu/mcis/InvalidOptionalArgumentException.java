package edu.jsu.mcis;

import java.io.*;

public class InvalidOptionalArgumentException extends RuntimeException {
        public InvalidOptionalArgumentException(String msg){
            super(msg);
        }
    }
