package edu.jsu.mcis;

import java.util.*;

public class ArgumentParser
{   
    public Map<String, ArgumentObject> myArgs = new HashMap<>();
    private ArrayList<String> myNames = new ArrayList<String>();
    private ArrayList<String> nicknames = new ArrayList<String>();
    
    public void parse(String[] args) 
	{
        int count = 0;
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("-")) {
                if (args[i].startsWith("--")) {
                    if (myArgs.containsKey(args[i].substring(2))) {
                        myArgs.get(args[i].substring(2)).myString = args[i+1];
                    } else {
                        //throw new invalidInputException();
                    }
                    i++;
                }else if (args[i].equals("-h")) {
                    printHelpInfo();
                } else if (nicknames.contains(args[i].substring(1))) {
                    for (int w = 0; w < myNames.size(); w++) {
                        if (myArgs.get(myNames.get(w)).nickname.equals(args[i].substring(1))) {
                            myArgs.get(myNames.get(w)).myString = args[i+1];
                            i++;
                        }
                    }
                }
            } else
            {   
                if ("String".equals(myArgs.get(myNames.get(count)).dataType)) 
				{
                    myArgs.get(myNames.get(count)).myString = args[i];
                } else if ("int".equals(myArgs.get(myNames.get(count)).dataType)) 
				{
                    try
                    {
                        myArgs.get(myNames.get(count)).myInt = Integer.parseInt(args[i]);
                    } catch (java.lang.NumberFormatException e) {
                       System.out.println("Value expected: Integer");
                    }
                } else if ("float".equals(myArgs.get(myNames.get(count)).dataType)) 
				{
                    try
                    {
                    myArgs.get(myNames.get(count)).myFloat = Float.parseFloat(args[i]);
                    } catch (java.lang.NumberFormatException e) 
                    {
                        System.out.println("Value expected: Float");
                    }
                } else if ("boolean".equals(myArgs.get(myNames.get(count)).dataType)) 
				{
                    String boolTest = args[i];
                    if (boolTest.equals("true") || boolTest.equals("True")) 
					{
                        myArgs.get(myNames.get(count)).myBool = true;
                    }
                    else if (boolTest.equals("false") || boolTest.equals("False")) 
					{
                        myArgs.get(myNames.get(count)).myBool = false;
                    }
                    else 
					{
                        System.out.println("Value expected: boolean");
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
    public String getDescriptionValue(String s) 
	{
        return myArgs.get(s).myDescription;
    }
    private void setIntValue(String s, int n) 
	{
        myArgs.get(s).myInt = n;
    }
    private void setNicknameValue(String s, String n) {
        myArgs.get(s).nickname = n;
    }
    private void setFloatValue(String s, float n) 
	{
        myArgs.get(s).myFloat = n;
    }
    private void setBooleanValue(String s, boolean n) 
	{
        myArgs.get(s).myBool = n;
    }
    private void setStringValue(String s, String n) 
	{
        myArgs.get(s).myString = n;
    }
    private void setDescriptionValue(String s, String n) 
	{
        myArgs.get(s).myDescription = n;
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
    private void printHelpInfo() 
	{
        System.out.println("\nUsage Information:");
        for (String s : myNames) 
		{
            if (myArgs.get(s).myDescription != "") 
			{
                System.out.println(s + " --- " + myArgs.get(s).myDescription);
            }
        }
    }
    
    private class ArgumentObject 
    {
        public String myDescription = "";
        public int myInt;
        public float myFloat;
        public String myString = "";
        public boolean myBool;
        public String dataType = "";
        public String nickname = "";
    }
    
    public void addArguments(String name, String description, String dataType) 
	{
        ArgumentObject ao = new ArgumentObject();
        myArgs.put(name, ao);
        myNames.add(name);
        ao.myDescription = description;
        ao.dataType = dataType;
    }
	
    public void addArguments(String name, String dataType) 
	{
        ArgumentObject ao = new ArgumentObject();
        myArgs.put(name, ao);
        myNames.add(name);
        ao.dataType = dataType;
    }

}
