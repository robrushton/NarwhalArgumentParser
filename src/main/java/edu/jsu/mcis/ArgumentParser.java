package edu.jsu.mcis;
import java.util.*;

public class ArgumentParser { 
	
    private Map<String, Argument> myArgs = new HashMap<>();
    private ArrayList<String> keys = new ArrayList<>();
    private Map<String, String> nicknames = new HashMap<>();
    private String programDescription = "";
    private Map<String, Boolean> flags = new HashMap<>();
    private int realArgCounter;
    
    public void parse(String[] args) {
        Queue<String> userInputQueue = new LinkedList<>();
        convertArrayToQueue(args, userInputQueue);
        int count = 0;
        while (!userInputQueue.isEmpty()) {
            String userInput = userInputQueue.poll();
            if (isLongOptionalArgument(userInput)) {
                if (isInputInKeys(userInput)) {
                    setOptionalArgument(userInput, userInputQueue);
                } else if (isHelpArgument(userInput)) {
                    printHelpInfo();
                } else {
                    //throws new invalidLongArgument();
                }
            } else if (isShortOptionalArgument(userInput)) {
                if (isHelpArgument(userInput)) {
                    printHelpInfo();
                } else if (isInputInFlags(userInput)) {
                    flags.put(userInput.substring(1), Boolean.TRUE);
                } else if (isInputInNicknames(userInput)) {
                    setOptionalArgument(userInput, userInputQueue);
                } else {
                    //throws new invalidShortArgument();
                }
            } else {
                setValue(keys.get(count), userInput);
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
    
    private void convertArrayToQueue(String[] args, Queue<String> userInputQueue) {
        userInputQueue.addAll(Arrays.asList(args));
    }
    
    private boolean isInputInNicknames(String userInput) {
        return nicknames.containsKey(userInput.substring(1));
    }
    
    private boolean isInputInKeys(String userInput) {
        return myArgs.containsKey(userInput.substring(2));
    }
    
    private boolean isInputInFlags(String userInput) {
        return flags.containsKey(userInput.substring(1));
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
        return myArgs.get(keys.get(count)).dataType.equals(dataType);
    }
    
    private void setOptionalArgument(String userInput, Queue<String> userInputQueue) {
        if (nicknames.containsKey(userInput.substring(1))) {
            myArgs.get(nicknames.get(userInput.substring(1))).myValue = userInputQueue.poll();
        } else {
            myArgs.get(userInput.substring(2)).myValue = userInputQueue.poll();
        }
    }
     
    private boolean isHelpArgument(String s) {
        return s.equals("-h") || s.equals("--Help");
    }
    
    public <T> T getValue(String s) {
        if (myArgs.get(s).dataType.equals("String")) {
            return (T) myArgs.get(s).myValue;
        } else if (myArgs.get(s).dataType.equals("int")) {
            return (T) new Integer(Integer.parseInt(myArgs.get(s).myValue));
        } else if (myArgs.get(s).dataType.equals("float")) {
            return (T) new Float(Float.parseFloat(myArgs.get(s).myValue));
        } else if (myArgs.get(s).dataType.equals("boolean")) {
            return (T) new Boolean(Boolean.parseBoolean(myArgs.get(s).myValue));
        } else {
            return null;
        }
    }
    
    public String getArgumentDescription(String s) {
        return myArgs.get(s).myDescription;
    }
    
    public void addOptionalArgument(String type) {
        addArguments(type, "String");
        realArgCounter--;
    }
    public void addOptionalArgument(String type, String defaultValue) {
        addArguments(type, "String");
        setValue(type, defaultValue);
        realArgCounter--;
    }
    public void addOptionalArgument(String type, String defaultValue, String nickname) {
        addArguments(type, "String");
        setValue(type, defaultValue);
        nicknames.put(nickname, type);
        setNickname(type, nickname);
        realArgCounter--;
    }
    
    private void printHelpInfo() {
        int printLoopCount = 0;
        String className = this.getClass().getName();
        System.out.print("\nUsage Information: java " + className + " ");
        for (String k : keys) {
            if (printLoopCount < realArgCounter) {
                System.out.print(k + " ");
            }
            printLoopCount++;
        }
        System.out.println();
        System.out.println(programDescription);
        System.out.println();
        System.out.println("Arguments: ");
        printLoopCount = 0;
        for (String s : keys) {
            if (printLoopCount < realArgCounter) {
                if (!myArgs.get(s).myDescription.equals("")) {
                    System.out.println(s + ": " + myArgs.get(s).myDescription);
                }
            }
            printLoopCount++;
        }
    }
    
    private class Argument {
        public String myDescription = "";
        public String myValue = "";
        public String dataType = "";
        public String nickname = "";
    }
    
    private void setValue(String s, String n) {
        myArgs.get(s).myValue = n;
    }
    private void setNickname(String s, String n) {
        myArgs.get(s).nickname = n;
    }
    
    public void addArguments(String name, String description, String dataType) {
        Argument ao = new Argument();
        myArgs.put(name, ao);
        keys.add(name);
        ao.myDescription = description;
        ao.dataType = dataType;
        realArgCounter++;
    }
	
    public void addArguments(String name, String dataType) {
        Argument ao = new Argument();
        myArgs.put(name, ao);
        keys.add(name);
        ao.dataType = dataType;
        realArgCounter++;
    }
    
    public void setProgramDescription(String s) {
        programDescription = s;
    }
	
	public String checkProgramDescription(String s){
		return programDescription;
	}
    
    public void addFlag(String s) {
        flags.put(s, Boolean.FALSE);
    }
    
    public boolean checkFlag(String s) {
        return flags.get(s).booleanValue();
    }
}
