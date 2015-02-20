package VolumeCalculator;

import edu.jsu.mcis.ArgumentParser;

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
        ap.addOptionalArgument("Type", " ", "t");
        ap.addOptionalArgument("Color");
        ap.addFlag("x");
        ap.addFlag("w");
        ap.parse(args);

        String optionalValue = "";
        String one = ap.getValue("pet");
        int two = ap.getValue("number");
        boolean three = ap.getValue("rainy");
        float four = ap.getValue("bathrooms");
        optionalValue = ap.getValue("Type");
        int l = ap.getValue("Length");
        int w = ap.getValue("Width");
        int h = ap.getValue("Height");
        
        System.out.println(one + " " + two +  " " + three + " " + four + " " + optionalValue);
        System.out.println("Volume of object is: " + (l * w * h));
        System.out.println("x is " + ap.checkFlag("x"));
        System.out.println("w is " + ap.checkFlag("w"));
    }
}