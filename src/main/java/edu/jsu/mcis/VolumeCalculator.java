package edu.jsu.mcis;

import java.io.*;
import java.util.*;

public class VolumeCalculator {
    
    public static void main(String[] args) {
        ArgumentParser ap = new ArgumentParser();
        ap.addArguments("Length", "Length of the object", "float");
        ap.addArguments("Width", "Width of the object", "boolean");
        ap.addArguments("Height", "Height of the object", "int");
        ap.addOptionalArgument("Type", " ");
        ap.addOptionalArgument("Color");
        ap.parse(args);
        
        int one = 0;
        int two = 0;
        int three = 0;
        String optionalValue = "";
        one = ap.getIntValue("Length");
        two = ap.getIntValue("Width");
        three = ap.getIntValue("Height");
        optionalValue = ap.getStringValue("Type");
        
        System.out.println((one * three) + " " + optionalValue);
    }
}