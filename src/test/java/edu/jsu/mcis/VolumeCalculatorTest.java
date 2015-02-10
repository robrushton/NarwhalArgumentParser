package edu.jsu.mcis;

import org.junit.*;
<<<<<<< HEAD
import org.junit.Assert.*;

public class VolumeCalculatorTest{
    
    @Test
    public void addArgTest() {
        VolumeCalculator t = new VolumeCalculator();
        
        String red = "red";
        
        t.addArg(red);
        t.addArg("blue");
        t.addArg("green");
        
        t.argsList.get(red) = "hello";
        t.argsList.get(blue) = "hola";
        t.argsList.get(green) = "hi";
        
        assertTrue("hello", red);
        assertTrue("hola", blue);
        assertTrue("hi", green);
    }
=======
import static org.junit.Assert.*;

public class VolumeCalculatorTest {
>>>>>>> 6ab9c0cadbb2ceffc9b62c32797fb34172a94178
    
}