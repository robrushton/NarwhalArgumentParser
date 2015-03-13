package edu.jsu.mcis;

import java.util.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
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
                        String eRestrictions = e.getElementsByTagName("restrictions").item(0).getTextContent();
                        String[] restrictArray = eRestrictions.split(" ");
                        ap.addArguments(eName, ap.StringToDatatype(eDatatype), eDescription);
                        if (!ap.isItEmpty(restrictArray)) {
                            ap.setRestrictions(eName, restrictArray);
                        }
                    }
                    else if (e.getAttribute("type").equals("named")) {
                        String eName = e.getElementsByTagName("name").item(0).getTextContent();
                        String eDefault = e.getElementsByTagName("default").item(0).getTextContent();
                        String eDatatype = e.getElementsByTagName("datatype").item(0).getTextContent();
                        String eNickname = e.getElementsByTagName("nickname").item(0).getTextContent();
                        String eRequired = e.getElementsByTagName("required").item(0).getTextContent();
                        String eRestrictions = e.getElementsByTagName("restrictions").item(0).getTextContent();
                        String[] restrictArray = eRestrictions.split(" ");
                        ap.addNamedArgument(eName, eDefault, ap.StringToDatatype(eDatatype), eNickname, Boolean.parseBoolean(eRequired));
                        if (!ap.isItEmpty(restrictArray)) {
                            ap.setRestrictions(eName, restrictArray);
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
    
    public void saveXML(String fileName) {
        //
    }
}