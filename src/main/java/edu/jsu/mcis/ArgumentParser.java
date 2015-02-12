package edu.jsu.mcis;

import java.util.*;


public class ArgumentParser
{
        
    private static final Map<String, ArgumentObject> myArgs = new HashMap<>();
    private static final LinkedList myNames = new LinkedList();
    
    static class ArgumentObject 
    {

        String myDescription = null;
        int myInt;
        float myFloat;
        String myString = null;
        boolean myBool;

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
	public void addArg(String s, ArgumentObject a){
		myArgs.put(s, a);
	}
}
