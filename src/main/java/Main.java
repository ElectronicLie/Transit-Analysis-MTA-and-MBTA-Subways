import linalg.*;
import transit.*;
import java.io.*;
import malo.*;
import java.util.Arrays;

public class Main{

    public static void main(String[] args) throws FileNotFoundException{
        TransitSystem mta = new TransitSystem("mta", Mta.LINES, true);
        // Mta.addIntxnsAndTerminals(mta);
        // mta.close();

        Matrix data = mta.getStationLineData();
        System.out.println(data.weightedAverageOfPrincipalComponents(1));
        System.out.println("data:\n"+data);
        System.out.println("data pc:\n"+data.weightedAverageOfPrincipalComponents(1));
        System.out.println("lc:\n"+mta.lineCentralities(1));
        System.out.println("lcToString:\n"+mta.lineCentralitiesToString(1));
        System.out.println("sorted lc:\n"+mta.sortedLineCentralities(1));

        // TransitSystem toy = new TransitSystem("toy", Toy.LINES, false);
        // Toy.addAllStops(toy);
        // toy.close();
        //
        // System.out.println("data:\n"+toy.getStationLineData());
        // System.out.println("stop weights:\n"+toy.sortedStopWeights());
        // System.out.println("line centralities:\n"+toy.sortedLineCentralities());
    }

}
