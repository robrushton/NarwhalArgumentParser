package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
import java.io.*;

import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

import javax.xml.parsers.*;

import org.custommonkey.xmlunit.*;

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
        List<String> restrict = Arrays.asList("1", "2", "3");
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
        List<String> restrict = Arrays.asList("1", "2", "3");
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
        List<String> restrictColor = Arrays.asList("red", "yellow", "blue");
        ap.setRestrictions("Color", restrictColor);
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
        List<String> restrictColor = Arrays.asList("red", "yellow", "blue");
        List<String> restrictType = Arrays.asList("sphere", "square");
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
        ap = XML.loadXML(".\\Demos\\testXML.xml");
        String[] inp = {"dog", "8", "true", "3.4", "2", "1", "3"};
        ap.parse(inp);
        assertEquals("dog", ap.getValue("pet"));
        assertEquals(8, ap.getValue("number"));
        assertEquals(true, ap.getValue("rainy"));
        assertEquals(ap.getValue("bathrooms"), 3.4f);
        assertEquals(2, ap.getValue("Length"));
        assertEquals(1, ap.getValue("Width"));
        assertEquals(3, ap.getValue("Height"));
    }
    
    @Test
    public void testLoadXMLGetNamedArgumentValue() {
        ap = XML.loadXML(".\\Demos\\testXML.xml");
        String[] inp = {"dog", "8", "true", "3.4", "--Type", "sphere", "2", "1", "3"};
        ap.parse(inp);
        assertEquals("dog", ap.getValue("pet"));
        assertEquals("sphere", ap.getValue("Type"));
    }
    
    @Test
    public void testLoadXMLGetNamedArgumentUsingNicknameValue() {
        ap = XML.loadXML(".\\Demos\\testXML.xml");
        String[] inp = {"dog", "8", "true", "3.4", "-t", "sphere", "2", "1", "3"};
        ap.parse(inp);
        assertEquals("sphere", ap.getValue("Type"));
    }
    
    @Test
    public void testLoadXMLGetTwoNamedArgumentValue() {
        ap = XML.loadXML(".\\Demos\\testXML.xml");
        String[] inp = {"dog", "8", "true", "3.4", "-t", "sphere", "--Color", "red", "2", "1", "3"};
        ap.parse(inp);
        assertEquals("sphere", ap.getValue("Type"));
        assertEquals("red", ap.getValue("Color"));
    }
    
    @Test
    public void testLoadXMLGetFlagValue() {
        ap = XML.loadXML(".\\Demos\\testXML.xml");
        String[] inp = {"dog", "8", "true", "3.4", "-x", "2", "1", "3"};
        ap.parse(inp);
        assertEquals(true, ap.getValue("x"));
        assertEquals(false, ap.getValue("w"));
    }
    
    @Test
    public void testLoadXMLGetTwoFlagValue() {
        ap = XML.loadXML(".\\Demos\\testXML.xml");
        String[] inp = {"dog", "8", "true", "3.4", "-x", "-w", "2", "1", "3"};
        ap.parse(inp);
        assertEquals(true, ap.getValue("x"));
        assertEquals(true, ap.getValue("w"));
    }
    
    @Test
    public void testCreateTwoGroupsWithTwoNamedArguments() {
        String[] inp = {"--Color", "red", "--Type", "sphere"};
        ap.addNamedArgument("Color", false);
        ap.addNamedArgument("Type", false);
        ap.addNamedArgument("Size", false);
        List<String> namedArgs1 = new ArrayList<>();
        List<String> namedArgs2 = new ArrayList<>();
        namedArgs1.add("Color");
        namedArgs1.add("Type");
        namedArgs2.add("Size");
        List<List<String>> listOfNamed= new ArrayList<>();
        listOfNamed.add(namedArgs1);
        listOfNamed.add(namedArgs2);
        ap.addNamedGroups(listOfNamed);
        ap.parse(inp);
        assertEquals("red", ap.getValue("Color"));
        assertEquals("sphere", ap.getValue("Type"));
    }
    
    @Test
    public void testMultipleValuesForOnePositional() {
        String[] inp = {"5", "6", "7"};
        ap.addArguments("Dimensions", ArgumentParser.Datatype.INT, "Cube size", 3);
        ap.parse(inp);
        assertEquals(5, ap.getValue("Dimensions", 0));
        assertEquals(6, ap.getValue("Dimensions", 1));
        assertEquals(7, ap.getValue("Dimensions", 2));
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
    
    @Test (expected = RestrictedValueException.class)
    public void testRestrictionPositionalException() {
        ap.addArguments("Color", ArgumentParser.Datatype.STRING);
        List<String> restrictColor = Arrays.asList("red", "green", "blue");
        ap.setRestrictions("Color", restrictColor);
        String[] inp = {"yellow"};
        ap.parse(inp);
    }

    
    @Test (expected = RestrictedValueException.class)
    public void testRestrictionLongNamedException() {
        ap.addArguments("Length", ArgumentParser.Datatype.INT);
        ap.addNamedArgument("Color", true);
        List<String> restrictColor = Arrays.asList("red", "green", "blue");
        ap.setRestrictions("Color", restrictColor);
        String[] inp = {"1", "--Color", "yellow"};
        ap.parse(inp);
    }
    
    @Test (expected = RestrictedValueException.class)
    public void testRestrictionShortNamedException() {
        ap.addArguments("Length", ArgumentParser.Datatype.INT);
        ap.addNamedArgument("Color", "", ArgumentParser.Datatype.STRING, "c", true);
        List<String> restrictColor = Arrays.asList("red", "green", "blue");
        ap.setRestrictions("Color", restrictColor);
        String[] inp = {"1", "-c", "yellow"};
        ap.parse(inp);
    }
    
    @Test (expected = RestrictedValueException.class)
    public void testLoadXMLCheckPositionalRestrictions() {
        ap = XML.loadXML(".\\Demos\\testXML.xml");
        String[] inp = {"monkey", "8", "true", "3.4", "2", "1", "3"};
        ap.parse(inp);
        assertEquals("monkey", ap.getValue("pet"));
        assertEquals(8, ap.getValue("number"));
        assertEquals(true, ap.getValue("rainy"));
        assertEquals(ap.getValue("bathrooms"), 3.4f);
        assertEquals(2, ap.getValue("Length"));
        assertEquals(1, ap.getValue("Width"));
        assertEquals(3, ap.getValue("Height"));
    }
    
    @Test (expected = RestrictedValueException.class)
    public void testLoadXMLCheckNamedRestrictions() {
        ap = XML.loadXML(".\\Demos\\testXML.xml");
        String[] inp = {"dog", "8", "true", "3.4", "--Color", "yellow", "2", "1", "3"};
        ap.parse(inp);
        assertEquals("dog", ap.getValue("pet"));
        assertEquals(8, ap.getValue("number"));
        assertEquals(true, ap.getValue("rainy"));
        assertEquals(ap.getValue("bathrooms"), 3.4f);
        assertEquals(2, ap.getValue("Length"));
        assertEquals(1, ap.getValue("Width"));
        assertEquals(3, ap.getValue("Height"));
    }
    
     public void setUp() throws Exception {
        XMLUnit.setControlParser(
            "org.apache.xerces.jaxp.DocumentBuilderFactoryImpl");
        XMLUnit.setTestParser(
            "org.apache.xerces.jaxp.DocumentBuilderFactoryImpl");

        XMLUnit.setSAXParserFactory(
            "org.apache.xerces.jaxp.SAXParserFactoryImpl");
        XMLUnit.setTransformerFactory(
            "org.apache.xalan.processor.TransformerFactoryImpl");
    }

    @Test
    public void testCreatingNewXML() throws Exception{
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
        ap.addArguments("month", ArgumentParser.Datatype.INT, "Month of the year");
        ap.addArguments("year", ArgumentParser.Datatype.FLOAT, "The year");
        List<String> restrictYear = Arrays.asList("2015");
        ap.setRestrictions("year", restrictYear);
        ap.addNamedArgument("Type", "", ArgumentParser.Datatype.STRING, "t", true);
        List<String> restrictType = Arrays.asList("sphere", "box", "other");
        ap.setRestrictions("Type", restrictType);
        ap.addNamedArgument("Size", "", ArgumentParser.Datatype.BOOLEAN, "s", true);
        ap.addFlag("x");
        String testfile = new String(".\\Demos\\saveXMLControl.xml");
        String newfile = new String(".\\Demos\\saveXMLTest.xml");
        XML.saveXML(newfile, ap);
        Document control = docBuilder.parse(testfile);
        Document test = docBuilder.parse(newfile);
        
        XMLAssert.assertXMLEqual(control, test);
    }
    
    @Test (expected =  FileErrorException.class)
    public void testFileNotFoundXML(){
        XML.loadXML(".\\Demos\\fakeXML.xml");
    }
    
    @Test (expected =  FileErrorException.class)
    public void testFailToWriteToFileXML(){
        XML.saveXML(".\\Demos\\readOnlyXML.xml", ap);
    }
    
}
