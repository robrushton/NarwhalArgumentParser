package edu.jsu.mcis;

import java.util.ArrayList;

public class ArgumentParser {
    private ArrayList<String> arguments = new ArrayList<String>();
    public void addArg(String arg) {
        arguments.add(arg);
    }
    
    public String getArg(int n) {
        return arguments.get(n);
    }
public boolean addArg(){
		return false;
	}
		

}