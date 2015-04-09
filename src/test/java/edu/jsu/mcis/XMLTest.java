package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
import org.w3c.dom.Document;
import javax.xml.parsers.*;
import org.custommonkey.xmlunit.*;

public class XMLTest {
    private ArgumentParser ap;
	
    @Before
    public void testXML(){
        ap = new ArgumentParser();
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
        String[] inp = {"dog", "8", "true", "3.4", "--x", "2", "1", "3"};
        ap.parse(inp);
        assertEquals(true, ap.getValue("x"));
        assertEquals(false, ap.getValue("w"));
    }
    
    @Test
    public void testLoadXMLGetTwoFlagValue() {
        ap = XML.loadXML(".\\Demos\\testXML.xml");
        String[] inp = {"dog", "8", "true", "3.4", "--x", "--w", "2", "1", "3"};
        ap.parse(inp);
        assertEquals(true, ap.getValue("x"));
        assertEquals(true, ap.getValue("w"));
    }
    
    @Test
    public void testLoadXMLMutualGroups() {
        ap = XML.loadXML(".\\Demos\\testXMLMutual.xml");
        String[] inp = {"dog", "true", "3.5", "--Gender", "Male", "--Height", "6"};
        ap.parse(inp);
        assertEquals("dog", ap.getValue("pet"));
        assertEquals(true, ap.getValue("rainy"));
        assertEquals(3.5f, ap.getValue("bathrooms"));
        assertEquals("Male", ap.getValue("Gender"));
        assertEquals(6, ap.getValue("Height"));
    }
    
    @Test (expected = mutualExclusionException.class)
    public void testLoadXMLMutualExclusionException() {
        ap = XML.loadXML(".\\Demos\\testXMLMutual.xml");
        String[] inp = {"dog", "true", "3.5", "--Gender", "Male", "--Shape", "Square"};
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
    
    @Test (expected = FileErrorException.class)
    public void testInvalidXMLArgument() {
        ap = XML.loadXML(".\\Demos\\brokenXML.xml");
        
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
