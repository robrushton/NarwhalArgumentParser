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
    public void testAddArg()
    {
        ArgumentParser ap = new ArgumentParser();
	ArgumentParser.ArgumentObject ao = new ArgumentParser.ArgumentObject();
	String s = new String("Stuff");
        ap.addArg(s, ao);
        ArgumentParser.ArgumentObject aoTwo = ArgumentParser.myArgs.get("Stuff");
	aoTwo.setStringValue("String");
	String str = aoTwo.getStringValue();
        assertEquals(str, "String");		
    }
    
    
    
    
}
