package edu.jsu.mcis;

import java.io.*;
import java.util.*;

public class VolumeCalculator {
    
    public static void main(String[] args) {
        ArgumentParser ap = new ArgumentParser();
        ap.addArguments("pet", "Length of the object", "String");
        ap.addArguments("number", "Width of the object", "int");
        ap.addArguments("rainy", "Height of the object", "boolean");
        ap.addArguments("bathrooms", "Other thing", "float");
        ap.addOptionalArgument("Type", " ");
        ap.addOptionalArgument("Color");
        ap.parse(args);
        
        
        String optionalValue = "";
        String one = ap.getStringValue("pet");
        int two = ap.getIntValue("number");
        boolean three = ap.getBooleanValue("rainy");
        float four = ap.getFloatValue("bathrooms");
        optionalValue = ap.getStringValue("Type");
        
        System.out.println(one + " " + two +  " " + three + " " + four + " " + optionalValue);
    }
}