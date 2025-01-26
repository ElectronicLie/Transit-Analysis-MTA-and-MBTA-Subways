package transit;

import linalg.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.FileWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import malo.Malo;
import malo.Mathematic;

public class TransitSystem extends EvenNetwork<Stop>{

  protected String[] lines;
  protected MarkovChain mc;
  protected boolean doneAddingLines;
  protected String name;
  protected boolean updated;

  public TransitSystem(String n, String[] lns, boolean u){
    nodes = new ArrayList<Stop>();
    this.lines = lns;
    doneAddingLines = false;
    this.name = n;
    this.updated = u;
  }

  public void close(){
    doneAddingLines = true;
    mc = new MarkovChain(this);
  }

  public double[] getStopWeights(){
    return this.mc.getSteadyState().getVals();
  }

  public String stopWeightsToString(){
    return this.mc.steadyStateToString();
  }

  public String sortedStopWeights(){
    return this.mc.sortedSteadyStateToString();
  }

  public MarkovChain getMarkovChain(){
    return mc;
  }

  protected void updateStationLineData(){
    double[] weights = getStopWeights();
    if (weights.length != nodes.size()){
      throw new IllegalArgumentException("numbers of stops and stop weights are unequal");
    }
    ArrayList<Vector> cols = new ArrayList<Vector>();
    Vector col;
    for (int s = 0; s < nodes.size(); s++){
      col = nodes.get(s).getLinesVector().copy();
      col.scale(weights[s]);
      cols.add(col);
    }
    Matrix data = new Matrix(cols);
    // data.scale(1000);
    String str = data.toString(-9);
    // System.out.println(str);
    String fileName = "src/main/java/transit/"+name+"_stop-line-data.txt";
    try (FileWriter writer = new FileWriter(fileName)){
      writer.write(str);
      writer.close();
    }catch(IOException e){
      e.printStackTrace();
    }
    this.updated = true;
  }

  public Matrix getStationLineData() throws FileNotFoundException{
    if (! updated){
      updateStationLineData();
    }
    File file = new File("src/main/java/transit/"+this.name+"_stop-line-data.txt");
    Scanner scanner = new Scanner(file);
    String str = "";
    while (scanner.hasNextLine()){
      str += scanner.nextLine()+"\n";
    }
    Matrix slData = Matrix.parseMatrix(str);
    slData.scale(1000);
    return slData;
  }

  public Vector lineCentralities(int j) throws FileNotFoundException{
    System.out.println("covariance matrix:\n"+getStationLineData().coVarianceMatrix());
    Vector lc = getStationLineData().weightedAverageOfPrincipalComponents(j);
    // lc.scaleToAverageScale(5.0);
    return lc;
  }

  public String lineCentralitiesToString(int k) throws FileNotFoundException{
    String result = "";
    Vector lc = lineCentralities(k);
    String[] lineNames = getLineNames();
    for (int i = 0; i < lc.dim(); i++){
      result += "[ " + lc.get(i) + " ] " + lineNames[i] + "\n";
    }
    return result;
  }

  public String sortedLineCentralities(int k) throws FileNotFoundException{
    return sortedLineCentralities(k, Mathematic.DEFAULT_ROUND);
  }

  public String sortedLineCentralities(int k, int p) throws FileNotFoundException{
    String[] lineNames = getLineNames();
    Vector lc = lineCentralities(k);
    double[] v = lc.getVals();
    for (int i = v.length-1; i >= 0; i--){ //bubble sort
      for (int j = 0; j < i; j++){
        if (v[j] > (v[j+1])){
          double tmp = v[j];
          v[j] = v[j+1];
          v[j+1] = tmp;
          String strTmp = lineNames[j];
          lineNames[j] = lineNames[j+1];
          lineNames[j+1] = strTmp;
        }
      }
    }
    String result = "";
    for (int n = 0; n < lineNames.length; n++){
      result += "[ " + Malo.roundDouble(v[n],p) + " ] " + lineNames[n] + "\n";
    }
    return result;
  }

  public String[] getLineNames(){
    return this.lines;
  }

  public Vector linesStringsToVector(String[] strLines){
    int[] ary = new int[this.lines.length];
    for (int i = 0; i < ary.length; i++){
      if (Malo.aryContains(strLines, this.lines[i])){
        ary[i] = 1;
      }else{
        ary[i] = 0;
      }
    }
    if (numberOfOnesInArray(ary) < strLines.length){
      throw new IllegalArgumentException
        ("one or more of the line names in "+Arrays.toString(strLines)+" are not lines included in this"
        +" TransitSystem");
    }
    return new Vector(ary);
  }

  public void addLine(String[] nodeNames, String lineName){
    if (! Malo.aryContains(this.lines, lineName)){
      throw new IllegalArgumentException(lineName+" is not a line in the TransitSystem");
    }
    Stop newStop;
    int index;
    for (int n = 0; n < nodeNames.length; n++){
      index = indexOfNode(nodeNames[n]);
      if (index == -1){
        newStop = new Stop(nodeNames[n], aryAdjacents(nodeNames, n), new String[] {lineName}, this);
        addNode(newStop);
      }else{
        nodes.get(index).addLine(lineName);
        nodes.get(index).addNabrs(aryAdjacents(nodeNames, n));
      }
    }
  }

  public void addLine(String[] nodeNames){
    Stop newStop;
    int index;
    for (int n = 0; n < nodeNames.length; n++){
      index = indexOfNode(nodeNames[n]);
      if (index == -1){
        throw new IllegalStateException("cannot create walking connections between new Stops");
      }else{
        nodes.get(index).addNabrs(aryAdjacents(nodeNames, n));
        updateNode(index);
      }
    }
  }

  public String deepToString(){
    return "Transit System" + super.deepToString().substring(7);
  }

  public String sumToString(){
    return "Transit System" + super.sumToString().substring(7);
  }

  private static String[] aryAdjacents(String[] ary, int index){
    if (ary.length <= 1){
      return new String[] {};
    }
    if (index == 0){
      return new String[] {ary[1]};
    }
    if (index == ary.length-1){
      return new String[] {ary[index-1]};
    }
    return new String[] {ary[index-1], ary[index+1]};
  }

  private static int numberOfOnesInArray(int[] ary){
    int no = 0;
    for (int i = 0; i < ary.length; i++){
      if (ary[i] == 1){
        no++;
      }
    }
    return no;
  }

}
