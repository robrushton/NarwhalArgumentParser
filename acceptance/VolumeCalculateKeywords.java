import edu.jsu.mcis.*;

public class VolumeCalculateKeywords{
    
    private ArgumentParser ap = new ArgumentParser();
    
    public void startProgramWithArguments(String[] args) {
        ap.parse(args);
    }
    
    public void initializeFirstTest() {
        String[] args = {"dog", "2", "true", "3.5"};
        ap.addArguments("pet", "It's a pet", "String");
        ap.addArguments("number", "It's a number", "int");
        ap.addArguments("rainy", "Is it raining", "boolean");
        ap.addArguments("bathrooms", "how many bathrooms", "float");
        ap.parse(args);
    }
    
    public void initializeSecondTest() {
        String[] args = {"7","5","2"};
        ap.addArguments("length", "Length of the object", "int");
        ap.addArguments("width", "Width of the object", "int");
        ap.addArguments("height", "Height of the object", "int");
        ap.parse(args);
    }
    
    public String getLengths(){
        return Integer.toString(ap.getIntValue("length"));
    }

    public String getWidths(){
        return Integer.toString(ap.getIntValue("width"));
    }

    public String getHeights(){
        return Integer.toString(ap.getIntValue("height"));
    }
    
    public String getPet(){
        return ap.getStringValue("pet");
    }
    
    public String getNumber(){
        return Integer.toString(ap.getIntValue("number"));
    }
    
    public String getRainy(){
        return Boolean.toString(ap.getBooleanValue("rainy"));
    }
    
    public String getBathrooms(){
        return Float.toString(ap.getFloatValue("bathrooms"));
    }

}