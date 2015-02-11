package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;

public class ArgumentParserTest {
    
    @Test
    public void testGetArgWithLengthOf10() {
        ArgumentParser ap = new ArgumentParser();
        ap.addArg("Length", 10);
        int t = ap.getValue("Length");
        assertEquals(t, 10);
    }
    
    @Test
    public void AddArgLengthToAP(){
        ArgumentParser ap = new ArgumentParser();
        ap.addArg("Length", 1);
        int t = ap.getValue("Length");
        assertEquals(1, t);
    }
    
    
}