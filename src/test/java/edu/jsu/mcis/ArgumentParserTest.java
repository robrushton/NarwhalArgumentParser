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
        ap.addArguments(one, "String");
        ap.addArguments(two, "int");
        ap.addArguments(three, "boolean");
        String[] myStringArray = {"Hello","5","true"};
        try{       
            ap.parse(myStringArray);
        }catch(ArgumentParser.InvalidLongArgumentException e){
        }catch(ArgumentParser.InvalidShortArgumentException e){}
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
        try{       
            ap.parse(myStringArray);
        }catch(ArgumentParser.InvalidLongArgumentException e){}
        catch(ArgumentParser.InvalidShortArgumentException e){}
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
        ap.addArguments(one, "float", "Length of the object");
        ap.addArguments(two, "boolean", "Width of the object");
        ap.addArguments(three, "int", "Height of the object");
        ap.addArguments(four, "String", "Name of object");
        String[] args = {"12.34", "False", "7", "Fred"};
        try{       
            ap.parse(args);
        }catch(ArgumentParser.InvalidLongArgumentException e){}
        catch(ArgumentParser.InvalidShortArgumentException e){}
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
        ap.addArguments(one, "float", "Length of the object");
        ap.addArguments(two, "boolean", "Width of the object");
        ap.addArguments(three, "int", "Height of the object");
        ap.addArguments(four, "String", "Name of object");
        ap.setProgramDescription("This program prints arguments");
        ap.setProgramName("ArgumentPrinter");
        String[] args = {"12.34", "False", "7", "Fred"};
        try{       
            ap.parse(args);
        }catch(ArgumentParser.InvalidLongArgumentException e){}
        catch(ArgumentParser.InvalidShortArgumentException e){}
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
        try{       
            ap.parse(inp);
        }catch(ArgumentParser.InvalidLongArgumentException e){}
        catch(ArgumentParser.InvalidShortArgumentException e){}
        assertEquals("10", (String) ap.getValue("stuff"));
        
    }  
    
    @Test
    public void testSingleArgumentNoDefaultValue() {
        ap.addArguments("thing", "float", "Length of the object");
        ap.addOptionalArgument("stuff");
        String[] inp = {"--stuff", "5"};
        try{       
            ap.parse(inp);
        }catch(ArgumentParser.InvalidLongArgumentException e){}
        catch(ArgumentParser.InvalidShortArgumentException e){}
        assertEquals("5", (String) ap.getValue("stuff"));
        
    }
    
    @Test
    public void testBooleanMultipleTimesTrueAndFalse() {
        ap.addArguments("Arg 1", "boolean", "This should be true");
        ap.addArguments("Arg 2", "boolean", "This should be false");
        ap.addArguments("Arg 3", "boolean", "This should be true");
        String[] args = {"true", "false", "true"};
        try{       
            ap.parse(args);
        }catch(ArgumentParser.InvalidLongArgumentException e){}
        catch(ArgumentParser.InvalidShortArgumentException e){}
        assertEquals(true, (boolean) ap.getValue("Arg 1"));
        assertEquals(false, (boolean) ap.getValue("Arg 2"));
        assertEquals(true, (boolean) ap.getValue("Arg 3"));  
    }
    
    @Test
    public void testShortOptionalArgumentForLongName() {
        ap.addArguments("Length", "int", "Length of the object");
        ap.addOptionalArgument("type", " ", "t");
        String[] inp = {"-t", "circle", "5"};
        try{       
            ap.parse(inp);
        }catch(ArgumentParser.InvalidLongArgumentException e){}
        catch(ArgumentParser.InvalidShortArgumentException e){}
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
        try{       
            ap.parse(inp);
        }catch(ArgumentParser.InvalidLongArgumentException e){}
        catch(ArgumentParser.InvalidShortArgumentException e){}
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
        try{       
            ap.parse(inp);
        }catch(ArgumentParser.InvalidLongArgumentException e){}
        catch(ArgumentParser.InvalidShortArgumentException e){}
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
        try{       
            ap.parse(inp);
        }catch(ArgumentParser.InvalidLongArgumentException e){}
        catch(ArgumentParser.InvalidShortArgumentException e){}
        assertEquals("4", (String) ap.getValue("stuff"));
    }  
    
    @Test
    public void testDashDashMiddle() {
        ap.addArguments("Arg 1", "boolean", "This should be true");
        ap.addArguments("Arg 2", "boolean", "This should be false");
        ap.addArguments("Arg 3", "boolean", "This should be true");
        ap.addOptionalArgument("stuff", "5");
        String[] inp = { "true", "false", "--stuff", "4", "true"};
        try{       
            ap.parse(inp);
        }catch(ArgumentParser.InvalidLongArgumentException e){}
        catch(ArgumentParser.InvalidShortArgumentException e){}
        assertEquals("4", (String) ap.getValue("stuff"));
    }  
    
    @Test
    public void testDashDashEnd() {
        ap.addArguments("Arg 1", "boolean", "This should be true");
        ap.addArguments("Arg 2", "boolean", "This should be false");
        ap.addArguments("Arg 3", "boolean", "This should be true");
        ap.addOptionalArgument("stuff", "5");
        String[] inp = {"true", "false", "true", "--stuff", "4"};
        try{       
            ap.parse(inp);
        }catch(ArgumentParser.InvalidLongArgumentException e){}
        catch(ArgumentParser.InvalidShortArgumentException e){}
        assertEquals("4", (String) ap.getValue("stuff"));
    }
    
    @Test
    public void testFlagTrueBeginning() {
        ap.addArguments("Arg 1", "boolean", "This should be true");
        ap.addFlag("t");
        String[] inp = {"-t"};
        try{       
            ap.parse(inp);
        }catch(ArgumentParser.InvalidLongArgumentException e){}
        catch(ArgumentParser.InvalidShortArgumentException e){}
        assertEquals(true, ap.getValue("t"));
    }
    
    @Test
    public void testFlagFalseBeginning() {
        ap.addFlag("t");
        ap.addArguments("Arg 1", "boolean", "This should be true");
        String[] inp = {"true"};
        try{       
            ap.parse(inp);
        }catch(ArgumentParser.InvalidLongArgumentException e){}
        catch(ArgumentParser.InvalidShortArgumentException e){}
        assertEquals(false, ap.getValue("t"));
    }
    
    @Test
    public void testFlagTrueMiddle() {
        ap.addArguments("Arg 1", "boolean", "Test argument as well");
        ap.addArguments("Arg 2", "boolean", "Test argument");
        ap.addFlag("t");
        String[] inp = {"true","-t","false"};
        try{       
            ap.parse(inp);
        }catch(ArgumentParser.InvalidLongArgumentException e){}
        catch(ArgumentParser.InvalidShortArgumentException e){}
        assertEquals(true, ap.getValue("t"));
    }
    
    @Test
    public void testMultipleFlagsSeperatedBothTrue() {
        ap.addArguments("Arg 1", "boolean", "Test argument as well");
        ap.addArguments("Arg 2", "boolean", "Test argument");
        ap.addFlag("t");
        ap.addFlag("r");
        String[] inp = {"true","-t","false", "-r"};
        try{       
            ap.parse(inp);
        }catch(ArgumentParser.InvalidLongArgumentException e){}
        catch(ArgumentParser.InvalidShortArgumentException e){}
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
        try{       
            ap.parse(inp);
        }catch(ArgumentParser.InvalidLongArgumentException e){}
catch(ArgumentParser.InvalidShortArgumentException e){}        
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
        try{       
            ap.parse(inp);
        }catch(ArgumentParser.InvalidLongArgumentException e){}
        catch(ArgumentParser.InvalidShortArgumentException e){}
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
        try{       
            ap.parse(inp);
        }catch(ArgumentParser.InvalidLongArgumentException e){}
        catch(ArgumentParser.InvalidShortArgumentException e){}
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
        try{       
            ap.parse(inp);
        }catch(ArgumentParser.InvalidLongArgumentException e){}
        catch(ArgumentParser.InvalidShortArgumentException e){}
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
        try{       
            ap.parse(inp);
        }catch(ArgumentParser.InvalidLongArgumentException e){}
        catch(ArgumentParser.InvalidShortArgumentException e){}
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
        try{       
            ap.parse(inp);
        }catch(ArgumentParser.InvalidLongArgumentException e){}
        catch(ArgumentParser.InvalidShortArgumentException e){}
        assertEquals(true, ap.getValue("k"));
        assertEquals(true, ap.getValue("a"));
        assertEquals(true, ap.getValue("n"));
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
        try{       
            ap.parse(input);
        }catch(ArgumentParser.InvalidLongArgumentException e){}
        catch(ArgumentParser.InvalidShortArgumentException e){}
        
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
   /* 
    @Test(expected = ArgumentParser.InvalidLongArgumentException.class)
    public void testInvalidLongArgumentException(){
        
    }
    */
}
