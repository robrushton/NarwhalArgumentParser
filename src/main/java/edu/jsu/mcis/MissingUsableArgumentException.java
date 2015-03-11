package edu.jsu.mcis;

public class MissingUsableArgumentException extends RuntimeException {
        public MissingUsableArgumentException(String msg){
            super(msg);
        }
    }