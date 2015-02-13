package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;

public class ArgumentParserTest {
    
    @Test
    public void testObjectIntValue() 
	{
        ArgumentParser ap = new ArgumentParser();
        ArgumentParser.ArgumentObject ao = new ArgumentParser.ArgumentObject();
        ao.setIntValue(10);
        int t = ao.getIntValue();
        assertEquals(t, 10);
    }
    
    @Test
    public void testObjectStringNull()
	{
        ArgumentParser ap = new ArgumentParser();
        ArgumentParser.ArgumentObject ao = new ArgumentParser.ArgumentObject();
        String s = ao.getStringValue();
        assertEquals(null, s);
    }
    
    @Test
    public void testObjectDescriptionNull()
    {
        ArgumentParser ap = new ArgumentParser();
        ArgumentParser.ArgumentObject ao = new ArgumentParser.ArgumentObject();
        assertEquals(null, ao.getDescriptionValue());
    } 
    
    @Test
    public void testSetDescriptionValue()
    {
        ArgumentParser ap = new ArgumentParser();
        ArgumentParser.ArgumentObject ao = new ArgumentParser.ArgumentObject();
        String desc = new String("stuff");
        ao.setDescriptionValue(desc);
        assertEquals("stuff", ao.getDescriptionValue());
    }
    
    @Test
    public void testGetFloatValue()
    {
        ArgumentParser ap = new ArgumentParser();
        ArgumentParser.ArgumentObject ao = new ArgumentParser.ArgumentObject();
        assertEquals(0, ao.getFloatValue(), 0.01);
    }
    
    @Test
    public void testSetFloatValue()
    {
        ArgumentParser ap = new ArgumentParser();
        ArgumentParser.ArgumentObject ao = new ArgumentParser.ArgumentObject();
        float flt = 1.2f;
        ao.setFloatValue(flt);
        assertEquals(1.2, ao.getFloatValue(), 0.01);
    }
    
    @Test
    public void testSetStringValue()
    {
        ArgumentParser ap = new ArgumentParser();
        ArgumentParser.ArgumentObject ao = new ArgumentParser.ArgumentObject();
        String s = new String("stuff");
        ao.setStringValue(s);
        assertEquals("stuff", ao.getStringValue());
    }
    
    @Test
    public void testGetBooleanValue()
    {
        ArgumentParser ap = new ArgumentParser();
        ArgumentParser.ArgumentObject ao = new ArgumentParser.ArgumentObject();
        assertEquals(false, ao.getBooleanValue());
    }
    
    @Test
    public void testSetBooleanValue()
    {
        ArgumentParser ap = new ArgumentParser();
        ArgumentParser.ArgumentObject ao = new ArgumentParser.ArgumentObject();
        Boolean b = true;
        ao.setBooleanValue(b);
        assertEquals(true, ao.getBooleanValue());
    }
	
    @Test
    public void testAddArgumentsNoDescription()
    {
        ArgumentParser ap = new ArgumentParser();
        String one = new String("Test Name 1");
        String two = new String("Test Name 2");
        String three = new String("Test Name 3");
        ap.addArguments(one, "String");
        ap.addArguments(two, "int");
        ap.addArguments(three, "boolean");
        String[] myStringArray = {"Hello","5","true"};
        ap.parse(myStringArray);
        assertEquals("Hello", ap.getStringValue(one));
        assertEquals(5, ap.getIntValue(two));
        assertEquals(true, ap.getBooleanValue(three));
    }
    
    @Test
    public void testAddArgumentsWithDescription()
    {
        ArgumentParser ap = new ArgumentParser();
        String one = new String("Test Name 1");
        String two = new String("Test Name 2");
        String three = new String("Test Name 3");
        ap.addArguments(one, "It's a string thing", "String");
        ap.addArguments(two, "It's an int!", "int");
        ap.addArguments(three, "It is a bool", "boolean");
        String[] myStringArray = {"Hello","5","true"};
        ap.parse(myStringArray);
        assertEquals("Hello", ap.getStringValue(one));
        assertEquals(5, ap.getIntValue(two));
        assertEquals(true, ap.getBooleanValue(three));
        assertEquals("It's a string thing", ap.getDescriptionValue(one));
        assertEquals("It's an int!", ap.getDescriptionValue(two));
        assertEquals("It is a bool", ap.getDescriptionValue(three));
    }
}
