import linalg.*;
import transit.*;
import java.io.*;
import malo.*;
import java.util.Arrays;

public class Main{

    public static void main(String[] args) throws FileNotFoundException{
        TransitSystem mta = new TransitSystem("mta", Mta.LINES, true);
        // Mta.addIntxnsAndTerminals(mta);
        // mta.doneAddingLines();

        Matrix data = mta.getStationLineData();
        System.out.println(data.weightedAverageOfPrincipalComponents());
        System.out.println("data:\n"+data);
        System.out.println("data pc:\n"+data.weightedAverageOfPrincipalComponents());
        System.out.println("lc:\n"+mta.lineCentralities());
        System.out.println("lcToString:\n"+mta.lineCentralitiesToString());
        System.out.println("sorted lc:\n"+mta.sortedLineCentralities());
    }

}
