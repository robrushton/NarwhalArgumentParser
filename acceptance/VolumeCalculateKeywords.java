import edu.jsu.mcis.*;

public class VolumeCalculateKeywords{
    
    private ArgumentParser ap = new ArgumentParser();
    
    public void startProgramWithArguments(String[] args) {
        ap.parse(args);
    }
    
    public void initializeFirstTest() {
        String[] args = {"dog", "2", "true", "3.5"};
        ap.addArguments("pet", ArgumentParser.Datatype.STRING, "It's a pet");
        ap.addArguments("number",ArgumentParser.Datatype.INT, "It's a number");
        ap.addArguments("rainy", ArgumentParser.Datatype.BOOLEAN, "Is it raining");
        ap.addArguments("bathrooms", ArgumentParser.Datatype.FLOAT, "how many bathrooms");
        ap.parse(args);
    }
    
    public void initializeSecondTest() {
        String[] args = {"7","5","2"};
        ap.addArguments("length", ArgumentParser.Datatype.INT, "Length of the object");
        ap.addArguments("width", ArgumentParser.Datatype.INT, "Width of the object");
        ap.addArguments("height", ArgumentParser.Datatype.INT, "Height of the object");
        ap.parse(args);
    }

    public void initializeThirdTest(){
        String[] args = {"true","6","cat","8","5"};
        ap.addArguments("boolean", ArgumentParser.Datatype.BOOLEAN, "This is true");
        ap.addArguments("length", ArgumentParser.Datatype.INT, "This is the length");
        ap.addArguments("animal", ArgumentParser.Datatype.STRING, "This is a animal");
        ap.addArguments("width", ArgumentParser.Datatype.INT, "This is the width");
        ap.addArguments("heigth", ArgumentParser.Datatype.INT,"This is the heigth");
        ap.parse(args);
    }

    public void initializeFourTest(){
        String[] args = {"life","blue","round"};
        ap.addArguments("world", ArgumentParser.Datatype.STRING, "This is what the world is");
        ap.addArguments("color", ArgumentParser.Datatype.STRING, "What color");
        ap.addArguments("ball", ArgumentParser.Datatype.STRING, "What is ball");
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

    public String getBoolean(){
        return Boolean.toString((boolean) ap.getValue("boolean"));
    }

    public String getLength(){
        return Integer.toString((int) ap.getValue("length"));
    }

    public String getAnimal(){
        return ap.getValue("animal");
    }

    public String getWidth(){
        return Integer.toString((int) ap.getValue("width"));
    }

    public String getHeigth(){
        return Integer.toString((int) ap.getValue("heigth"));
    }

    public String getWorld(){
        return ap.getValue("world");
    }

    public String getColor(){
        return ap.getValue("color");
    }

    public String getBall(){
        return ap.getValue("ball");
    }
}