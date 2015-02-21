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