package edu.jsu.mcis;
import java.util.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import javax.xml.bind.*;

public class ArgumentParser { 
	
    private Map<String, PositionalArgument> positionalArgs;
    private Map<String, NamedArgument> namedArgs;
    private Map<String, Boolean> flagArgs;
    private Map<String, String> nicknames;
    public static List<Argument> myArgs;
    public enum Datatype {STRING, FLOAT, INT, BOOLEAN};
    private String programDescription;
    private String programName;
    private int numPositionalArgs;
    
    public ArgumentParser() {
        this.programName = "";
        this.programDescription = "";
        this.nicknames = new HashMap<>();
        this.flagArgs = new HashMap<>();
        this.namedArgs = new HashMap<>();
        this.positionalArgs = new LinkedHashMap<>();
        
    }
    
    public void parse(String[] args) {
        Queue<String> userInputQueue = new LinkedList<String>();
        convertArrayToQueue(args, userInputQueue);
        int positionalPlaced = 0;
        while (!userInputQueue.isEmpty()) {
            String userInput = userInputQueue.poll();
            if (isLongNamedArgument(userInput)) {
                parseLongNamedArguments(userInput, userInputQueue);
            }
            else if (isShortNamedArgument(userInput)) {
                parseShortNamedArguments(userInput, userInputQueue);
            }
            else {
                positionalPlaced = parsePositionalArguments(userInput, userInputQueue, positionalPlaced);
            }
        }
        checkIfEnoughPositionalArgsGiven(positionalPlaced);
        checkIfAllRequiredNamedArgumentsGiven();
    }
    
    private int parsePositionalArguments(String userInput, Queue<String> userInputQueue, int positionalPlaced) {
        if (isItTooManyArgs(positionalPlaced)) {
            setValue((String) positionalArgs.keySet().toArray()[positionalPlaced], userInput);
            if (isDataTypeEqualTo(Datatype.INT, positionalPlaced)) {
                try {
                    Integer.parseInt(userInput);
                } 
                catch (java.lang.NumberFormatException e) {
                    throw new InvalidDataTypeException("\n " + userInput + ". Value is invalid data type. Expected int");
                }
            }
            else if (isDataTypeEqualTo(Datatype.FLOAT, positionalPlaced)) {
                try {
                    Float.parseFloat(userInput);
                }
                catch (java.lang.NumberFormatException e) {
                    throw new InvalidDataTypeException("\n " + userInput + ". Value is invalid data type. Expected float");
                }
            }
            else if (isDataTypeEqualTo(Datatype.BOOLEAN, positionalPlaced)) {
                if (!isItAValidBoolean(userInput)) {
                    throw new InvalidDataTypeException("\n " + userInput + ". Value is invalid data type. Expected boolean");
                }
            }
            positionalPlaced++;
        } 
        else {
            throw new PositionalArgumentException("\n " + userInput + ". Too many positional arguments.");
        }
        return positionalPlaced;
    }
    
    private void parseLongNamedArguments(String userInput, Queue<String> userInputQueue) {
        if (checkIfNamedArgument(userInput)) {
            setNamedArgument(userInput, userInputQueue);
        }
        else if (isHelpArgument(userInput)) {
            printHelpInfo();
        }
        else {
            throw new InvalidNamedArgumentException("\n " + userInput + " '--' value not defined.");
        }
    }
    
    private void parseShortNamedArguments(String userInput, Queue<String> userInputQueue) {
        if (isHelpArgument(userInput)) {
            printHelpInfo();
        } 
        else if (isItAFlagLong(userInput.substring(1))) {
            flipFlag(userInput.substring(1));
        } 
        else if (isItANickname(userInput.substring(1))) {
            setNamedArgument(userInput, userInputQueue);
        } 
        else {
            throw new InvalidNamedArgumentException("\n " + userInput + " '-' value not defined.");
        }
    }
    
    
    
    private void checkIfAllRequiredNamedArgumentsGiven() {
        for (String s : namedArgs.keySet()) {
            if (isArgumentRequiredButNotGiven(s)) {
                throw new RequiredNamedArgumentNotGivenException("\n Named Argument " + namedArgs.get(s) + " is required");
            }
        }
    }
    
    private void checkIfEnoughPositionalArgsGiven(int given) {
        if (positionalArgs.size() != given) {
            throw new PositionalArgumentException("\n Not enough positional arguments.");
        }
    }
    
    private boolean isArgumentRequiredButNotGiven(String s) {
        return namedArgs.get(s).getRequired() && !namedArgs.get(s).getWasEntered();
    }
    
    private boolean isItTooManyArgs(int given) {
        return positionalArgs.size() > given;
    }
    
    private boolean isItAFlagLong(String userInput) {
        for (int i = 0; i < userInput.length(); i++){
            String singleFlag = userInput.substring(i, i+1);
            if (!isItAFlag(singleFlag)) {
                return false;
            }
        }
        return true;
    }
    
    private void flipFlag(String userInput) {
        for (int i = 0; i < userInput.length(); i++) {
            flagArgs.put(userInput.substring(i, i+1), Boolean.TRUE);
        }
    }
    
    private void convertArrayToQueue(String[] args, Queue<String> userInputQueue) {
        userInputQueue.addAll(Arrays.asList(args));
    }
    
    private boolean checkIfNamedArgument(String s) {
        return namedArgs.containsKey(s.substring(2));
    }
    
    private boolean isItAValidBoolean(String userInput) {
        return (userInput.equals("false") || userInput.equals("true") ||
                userInput.equals("True") || userInput.equals("False"));
    }
    
    private boolean isLongNamedArgument(String userInput) {
        return (userInput.startsWith("--"));
    }
    
    private boolean isShortNamedArgument(String userInput) {
        return (userInput.startsWith("-"));
    }
       
    private boolean isDataTypeEqualTo(Datatype dataType, int count) {
        return positionalArgs.get((String) positionalArgs.keySet().toArray()[count]).getDataType() == dataType;
    }
    
    private void setNamedArgument(String userInput, Queue<String> userInputQueue) {
        if (nicknames.containsKey(userInput.substring(1))) {
            namedArgs.get(nicknames.get(userInput.substring(1))).setValue(userInputQueue.poll());
            namedArgs.get(nicknames.get(userInput.substring(1))).setWasEntered(true);
        }
        else {
            namedArgs.get(userInput.substring(2)).setValue(userInputQueue.poll());
            namedArgs.get(userInput.substring(2)).setWasEntered(true);
        }
    }
     
    private boolean isHelpArgument(String s) {
        return s.equals("-h") || s.equals("--Help");
    }
    
    private void setValue(String s, String n) {
        positionalArgs.get(s).setValue(n);
    }
    
    public <T> T getValue(String s) {
        if (isItAPositional(s)) {
            if (positionalArgs.get(s).getDataType() == Datatype.STRING) {
                return (T) positionalArgs.get(s).getValue();
            } 
            else if (positionalArgs.get(s).getDataType() == Datatype.INT) {
                return (T) new Integer(Integer.parseInt(positionalArgs.get(s).getValue()));
            } 
            else if (positionalArgs.get(s).getDataType() == Datatype.FLOAT) {
                return (T) new Float(Float.parseFloat(positionalArgs.get(s).getValue()));
            } 
            else if (positionalArgs.get(s).getDataType() == Datatype.BOOLEAN) {
                return (T) new Boolean(Boolean.parseBoolean(positionalArgs.get(s).getValue()));
            }
        } 
        else if (isItAnNamed(s)) {
            return (T) namedArgs.get(s).getValue();
        }
        else if (isItAFlag(s)) {
            return (T) flagArgs.get(s);
        }
        throw new NoArgCalledException("\n " + s + " is not a valid argument.");
    }
    
    private boolean isItAPositional(String s) {
        return positionalArgs.containsKey(s);
    }
    
    private boolean isItAnNamed(String s) {
        return namedArgs.containsKey(s);
    }
    
    private boolean isItANickname(String userInput) {
        return nicknames.containsKey(userInput);
    }
     
    private boolean isItAFlag(String userInput) {
        return flagArgs.containsKey(userInput);
    }
	
    public void addNamedArgDefaultValue(String name, String defaultValue){
        namedArgs.get(name).setValue(defaultValue);
    }
    
    public void addNamedArgument(String name) {
        NamedArgument oa = new NamedArgument();
        namedArgs.put(name, oa);
    }
    
    public void addNamedArgument(String name, boolean required) {
        addNamedArgument(name);
        setRequired(name, required);
    }
    
    public void addNamedArgument(String name, String defaultValue) {
        addNamedArgument(name);
        NamedArgument oa = getNamedArgument(name);
        oa.setValue(defaultValue);
    }
    
    public void addNamedArgument(String name, String defaultValue, boolean required) {
        addNamedArgument(name, defaultValue);
        setRequired(name, required);
    }
    
    public void addNamedArgument(String name, String defaultValue, String nickname) {
        addNamedArgument(name, defaultValue);
        nicknames.put(nickname, name);
        setNickname(name, nickname);
    }
    
    public void addNamedArgument(String name, String defaultValue, String nickname, boolean required) {
        addNamedArgument(name, defaultValue, nickname);
        setRequired(name, required);
    }
    
    private void setRequired(String name, boolean required) {
        NamedArgument oa = getNamedArgument(name);
        oa.setRequired(required);
    }
    
    private NamedArgument getNamedArgument(String key) {
        return namedArgs.get(key);
    }
    
    private void setNickname(String s, String n) {
        namedArgs.get(s).setNickname(n);
    }
    
    private void printHelpInfo() {
        int printLoopCount = 0;
        System.out.print("\nUsage Information: java " + programName + " ");
        for (String k : positionalArgs.keySet()) {
            if (printLoopCount < numPositionalArgs) {
                System.out.print(k + " ");
            }
            printLoopCount++;
        }
        System.out.println();
        System.out.println(programDescription);
        System.out.println();
        System.out.println("Arguments: ");
        printLoopCount = 0;
        for (String s : positionalArgs.keySet()) {
            if (printLoopCount < numPositionalArgs) {
                if (!positionalArgs.get(s).getDescription().equals("")) {
                    System.out.println(s + ": " + positionalArgs.get(s).getDescription());
                }
                else {
                    System.out.println(s + ": No Description Given");
                }
            }
            printLoopCount++;
        }
        System.exit(0);
    }
    
	
    public void addArguments(String name, Datatype dataType) {
        PositionalArgument ao = new PositionalArgument();
        positionalArgs.put(name, ao);
        ao.setDataType(dataType);
        numPositionalArgs++;
    }
    
    public void addArguments(String name, Datatype dataType, String description) {
        addArguments(name, dataType);
        positionalArgs.get(name).setDescription(description);
    }
    
    public void setProgramDescription(String s) {
        programDescription = s;
    }
    
    public void setProgramName(String s) {
        programName = s;
    }
    
    public void addFlag(String s) {
        flagArgs.put(s, Boolean.FALSE);
    }
    
    public void createXML() throws JAXBException{
        myArgs = new ArrayList<Argument>();
        JAXBContext context = JAXBContext.newInstance(Argument.class, PositionalArgument.class, NamedArgument.class);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        for (Map.Entry<String, PositionalArgument> p : positionalArgs.entrySet()){
            String name = p.getKey();
            try {
                myArgs.add(positionalArgs.get(name));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        for (Map.Entry<String, NamedArgument> n : namedArgs.entrySet()){
            String name = n.getKey();
            try {
                myArgs.add(namedArgs.get(name));
            } catch  (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        Argument mine = new Argument();
        mine.setArgument(myArgs);
        m.marshal(mine, System.out);
    }
    
    public void loadXML(String s) {
        try {
            File xmlFile = new File(s);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
            Document xmlDoc = docBuilder.parse(s);
            xmlDoc.getDocumentElement().normalize();
            NodeList nodeList = xmlDoc.getElementsByTagName("argument");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element e = (Element) node;
                    if (e.getAttribute("type").equals("positional")) {
                        String eName = e.getElementsByTagName("name").item(0).getTextContent();
                        String eDatatype = e.getElementsByTagName("datatype").item(0).getTextContent();
                        String eDescription = e.getElementsByTagName("description").item(0).getTextContent();
                        addArguments(eName, StringToDatatype(eDatatype), eDescription);
                    }
                    else if (e.getAttribute("type").equals("named")) {
                        String eName = e.getElementsByTagName("name").item(0).getTextContent();
                        String eDefault = e.getElementsByTagName("default").item(0).getTextContent();
                        String eNickname = e.getElementsByTagName("nickname").item(0).getTextContent();
                        String eRequired = e.getElementsByTagName("required").item(0).getTextContent();
                        addNamedArgument( eName, eDefault, eNickname, Boolean.parseBoolean(eRequired));
                    }
                    else if (e.getAttribute("type").equals("flag")) {
                        String eFlagname = e.getElementsByTagName("flagname").item(0).getTextContent();
                        addFlag(eFlagname);
                    }
                    else {
                        throw new MissingUsableArgumentException("No usable arguments found");
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private Datatype StringToDatatype(String data) {
        if (data.equals("String")) {
            return Datatype.STRING;
        } 
        else if (data.equals("float")) {
            return Datatype.FLOAT;
        } 
        else if (data.equals("int")) {
            return Datatype.INT;
        } 
        else if (data.equals("boolean")) {
            return Datatype.BOOLEAN;
        }else {
            throw new InvalidDataTypeException("\n " + data + ": is not an excepted data type.");
        }
    }
    public void setRestrictions(String name, Object[] o){
        String key;
        for (Map.Entry<String, PositionalArgument> p : positionalArgs.entrySet()){
            key = p.getKey();
            if (key == name){
                    for (Object ob : o)
                    positionalArgs.get(name).restrictions.add(ob);
            }
        }
        for (Map.Entry<String, NamedArgument> n : namedArgs.entrySet()){
            key = n.getKey();
            if (key == name){
                    for (Object ob : o){
                            namedArgs.get(name).restrictions.add(ob);
                    }
            }
        }
    }
	
	public List<Object> getRestrictions(String name){
		String key;
		for (Map.Entry<String, PositionalArgument> p : positionalArgs.entrySet()){
			key = p.getKey();
			if (key == name){
				
				return positionalArgs.get(name).restrictions;
			}
		}
		for (Map.Entry<String, NamedArgument> n : namedArgs.entrySet()){
			key = n.getKey();
			if (key == name){
				return namedArgs.get(name).restrictions;
			}
		}
		return positionalArgs.get(name).restrictions;
	}
}
