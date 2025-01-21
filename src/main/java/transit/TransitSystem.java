package transit;

import linalg.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import malo.Malo;
import malo.Mathematic;

public class TransitSystem extends EvenNetwork<Stop>{

  protected String[] lines;
  protected MarkovChain mc;
  protected boolean doneAddingLines;
  protected String name;

  public TransitSystem(String n, String[] lns){
    nodes = new ArrayList<Stop>();
    this.lines = lns;
    doneAddingLines = false;
    this.name = n;
  }

  public void doneAddingLines(){
    doneAddingLines = true;
    mc = new MarkovChain(this);
  }

  public double[] getStopWeights(){
    return this.mc.getSteadyState().getVals();
  }

  public String stopWeightsToString(){
    return this.mc.steadyStateToString();
  }

  public String sortedStopWeightsToString(){
    return this.mc.sortedSteadyStateToString();
  }

  public MarkovChain getMarkovChain(){
    return mc;
  }

  public void updateStationLineData(){
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
    String str = data.toString();
    System.out.println(str);
    try (FileWriter writer = new FileWriter(name+"_stop-line-data.txt")){
      writer.write(str);
      writer.close();
    }catch(IOException e){
      e.printStackTrace();
    }
  }

  public Matrix getStationLineData(){
    Scanner scanner = new Scanner(name+"_stop-line-data.txt");
    String str = "";
    while (scanner.hasNext()){
      str += scanner.next();
    }
    return Matrix.parseMatrix(str);
  }

  public Vector lineCentralities(){
    Vector lc = getStationLineData().weightedAverageOfPrincipalComponents();
    System.out.println(lc);
    lc.scaleToAverageScale(5.0);
    return lc;
  }

  public String lineCentralitiesToString(){
    String result = "";
    Vector lc = lineCentralities();
    String[] lineNames = getLineNames();
    for (int i = 0; i < lc.dim(); i++){
      result += "[ " + lc.get(i) + " ] " + lineNames[i] + "\n";
    }
    return result;
  }

  public String sortedLineCentralitiesToString(){
    return sortedLineCentralitiesToString(Mathematic.DEFAULT_ROUND);
  }

  public String sortedLineCentralitiesToString(int p){
    String[] lineNames = getLineNames();
    double[] v = lineCentralities().getVals();
    for (int i = v.length-1; i >= 0; i--){ //bubble sort
      for (int j = 0; j < i; j++){
        if (v[j] > (v[j+1])){
          double tmp = v[j];
          v[j] = Malo.roundDouble(v[j+1], p);
          v[j+1] = Malo.roundDouble(tmp, p);
          String strTmp = lineNames[j];
          lineNames[j] = lineNames[j+1];
          lineNames[j+1] = strTmp;
        }
      }
    }
    String result = "";
    for (int n = 0; n < lineNames.length; n++){
      result += "[ " + v[n] + " ] " + lineNames[n] + "\n";
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
