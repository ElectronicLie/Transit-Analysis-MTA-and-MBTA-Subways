import linalg.*;
import transit.*;
import java.io.*;
import malo.*;
import java.util.Arrays;

public class Main{

    public static void main(String[] args) throws FileNotFoundException{
         TransitSystem mta = new TransitSystem("mta_subway", Mta.LINES, true);
         Mta.addIntxnsAndTerminals(mta);
         mta.close();
         System.out.println("stop weights:\n"+mta.sortedStopWeights());
         System.out.println("line centralities:\n"+mta.sortedLineCentralities(25));

//        TransitSystem t = new TransitSystem("T", T.LINES, false);
//        T.addAllStops(t);
//        t.close();
//        System.out.println("stop weights:\n"+t.sortedStopWeights());
//        System.out.println("line centralities:\n"+t.sortedLineCentralities(5));

        // TransitSystem toy = new TransitSystem("toy", Toy.LINES, false);
        // Toy.addAllStops(toy);
        // toy.close();
        // System.out.println(toy.deepToString());
        // System.out.println("stop weights:\n"+toy.sortedStopWeights());
        // System.out.println("line centralities:\n"+toy.sortedLineCentralities(2));
    }

}
