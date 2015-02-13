package edu.jsu.mcis;

import java.util.*;


public class ArgumentParser
{   
    public Map<String, ArgumentObject> myArgs = new HashMap<>();
    private ArrayList<String> myNames = new ArrayList<String>();
    
    public void parse(String[] args) {
        int count = 0;//Keep track of how many values I have placed
        for (int i = 0; i < args.length; i++)
        {
            if (args[i].startsWith("-"))//If it starts with a dash then do this
            {
                if (args[i].startsWith("--")) {
                    if (myArgs.containsKey(args[i].substring(2))) {
                        myArgs.get(args[i].substring(2)).setStringValue(args[i+1]);
                    } else {
                        //Error unique type not specified in volumecalc
                    }
                    i++;
                }else if (args[i].equals("-h"))
                {
                    printHelpInfo();
                } else
                {
                    
                }
            } else // if it doesn't start with dash then it is a value
            {   
                if ("String".equals(myArgs.get(myNames.get(count)).dataType)) {//If dataType is supposed to be string then place a string in its string value
                    myArgs.get(myNames.get(count)).setStringValue(args[i]);
                } else if ("int".equals(myArgs.get(myNames.get(count)).dataType)) {
                    try
                    {
                        myArgs.get(myNames.get(count)).setIntValue(Integer.parseInt(args[i]));
                    } catch (java.lang.NumberFormatException e) {
                        //Error.. Value s supposed to be an int
                    }
                } else if ("float".equals(myArgs.get(myNames.get(count)).dataType)) {
                    try
                    {
                    myArgs.get(myNames.get(count)).setFloatValue(Float.parseFloat(args[i]));
                    } catch (java.lang.NumberFormatException e) 
                    {
                        //Error.. Value s supposed to be an int
                    }
                } else if ("boolean".equals(myArgs.get(myNames.get(count)).dataType)) {
                    myArgs.get(myNames.get(count)).setBooleanValue(Boolean.parseBoolean(args[i]));
                }
                count++;//I placed a value so increment
            }
        }
    }
    
    public int getIntValue(String s) {
        return myArgs.get(s).getIntValue();
    }
    public float getFloatValue(String s) {
        return myArgs.get(s).getFloatValue();
    }
    public boolean getBooleanValue(String s) {
        return myArgs.get(s).getBooleanValue();
    }
    public String getStringValue(String s) {
        return myArgs.get(s).getStringValue();
    }
    public String getDescriptionValue(String s) {
        return myArgs.get(s).getDescriptionValue();
    }
    private void setIntValue(String s, int n) {
        myArgs.get(s).setIntValue(n);
    }
    private void setFloatValue(String s, float n) {
        myArgs.get(s).setFloatValue(n);
    }
    private void setBoolenValue(String s, boolean n) {
        myArgs.get(s).setBooleanValue(n);
    }
    private void setStringValue(String s, String n) {
        myArgs.get(s).setStringValue(n);
    }
    private void setDescriptionValue(String s, String n) {
        myArgs.get(s).setDescriptionValue(n);

    }
    


    public void addOptionalArgument(String type) {
        //Add an argument called type where he can store a string value into
        addArguments(type, "String");
    }
    
    public void addOptionalArgument(String type, String defaultValue) {
        addArguments(type, "String");
        setStringValue(type, defaultValue);
    }

    private void printHelpInfo() {
        System.out.println("Usage Information:");
        for (String s : myNames) {
            if (myArgs.get(s).getDescriptionValue() != null) {
                System.out.println(s + " --- " + myArgs.get(s).getDescriptionValue());
            }
        }
    }
    
    static class ArgumentObject 
    {
        String myDescription = null;
        int myInt;
        float myFloat;
        String myString = null;
        boolean myBool;
        String dataType = null;
        
        public String getDataType() {
            return dataType;
        }
        
        public void setDataType(String s) {
            dataType = s;
        }

        public String getDescriptionValue() 
        {
            return myDescription;
        }

        public void setDescriptionValue(String d) 
		{
            myDescription = d;
        }

        public int getIntValue() 
		{
            return myInt;
        }

        public void setIntValue(int i) 
		{
            myInt = i;
        }

        public float getFloatValue() 
		{
            return myFloat;
        }

        public void setFloatValue(float f) 
		{
            myFloat = f;
        }

        public String getStringValue() 
		{
            return myString;
        }

        public void setStringValue(String s) 
		{
            myString = s;
        }

        public boolean getBooleanValue() 
		{
            return myBool;
        }

        public void setBooleanValue(boolean b) 
		{
            myBool = b;
        }
    }
    
    
    
    public void addArguments(String name, String description, String dataType) {
        ArgumentObject ao = new ArgumentObject();
        myArgs.put(name, ao);
        myNames.add(name);
        ao.setDescriptionValue(description);
        ao.setDataType(dataType);
    }
    public void addArguments(String name, String dataType) {
        ArgumentObject ao = new ArgumentObject();
        myArgs.put(name, ao);
        myNames.add(name);
        ao.setDataType(dataType);
    }
}
