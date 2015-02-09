package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;

public class ArgumentParserTest {
    @Test
    public void testAddArgumentLengthToAP() {
        ArgumentParser ap = new ArgumentParser();
        ap.addArg("Length");
        assertEquals("Length", ap.getArg(0));
    }
    
    @Test
    public void testFourPositionalArgumentsIsError() {
        ArgumentParser ap = new ArgumentParser();
        //ap.parse(new String[] {"4", "2", "1", "42"});
    }

	@Test 
	public void testEmptyExit(){
		ArgumentParser ap = new ArgumentParser();
		boolean b = ap.addArg();
		assertFalse(b);
	}
	
	//@Test 
	public void testPositionalArguments(){
		ArgumentParser ap = new ArgumentParser();
	
	}
	

}