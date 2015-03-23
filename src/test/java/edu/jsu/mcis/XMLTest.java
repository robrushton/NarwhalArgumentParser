package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
import org.w3c.dom.Document;
import javax.xml.parsers.*;
import org.custommonkey.xmlunit.*;

public class XMLTest {
    private ArgumentParser xml;
	
    @Before
    public void testXML(){
        xml = new XML();
    }   
    
    
    @Test
    public void testLoadXMLGetPositionalArgumentValue() {
        xml = XML.loadXML(".\\Demos\\testXML.xml");
        String[] inp = {"dog", "8", "true", "3.4", "2", "1", "3"};
        xml.parse(inp);
        assertEquals("dog", xml.getValue("pet"));
        assertEquals(8, xml.getValue("number"));
        assertEquals(true, xml.getValue("rainy"));
        assertEquals(xml.getValue("bathrooms"), 3.4f);
        assertEquals(2, xml.getValue("Length"));
        assertEquals(1, xml.getValue("Width"));
        assertEquals(3, xml.getValue("Height"));
    }
    
    @Test
    public void testLoadXMLGetNamedArgumentValue() {
        xml = XML.loadXML(".\\Demos\\testXML.xml");
        String[] inp = {"dog", "8", "true", "3.4", "--Type", "sphere", "2", "1", "3"};
        xml.parse(inp);
        assertEquals("dog", xml.getValue("pet"));
        assertEquals("sphere", xml.getValue("Type"));
    }
    
    @Test
    public void testLoadXMLGetNamedArgumentUsingNicknameValue() {
        xml = XML.loadXML(".\\Demos\\testXML.xml");
        String[] inp = {"dog", "8", "true", "3.4", "-t", "sphere", "2", "1", "3"};
        xml.parse(inp);
        assertEquals("sphere", xml.getValue("Type"));
    }
    
    @Test
    public void testLoadXMLGetTwoNamedArgumentValue() {
        xml = XML.loadXML(".\\Demos\\testXML.xml");
        String[] inp = {"dog", "8", "true", "3.4", "-t", "sphere", "--Color", "red", "2", "1", "3"};
        xml.parse(inp);
        assertEquals("sphere", xml.getValue("Type"));
        assertEquals("red", xml.getValue("Color"));
    }
    
    @Test
    public void testLoadXMLGetFlagValue() {
        xml = XML.loadXML(".\\Demos\\testXML.xml");
        String[] inp = {"dog", "8", "true", "3.4", "-x", "2", "1", "3"};
        xml.parse(inp);
        assertEquals(true, xml.getValue("x"));
        assertEquals(false, xml.getValue("w"));
    }
    
    @Test
    public void testLoadXMLGetTwoFlagValue() {
        xml = XML.loadXML(".\\Demos\\testXML.xml");
        String[] inp = {"dog", "8", "true", "3.4", "-x", "-w", "2", "1", "3"};
        xml.parse(inp);
        assertEquals(true, xml.getValue("x"));
        assertEquals(true, xml.getValue("w"));
    }
    
    
    @Test(expected = InvalidDataTypeException.class)
    public void testEnterFloatWhenShouldBeInt() {
        xml.addArguments("Arg 1", ArgumentParser.Datatype.INT);
        String[] inp = {"1.5"};
        xml.parse(inp);
    }
    
    @Test(expected = InvalidDataTypeException.class)
    public void testEnterIntWhenShouldBeBoolean() {
        xml.addArguments("Arg 1", ArgumentParser.Datatype.BOOLEAN);
        String[] inp = {"1"};
        xml.parse(inp);
    }
    
    @Test(expected = InvalidDataTypeException.class)
    public void testEnterStringWhenShouldBeFloat() {
        xml.addArguments("Arg 1", ArgumentParser.Datatype.FLOAT);
        String[] inp = {"hello"};
        xml.parse(inp);
    }
    
    @Test(expected = PositionalArgumentException.class)
    public void testUserEntersTooManyPositionalArguments() {
        xml.addArguments("Arg 1", ArgumentParser.Datatype.INT);
        String[] inp = {"1", "2"};
        xml.parse(inp);
    }
    
    @Test(expected = PositionalArgumentException.class)
    public void testNotEnoughPositionalArgsGiven() {
        xml.addArguments("Arg 1", ArgumentParser.Datatype.INT);
        xml.addArguments("Arg2", ArgumentParser.Datatype.INT);
        String[] inp = {"1"};
        xml.parse(inp);
    }
    
    @Test(expected = NoArgCalledException.class)
    public void testProductOwnerCallsForValueThatIsNotAnArgument() {
        xml.addArguments("Length", ArgumentParser.Datatype.INT);
        String[] inp = {"5"};
        xml.parse(inp);         
        xml.getValue("Width");
    }
    
    @Test (expected = InvalidNamedArgumentException.class)
    public void testUserEnterInvalidLongArgument() {
        xml.addNamedArgument("type", false);
        String[] inp = {"--circle"};
        xml.parse(inp);
    }
    
    @Test (expected = InvalidNamedArgumentException.class)
    public void testUserEnterInvalidShortArgument() {
        xml.addNamedArgument("type", "",ArgumentParser.Datatype.STRING, "t", false);
        String[] inp = {"-c"};
        xml.parse(inp);
    }
    
    @Test (expected = RequiredNamedArgumentNotGivenException.class)
    public void testRequiredNamedArgumentNotGiven() {
        xml.addNamedArgument("Type", "", ArgumentParser.Datatype.STRING, "t", true);
        xml.addArguments("Length", ArgumentParser.Datatype.INT);
        String[] inp = {"7"};
        xml.parse(inp);
    }
    
    @Test (expected = RestrictedValueException.class)
    public void testRestrictionPositionalException() {
        xml.addArguments("Color", ArgumentParser.Datatype.STRING);
        List<String> restrictColor = Arrays.asList("red", "green", "blue");
        xml.setRestrictions("Color", restrictColor);
        String[] inp = {"yellow"};
        xml.parse(inp);
    }

    
    @Test (expected = RestrictedValueException.class)
    public void testRestrictionLongNamedException() {
        xml.addArguments("Length", ArgumentParser.Datatype.INT);
        xml.addNamedArgument("Color", true);
        List<String> restrictColor = Arrays.asList("red", "green", "blue");
        xml.setRestrictions("Color", restrictColor);
        String[] inp = {"1", "--Color", "yellow"};
        xml.parse(inp);
    }
    
    @Test (expected = RestrictedValueException.class)
    public void testRestrictionShortNamedException() {
        xml.addArguments("Length", ArgumentParser.Datatype.INT);
        xml.addNamedArgument("Color", "", ArgumentParser.Datatype.STRING, "c", true);
        List<String> restrictColor = Arrays.asList("red", "green", "blue");
        xml.setRestrictions("Color", restrictColor);
        String[] inp = {"1", "-c", "yellow"};
        xml.parse(inp);
    }
    
    @Test (expected = RestrictedValueException.class)
    public void testLoadXMLCheckPositionalRestrictions() {
        xml = XML.loadXML(".\\Demos\\testXML.xml");
        String[] inp = {"monkey", "8", "true", "3.4", "2", "1", "3"};
        xml.parse(inp);
        assertEquals("monkey", xml.getValue("pet"));
        assertEquals(8, xml.getValue("number"));
        assertEquals(true, xml.getValue("rainy"));
        assertEquals(xml.getValue("bathrooms"), 3.4f);
        assertEquals(2, xml.getValue("Length"));
        assertEquals(1, xml.getValue("Width"));
        assertEquals(3, xml.getValue("Height"));
    }
    
    @Test (expected = RestrictedValueException.class)
    public void testLoadXMLCheckNamedRestrictions() {
        xml = XML.loadXML(".\\Demos\\testXML.xml");
        String[] inp = {"dog", "8", "true", "3.4", "--Color", "yellow", "2", "1", "3"};
        xml.parse(inp);
        assertEquals("dog", xml.getValue("pet"));
        assertEquals(8, xml.getValue("number"));
        assertEquals(true, xml.getValue("rainy"));
        assertEquals(xml.getValue("bathrooms"), 3.4f);
        assertEquals(2, xml.getValue("Length"));
        assertEquals(1, xml.getValue("Width"));
        assertEquals(3, xml.getValue("Height"));
    }
    
     public void setUp() throws Exception {
        XMLUnit.setControlParser(
            "org.xmlache.xerces.jaxp.DocumentBuilderFactoryImpl");
        XMLUnit.setTestParser(
            "org.xmlache.xerces.jaxp.DocumentBuilderFactoryImpl");

        XMLUnit.setSAXParserFactory(
            "org.xmlache.xerces.jaxp.SAXParserFactoryImpl");
        XMLUnit.setTransformerFactory(
            "org.xmlache.xalan.processor.TransformerFactoryImpl");
    }

    @Test
    public void testCreatingNewXML() throws Exception{
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
        xml.addArguments("month", ArgumentParser.Datatype.INT, "Month of the year");
        xml.addArguments("year", ArgumentParser.Datatype.FLOAT, "The year");
        List<String> restrictYear = Arrays.asList("2015");
        xml.setRestrictions("year", restrictYear);
        xml.addNamedArgument("Type", "", ArgumentParser.Datatype.STRING, "t", true);
        List<String> restrictType = Arrays.asList("sphere", "box", "other");
        xml.setRestrictions("Type", restrictType);
        xml.addNamedArgument("Size", "", ArgumentParser.Datatype.BOOLEAN, "s", true);
        xml.addFlag("x");
        String testfile = new String(".\\Demos\\saveXMLControl.xml");
        String newfile = new String(".\\Demos\\saveXMLTest.xml");
        XML.saveXML(newfile, xml);
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
        XML.saveXML(".\\Demos\\readOnlyXML.xml", xml);
    }
    
}
