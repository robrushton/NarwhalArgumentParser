package edu.jsu.mcis;

import java.util.*;


public class ArgumentParser
{
        
    public static final Map<String, ArgumentObject> myArgs = new HashMap<>();
    private static final LinkedList myNames = new LinkedList();
	
	public void setDashH()
	{
		ArgumentObject ao = new ArgumentObject();
		addArg("-h", ao);
		ao.setStringValue("usage: java VolumeCalculator length width height"+"\n"+"\n"
							+"Calculate the volume of a box."+"\n"+"\n"+"positional arguments: length the length of the box"
							+"\n"+"width: width of the box"+"\n"+"height: height of the box");
	}
	
	public String getDashH()
	{
		ArgumentObject ao = myArgs.get("-h");
		String stri = ao.getStringValue();
		return stri;
	}
    
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
