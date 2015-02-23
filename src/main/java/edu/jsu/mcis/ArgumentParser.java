package edu.jsu.mcis;
import java.util.*;

public class ArgumentParser { 
	
    private Map<String, PositionalArgument> positionalArgs = new LinkedHashMap<>();
    private Map<String, OptionalArgument> optionalArgs = new HashMap<>();
    private Map<String, Boolean> flagArgs = new HashMap<>();
    private Map<String, String> nicknames = new HashMap<>();
    private String programDescription = "";
    private String programName = "";
    private int numPositionalArgs;
    
    public void parse(String[] args) throws InvalidLongArgumentException, InvalidShortArgumentException{
        Queue<String> userInputQueue = new LinkedList<>();
        convertArrayToQueue(args, userInputQueue);
        int count = 0;
        while (!userInputQueue.isEmpty()) {
            String userInput = userInputQueue.poll();
            if (isLongOptionalArgument(userInput)) {
                if (checkIfOptionalArgument(userInput)) {
                    setOptionalArgument(userInput, userInputQueue);
                } else if (isHelpArgument(userInput)) {
                    printHelpInfo();
                } else {
                    throw new InvalidLongArgumentException();
                }
            } else if (isShortOptionalArgument(userInput)) {
                if (isHelpArgument(userInput)) {
                    printHelpInfo();
                } else if (isItAFlagLong(userInput.substring(1))) {
                    flipFlag(userInput.substring(1));
                } else if (isItANickname(userInput.substring(1))) {
                    setOptionalArgument(userInput, userInputQueue);
                } else {
                    throw new InvalidShortArgumentException();
                }
            } else {
                setValue((String) positionalArgs.keySet().toArray()[count], userInput);
                if (isDataTypeEqualTo("int", count)) {
                    try {
                        Integer.parseInt(userInput);
                    } catch (java.lang.NumberFormatException e) {
                       //throw should be int exception
                    }
                } else if (isDataTypeEqualTo("float", count)) {
                    try {
                        Float.parseFloat(userInput);
                    } catch (java.lang.NumberFormatException e) {
                        //throw should be float exception
                    }
                } else if (isDataTypeEqualTo("boolean", count)) {
                    if (!isItAValidBoolean(userInput)) {
                        //throw should be boolean exception
                    }
                }
                count++;
            }
        }  
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
       
    private boolean isDataTypeEqualTo(String dataType, int count) {
        return positionalArgs.get((String) positionalArgs.keySet().toArray()[count]).dataType.equals(dataType);
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
            if (positionalArgs.get(s).dataType.equals("String")) {
                return (T) positionalArgs.get(s).myValue;
            } else if (positionalArgs.get(s).dataType.equals("int")) {
                return (T) new Integer(Integer.parseInt(positionalArgs.get(s).myValue));
            } else if (positionalArgs.get(s).dataType.equals("float")) {
                return (T) new Float(Float.parseFloat(positionalArgs.get(s).myValue));
            } else if (positionalArgs.get(s).dataType.equals("boolean")) {
                return (T) new Boolean(Boolean.parseBoolean(positionalArgs.get(s).myValue));
            }
        } else if (isItAnOptional(s)) {
            return (T) optionalArgs.get(s).value;
        } else if (isItAFlag(s)) {
            return (T) flagArgs.get(s);
        }
        //throws no argument called s exception
        return null;
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
        String className = this.getClass().getName();
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
        public String dataType = "";
    }
    
    private class OptionalArgument {
        public String nickname = "";
        public String value = "";
    }
	
    public void addArguments(String name, String dataType) {
        PositionalArgument ao = new PositionalArgument();
        positionalArgs.put(name, ao);
        if (isProperDataType(dataType)) {
            ao.dataType = dataType;
        } else {
            ao.dataType = "String";
            //throws inproper datatype exception
        }
        numPositionalArgs++;
    }
    
    private boolean isProperDataType(String dt) {
        return dt.equals("String") || dt.equals("int") || dt.equals("float") || dt.equals("boolean");
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
    
    public class InvalidLongArgumentException extends RuntimeException {
        
    }
    
    public class InvalidShortArgumentException extends RuntimeException {
        
    }
    
}
