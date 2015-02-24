package edu.jsu.mcis;
import java.util.*;

public class ArgumentParser { 
	
    private Map<String, PositionalArgument> positionalArgs = new LinkedHashMap<>();
    private Map<String, OptionalArgument> optionalArgs = new HashMap<>();
    private Map<String, Boolean> flagArgs = new HashMap<>();
    private Map<String, String> nicknames = new HashMap<>();
    private enum Datatype {STRING, FLOAT, INT, BOOLEAN};
    private String programDescription = "";
    private String programName = "";
    private int numPositionalArgs;
    
    public void parse(String[] args) {
        Queue<String> userInputQueue = new LinkedList<>();
        convertArrayToQueue(args, userInputQueue);
        int positionalArgsPlaced = 0;
        while (!userInputQueue.isEmpty()) {
            String userInput = userInputQueue.poll();
            if (isLongOptionalArgument(userInput)) {
                if (checkIfOptionalArgument(userInput)) {
                    setOptionalArgument(userInput, userInputQueue);
                } else if (isHelpArgument(userInput)) {
                    printHelpInfo();
                } else {
                    throw new InvalidOptionalArgumentException("\n " + userInput + " '--' value not defined.");
                }
            } else if (isShortOptionalArgument(userInput)) {
                if (isHelpArgument(userInput)) {
                    printHelpInfo();
                } else if (isItAFlagLong(userInput.substring(1))) {
                    flipFlag(userInput.substring(1));
                } else if (isItANickname(userInput.substring(1))) {
                    setOptionalArgument(userInput, userInputQueue);
                } else {
                    throw new InvalidOptionalArgumentException("\n " + userInput + " '-' value not defined.");
                }
            } else {
                if (isItTooManyArgs(positionalArgsPlaced)) {
                    setValue((String) positionalArgs.keySet().toArray()[positionalArgsPlaced], userInput);
                    if (isDataTypeEqualTo(Datatype.INT, positionalArgsPlaced)) {
                        try {
                            Integer.parseInt(userInput);
                        } catch (java.lang.NumberFormatException e) {
                           throw new InvalidDataTypeException("\n " + userInput + ". Value is invalid data type. Expected int");
                        }
                    } else if (isDataTypeEqualTo(Datatype.FLOAT, positionalArgsPlaced)) {
                        try {
                            Float.parseFloat(userInput);
                        } catch (java.lang.NumberFormatException e) {
                            throw new InvalidDataTypeException("\n " + userInput + ". Value is invalid data type. Expected float");
                        }
                    } else if (isDataTypeEqualTo(Datatype.BOOLEAN, positionalArgsPlaced)) {
                        if (!isItAValidBoolean(userInput)) {
                            throw new InvalidDataTypeException("\n " + userInput + ". Value is invalid data type. Expected boolean");
                        }
                    }
                    positionalArgsPlaced++;
                } else {
                    throw new PositionalArgumentException("\n " + userInput + ". Too many positional arguments.");
                }
            }
        }
        if (notGivenEnoughPositionalArgs(positionalArgsPlaced)) {
            throw new PositionalArgumentException("\n Not enough positional arguments.");
        }        
    }
    
    private boolean notGivenEnoughPositionalArgs(int given) {
        return positionalArgs.size() != given;
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
    
    private boolean checkIfOptionalArgument(String s) {
        return optionalArgs.containsKey(s.substring(2));
    }
    
    private boolean isItAValidBoolean(String userInput) {
        return (userInput.equals("false") || userInput.equals("true") ||
                userInput.equals("True") || userInput.equals("False"));
    }
    
    private boolean isLongOptionalArgument(String userInput) {
        return (userInput.startsWith("--"));
    }
    
    private boolean isShortOptionalArgument(String userInput) {
        return (userInput.startsWith("-"));
    }
       
    private boolean isDataTypeEqualTo(Datatype dataType, int count) {
        return positionalArgs.get((String) positionalArgs.keySet().toArray()[count]).dataType == dataType;
    }
    
    private void setOptionalArgument(String userInput, Queue<String> userInputQueue) {
        if (nicknames.containsKey(userInput.substring(1))) {
            optionalArgs.get(nicknames.get(userInput.substring(1))).value = userInputQueue.poll();
        } else {
            optionalArgs.get(userInput.substring(2)).value = userInputQueue.poll();
        }
    }
     
    private boolean isHelpArgument(String s) {
        return s.equals("-h") || s.equals("--Help");
    }
    
    private void setValue(String s, String n) {
        positionalArgs.get(s).myValue = n;
    }
    
    public <T> T getValue(String s) {
        if (isItAPositional(s)) {
            if (positionalArgs.get(s).dataType == Datatype.STRING) {
                return (T) positionalArgs.get(s).myValue;
            } else if (positionalArgs.get(s).dataType == Datatype.INT) {
                return (T) new Integer(Integer.parseInt(positionalArgs.get(s).myValue));
            } else if (positionalArgs.get(s).dataType == Datatype.FLOAT) {
                return (T) new Float(Float.parseFloat(positionalArgs.get(s).myValue));
            } else if (positionalArgs.get(s).dataType == Datatype.BOOLEAN) {
                return (T) new Boolean(Boolean.parseBoolean(positionalArgs.get(s).myValue));
            }
        } else if (isItAnOptional(s)) {
            return (T) optionalArgs.get(s).value;
        } else if (isItAFlag(s)) {
            return (T) flagArgs.get(s);
        }
        throw new NoArgCalledException("\n " + s + " is not a valid argument.");
    }
    
    private boolean isItAPositional(String s) {
        return positionalArgs.containsKey(s);
    }
    
    private boolean isItAnOptional(String s) {
        return optionalArgs.containsKey(s);
    }
    
    private boolean isItANickname(String userInput) {
        return nicknames.containsKey(userInput);
    }
     
    private boolean isItAFlag(String userInput) {
        return flagArgs.containsKey(userInput);
    }
	
	public void addOptionalArgDefaultValue(String type, String defaultValue){
		optionalArgs.get(type).value = defaultValue;
	}
    
    public void addOptionalArgument(String type) {
        OptionalArgument oa = new OptionalArgument();
        optionalArgs.put(type, oa);
    }
    public void addOptionalArgument(String type, String defaultValue) {
        addOptionalArgument(type);
        optionalArgs.get(type).value = defaultValue;
    }
    public void addOptionalArgument(String type, String defaultValue, String nickname) {
        addOptionalArgument(type, defaultValue);
        nicknames.put(nickname, type);
        setNickname(type, nickname);
    }
    
    private void setNickname(String s, String n) {
        optionalArgs.get(s).nickname = n;
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
                if (!positionalArgs.get(s).myDescription.equals("")) {
                    System.out.println(s + ": " + positionalArgs.get(s).myDescription);
                }
            }
            printLoopCount++;
        }
        System.exit(0);
    }
    
    private class PositionalArgument {
        public String myDescription = "";
        public String myValue = "";
        public Datatype dataType;
    }
    
    private class OptionalArgument {
        public String nickname = "";
        public String value = "";
    }
	
    public void addArguments(String name, String dataType) {
        PositionalArgument ao = new PositionalArgument();
        positionalArgs.put(name, ao);
        if (dataType.equals("String")) {
            ao.dataType = Datatype.STRING;
        } else if (dataType.equals("float")) {
            ao.dataType = Datatype.FLOAT;
        } else if (dataType.equals("int")) {
            ao.dataType = Datatype.INT;
        } else if (dataType.equals("boolean")) {
            ao.dataType = Datatype.BOOLEAN;
        } else {
            throw new InvalidDataTypeException("\n " + dataType + ": is not an excepted data type.");
        }
        numPositionalArgs++;
    }
    
    public void addArguments(String name, String dataType, String description) {
        addArguments(name, dataType);
        positionalArgs.get(name).myDescription = description;
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
    
    public class InvalidOptionalArgumentException extends RuntimeException {
        public InvalidOptionalArgumentException(String msg){
            super(msg);
        }
        
    }

    public class InvalidDataTypeException extends RuntimeException {
        public InvalidDataTypeException(String msg){
            super(msg);
        }
    }
    
    public class PositionalArgumentException extends RuntimeException {
        public PositionalArgumentException(String msg){
            super(msg);
        }
    }
    
    public class NoArgCalledException extends RuntimeException {
        public NoArgCalledException(String msg){
            super(msg);
        }
    }
    
}
