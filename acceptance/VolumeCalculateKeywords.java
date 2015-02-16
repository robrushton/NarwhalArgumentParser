import edu.jsu.mcis.*;

public class VolumeCalculateKeywords{
    
    ArgumentParser ap = new ArgumentParser();
    
    public void startProgramWithArguments(String[] args) {
        ap.parse(args);
    }
    
    public void initializeFirstTest() {
        ap.addArguments("length", "Length of the object", "int");
        ap.addArguments("width", "Width of the object", "int");
        ap.addArguments("height", "Height of the object", "int");
    }
    
    public void initializeSecondTest() {
        ap.addArguments("pet", "It's a pet", "String");
        ap.addArguments("number", "It's a number", "int");
        ap.addArguments("rainy", "Is it raining", "boolean");
        ap.addArguments("bathrooms", "how many bathrooms", "float");
    }
    
    public int getLength (){
        return ap.getIntValue("length");
    }

    public int getWidth(){
        return ap.getIntValue("width");
    }

    public int getHeight(){
        return ap.getIntValue("height");
    }
    
    public String getPet(){
        return ap.getStringValue("pet");
    }
    
    public int getNumber(){
        return ap.getIntValue("number");
    }
    
    public boolean getRainy(){
        return ap.getBooleanValue("rainy");
    }
    
    public float getBathrooms(){
        return ap.getFloatValue("bathrooms");
    }

}