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
}
