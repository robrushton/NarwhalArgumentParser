package edu.jsu.mcis;

import java.util.*;

public class ArgumentParser
{   
    public Map<String, Argument> myArgs = new HashMap<>();
    private ArrayList<String> keys = new ArrayList<String>();
    private ArrayList<String> nicknames = new ArrayList<String>();
    
    public void parse(String[] userInput) 
	{
        int count = 0;
        for (int i = 0; i < userInput.length; i++) {
            if (userInput[i].startsWith("-")) {
                if (isHelpArgument(userInput[i])) {
                    printHelpInfo();
                } else if (userInput[i].startsWith("--")) {
                    if (myArgs.containsKey(userInput[i].substring(2))) {
                        setOptionalArgument(userInput, i);
                    } else {
                        //throw new invalidInputException();
                    }
                    i++;
                } else if (nicknames.contains(userInput[i].substring(1))) {
                    for (int w = 0; w < keys.size(); w++) {
                        if (isNicknameAtW(userInput, i, w)) {
                            setOptionalArgumentNickname(userInput, i, w);
                            i++;
                        }
                    }
                }
            } else {   
                if (isDataTypeEqualTo("String", count)) {
                    setStringValue(keys.get(count), userInput[i]);
                } else if (isDataTypeEqualTo("int", count)) {
                    try {
                        setIntValue(keys.get(count), Integer.parseInt(userInput[i]));
                    } catch (java.lang.NumberFormatException e) {
                       //throw should be int exception
                    }
                } else if (isDataTypeEqualTo("float", count)) {
                    try {
                        setFloatValue(keys.get(count), Float.parseFloat(userInput[i]));
                    } catch (java.lang.NumberFormatException e) {
                        //throw should be float exception
                    }
                } else if (isDataTypeEqualTo("boolean", count)) {
                    if (userInput[i].equals("true") || userInput[i].equals("True")) {
                        setBooleanValue(keys.get(count), true);
                    }
                    else if (userInput[i].equals("false") || userInput[i].equals("False")) {
                        setBooleanValue(keys.get(count), false);
                    }
                    else  {
                        //throw should be boolean exception
                    }
                }
                count++;
            }
        }
    }
    
    public int getIntValue(String s) 
	{
        return myArgs.get(s).myInt;
    }
    public float getFloatValue(String s) 
	{
        return myArgs.get(s).myFloat;
    }
    public boolean getBooleanValue(String s) 
	{
        return myArgs.get(s).myBool;
    }
    public String getStringValue(String s) 
	{
        return myArgs.get(s).myString;
    }
    public String getDescription(String s) 
	{
        return myArgs.get(s).myDescription;
    }
    
    
    public void addOptionalArgument(String type) 
	{
        addArguments(type, "String");
    }
    public void addOptionalArgument(String type, String defaultValue) 
	{
        addArguments(type, "String");
        setStringValue(type, defaultValue);
    }
    public void addOptionalArgument(String type, String defaultValue, String nickname) {
        addArguments(type, "String");
        setStringValue(type, defaultValue);
        nicknames.add(nickname);
        setNicknameValue(type, nickname);
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
        public int myInt;
        public float myFloat;
        public String myString = "";
        public boolean myBool;
        public String dataType = "";
        public String nickname = "";
    }
    
    private void setIntValue(String s, int n) {
        myArgs.get(s).myInt = n;
    }
    private void setNicknameValue(String s, String n) {
        myArgs.get(s).nickname = n;
    }
    private void setFloatValue(String s, float n) {
        myArgs.get(s).myFloat = n;
    }
    private void setBooleanValue(String s, boolean n) {
        myArgs.get(s).myBool = n;
    }
    private void setStringValue(String s, String n) {
        myArgs.get(s).myString = n;
    }
    private void setDescriptionValue(String s, String n) {
        myArgs.get(s).myDescription = n;
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
        if (s.equals("-h")) {
            return true;
        } else {
            return false;
        }
    }
    
    private void setOptionalArgument(String[] userInput, int index) {
        myArgs.get(userInput[index].substring(2)).myString = userInput[index+1];
    }
    
    private boolean isNicknameAtW(String[] userInput, int index, int w) {
        return myArgs.get(keys.get(w)).nickname.equals(userInput[index].substring(1));
    }
    
    private void setOptionalArgumentNickname(String[] userInput, int index, int w) {
        myArgs.get(keys.get(w)).myString = userInput[index+1];
    }
    
    private boolean isDataTypeEqualTo(String dataType, int count) {
         if (myArgs.get(keys.get(count)).dataType.equals(dataType)) {
             return true;
         } else {
             return false;
         }
    }
}
