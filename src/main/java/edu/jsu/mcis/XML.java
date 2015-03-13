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
    
    private ArgumentParser p;
    
    public XML(ArgumentParser a) {
        p = a;
    }
    
    public void loadXML(String fileName) {
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
                        p.addArguments(eName, p.StringToDatatype(eDatatype), eDescription);
                        if (!p.isItEmpty(restrictArray)) {
                            p.setRestrictions(eName, restrictArray);
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
                        p.addNamedArgument(eName, eDefault, p.StringToDatatype(eDatatype), eNickname, Boolean.parseBoolean(eRequired));
                        if (!p.isItEmpty(restrictArray)) {
                            p.setRestrictions(eName, restrictArray);
                        }
                    }
                    else if (e.getAttribute("type").equals("flag")) {
                        String eFlagname = e.getElementsByTagName("flagname").item(0).getTextContent();
                        p.addFlag(eFlagname);
                    }
                    else {
                        throw new MissingUsableArgumentException("No usable arguments found");
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void saveXML(String fileName) {
        //
    }
}