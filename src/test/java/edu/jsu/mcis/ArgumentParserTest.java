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
    public void testGetDescriptionValue() {
    ap.addArguments("testString", "String", "It's a string thing");
        assertEquals("It's a string thing", ap.getArgumentDescription("testString"));
    }
	
    @Test
    public void testAddArgumentsNoDescription() {
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
    public void testAddArgumentsWithDescription() {
        String one = "Test Name 1";
        String two = "Test Name 2";
        String three = "Test Name 3";
        ap.addArguments(one, "String", "It's a string thing");
        ap.addArguments(two, "int", "It's an int!");
        ap.addArguments(three, "boolean", "It is a bool");
        String[] myStringArray = {"Hello","5","True"};
        ap.parse(myStringArray);
        assertEquals("Hello", (String) ap.getValue(one));
        assertEquals(5, (int) ap.getValue(two));
        assertEquals(true, (boolean) ap.getValue(three));
        assertEquals("It's a string thing", ap.getArgumentDescription(one));
        assertEquals("It's an int!", ap.getArgumentDescription(two));
        assertEquals("It is a bool", ap.getArgumentDescription(three));
    }
    
    @Test
    public void testParseValuesNotDashH() {
        String one = "Length";
        String two = "Width";
        String three = "Height";
        String four = "Name";
        ap.addArguments(one, "float", "Length of the object");
        ap.addArguments(two, "boolean", "Width of the object");
        ap.addArguments(three, "int", "Height of the object");
        ap.addArguments(four, "String", "Name of object");
        String[] args = {"12.34", "False", "7", "Fred"};
        ap.parse(args);
        assertEquals(12.34, (float) ap.getValue(one), 0.01);
        assertEquals(false, (boolean) ap.getValue(two));
        assertEquals(7, (int) ap.getValue(three));
        assertEquals("Fred", (String) ap.getValue(four));   
    }
   
    @Test
    public void testOptionalArgumentDefaultValue() {
        ap.addArguments("thing", "float", "Length of the object");
        ap.addOptionalArgument("stuff", "5");
        assertEquals("5", (String) ap.getValue("stuff"));
    }
    
    @Test
    public void testOptionalArgumentDefaultValueBeingChanged() {
        ap.addArguments("thing", "float", "Length of the object");
        ap.addOptionalArgument("stuff", "5");
        String[] inp = {"--stuff", "10"};
        ap.parse(inp);
        assertEquals("10", (String) ap.getValue("stuff"));
        
    }  
    
    @Test
    public void testSingleArgumentNoDefaultValue() {
        ap.addArguments("thing", "float", "Length of the object");
        ap.addOptionalArgument("stuff");
        String[] inp = {"--stuff", "5"};
        ap.parse(inp);
        assertEquals("5", (String) ap.getValue("stuff"));
        
    }
    
    @Test
    public void testBooleanMultipleTimesTrueAndFalse() {
        ap.addArguments("Arg 1", "boolean", "This should be true");
        ap.addArguments("Arg 2", "boolean", "This should be false");
        ap.addArguments("Arg 3", "boolean", "This should be true");
        String[] args = {"true", "false", "true"};
        ap.parse(args);
        assertEquals(true, (boolean) ap.getValue("Arg 1"));
        assertEquals(false, (boolean) ap.getValue("Arg 2"));
        assertEquals(true, (boolean) ap.getValue("Arg 3"));  
    }
    
    @Test
    public void testShortOptionalArgumentForLongName() {
        ap.addArguments("Length", "int", "Length of the object");
        ap.addOptionalArgument("type", " ", "t");
        String[] inp = {"-t", "circle", "5"};
        ap.parse(inp);
        assertEquals("circle", (String) ap.getValue("type")); 
    }
    
    @Test
    public void testMultipleShortOptionalArgumentForLongName() {
        ap.addArguments("Length", "int", "Length of the object");
        ap.addArguments("Width", "int", "Width of the object");
        ap.addArguments("Height", "int", "Height of the object");
        ap.addOptionalArgument("type", " ", "t");
        ap.addOptionalArgument("color", " ", "c");
        String[] inp = {"-t", "circle", "5", "-c", "red", "7", "10"};
        ap.parse(inp);
        assertEquals("circle", (String) ap.getValue("type")); 
        assertEquals("red", (String) ap.getValue("color"));
    }
    
    @Test
    public void testOneShortOptionalArgumentForLongNameAndOneLong() {
        ap.addArguments("Length", "int", "Length of the object");
        ap.addArguments("Width", "int", "Width of the object");
        ap.addArguments("Height", "int", "Height of the object");
        ap.addOptionalArgument("type", " ", "t");
        ap.addOptionalArgument("color", " ", "c");
        String[] inp = {"-t", "circle", "5", "--color", "red", "7", "10"};
        ap.parse(inp);
        assertEquals("circle", (String) ap.getValue("type")); 
        assertEquals("red", (String) ap.getValue("color"));
    }
    
    @Test
    public void testDashDashFront() {
        ap.addArguments("Arg 1", "boolean", "This should be true");
        ap.addArguments("Arg 2", "boolean", "This should be false");
        ap.addArguments("Arg 3", "boolean", "This should be true");
        ap.addOptionalArgument("stuff", "5");
        String[] inp = {"--stuff", "4", "true", "false", "true"};
        ap.parse(inp);
        assertEquals("4", (String) ap.getValue("stuff"));
    }  
    
    @Test
    public void testDashDashMiddle() {
        ap.addArguments("Arg 1", "boolean", "This should be true");
        ap.addArguments("Arg 2", "boolean", "This should be false");
        ap.addArguments("Arg 3", "boolean", "This should be true");
        ap.addOptionalArgument("stuff", "5");
        String[] inp = { "true", "false", "--stuff", "4", "true"};
        ap.parse(inp);
        assertEquals("4", (String) ap.getValue("stuff"));
    }  
    
    @Test
    public void testDashDashEnd() {
        ap.addArguments("Arg 1", "boolean", "This should be true");
        ap.addArguments("Arg 2", "boolean", "This should be false");
        ap.addArguments("Arg 3", "boolean", "This should be true");
        ap.addOptionalArgument("stuff", "5");
        String[] inp = {"true", "false", "true", "--stuff", "4"};
        ap.parse(inp);
        assertEquals("4", (String) ap.getValue("stuff"));
    }
    
    @Test
    public void testFlagTrueBeginning() {
        ap.addArguments("Arg 1", "boolean", "This should be true");
        ap.addFlag("t");
        String[] inp = {"-t"};
        ap.parse(inp);
        assertEquals(true, ap.getValue("t"));
    }
    
    @Test
    public void testFlagFalseBeginning() {
        ap.addFlag("t");
        ap.addArguments("Arg 1", "boolean", "This should be true");
        String[] inp = {"true"};
        ap.parse(inp);
        assertEquals(false, ap.getValue("t"));
    }
    
    @Test
    public void testFlagTrueMiddle() {
        ap.addArguments("Arg 1", "boolean", "Test argument as well");
        ap.addArguments("Arg 2", "boolean", "Test argument");
        ap.addFlag("t");
        String[] inp = {"true","-t","false"};
        ap.parse(inp);
        assertEquals(true, ap.getValue("t"));
    }
    
    @Test
    public void testMultipleFlagsSeperatedBothTrue() {
        ap.addArguments("Arg 1", "boolean", "Test argument as well");
        ap.addArguments("Arg 2", "boolean", "Test argument");
        ap.addFlag("t");
        ap.addFlag("r");
        String[] inp = {"true","-t","false", "-r"};
        ap.parse(inp);
        assertEquals(true, ap.getValue("t"));
        assertEquals(true, ap.getValue("r"));
    }
    
    @Test
    public void testMultipleFlagsSeperatedOneTrueOtherFalse() {
        ap.addArguments("Arg 1", "boolean", "Test argument as well");
        ap.addArguments("Arg 2", "boolean", "Test argument");
        ap.addFlag("t");
        ap.addFlag("r");
        String[] inp = {"true","-t","false"};
        ap.parse(inp);
        assertEquals(true, ap.getValue("t"));
        assertEquals(false, ap.getValue("r"));
    }
    
    @Test
    public void testTwoFlagsTogetherInMiddleBothTrue() {
        ap.addArguments("Arg 1", "boolean", "Test argument as well");
        ap.addArguments("Arg 2", "boolean", "Test argument");
        ap.addFlag("t");
        ap.addFlag("r");
        String[] inp = {"true","-tr","false"};
        ap.parse(inp);
        assertEquals(true, ap.getValue("t"));
        assertEquals(true, ap.getValue("r"));
    }
    
    @Test
    public void testFourFlagsTwoTogetherTwoSeperateAllTrue() {
        ap.addArguments("Arg 1", "boolean", "Test argument as well");
        ap.addArguments("Arg 2", "boolean", "Test argument");
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
        ap.addArguments("Arg 1", "boolean", "Test argument as well");
        ap.addArguments("Arg 2", "boolean", "Test argument");
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
        ap.addArguments("Arg 1", "boolean", "Test argument as well");
        ap.addArguments("Arg 2", "boolean", "Test argument");
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
        ap.addArguments("Arg 1", "boolean", "Test argument as well");
        ap.addArguments("Arg 2", "boolean", "Test argument");
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
    public void testProgramDescription(){
        ap.addArguments("DNR 1", "boolean", "Testing the program");
        ap.setProgramDescription("Testing DNR");
        String[] inp = {"the description"};
        ap.parse(inp);
        assertEquals("Testing DNR", ap.getProgramDescription());
    }
    
    @Test
    public void testEnterFloatWhenShouldBeInt() {
        
    }
    
    @Test
    public void testEnterIntWhenShouldBeBoolean() {
        
    }
    
    @Test
    public void testEnterStringWhenShouldBeFloat() {
        
    }
    
    @Test
    public void testProductOwnerCallsForValueNotThatIsNotAnArgument() {
        ap.addArguments("Length", "int");
        String[] input = {"5"};
        ap.parse(input);
        
        /*
        
        ap.getValue("Width");
        Catch exception
        
        */
    }
    
    @Test
    public void testProductOwnerAddsArgumentWithInproperDataType() {
        ap.addArguments("Length", "int");
        
        /*
        ap.addArguments("Length", "type");
        Catch exception
        
        */
        
    }
}
