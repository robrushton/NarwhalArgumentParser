package edu.jsu.mcis;

import java.util.*;


public class ArgumentParser
{
        
    public static final Map<String, ArgumentObject> myArgs = new HashMap<>();
    private static final ArrayList<String> myNames = new ArrayList<String>();
    
    public void parse(String[] args) {
        int count = 0;//Keep track of how many values I have placed
        for (String s: args)
        {
            if (s.startsWith("-"))//If it starts with a dash then do this
            {
                if (s.equals("-h"))
                {
                    System.out.println("You entered -h");
                } else
                {
                    
                }
            } else // if it doesn't start with dash then it is a value
            {   
                if ("String".equals(myArgs.get(myNames.get(count)).dataType)) {//If dataType is supposed to be string then place a string in its string vaue
                    myArgs.get(myNames.get(count)).setStringValue(s);
                } else if ("int".equals(myArgs.get(myNames.get(count)).dataType)) {
                    myArgs.get(myNames.get(count)).setIntValue(Integer.parseInt(s));
                } else if ("float".equals(myArgs.get(myNames.get(count)).dataType)) {
                    myArgs.get(myNames.get(count)).setFloatValue(Float.parseFloat(s));
                } else if ("boolean".equals(myArgs.get(myNames.get(count)).dataType)) {
                    myArgs.get(myNames.get(count)).setBooleanValue(Boolean.parseBoolean(s));
                }
                count++;//I placed a value so increment
            }
        }
    }
	
	
    public String printDashH()
    {
        ArgumentObject ao = new ArgumentObject();
        addArg("-h", ao);
        ao.setDescriptionValue("usage: java VolumeCalculator length width height"+"\n"+"\n"
                                    +"Calculate the volume of a box."+"\n"+"\n"+"positional arguments: length the length of the box"
                                    +"\n"+"width: width of the box"+"\n"+"height: height of the box");
        String stri = ao.getDescriptionValue();
        return stri;
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
    
    public void addArg(String s, ArgumentObject a){
        myArgs.put(s, a);
    }
}
