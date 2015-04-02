package edu.jsu.mcis;
import java.util.*;
import java.util.Map.Entry;

public class ArgumentParser { 
	
    protected Map<String, PositionalArgument> positionalArgs;
    protected Map<String, NamedArgument> namedArgs;
    protected Map<String, Boolean> flagArgs;
    private Map<String, String> nicknames;
    public static List<Argument> myArgs;
    public enum Datatype {STRING, FLOAT, INT, BOOLEAN};
    private String programDescription;
    private String programName;
    private int numPositionalArgs;
    private int currentGroup;
    private List<NamedArgument> namedArgsEntered;
    
    public ArgumentParser() {
        this.programName = "";
        this.programDescription = "";
        this.nicknames = new HashMap<>();
        this.flagArgs = new HashMap<>();
        this.namedArgs = new HashMap<>();
        this.positionalArgs = new LinkedHashMap<>();
        currentGroup = 0;
        namedArgsEntered = new ArrayList<>();
        
    }
    
    public void parse(String[] args) {
        Queue<String> userInputQueue = arrayToQueue(args);
        Queue<PositionalArgument> positionalArgQueue = positionalArgsToQueue();
        while(!userInputQueue.isEmpty()) {
            String value = userInputQueue.poll();
            String nextValue = userInputQueue.peek();
            if (value.equals("-h") || value.equals("--Help")) {
                printHelpInfo();
            }else if (value.startsWith("--")) {
                value = value.substring(2);
                if (namedArgs.containsKey(value)) {
                    NamedArgument namedArg = namedArgs.get(value);
                    parseNamedArguments(namedArg, nextValue, userInputQueue, value);
                } else {
                    throw new InvalidNamedArgumentException("\n " + value + " '--' value not defined.");
                }
            } else if (value.startsWith("-")) {
                value = value.substring(1);
                if (nicknames.containsKey(value)) {
                    String name = nicknames.get(value);
                    NamedArgument namedArg = namedArgs.get(name);
                    parseNamedArguments(namedArg, nextValue, userInputQueue, value);
                } else if (isItAFlag(value)) {
                    flipFlag(value);
                } else {
                    throw new InvalidNamedArgumentException("\n " + value + " '-' value not defined.");
                }
            } else {
                if (!positionalArgQueue.isEmpty()) {
                    PositionalArgument posArg = positionalArgQueue.poll();
                    int numberPosValues = posArg.getNumberOfValues();
                    for (int i=1; i<=numberPosValues; i++) {
                        if (datatypeIsValid(posArg, value)) {
                            if (valueInRestrictions(posArg, value)) {
                                posArg.setValue(value);
                            } else {
                                throw new RestrictedValueException(value + " is not in set of restrictions");
                            }
                        } else if (value == null) {}
                        else {
                            throw new InvalidDataTypeException("\n " + value + ". Value is invalid data type.");
                        }
                        if (i < numberPosValues) {
                            value = userInputQueue.poll();
                        }
                    }
                } else {
                    throw new PositionalArgumentException("\n Too many positional arguments.");
                }
            }
        }
        if (!allPositionalArgsUsed(positionalArgQueue)) {
            throw new PositionalArgumentException("\n Not enough positional arguments.");
        }
        if (!checkIfAllRequiredNamedArgumentsGiven()) {
            throw new RequiredNamedArgumentNotGivenException("\n required Named Argument not given");
        }
    }
    
    private boolean allPositionalArgsUsed(Queue<PositionalArgument> positionalArgQueue) {
        if (!positionalArgQueue.isEmpty()) {
            return false;
        } else {
            PositionalArgument posArg;
            for (Map.Entry<String, PositionalArgument> p : positionalArgs.entrySet()){
                posArg = p.getValue();
                if (posArg.getValueListAsString().size() != posArg.getNumberOfValues()) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private void parseNamedArguments(NamedArgument namedArg, String nextValue, Queue<String> userInputQueue, String value) {
        if (currentGroup == 0 || currentGroup == namedArg.getGroup()) {
            if (valueInRestrictions(namedArg, nextValue)) {
                namedArg.setValue(userInputQueue.poll());
                namedArgsEntered.add(namedArg);
                if (namedArg.getGroup() != 0) {
                    currentGroup = namedArg.getGroup();
                }
            } else {
                throw new RestrictedValueException(value + " is not in set of restrictions");
            }
        } else {
            throw new mutualExclusionException("illegal use of mutually exlusive groups");
        }
    }
    
    private boolean valueInRestrictions(Argument arg, String value) {
        if (arg.restrictions.isEmpty()) {
            return true;
        }else if (arg.restrictions.contains(value)) {
            return true;
        } else {
            return false;
        }
    }
    
    private Queue<String> arrayToQueue(String[] array) {
        Queue<String> output = new LinkedList<>();
        output.addAll(Arrays.asList(array));
        return output;
    }
    
    private Queue<PositionalArgument> positionalArgsToQueue() {
        Queue<PositionalArgument> output = new LinkedList<>();
        for (Entry<String, PositionalArgument> entry : positionalArgs.entrySet()) {
            output.add(entry.getValue());
        }
        return output;
    }
    
    private boolean datatypeIsValid(PositionalArgument posArg, String value) {
        if (posArg.dataType == Datatype.STRING) {
            return true;
        } else if (posArg.dataType == Datatype.INT) {
            try {
                Integer.parseInt(value);
            } 
            catch (java.lang.NumberFormatException e) {
                return false;
            }
        } else if (posArg.dataType == Datatype.FLOAT) {
            try {
                Float.parseFloat(value);
            } 
            catch (java.lang.NumberFormatException e) {
                return false;
            }
        } else {
            return value.equals("True") || value.equals("true") || value.equals("False") || value.equals("false");
        }
        return true;
    }
    
    
    
    private boolean checkIfAllRequiredNamedArgumentsGiven() {
        for (String s : namedArgs.keySet()) {
            if (isArgumentRequiredButNotGiven(s)) {
                return false;
            }
        }
        return true;
    }
    
    private boolean isArgumentRequiredButNotGiven(String s) {
        return namedArgs.get(s).getRequired() && !namedArgsEntered.contains(namedArgs.get(s));
    }
    
    private boolean isItAFlag(String userInput) {
        for (int i = 0; i < userInput.length(); i++){
            String singleFlag = userInput.substring(i, i+1);
            if (!flagArgs.containsKey(singleFlag)) {
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
    
    public <T> T getValue(String s) {
        if (isItAPositional(s)) {
            if (positionalArgs.get(s).getValueListAsString().size() == 1) {
                if (positionalArgs.get(s).getDataType() == Datatype.BOOLEAN) {
                    return (T) Boolean.valueOf(positionalArgs.get(s).getValue(0));
                } 
                else if (positionalArgs.get(s).getDataType() == Datatype.INT) {
                    return (T) new Integer(positionalArgs.get(s).getValue(0));
                } 
                else if (positionalArgs.get(s).getDataType() == Datatype.FLOAT) {
                    return (T) new Float(positionalArgs.get(s).getValue(0));
                } 
                else{
                    return (T) positionalArgs.get(s).getValue(0);
                }
            }
            else {
                List<T> output;
                output = positionalArgs.get(s).getValueList();
                return (T) output;
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
	    
    public void addNamedArgument(String name, boolean required) {
        NamedArgument oa = new NamedArgument();
        namedArgs.put(name, oa);
        oa.setName(name);
        setRequired(name, required);
    }
    
    
    public void addNamedArgument(String name, String defaultValue, boolean required) {
        addNamedArgument(name, required);
        NamedArgument oa = getNamedArgument(name);
        oa.setValue(defaultValue);
        oa.setDefaultValue(defaultValue);
    }
    
    public void addNamedArgument(String name, String defaultValue, Datatype datatype, boolean required) {
        addNamedArgument(name, defaultValue, required);
        NamedArgument oa = getNamedArgument(name);
        oa.setDataType(datatype);
    }
    
    public void addNamedArgument(String name, String defaultValue, Datatype datatype, String nickname, boolean required) {
        addNamedArgument(name, defaultValue, datatype, required);
        nicknames.put(nickname, name);
        setNickname(name, nickname);
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
        System.out.println("Positional Arguments: ");
        printLoopCount = 0;
        for (String s : positionalArgs.keySet()) {
            if (printLoopCount < numPositionalArgs) {
                System.out.print ("\n" + s + ": ");
                if (!positionalArgs.get(s).getDescription().equals("")) {
                    System.out.print(positionalArgs.get(s).getDescription());
                }
                else {
                    System.out.println("No Description Given");
                }
                System.out.print("    Datatype: " + positionalArgs.get(s).getDataType());
                if (positionalArgs.get(s).getRestrictions().size() == 0 || positionalArgs.get(s).getRestrictions().size() == 1 && positionalArgs.get(s).getRestrictions().get(0).equals("")) {
                    //
                }
                else {
                    System.out.print("    Restrictions:");
                    Iterator itr = positionalArgs.get(s).getRestrictions().iterator();
                    while (itr.hasNext()) {
                        System.out.print(" " + itr.next());
                    }
                }
            }
            printLoopCount++;
        }
        System.out.println();
        System.out.println("Named Arguments");
        for (String s : namedArgs.keySet()) {
            System.out.print("\n" + s + ": " + "Required: " + namedArgs.get(s).getRequired());
            if (!namedArgs.get(s).getNickname().equals("")) {
                System.out.print("    Nickname: " + namedArgs.get(s).getNickname());
            }
        }
        System.exit(0);
    }
    
	
    public void addArguments(String name, Datatype dataType) {
        PositionalArgument ao = new PositionalArgument();
        positionalArgs.put(name, ao);
        ao.setName(name);
        ao.setDataType(dataType);
        numPositionalArgs++;
    }
    
    public void addArguments(String name, Datatype dataType, String description) {
        addArguments(name, dataType);
        positionalArgs.get(name).setDescription(description);
    }
    
    public void addArguments(String name, Datatype dataType, String description, int numValues) {
        addArguments(name, dataType, description);
        positionalArgs.get(name).setNumberOfValues(numValues);
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
    
    protected Datatype StringToDatatype(String data) {
        if (data.equals("float")) {
            return Datatype.FLOAT;
        } 
        else if (data.equals("int")) {
            return Datatype.INT;
        } 
        else if (data.equals("boolean")) {
            return Datatype.BOOLEAN;
        }
        else {
            return Datatype.STRING;
        }
    }
    
    protected String datatypeToString(Datatype data) {
        if (data == Datatype.FLOAT) {
            return "float";
        }
        else if (data == Datatype.INT) {
            return "int";
        }
        else if (data == Datatype.BOOLEAN) {
            return "boolean";
        }
        else {
            return "String";
        }
    }
    
    public <T> void setRestrictions(String name, List<T> o){
        String key;
        for (Map.Entry<String, PositionalArgument> p : positionalArgs.entrySet()){
            key = p.getKey();
            if (key.equals(name)){
                for (T ob : o) {
                    String str = ob.toString();
                    positionalArgs.get(name).getRestrictions().add(str);
                }
            }
        }
        for (Map.Entry<String, NamedArgument> n : namedArgs.entrySet()){
            key = n.getKey();
            if (key.equals(name)){
                for (T ob : o){
                    String str = ob.toString();
                    namedArgs.get(name).getRestrictions().add(str);
                }
            }
        }
    }
    
    public void addNamedGroups(List<List<String>> list) {
        int count = 1;
        for (List<String> namedLists : list) {
            for (String named : namedLists) {
                namedArgs.get(named).setGroup(count);
            }
            count++;
        }
    }
}
