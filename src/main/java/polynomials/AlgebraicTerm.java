package polynomials;

import malo.*;
import java.util.Scanner;
import java.util.Arrays;
import fractions.*;

public class AlgebraicTerm{

  // FIELDS

  private int[] expts;
  private int coeff;
  private String[] vars; //never has duplicates

  // CONSTRUCTORS //

  private AlgebraicTerm(int coefficient, int[] exponents, String[] variables){
    coeff = coefficient;
    expts = exponents;
    vars = variables;
    removeZeroExpts();
  }

  public AlgebraicTerm(){
    this(1, new int[] {}, new String[] {});
  }

  public AlgebraicTerm(String cym){
    this(1, new int[] {1}, new String[] {cym});
  }

  public AlgebraicTerm(int c){
    this(c, new int[] {}, new String[] {});
  }

  private void removeZeroExpts(){
    for (int i = 0; i < expts.length; i++){
      if (expts[i] == 0){
        expts = Malo.aryRemove(expts, i);
        vars = Malo.aryRemove(vars, i);
        i--;
      }
    }
  }

  // GETTERS //

  int getExponent(String syn){
    int indexOf = Malo.aryIndexOf(vars, syn);
    if (indexOf == -1){
      return 0;
    }
    return expts[indexOf];
  }

  public int degree(){
    return Malo.arySum(expts);
  }

  String getSingleVariable(){
    if (degree() != 1){
      throw new IllegalStateException("degree is "+degree()+", not 1");
    }
    return vars[0];
  }

  // CALCULATORS //

  public Polynomial plugIn(String[] varsOrder, Polynomial[] values){
    if (varsOrder.length != values.length){
      throw new IllegalArgumentException("different number of variables and values to plug in");
    }
    Polynomial value = new Polynomial(coeff, values[0].getVar());
    int power;
    for (int i = 0; i < varsOrder.length; i++){
      power = getExponent(varsOrder[i]);
      value = value.mult(values[i].pow(power));
    }
    return value;
  }

  public Fraction plugIn(String[] varsOrder, Fraction[] values){
    if (varsOrder.length != values.length){
      throw new IllegalArgumentException("different number of variables and values to plug in");
    }
    Fraction value = new Fraction(coeff);
    int power;
    for (int i = 0; i < varsOrder.length; i++){
      power = getExponent(varsOrder[i]);
      value = value.mult(values[i].pow(power));
    }
    return value;
  }

  public double plugIn(String[] varsOrder, double[] values){
    if (varsOrder.length != values.length){
      throw new IllegalArgumentException("different number of variables and values to plug in");
    }
    double value = coeff;
    int power;
    for (int i = 0; i < varsOrder.length; i++){
      power = getExponent(varsOrder[i]);
      value *= Math.pow(values[i], power);
    }
    return value;
  }

  public AlgebraicTerm plugIn(String[] varsOrder, String[] values){
    if (varsOrder.length != values.length){
      throw new IllegalArgumentException("different number of variables and values to plug in");
    }
    String[] newVars = Malo.aryCopy(this.vars);
    int indexOf;
    for (int i = 0; i < varsOrder.length; i++){
      indexOf = Malo.aryIndexOf(this.vars, varsOrder[i]);
      // System.out.println("index of "+varsOrder[i]+" in "+Arrays.toString(vars)+": "+indexOf);
      if (indexOf != -1){
        newVars[indexOf] = values[i];
      }
    }
    return new AlgebraicTerm(this.coeff, this.expts, newVars);
  }

  // OPERATIONS //

  public AlgebraicTerm mult(AlgebraicTerm other){
    if (this.isZero() || other.isZero()){
      return zero();
    }
    int noUniqueSyms = noUniqueVars(this, other);
    String[] productVars = uniqueVars(this, other);
    int[] productExpts = new int[noUniqueSyms];
    for (int i = 0; i < productVars.length; i++){
      productExpts[i] = this.getExponent(productVars[i]) + other.getExponent(productVars[i]);
    }
    int productCoeff = this.coeff * other.coeff;
    return new AlgebraicTerm(productCoeff, productExpts, productVars);
  }

  public AlgebraicTerm divide(AlgebraicTerm other){
    if (other.isFactorOf(this)){
      String[] quotientVars = new String[vars.length];
      int[] quotientExpts = new int[expts.length];
      for (int i = 0; i < expts.length; i++){
        quotientExpts[i] = expts[i] - other.getExponent(vars[i]);
        quotientVars[i] = vars[i];
      }
      return new AlgebraicTerm(coeff / other.coeff, quotientExpts, quotientVars);
    }else{
      throw new IllegalArgumentException(other + " is not a factor of " + this);
    }
  }

  void addLikeTerm(AlgebraicTerm other){
    if (likeTerms(this, other)){
      this.coeff += other.coeff;
    }else{
      throw new IllegalArgumentException("cannot combine non-like terms");
    }
  }

  void scale(int k){
    this.coeff *= k;
  }

  AlgebraicTerm gcf(AlgebraicTerm b){
    int scalarGCF = Malo.gcf(this.coeff, b.coeff);
    int noUniqueSyms = noUniqueVars(this, b);
    AlgebraicTerm gcf = new AlgebraicTerm();
    gcf.coeff = scalarGCF;
    gcf.expts = new int[noUniqueSyms];
    gcf.vars = uniqueVars(this, b);
    for (int i = 0; i < expts.length; i++){
      expts[i] = Malo.min(this.getExponent(gcf.vars[i]), b.getExponent(gcf.vars[i]));
    }
    return gcf;
  }

  static AlgebraicTerm gcf(AlgebraicTerm a, AlgebraicTerm b){
    return a.gcf(b);
  }

  // boolean methods //

  public boolean equals(AlgebraicTerm other){
    return (likeTerms(this, other) && (this.coeff == other.coeff));
  }

  public boolean isZero(){
    return (coeff == 0);
  }

  boolean posCoefficient(){
    return (coeff > 0);
  }

  boolean negCoefficient(){
    return (coeff < 0);
  }

  public boolean isFactorOf(AlgebraicTerm other){
    if (coeff % other.coeff != 0){
      return false;
    }
    for (int i = 0; i < expts.length; i++){
      if (expts[i] > other.getExponent(vars[i])){
        return false;
      }
    }
    return true;
  }

  //vars field never has duplicates
  static boolean likeTerms(AlgebraicTerm a, AlgebraicTerm b){
    if (a.vars.length != b.vars.length){
      return false;
    }
    int indexOf;
    for (int i = 0; i < b.vars.length; i++){
      indexOf = Malo.aryIndexOf(a.vars, b.vars[i]);
      if ((indexOf == -1)){
        return false;
      }else if (a.expts[indexOf] != b.expts[i]){
        return false;
      }
    }
    return true;
  }

  // HELPERS //

  private String[] uniqueVars(AlgebraicTerm b){
    String[] result = new String[noUniqueVars(this, b)];
    for (int i = 0; i < this.vars.length; i++){
      result[i] = this.vars[i];
    }
    int passedInCommon = 0;
    for (int i = 0; i < b.vars.length; i++){
      if (Malo.aryIndexOf(this.vars, b.vars[i]) == -1){
        result[i+this.vars.length-passedInCommon] = b.vars[i];
      }else{
        passedInCommon++;
      }
    }
    return result;
  }

  private static String[] uniqueVars(AlgebraicTerm a, AlgebraicTerm b){
    return a.uniqueVars(b);
  }

  private int noUniqueVars(AlgebraicTerm b){
    int noUniqueSyms = this.vars.length;
    for (int i = 0; i < b.vars.length; i++){
      if (Malo.aryIndexOf(this.vars, b.vars[i]) == -1){
        noUniqueSyms++;
      }
    }
    return noUniqueSyms;
  }

  private static int noUniqueVars(AlgebraicTerm a, AlgebraicTerm b){
    return a.noUniqueVars(b);
  }

  // PRESETS //

  public static AlgebraicTerm zero(){
    return new AlgebraicTerm(0);
  }

  public static AlgebraicTerm one(){
    return new AlgebraicTerm(1);
  }

  public static AlgebraicTerm negOne(){
    return new AlgebraicTerm(-1);
  }

  // copy //

  public AlgebraicTerm copy(){
    return new AlgebraicTerm(coeff, Malo.aryCopy(expts), Malo.aryCopy(vars));
  }

  // toString //

  public String toString(){
    if (isZero()){
      return "0";
    }
    if (vars.length == 0){
      return coeff + "";
    }
    String result = "";
    if (coeff != 1){
      if (coeff == -1){
        result += "-";
      }else{
        result += coeff;
      }
    }
    for (int i = 0; i < vars.length; i++){
      result += vars[i];
      if (! (expts[i] == 1)){
        result += "^"+ expts[i];
      }
    }
    return result;
  }

  String negToString(){
    if (coeff > 0){
      throw new IllegalStateException
        ("cannot get negative toString when coefficient ("+coeff+") is positive");
    }
    if (isZero()){
      return "0";
    }
    String result = "";
    int coeffi = coeff * -1;
    if (coeffi != 1){
      if (coeffi == -1){
        result += "-";
      }else{
        result += coeffi;
      }
    }
    for (int i = 0; i < vars.length; i++){
      result += vars[i];
      if (! (expts[i] == 1)){
        result += "^"+ expts[i];
      }
    }
    return result;
  }

  public String parseableToString(){
    String result = coeff + " ";
    for (int i = 0; i < vars.length; i++){
      result += vars[i] + " ";
      result += expts[i] + " ";
    }
    return result;
  }

  // parse from String //

  // str must have been generated from parseableToString()
  public static AlgebraicTerm parseAlgebraicTerm(String str){
    Scanner scanner = new Scanner(str);
    int coefficient = scanner.nextInt();
    int noVars = 0;
    String next;
    while (scanner.hasNext()){
      next = scanner.next();
      noVars++;
    }
    noVars /= 2;
    scanner = new Scanner(str);
    coefficient = scanner.nextInt();
    String[] variables = new String[noVars];
    int[] exponents = new int[noVars];
    for (int i = 0; i < noVars; i++){
      variables[i] = scanner.next();
      exponents[i] = scanner.nextInt();
    }
    return new AlgebraicTerm(coefficient, exponents, variables);
  }

}
