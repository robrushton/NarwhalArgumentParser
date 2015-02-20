package edu.jsu.mcis;

import java.util.*;


public class ArgumentParser { 
    private Map<String, Argument> myArgs = new HashMap<>();
    private ArrayList<String> keys = new ArrayList<>();
    private Map<String, String> nicknames = new HashMap<>();
    private String programDescription = "";
    private Map<String, Boolean> flags = new HashMap<>();
    
    public void parse(String[] userInput) {
        int count = 0;
        for (int i = 0; i < userInput.length; i++) {
            if (isLongOptionalArgument(userInput, i)) {
                if (myArgs.containsKey(userInput[i].substring(2))) {
                    setOptionalArgument(userInput, i);
                } else {
                        //throw new invalidILongArgument();
                }
                i++;
            } else if (isShortOptionalArgument(userInput, i)) {
                if (isHelpArgument(userInput[i])) {
                    printHelpInfo();
                } else if (isItAFlagArgument(userInput, i)) {
                    flags.put(userInput[i].substring(1), true);
                } else if (nicknames.containsKey(userInput[i].substring(1))) {
                    setOptionalArgument(userInput, i, nicknames.get(userInput[i].substring(1)));
                    i++;
                } else {
                    //throws new invalidShortArgument();
                }
            } else {
                setValue(keys.get(count), userInput[i]);
                if (isDataTypeEqualTo("int", count)) {
                    try {
                        Integer.parseInt(userInput[i]);
                    } catch (java.lang.NumberFormatException e) {
                       //throw should be int exception
                    }
                } else if (isDataTypeEqualTo("float", count)) {
                    try {
                        Float.parseFloat(userInput[i]);
                    } catch (java.lang.NumberFormatException e) {
                        //throw should be float exception
                    }
                } else if (isDataTypeEqualTo("boolean", count)) {
                    if (!isItAValidBoolean(userInput, i)) {
                        //throw should be boolean exception
                    }
                }
                count++;
            }
        }
    }
    
    private boolean isItAFlagArgument(String[] userInput, int i) {
        return flags.containsKey(userInput[i].substring(1));
    }
    
    private boolean isItAValidBoolean(String[] userInput, int index) {
        return (userInput[index].equals("false") || userInput[index].equals("true") ||
                userInput[index].equals("True") || userInput[index].equals("False"));
    }
    
    private boolean isLongOptionalArgument(String[] userInput, int index) {
        return (userInput[index].startsWith("--"));
    }
    
    private boolean isShortOptionalArgument(String[] userInput, int index) {
        return (userInput[index].startsWith("-"));
    }
    
    private void setOptionalArgument(String[] userInput, int index) {
        myArgs.get(userInput[index].substring(2)).myValue = userInput[index+1];
    }
    
    private void setOptionalArgument(String[] userInput, int index, String key) {
        myArgs.get(key).myValue = userInput[index+1];
    }
    
    public <T> T getValue(String s) {
        if (myArgs.get(s).dataType.equals("String")) {
            return (T) myArgs.get(s).myValue;
        } else if (myArgs.get(s).dataType.equals("int")) {
            return (T) new Integer(Integer.parseInt(myArgs.get(s).myValue));
        } else if (myArgs.get(s).dataType.equals("float")) {
            return (T) new Float(Float.parseFloat(myArgs.get(s).myValue));
        } else if (myArgs.get(s).dataType.equals("boolean")) {
            return (T) new Boolean(Boolean.parseBoolean(myArgs.get(s).myValue));
        } else {
            return null;
        }
    }
    
    public String getArgumentDescription(String s) {
        return myArgs.get(s).myDescription;
    }
    
    
    public void addOptionalArgument(String type) {
        addArguments(type, "String");
    }
    public void addOptionalArgument(String type, String defaultValue) {
        addArguments(type, "String");
        setValue(type, defaultValue);
    }
    public void addOptionalArgument(String type, String defaultValue, String nickname) {
        addArguments(type, "String");
        setValue(type, defaultValue);
        nicknames.put(nickname, type);
        setNickname(type, nickname);
    }
    private void printHelpInfo() {
        System.out.println("\nUsage Information:");
        for (String s : keys) {
            if (!myArgs.get(s).myDescription.equals("")) {
                System.out.println(s + " --- " + myArgs.get(s).myDescription);
            }
        }
    }
    
    private class Argument {
        public String myDescription = "";
        public String myValue = "";
        public String dataType = "";
        public String nickname = "";
    }
    
    private void setValue(String s, String n) {
        myArgs.get(s).myValue = n;
    }
    private void setNickname(String s, String n) {
        myArgs.get(s).nickname = n;
    }
    
    public void addArguments(String name, String description, String dataType) 
	{
        Argument ao = new Argument();
        myArgs.put(name, ao);
        keys.add(name);
        ao.myDescription = description;
        ao.dataType = dataType;
    }
	
    public void addArguments(String name, String dataType) 
	{
        Argument ao = new Argument();
        myArgs.put(name, ao);
        keys.add(name);
        ao.dataType = dataType;
    }
    
    private boolean isHelpArgument(String s) {
        return s.equals("-h");
    }
    
    private boolean isDataTypeEqualTo(String dataType, int count) {
        return myArgs.get(keys.get(count)).dataType.equals(dataType);
    }
    
    public void setProgramDescription(String s) {
        programDescription = s;
    }
    
    public void addFlag(String s) {
        flags.put(s, Boolean.FALSE);
    }
    
    public boolean checkFlag(String s) {
        return flags.get(s).booleanValue();
    }
}
