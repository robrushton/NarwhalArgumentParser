import edu.jsu.mcis.*;
import java.util.*;

public class VolumeCalculator {
    
    public static void main(String[] args) {
        ArgumentParser ap = new ArgumentParser();
        
        ap.addArguments("pet", ArgumentParser.Datatype.STRING, "Its a pet");
        ap.addArguments("number", ArgumentParser.Datatype.INT, "some number");
        ap.addArguments("rainy", ArgumentParser.Datatype.BOOLEAN, "is it rainy");
        ap.addArguments("bathrooms", ArgumentParser.Datatype.FLOAT, "Other thing");
        ap.addArguments("Length", ArgumentParser.Datatype.INT, "Length of the object");
        ap.addArguments("Width", ArgumentParser.Datatype.INT, "Width of the object");
        ap.addArguments("Height", ArgumentParser.Datatype.INT, "Height of the object");
        List<String> petRestrict = Arrays.asList("dog", "cat", "bird");
        ap.setRestrictions("pet", petRestrict);
        ap.addNamedArgument("Type", " ", ArgumentParser.Datatype.STRING, "t", true);
        ap.addNamedArgument("Color", true);
        ap.addFlag("x");
        ap.addFlag("w");
        ap.setProgramDescription("This description can be edited");
        ap.setProgramName("VolumeCalculator");
        System.out.print("1");
        XML.saveXML("demoSaveXML.xml", ap);
        System.out.print("1");
        ap.parse(args);
    }
}