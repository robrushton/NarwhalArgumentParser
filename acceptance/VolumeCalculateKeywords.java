import edu.jsu.mcis.*;

public class VolumeCalculateKeywords{
    
    private ArgumentParser ap = new ArgumentParser();
    
    public void startProgramWithArguments(String[] args) {
        ap.parse(args);
    }
    
    public void initializeFirstTest() {
        String[] args = {"dog", "2", "true", "3.5"};
        ap.addArguments("pet", "String", "It's a pet");
        ap.addArguments("number", "int", "It's a number");
        ap.addArguments("rainy", "boolean", "Is it raining");
        ap.addArguments("bathrooms", "float", "how many bathrooms");
        ap.parse(args);
    }
    
    public void initializeSecondTest() {
        String[] args = {"7","5","2"};
        ap.addArguments("length", "int", "Length of the object");
        ap.addArguments("width", "int", "Width of the object");
        ap.addArguments("height", "int", "Height of the object");
        ap.parse(args);
    }
    
    public String getLengths(){
        return Integer.toString((int) ap.getValue("length"));
    }

    public String getWidths(){
        return Integer.toString((int) ap.getValue("width"));
    }

    public String getHeights(){
        return Integer.toString((int) ap.getValue("height"));
    }
    
    public String getPet(){
        return ap.getValue("pet");
    }
    
    public String getNumber(){
        return Integer.toString((int) ap.getValue("number"));
    }
    
    public String getRainy(){
        return Boolean.toString((boolean) ap.getValue("rainy"));
    }
    
    public String getBathrooms(){
        return Float.toString((float) ap.getValue("bathrooms"));
    }

}