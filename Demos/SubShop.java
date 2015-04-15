import java.util.*;
import edu.jsu.mcis.*;

public class SubShop {
    
    public static void main (String[] args) {
        ArgumentParser ap = XML.loadXML("SubShopDemo.xml");
        ap.parse(args);
        
        String l;
        String t;
        String so;
        List<Integer> s = ap.getValue("sandwich");
        if(ap.getValue("lettuce")) {
            l = "yes";
        } 
        else {
            l = "none";
        }
        if (ap.getValue("tomato")) {
            t = "yes";
        }
        else {
            t = "none";
        }
        if (ap.getValue("toasted")) {
            so = "toasted";
        }
        else if (ap.getValue("pressed")) {
            so = "pressed";
        }
        else {
            so = "none";
        }
        System.out.println("Your Order:");
        System.out.println();
        System.out.println("Quantity:       " + s.get(0));
        System.out.println("Length:         " + s.get(1));
        System.out.println("Bread:          " + ap.getValue("bread"));
        System.out.println("Meat:           " + ap.getValue("meat"));
        System.out.println("Cheese:         " + ap.getValue("cheese"));
        System.out.println("Condiment:      " + ap.getValue("condiment"));
        System.out.println("Lettuce:        " + l);
        System.out.println("Tomato:         " + t);
        System.out.println("Special Order:  " + so);
    }
}