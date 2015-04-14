import java.util.*;
import edu.jsu.mcis.*;

public class SubShop {
    
    public static void main (String[] args) {
        ArgumentParser ap = XML.loadXML("SubShopDemo.xml");
        ap.parse(args);
    }
}