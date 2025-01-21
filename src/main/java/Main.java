import linalg.*;
import transit.*;
import java.io.*;

public class Main{

    public static void main(String[] args){
        // TransitSystem mta = new TransitSystem("mta", Mta.LINES);
        // Mta.addIntxnsAndTerminals(mta);
        // mta.doneAddingLines();
        // mta.updateStationLineData();
        // System.out.println(new File(".").getAbsolutePath());
        // File directory = new File("transit");
        // if (! directory.isDirectory()){
        //   throw new IllegalStateException();
        // }
        try (FileWriter wr = new FileWriter("src/main/java/transit/mta_stop-line-data.txt")){
          wr.write("AAAAAAAAAAA");
        }catch(IOException e){
          e.printStackTrace();
        }
    }

}
