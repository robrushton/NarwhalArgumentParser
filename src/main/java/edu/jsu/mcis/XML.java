package edu.jsu.mcis;

import java.util.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.*;
import javax.xml.bind.*;

public class XML extends ArgumentParser {
    
    
    public static ArgumentParser loadXML(String fileName) {
        ArgumentParser ap = new ArgumentParser();
        try {
            File xmlFile = new File(fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
            Document xmlDoc = docBuilder.parse(xmlFile);
            xmlDoc.getDocumentElement().normalize();
            NodeList nodeList = xmlDoc.getElementsByTagName("argument");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element e = (Element) node;
                    if (e.getAttribute("type").equals("positional")) {
                        String eName = e.getElementsByTagName("name").item(0).getTextContent();
                        String eDatatype = e.getElementsByTagName("datatype").item(0).getTextContent();
                        String eDescription = e.getElementsByTagName("description").item(0).getTextContent();
                        ArrayList<String> restrictList = new ArrayList<String>();
                        NodeList restrict = e.getElementsByTagName("restrict");
                        for (int k = 0; k < restrict.getLength(); k++) {
                            restrictList.add(restrict.item(k).getTextContent());
                        }
                        ap.addArguments(eName, ap.StringToDatatype(eDatatype), eDescription);
                        if (!ap.isItEmpty(restrictList)) {
                            ap.setRestrictions(eName, restrictList);
                        }
                    }
                    else if (e.getAttribute("type").equals("named")) {
                        String eName = e.getElementsByTagName("name").item(0).getTextContent();
                        String eDefault = e.getElementsByTagName("default").item(0).getTextContent();
                        String eDatatype = e.getElementsByTagName("datatype").item(0).getTextContent();
                        String eNickname = e.getElementsByTagName("nickname").item(0).getTextContent();
                        String eRequired = e.getElementsByTagName("required").item(0).getTextContent();
                        ArrayList<String> restrictList = new ArrayList<String>();
                        NodeList restrict = e.getElementsByTagName("restrict");
                        for (int k = 0; k < restrict.getLength(); k++) {
                            restrictList.add(restrict.item(k).getTextContent());
                        }
                        ap.addNamedArgument(eName, eDefault, ap.StringToDatatype(eDatatype), eNickname, Boolean.parseBoolean(eRequired));
                        if (!ap.isItEmpty(restrictList)) {
                            ap.setRestrictions(eName, restrictList);
                        }
                    }
                    else if (e.getAttribute("type").equals("flag")) {
                        String eFlagname = e.getElementsByTagName("flagname").item(0).getTextContent();
                        ap.addFlag(eFlagname);
                    }
                    else {
                        throw new MissingUsableArgumentException("No usable arguments found");
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return ap;
    }
    
    public static void saveXML(String fileName, ArgumentParser a) {
        ArgumentParser ap = a;
        File newXML = new File(fileName);
        try {
            PrintWriter printer = new PrintWriter(newXML);
            
            printer.println("<?xml version=\"1.0\"?>");
            printer.println("<parser>");
            
            for (Map.Entry<String, PositionalArgument> entry : ap.positionalArgs.entrySet()) {
                String key = entry.getKey();

                printer.println("\t<argument type=\"positional\">");
                printer.println("\t\t<name>" + ap.positionalArgs.get(key).getName() + "</name>");
                printer.println("\t\t<datatype>" + ap.datatypeToString(ap.positionalArgs.get(key).getDataType()) + "</datatype>");
                printer.println("\t\t<description>" + ap.positionalArgs.get(key).getDescription() + "</description>");
                if (!ap.isItEmpty(ap.positionalArgs.get(key).getRestrictions())) {
                    printer.println("\t\t<restrictions>");
                    for (Iterator<String> i = ap.positionalArgs.get(key).getRestrictions().iterator(); i.hasNext();) {
                        printer.println("\t\t\t<restrict>" + i.next() + "</restrict>");
                    }
                    printer.println("\t\t</restrictions>");
                }
                else {
                    printer.println("\t\t<restrictions></restrictions>");
                }
                printer.println("\t</argument>");
                
            }
            
            for (Map.Entry<String, NamedArgument> entry : ap.namedArgs.entrySet()) {
                String key = entry.getKey();
                
                printer.println("\t<argument type=\"named\">");
                printer.println("\t\t<name>" + ap.namedArgs.get(key).getName() + "</named>" );
                printer.println("\t\t<default>" + ap.namedArgs.get(key).getDefaultValue() + "</default>" );
                printer.println("\t\t<datatype>" + ap.datatypeToString(ap.namedArgs.get(key).getDataType()) + "</datatype>" );
                printer.println("\t\t<nickname>" + ap.namedArgs.get(key).getNickname() + "</nickname>" );
                printer.println("\t\t<required>" + ap.namedArgs.get(key).getRequired() + "</required>" );
                if (ap.isItEmpty(ap.namedArgs.get(key).getRestrictions())) {
                    printer.println("\t\t<restrictions></restrictions>");
                }
                else {
                    printer.println("\t\t<restrictions>");
                    for (Iterator<String> i = ap.namedArgs.get(key).getRestrictions().iterator(); i.hasNext();) {
                        printer.println("\t\t\t<restrict>" + i.next() + "</restrict>");
                    }
                    printer.println("\t\t</restrictions>");
                }
                printer.println("\t</argument>");
            }
            
            for (Map.Entry<String, Boolean> entry : ap.flagArgs.entrySet()) {
                String key = entry.getKey();
                
                printer.println("\t<argument type=\"flag\">");
                printer.println("\t\t<flagname>" + key + "</flagname>");
                printer.println("\t</argument>");
            }
            printer.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}