package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;

public class ArgumentParserTest {
    
    @Test
    public void testGetDescriptionValue()
    {
        ArgumentParser ap = new ArgumentParser();
	ap.addArguments("testString", "It's a string thing", "String");
        assertEquals("It's a string thing", ap.getDescription("testString"));
    }
	
    @Test
    public void testAddArgumentsNoDescription()
    {
        ArgumentParser ap = new ArgumentParser();
        String one = "Test Name 1";
        String two = "Test Name 2";
        String three = "Test Name 3";
        ap.addArguments(one, "String");
        ap.addArguments(two, "int");
        ap.addArguments(three, "boolean");
        String[] myStringArray = {"Hello","5","true"};
        ap.parse(myStringArray);
        assertEquals("Hello", (String) ap.getValue(one));
        assertEquals(5, (int) ap.getValue(two));
        assertEquals(true, (boolean) ap.getValue(three));
    }
    
    @Test
    public void testAddArgumentsWithDescription()
    {
        ArgumentParser ap = new ArgumentParser();
        String one = "Test Name 1";
        String two = "Test Name 2";
        String three = "Test Name 3";
        ap.addArguments(one, "It's a string thing", "String");
        ap.addArguments(two, "It's an int!", "int");
        ap.addArguments(three, "It is a bool", "boolean");
        String[] myStringArray = {"Hello","5","True"};
        ap.parse(myStringArray);
        assertEquals("Hello", (String) ap.getValue(one));
        assertEquals(5, (int) ap.getValue(two));
        assertEquals(true, (boolean) ap.getValue(three));
        assertEquals("It's a string thing", ap.getDescription(one));
        assertEquals("It's an int!", ap.getDescription(two));
        assertEquals("It is a bool", ap.getDescription(three));
    }
    
    @Test
    public void testParseValuesNotDashH() {
        ArgumentParser ap = new ArgumentParser();
        String one = "Length";
        String two = "Width";
        String three = "Height";
        String four = "Name";
        ap.addArguments(one, "Length of the object", "float");
        ap.addArguments(two, "Width of the object", "boolean");
        ap.addArguments(three, "Height of the object", "int");
        ap.addArguments(four, "Name of object", "String");
        String[] args = {"12.34", "False", "7", "Fred"};
        ap.parse(args);
        assertEquals(12.34, (float) ap.getValue(one), 0.01);
        assertEquals(false, (boolean) ap.getValue(two));
        assertEquals(7, (int) ap.getValue(three));
        assertEquals("Fred", (String) ap.getValue(four));   
    }
   
    @Test
    public void testOptionalArgumentDefaultValue()
    {
        ArgumentParser ap = new ArgumentParser();
        ap.addArguments("thing", "Length of the object", "float");
        ap.addOptionalArgument("stuff", "5");
        assertEquals("5", (String) ap.getValue("stuff"));
        
    }    
    
    @Test
    public void testSingleArgumentNoDefaultValue()
    {
        ArgumentParser ap = new ArgumentParser();
        ap.addArguments("thing", "Length of the object", "float");
        ap.addOptionalArgument("stuff");
        String[] inp = {"--stuff", "5"};
        ap.parse(inp);
        assertEquals("5", (String) ap.getValue("stuff"));
        
    }
    
    @Test
    public void testBooleanMultipleTimesTrueAndFalse()
    {
        ArgumentParser ap = new ArgumentParser();
        ap.addArguments("Arg 1", "This should be true", "boolean");
        ap.addArguments("Arg 2", "This should be false", "boolean");
        ap.addArguments("Arg 3", "This should be true", "boolean");
        String[] args = {"true", "false", "true"};
        ap.parse(args);
        assertEquals(true, (boolean) ap.getValue("Arg 1"));
        assertEquals(false, (boolean) ap.getValue("Arg 2"));
        assertEquals(true, (boolean) ap.getValue("Arg 3"));  
    }
    
    @Test
    public void testShortOptionalArgumentForLongName() {
        ArgumentParser ap = new ArgumentParser();
        ap.addArguments("Length", "Length of the object", "int");
        ap.addOptionalArgument("type", " ", "t");
        String[] inp = {"-t", "circle", "5"};
        ap.parse(inp);
        assertEquals("circle", (String) ap.getValue("type")); 
    }
    
    @Test
    public void testMultipleShortOptionalArgumentForLongName() {
        ArgumentParser ap = new ArgumentParser();
        ap.addArguments("Length", "Length of the object", "int");
        ap.addArguments("Width", "Width of the object", "int");
        ap.addArguments("Height", "Height of the object", "int");
        ap.addOptionalArgument("type", " ", "t");
        ap.addOptionalArgument("color", " ", "c");
        String[] inp = {"-t", "circle", "5", "-c", "red", "7", "10"};
        ap.parse(inp);
        assertEquals("circle", (String) ap.getValue("type")); 
        assertEquals("red", (String) ap.getValue("color"));
    }
    
    @Test
    public void testOneShortOptionalArgumentForLongNameAndOneLong() {
        ArgumentParser ap = new ArgumentParser();
        ap.addArguments("Length", "Length of the object", "int");
        ap.addArguments("Width", "Width of the object", "int");
        ap.addArguments("Height", "Height of the object", "int");
        ap.addOptionalArgument("type", " ", "t");
        ap.addOptionalArgument("color", " ", "c");
        String[] inp = {"-t", "circle", "5", "--color", "red", "7", "10"};
        ap.parse(inp);
        assertEquals("circle", (String) ap.getValue("type")); 
        assertEquals("red", (String) ap.getValue("color"));
    }
    
    @Test
    public void testDashDashFront()
    {
        ArgumentParser ap = new ArgumentParser();
        ap.addArguments("Arg 1", "This should be true", "boolean");
        ap.addArguments("Arg 2", "This should be false", "boolean");
        ap.addArguments("Arg 3", "This should be true", "boolean");
        ap.addOptionalArgument("stuff", "5");
        String[] inp = {"--stuff", "4", "true", "false", "true"};
        ap.parse(inp);
        assertEquals("4", (String) ap.getValue("stuff"));
    }  
    
    @Test
    public void testDashDashMiddle()
    {
        ArgumentParser ap = new ArgumentParser();
        ap.addArguments("Arg 1", "This should be true", "boolean");
        ap.addArguments("Arg 2", "This should be false", "boolean");
        ap.addArguments("Arg 3", "This should be true", "boolean");
        ap.addOptionalArgument("stuff", "5");
        String[] inp = { "true", "false", "--stuff", "4", "true"};
        ap.parse(inp);
        assertEquals("4", (String) ap.getValue("stuff"));
    }  
    
    @Test
    public void testDashDashEnd()
    {
        ArgumentParser ap = new ArgumentParser();
        ap.addArguments("Arg 1", "This should be true", "boolean");
        ap.addArguments("Arg 2", "This should be false", "boolean");
        ap.addArguments("Arg 3", "This should be true", "boolean");
        ap.addOptionalArgument("stuff", "5");
        String[] inp = {"true", "false", "true", "--stuff", "4"};
        ap.parse(inp);
        assertEquals("4", (String) ap.getValue("stuff"));
    }
    
    @Test
    public void testDashHPrintsHelp() {
        ArgumentParser ap = new ArgumentParser();
        ap.addArguments("Arg 1", "This should be true", "boolean");
        ap.addArguments("Arg 2", "This should be false", "boolean");
        ap.addArguments("Arg 3", "This should be true", "boolean");
        ap.addOptionalArgument("type", "", "t");
        String[] inp = {"-h"};
        ap.parse(inp);
    }
}
