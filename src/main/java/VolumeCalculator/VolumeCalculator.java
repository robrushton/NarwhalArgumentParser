

import edu.jsu.mcis.ArgumentParser;
import java.io.*;
import java.util.*;

public class VolumeCalculator {
    
    public static void main(String[] args) {
        ArgumentParser ap = new ArgumentParser();
        ap.addArguments("pet", "Length of the object", "String");
        ap.addArguments("number", "Width of the object", "int");
        ap.addArguments("rainy", "Height of the object", "boolean");
        ap.addArguments("bathrooms", "Other thing", "float");
        ap.addArguments("Length", "Length of the object", "int");
        ap.addArguments("Width", "Width of the object", "int");
        ap.addArguments("Height", "Height of the object", "int");
        ap.addOptionalArgument("Type", " ");
        ap.addOptionalArgument("Color");
        ap.parse(args);
        
        
        String optionalValue = "";
        String one = (String) ap.getValue("pet");
        int two = (int) ap.getValue("number");
        boolean three = (boolean) ap.getValue("rainy");
        float four = (float) ap.getValue("bathrooms");
        optionalValue = (String) ap.getValue("Type");
        int l = (int) ap.getValue("Length");
        int w = (int) ap.getValue("Width");
        int h = (int) ap.getValue("Height");
        
        System.out.println(one + " " + two +  " " + three + " " + four + " " + optionalValue);
        System.out.println("Volume of object is: " + (l * w * h));
    }
}