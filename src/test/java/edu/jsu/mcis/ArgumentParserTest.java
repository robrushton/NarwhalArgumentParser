package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;

public class ArgumentParserTest {
    private ArgumentParser ap;
	
    @Before
    public void startArgumentParser(){
        ap = new ArgumentParser();
    }
	
    @Test
    public void testAddArgumentsNoDescription() {
        String one = "Test Name 1";
        String two = "Test Name 2";
        String three = "Test Name 3";
        ap.addArguments(one, ArgumentParser.Datatype.STRING);
        ap.addArguments(two, ArgumentParser.Datatype.INT);
        ap.addArguments(three, ArgumentParser.Datatype.BOOLEAN);
        String[] inp = {"Hello","5","true"};
        ap.parse(inp);
        assertEquals("Hello", (String) ap.getValue(one));
        assertEquals(5, (int) ap.getValue(two));
        assertEquals(true, (boolean) ap.getValue(three));
    }
    
    @Test
    public void testAddArgumentsWithDescription() {
        String one = "Test Name 1";
        String two = "Test Name 2";
        String three = "Test Name 3";
        ap.addArguments(one, ArgumentParser.Datatype.STRING, "It's a string thing");
        ap.addArguments(two, ArgumentParser.Datatype.INT, "It's an int!");
        ap.addArguments(three, ArgumentParser.Datatype.BOOLEAN, "It is a bool");
        String[] inp = {"Hello","5","True"};
        ap.parse(inp);
        assertEquals("Hello", (String) ap.getValue(one));
        assertEquals(5, (int) ap.getValue(two));
        assertEquals(true, (boolean) ap.getValue(three));
    }
    
    @Test
    public void testParseValuesNotDashH() {
        String one = "Length";
        String two = "Width";
        String three = "Height";
        String four = "Name";
        ap.addArguments(one, ArgumentParser.Datatype.FLOAT, "Length of the object");
        ap.addArguments(two, ArgumentParser.Datatype.BOOLEAN, "Width of the object");
        ap.addArguments(three, ArgumentParser.Datatype.INT, "Height of the object");
        ap.addArguments(four, ArgumentParser.Datatype.STRING, "Name of object");
        String[] inp = {"12.34", "False", "7", "Fred"};
        ap.parse(inp);
        assertEquals(12.34, (float) ap.getValue(one), 0.01);
        assertEquals(false, (boolean) ap.getValue(two));
        assertEquals(7, (int) ap.getValue(three));
        assertEquals("Fred", (String) ap.getValue(four));   
    }
    
    @Test
    public void testAddingProgramDescriptionAndName() {
        String one = "Length";
        String two = "Width";
        String three = "Height";
        String four = "Name";
        ap.addArguments(one, ArgumentParser.Datatype.FLOAT, "Length of the object");
        ap.addArguments(two, ArgumentParser.Datatype.BOOLEAN, "Width of the object");
        ap.addArguments(three, ArgumentParser.Datatype.INT, "Height of the object");
        ap.addArguments(four, ArgumentParser.Datatype.STRING, "Name of object");
        ap.setProgramDescription("This program prints arguments");
        ap.setProgramName("ArgumentPrinter");
        String[] inp = {"12.34", "False", "7", "Fred"};
        ap.parse(inp);
        assertEquals(12.34, (float) ap.getValue(one), 0.01);
        assertEquals(false, (boolean) ap.getValue(two));
        assertEquals(7, (int) ap.getValue(three));
        assertEquals("Fred", (String) ap.getValue(four)); 
        
    }
   
    @Test
    public void testNamedArgumentDefaultValue() {
        ap.addArguments("thing", ArgumentParser.Datatype.FLOAT, "Length of the object");
        ap.addNamedArgument("stuff", "5", false);
        assertEquals("5", (String) ap.getValue("stuff"));
    }
    
    @Test
    public void testNamedArgumentDefaultValueBeingChanged() {
        ap.addArguments("thing", ArgumentParser.Datatype.FLOAT, "Length of the object");
        ap.addNamedArgument("stuff", "5", false);
        String[] inp = {"--stuff", "10", "4.6"};
        ap.parse(inp);
        assertEquals("10", (String) ap.getValue("stuff"));
        
    }  
    
    @Test
    public void testSingleArgumentNoDefaultValue() {
        ap.addArguments("thing", ArgumentParser.Datatype.FLOAT, "Length of the object");
        ap.addNamedArgument("stuff", true);
        String[] inp = {"--stuff", "5", "4.2"};
        ap.parse(inp);
        assertEquals("5", (String) ap.getValue("stuff"));
        
    }
    
    @Test
    public void testBooleanMultipleTimesTrueAndFalse() {
        ap.addArguments("Arg 1", ArgumentParser.Datatype.BOOLEAN, "This should be true");
        ap.addArguments("Arg 2", ArgumentParser.Datatype.BOOLEAN, "This should be false");
        ap.addArguments("Arg 3", ArgumentParser.Datatype.BOOLEAN, "This should be true");
        String[] inp = {"true", "false", "true"};
        ap.parse(inp);
        assertEquals(true, (boolean) ap.getValue("Arg 1"));
        assertEquals(false, (boolean) ap.getValue("Arg 2"));
        assertEquals(true, (boolean) ap.getValue("Arg 3"));  
    }
    
    @Test
    public void testShortNamedArgumentForLongName() {
        ap.addArguments("Length", ArgumentParser.Datatype.INT, "Length of the object");
        ap.addNamedArgument("type", " ", ArgumentParser.Datatype.STRING, "t", true);
        String[] inp = {"-t", "circle", "5"};
        ap.parse(inp);
        assertEquals("circle", (String) ap.getValue("type")); 
    }
    
     @Test
    public void testAddNameArgumentWithBooleanRequired() {
        ap.addArguments("Length", ArgumentParser.Datatype.INT, "Length of the object");
        ap.addNamedArgument("type", " ", true);
        String[] inp = {"--type", "circle", "5"};
        ap.parse(inp);
        assertEquals("circle", (String) ap.getValue("type")); 
    }

    @Test
    public void testMultipleShortNamedArgumentForLongName() {
        ap.addArguments("Length", ArgumentParser.Datatype.INT, "Length of the object");
        ap.addArguments("Width", ArgumentParser.Datatype.INT, "Width of the object");
        ap.addArguments("Height", ArgumentParser.Datatype.INT, "Height of the object");
        ap.addNamedArgument("type", " ",ArgumentParser.Datatype.STRING,"t", false);
        ap.addNamedArgument("color", " ", ArgumentParser.Datatype.STRING, "c", false);
        String[] inp = {"-t", "circle", "5", "-c", "red", "7", "10"};
        ap.parse(inp);
        assertEquals("circle", (String) ap.getValue("type")); 
        assertEquals("red", (String) ap.getValue("color"));
    }
    
    @Test
    public void testOneShortNamedArgumentForLongNameAndOneLong() {
        ap.addArguments("Length", ArgumentParser.Datatype.INT, "Length of the object");
        ap.addArguments("Width", ArgumentParser.Datatype.INT, "Width of the object");
        ap.addArguments("Height", ArgumentParser.Datatype.INT, "Height of the object");
        ap.addNamedArgument("type", " ", ArgumentParser.Datatype.STRING,"t", true);
        ap.addNamedArgument("color", " ",ArgumentParser.Datatype.STRING, "c", false);
        String[] inp = {"-t", "circle", "5", "--color", "red", "7", "10"};
        ap.parse(inp);
        assertEquals("circle", (String) ap.getValue("type")); 
        assertEquals("red", (String) ap.getValue("color"));
    }
    
    @Test
    public void testDashDashFront() {
        ap.addArguments("Arg 1", ArgumentParser.Datatype.BOOLEAN, "This should be true");
        ap.addArguments("Arg 2", ArgumentParser.Datatype.BOOLEAN, "This should be false");
        ap.addArguments("Arg 3", ArgumentParser.Datatype.BOOLEAN, "This should be true");
        ap.addNamedArgument("stuff", "5", true);
        String[] inp = {"--stuff", "4", "true", "false", "true"};
        ap.parse(inp);
        assertEquals("4", (String) ap.getValue("stuff"));
    }  
    
    @Test
    public void testDashDashMiddle() {
        ap.addArguments("Arg 1", ArgumentParser.Datatype.BOOLEAN, "This should be true");
        ap.addArguments("Arg 2", ArgumentParser.Datatype.BOOLEAN, "This should be false");
        ap.addArguments("Arg 3", ArgumentParser.Datatype.BOOLEAN, "This should be true");
        ap.addNamedArgument("stuff", "5", false);
        String[] inp = { "true", "false", "--stuff", "4", "true"};
        ap.parse(inp);
        assertEquals("4", (String) ap.getValue("stuff"));
    }  
    
    @Test
    public void testDashDashEnd() {
        ap.addArguments("Arg 1", ArgumentParser.Datatype.BOOLEAN, "This should be true");
        ap.addArguments("Arg 2", ArgumentParser.Datatype.BOOLEAN, "This should be false");
        ap.addArguments("Arg 3", ArgumentParser.Datatype.BOOLEAN, "This should be true");
        ap.addNamedArgument("stuff", "5", true);
        String[] inp = {"true", "false", "true", "--stuff", "4"};
        ap.parse(inp);
        assertEquals("4", (String) ap.getValue("stuff"));
    }
    
    @Test
    public void testFlagTrueBeginning() {
        ap.addArguments("Arg 1", ArgumentParser.Datatype.BOOLEAN, "This should be true");
        ap.addFlag("t");
        String[] inp = {"-t", "false"};
        ap.parse(inp);
        assertEquals(true, ap.getValue("t"));
    }
    
    @Test
    public void testFlagFalseBeginning() {
        ap.addFlag("t");
        ap.addArguments("Arg 1", ArgumentParser.Datatype.BOOLEAN, "This should be true");
        String[] inp = {"true"};
        ap.parse(inp);
        assertEquals(false, ap.getValue("t"));
    }
    
    @Test
    public void testFlagTrueMiddle() {
        ap.addArguments("Arg 1", ArgumentParser.Datatype.BOOLEAN, "Test argument as well");
        ap.addArguments("Arg 2", ArgumentParser.Datatype.BOOLEAN, "Test argument");
        ap.addFlag("t");
        String[] inp = {"true","-t","false"};
        ap.parse(inp);
        assertEquals(true, ap.getValue("t"));
    }
    
    @Test
    public void testMultipleFlagsSeperatedBothTrue() {
        ap.addArguments("Arg 1", ArgumentParser.Datatype.BOOLEAN, "Test argument as well");
        ap.addArguments("Arg 2", ArgumentParser.Datatype.BOOLEAN, "Test argument");
        ap.addFlag("t");
        ap.addFlag("r");
        String[] inp = {"true","-t","false", "-r"};
        ap.parse(inp);
        assertEquals(true, ap.getValue("t"));
        assertEquals(true, ap.getValue("r"));
    }
    
    @Test
    public void testMultipleFlagsSeperatedOneTrueOtherFalse() {
        ap.addArguments("Arg 1", ArgumentParser.Datatype.BOOLEAN, "Test argument as well");
        ap.addArguments("Arg 2", ArgumentParser.Datatype.BOOLEAN, "Test argument");
        ap.addFlag("t");
        ap.addFlag("r");
        String[] inp = {"true","-t","false"};
        ap.parse(inp);        
        assertEquals(true, ap.getValue("t"));
        assertEquals(false, ap.getValue("r"));
    }
    
    @Test
    public void testTwoFlagsTogetherInMiddleBothTrue() {
        ap.addArguments("Arg 1", ArgumentParser.Datatype.BOOLEAN, "Test argument as well");
        ap.addArguments("Arg 2", ArgumentParser.Datatype.BOOLEAN, "Test argument");
        ap.addFlag("t");
        ap.addFlag("r");
        String[] inp = {"true","-tr","false"};
        ap.parse(inp);
        assertEquals(true, ap.getValue("t"));
        assertEquals(true, ap.getValue("r"));
    }
    
    @Test
    public void testFourFlagsTwoTogetherTwoSeperateAllTrue() {
        ap.addArguments("Arg 1", ArgumentParser.Datatype.BOOLEAN, "Test argument as well");
        ap.addArguments("Arg 2", ArgumentParser.Datatype.BOOLEAN, "Test argument");
        ap.addFlag("k");
        ap.addFlag("a");
        ap.addFlag("n");
        ap.addFlag("e");
        String[] inp = {"true","-ke","false","-na"};
        ap.parse(inp);
        assertEquals(true, ap.getValue("k"));
        assertEquals(true, ap.getValue("a"));
        assertEquals(true, ap.getValue("n"));
        assertEquals(true, ap.getValue("e"));
    }
    
    @Test
    public void testFourFlagsTwoTogetherOneSeperateThreeTrue() {
        ap.addArguments("Arg 1", ArgumentParser.Datatype.BOOLEAN, "Test argument as well");
        ap.addArguments("Arg 2", ArgumentParser.Datatype.BOOLEAN, "Test argument");
        ap.addFlag("k");
        ap.addFlag("a");
        ap.addFlag("n");
        ap.addFlag("e");
        String[] inp = {"true","-ke","false","-a"};
        ap.parse(inp);
        assertEquals(true, ap.getValue("k"));
        assertEquals(true, ap.getValue("a"));
        assertEquals(false, ap.getValue("n"));
        assertEquals(true, ap.getValue("e"));
    }
    
    @Test
    public void testFourFlagsAllTogetherAllTrue() {
        ap.addArguments("Arg 1", ArgumentParser.Datatype.BOOLEAN, "Test argument as well");
        ap.addArguments("Arg 2", ArgumentParser.Datatype.BOOLEAN, "Test argument");
        ap.addFlag("k");
        ap.addFlag("a");
        ap.addFlag("n");
        ap.addFlag("e");
        String[] inp = {"true","-kane","false"};
        ap.parse(inp);
        assertEquals(true, ap.getValue("k"));
        assertEquals(true, ap.getValue("a"));
        assertEquals(true, ap.getValue("n"));
        assertEquals(true, ap.getValue("e"));
    }
    
    @Test
    public void testThreeFlagsTogetherOneFlaggedTwiceAllTrue() {
        ap.addArguments("Arg 1", ArgumentParser.Datatype.BOOLEAN, "Test argument as well");
        ap.addArguments("Arg 2", ArgumentParser.Datatype.BOOLEAN, "Test argument");
        ap.addFlag("k");
        ap.addFlag("a");
        ap.addFlag("n");
        String[] inp = {"true","-ka","false", "-an"};
        ap.parse(inp);
        assertEquals(true, ap.getValue("k"));
        assertEquals(true, ap.getValue("a"));
        assertEquals(true, ap.getValue("n"));
    }
	
    @Test
    public void testSetDefaultValue(){
        ap.addNamedArgument("name", true);
        ap.addNamedArgDefaultValue("name","20");
        assertEquals("20", (String) ap.getValue("name"));
    }
    
    @Test
    public void testOneRequiredNamedArgument() {
        ap.addArguments("Length", ArgumentParser.Datatype.INT);
        ap.addNamedArgument("Type", true);
        String[] inp = {"7", "--Type", "sphere"};
        ap.parse(inp);
        assertEquals("sphere", ap.getValue("Type"));
    }
    
    @Test
    public void testOneRequiredNamedArgumentOneNotRequired() {
        ap.addArguments("Length", ArgumentParser.Datatype.INT);
        ap.addNamedArgument("Type", true);
        ap.addNamedArgument("Color", false);
        String[] inp = {"7", "--Type", "sphere"};
        ap.parse(inp);
        assertEquals("sphere", ap.getValue("Type"));
    }
    
    @Test
    public void testAddingThreeRestrictionsForPositionalAndUsingThemCorrectly() {
        ap.addArguments("Length", ArgumentParser.Datatype.INT);
        ap.addArguments("Width", ArgumentParser.Datatype.INT);
        ap.addArguments("Height", ArgumentParser.Datatype.INT);
        String[] restrict = {"1", "2", "3"};
        ap.setRestrictions("Length", restrict);
        ap.setRestrictions("Width", restrict);
        ap.setRestrictions("Height", restrict);
        String[] inp = {"1", "2", "3"};
        ap.parse(inp);
        assertEquals(1, ap.getValue("Length"));
        assertEquals(2, ap.getValue("Width"));
        assertEquals(3, ap.getValue("Height"));
    }
    
    @Test
    public void testAddingTwoRestrictionsForPositionalAndUsingThemCorrectly() {
        ap.addArguments("Length", ArgumentParser.Datatype.INT);
        ap.addArguments("Width", ArgumentParser.Datatype.INT);
        ap.addArguments("Height", ArgumentParser.Datatype.INT);
        String[] restrict = {"1", "2", "3"};
        ap.setRestrictions("Length", restrict);
        ap.setRestrictions("Width", restrict);
        String[] inp = {"1", "2", "6"};
        ap.parse(inp);
        assertEquals(1, ap.getValue("Length"));
        assertEquals(2, ap.getValue("Width"));
        assertEquals(6, ap.getValue("Height"));
    }
    
    @Test
    public void testAddingOneRestrictionsForNamedAndUsingThemCorrectly() {
        ap.addArguments("Length", ArgumentParser.Datatype.INT);
        ap.addArguments("Width", ArgumentParser.Datatype.INT);
        ap.addArguments("Height", ArgumentParser.Datatype.INT);
        ap.addNamedArgument("Color", "black", false);
        String[] restrict = {"red", "yellow", "blue"};
        ap.setRestrictions("Color", restrict);
        String[] inp = {"1", "2", "6", "--Color", "red"};
        ap.parse(inp);
        assertEquals(1, ap.getValue("Length"));
        assertEquals(2, ap.getValue("Width"));
        assertEquals(6, ap.getValue("Height"));
        assertEquals("red", ap.getValue("Color"));
    }
    
    @Test
    public void testAddingTwoRestrictionsForNamedAndUsingThemCorrectly() {
        ap.addArguments("Length", ArgumentParser.Datatype.INT);
        ap.addArguments("Width", ArgumentParser.Datatype.INT);
        ap.addArguments("Height", ArgumentParser.Datatype.INT);
        ap.addNamedArgument("Color", "black", false);
        ap.addNamedArgument("Type", true);
        String[] restrictColor = {"red", "yellow", "blue"};
        String[] restrictType = {"sphere", "square"};
        ap.setRestrictions("Color", restrictColor);
        ap.setRestrictions("Type", restrictType);
        String[] inp = {"1", "--Type", "square", "2", "6", "--Color", "red"};
        ap.parse(inp);
        assertEquals(1, ap.getValue("Length"));
        assertEquals(2, ap.getValue("Width"));
        assertEquals(6, ap.getValue("Height"));
        assertEquals("red", ap.getValue("Color"));
        assertEquals("square", ap.getValue("Type"));
    }
    
    
    
    @Test
    public void testLoadXMLGetPositionalArgumentValue() {
        ap.loadXML(".\\Demos\\testXML.xml");
        String[] inp = {"Dog", "8", "true", "3.4", "2", "1", "3"};
        ap.parse(inp);
        assertEquals("Dog", ap.getValue("pet"));
        assertEquals(8, ap.getValue("number"));
        assertEquals(true, ap.getValue("rainy"));
        assertEquals(ap.getValue("bathrooms"), 3.4f);
        assertEquals(2, ap.getValue("Length"));
        assertEquals(1, ap.getValue("Width"));
        assertEquals(3, ap.getValue("Height"));
    }
    
    @Test
    public void testLoadXMLGetNamedArgumentValue() {
        ap.loadXML(".\\Demos\\testXML.xml");
        String[] inp = {"Dog", "8", "true", "3.4", "--Type", "sphere", "2", "1", "3"};
        ap.parse(inp);
        assertEquals("sphere", ap.getValue("Type"));
    }
    
    @Test
    public void testLoadXMLGetNamedArgumentUsingNicknameValue() {
        ap.loadXML(".\\Demos\\testXML.xml");
        String[] inp = {"Dog", "8", "true", "3.4", "-t", "sphere", "2", "1", "3"};
        ap.parse(inp);
        assertEquals("sphere", ap.getValue("Type"));
    }
    
    @Test
    public void testLoadXMLGetTwoNamedArgumentValue() {
        ap.loadXML(".\\Demos\\testXML.xml");
        String[] inp = {"Dog", "8", "true", "3.4", "-t", "sphere", "--Color", "red", "2", "1", "3"};
        ap.parse(inp);
        assertEquals("sphere", ap.getValue("Type"));
        assertEquals("red", ap.getValue("Color"));
    }
    
    @Test
    public void testLoadXMLGetFlagValue() {
        ap.loadXML(".\\Demos\\testXML.xml");
        String[] inp = {"Dog", "8", "true", "3.4", "-x", "2", "1", "3"};
        ap.parse(inp);
        assertEquals(true, ap.getValue("x"));
        assertEquals(false, ap.getValue("w"));
    }
    
    @Test
    public void testLoadXMLGetTwoFlagValue() {
        ap.loadXML(".\\Demos\\testXML.xml");
        String[] inp = {"Dog", "8", "true", "3.4", "-x", "-w", "2", "1", "3"};
        ap.parse(inp);
        assertEquals(true, ap.getValue("x"));
        assertEquals(true, ap.getValue("w"));
    }
    
    
    @Test(expected = InvalidDataTypeException.class)
    public void testEnterFloatWhenShouldBeInt() {
        ap.addArguments("Arg 1", ArgumentParser.Datatype.INT);
        String[] inp = {"1.5"};
        ap.parse(inp);
    }
    
    @Test(expected = InvalidDataTypeException.class)
    public void testEnterIntWhenShouldBeBoolean() {
        ap.addArguments("Arg 1", ArgumentParser.Datatype.BOOLEAN);
        String[] inp = {"1"};
        ap.parse(inp);
    }
    
    @Test(expected = InvalidDataTypeException.class)
    public void testEnterStringWhenShouldBeFloat() {
        ap.addArguments("Arg 1", ArgumentParser.Datatype.FLOAT);
        String[] inp = {"hello"};
        ap.parse(inp);
    }
    
    @Test(expected = PositionalArgumentException.class)
    public void testUserEntersTooManyPositionalArguments() {
        ap.addArguments("Arg 1", ArgumentParser.Datatype.INT);
        String[] inp = {"1", "2"};
        ap.parse(inp);
    }
    
    @Test(expected = PositionalArgumentException.class)
    public void testNotEnoughPositionalArgsGiven() {
        ap.addArguments("Arg 1", ArgumentParser.Datatype.INT);
        ap.addArguments("Arg2", ArgumentParser.Datatype.INT);
        String[] inp = {"1"};
        ap.parse(inp);
    }
    
    @Test(expected = NoArgCalledException.class)
    public void testProductOwnerCallsForValueThatIsNotAnArgument() {
        ap.addArguments("Length", ArgumentParser.Datatype.INT);
        String[] inp = {"5"};
        ap.parse(inp);         
        ap.getValue("Width");
    }
    
    @Test (expected = InvalidNamedArgumentException.class)
    public void testUserEnterInvalidLongArgument() {
        ap.addNamedArgument("type", false);
        String[] inp = {"--circle"};
        ap.parse(inp);
    }
    
    @Test (expected = InvalidNamedArgumentException.class)
    public void testUserEnterInvalidShortArgument() {
        ap.addNamedArgument("type", "",ArgumentParser.Datatype.STRING, "t", false);
        String[] inp = {"-c"};
        ap.parse(inp);
    }
    
    @Test (expected = RequiredNamedArgumentNotGivenException.class)
    public void testRequiredNamedArgumentNotGiven() {
        ap.addNamedArgument("Type", "", ArgumentParser.Datatype.STRING, "t", true);
        ap.addArguments("Length", ArgumentParser.Datatype.INT);
        String[] inp = {"7"};
        ap.parse(inp);
    }
    
}
