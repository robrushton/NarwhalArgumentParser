
import edu.jsu.mcis.*;

public class VolumeCalculator {
    
    public static void main(String[] args) {
        ArgumentParser ap = new ArgumentParser();
        ap.addArguments("pet", "String", "Its a pet");
        ap.addArguments("number", "int", "some number");
        ap.addArguments("rainy", "boolean", "is it rainy");
        ap.addArguments("bathrooms", "float", "Other thing");
        ap.addArguments("Length", "int", "Length of the object");
        ap.addArguments("Width", "int", "Width of the object");
        ap.addArguments("Height", "int", "Height of the object");
        ap.addNamedArgument("Type", " ", "t");
        ap.addNamedArgument("Color");
        ap.addFlag("x");
        ap.addFlag("w");
        ap.setProgramDescription("Gets volume of last three numbers");
        ap.setProgramName("VolumeCalculator");
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
        System.out.println("x is " + ap.getValue("x"));
        System.out.println("w is " + ap.getValue("w"));
    }
}