package polynomials;

import fractions.Fraction;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import linalg.*;
import malo.*;

public class Polynomial{

  // FIELDS //

  static final int DEFAULT_ROOT_MARGIN = -7;
  static final int DEFAULT_ROOT_RANGE = 2;

  protected Fraction[] coeffs;
  protected String var;

  // CONSTRUCTORS //

  public Polynomial(Fraction[] coefficients, String symbol){
    if (coefficients.length == 0){
      throw new IllegalArgumentException("cannot create polynomial with no coefficients");
    }
    this.coeffs = coefficients;
    this.var = symbol;
  }

  public Polynomial(double[] coefficients, String symbol){
    if (coefficients.length == 0){
      throw new IllegalArgumentException("cannot create polynomial with no coefficients");
    }
    this.coeffs = new Fraction[coefficients.length];
    for (int i = 0; i < coeffs.length; i++){
      coeffs[i] = new Fraction(coefficients[i], "round");
    }
    this.var = symbol;
  }

  public Polynomial(Fraction[] coefficients){
    this(coefficients, "x");
  }

  public Polynomial(int[] coefficients, String symbol){
    // System.out.println(Arrays.toString(coefficients));
    if (coefficients.length == 0){
      throw new IllegalArgumentException("cannot create polynomial with no coefficients");
    }
    coeffs = new Fraction[coefficients.length];
    for (int i = 0; i < coeffs.length; i++){
      coeffs[i] = new Fraction(coefficients[i]);
    }
    // System.out.println(Arrays.deepToString(coeffs));
    var = symbol;
  }

  public Polynomial(int[] coefficients){
    this(coefficients, "x");
  }

  public Polynomial(int coeff, String sym){
    this(new int[] {coeff}, sym);
  }

  public Polynomial(int coeff){
    this(coeff, "x");
  }

  // GETTERS //

  public int degree(){
    for (int i = coeffs.length-1; i >= 0; i--){
      if (! coeffs[i].isZero()){
        return i;
      }
    }
    return 0;
  }

  public String getVar(){
    return var;
  }

  // LOGICAL //

  public boolean isZero(){
    for (Fraction coeff : coeffs){
      if (! coeff.isZero()){
        return false;
      }
    }
    return true;
  }

  public boolean equals(Polynomial other){
    for (int i = 0; i < (int)(Math.min(coeffs.length, other.coeffs.length)); i++){
      if (! coeffs[i].equals(other.coeffs[i])){
        return false;
      }
    }
    if (coeffs.length > other.coeffs.length){
      for (int i = other.coeffs.length; i < coeffs.length; i++){
        if (! coeffs[i].isZero()){
          return false;
        }
      }
    }else if (other.coeffs.length > coeffs.length){
      for (int i = coeffs.length; i < other.coeffs.length; i++){
        if (! other.coeffs[i].isZero()){
          return false;
        }
      }
    }
    return true;
  }

  public Fraction scalarRatio(Polynomial other){ // this over other
    if (degree() != other.degree()){
      return Fraction.zero();
    }
    Fraction k = this.coeffs[degree()].divide(other.coeffs[degree()]);
    for (int i = degree()-1; i >= 0; i--){
      if (! other.coeffs[i].mult(k).equals(this.coeffs[i])){
        return Fraction.zero();
      }
    }
    return k;
  }

  Fraction leadingCoefficient(){
    return coeffs[degree()];
  }

  // CALCULATORS //

  public double apply(double x){
    double result = 0;
    for (int p = 0; p < coeffs.length; p++){
      result += coeffs[p].getValue() * Math.pow(x,p);
    }
    return result;
  }

  public Polynomial derivative(){
    Fraction[] dCoeffs = new Fraction[this.coeffs.length-1];
    for (int t = 0; t < coeffs.length-1; t++){
      dCoeffs[t] = coeffs[t+1].mult(t+1);
    }
    return new Polynomial(dCoeffs, this.var);
  }

  private Monomial extractMonomial(int pow){
    return new Monomial(coeffs[pow], pow, var);
  }

  public Polynomial copy(){
    Fraction[] copyCoeffs = new Fraction[this.coeffs.length];
    for (int i = 0; i < coeffs.length; i++){
      copyCoeffs[i] = coeffs[i];
    }
    return new Polynomial(copyCoeffs, this.var);
  }

  // OPERATIONS //

  public Polynomial add(Polynomial other){
    if (! other.var.equals(this.var)){
      throw new IllegalArgumentException
        ("cannot combine Polynomials with different variable symbols: "+this.var+" and "+other.var);
    }
    Fraction[] sum = new Fraction[Math.max(other.coeffs.length, this.coeffs.length)];
    for (int p = 0; p < sum.length; p++){
      if (p >= other.coeffs.length){
        sum[p] = this.coeffs[p];
      }else if (p >= this.coeffs.length){
        sum[p] = other.coeffs[p];
      }else{
        sum[p] = this.coeffs[p].add(other.coeffs[p]);
      }
    }
    Polynomial sumPoly = new Polynomial(sum, var);
    // sumPoly.correctZeros();
    return sumPoly;
  }

  public Polynomial subtract(Polynomial other){
    return this.add(other.scale(-1));
  }

  public Polynomial mult(Polynomial other){
    if (! other.var.equals(this.var)){
      throw new IllegalArgumentException
        ("cannot combine Polynomials with different variable symbols: "+this.var+" and "+other.var);
    }
    // System.out.println(this+" * "+other+ " =");
    if (other.isZero() || this.isZero()){
      return zero(this.var);
    }
    Polynomial product = zero(this.var);
    for (int p = 0; p < other.coeffs.length; p++){
      product = product.add(this.scale(other.extractMonomial(p)));
    }
    // System.out.println(product+"\n");
    return product;
  }

  public Polynomial pow(int p){
    if (p < 0){
      throw new IllegalArgumentException("cannot raise polynomial to negative power: "+p);
    }
    if (p == 0){
      return one(this.var);
    }
    return mult(pow(p-1));
  }

  public Polynomial scale(int k){
    Polynomial result = zero(this.var);
    for (int p = 0; p <= degree(); p++){
      result = result.add(extractMonomial(p).scale(k));
    }
    return result;
  }

  public Polynomial scale(Fraction k){
    Polynomial result = zero(this.var);
    for (int p = 0; p <= degree(); p++){
      result = result.add(extractMonomial(p).scale(k));
    }
    return result;
  }

  private Polynomial scale(Monomial m){
    if (! m.var.equals(this.var)){
      throw new IllegalArgumentException("cannot combine Polynomials with different variable symbols");
    }
    Polynomial result = zero(this.var);
    for (int p = 0; p <= degree(); p++){
      result = result.add(extractMonomial(p).times(m));
    }
    return result;
  }

  protected static Polynomial product(ArrayList<Polynomial> alp){
    if (alp.size() == 0){
      return zero("L");
    }
    Polynomial product = alp.get(0).copy();
    for (int i = 1; i < alp.size(); i++){
      product = product.mult(alp.get(i));
    }
    return product;
  }

  // PRESETS //

  public static Polynomial zero(String symbol){
    return new Polynomial(new Fraction[] {new Fraction(0)}, symbol);
  }

  public static Polynomial one(String symbol){
    return new Polynomial(new Fraction[] {new Fraction(1)}, symbol);
  }

  // ROOT FINDER //

  public double[] rootsAsDoubles(){
    return rootsAsDoubles(-1 * Math.pow(10,DEFAULT_ROOT_RANGE), Math.pow(10,DEFAULT_ROOT_RANGE), -3,
      Math.pow(10,DEFAULT_ROOT_MARGIN));
  }

  public double[] rootsAsDoubles(double min, double max, int scanStepPowerOfTen, double margin){
    double[] roots = doubleRoots(min, max, scanStepPowerOfTen, margin);
    for (int i = 0; i < roots.length; i++){
      roots[i] = Malo.roundDoubleProportionally(roots[i]);
    }
    return roots;
  }
  public Fraction[] roots(){
    return roots(-1 * Math.pow(10,DEFAULT_ROOT_RANGE), Math.pow(10,DEFAULT_ROOT_RANGE), -3,
      Math.pow(10,DEFAULT_ROOT_MARGIN));
  }

  public Fraction[] roots(double min, double max, int scanStepPowerOfTen, double margin){
    double[] doubleRts = doubleRoots(min, max, scanStepPowerOfTen, margin);
    Fraction[] roots = new Fraction[doubleRts.length];
    for (int i = 0; i < roots.length; i++){
      roots[i] = new Fraction(doubleRts[i], "round");
    }
    return roots;
  }

  private double[] doubleRoots(double min, double max, int scanStepPowerOfTen, double margin){

    if (min > max){
      throw new IllegalArgumentException("interval for root-searching is collapsed");
    }

    if (degree() == 0){
      if (coeffs[0].isZero()){
        return new double[] {};
      }else{
        return new double[] {};
      }
    }

    // double averageOfCoeffs = 0;
    // for (int i = 0; i < coeffs.length; i++){
    //   averageOfCoeffs += Math.abs(coeffs[i]);
    // }
    // averageOfCoeffs /= coeffs.length;
    //
    // for (int i = 0; i < coeffs.length; i++){
    //   coeffs[i] /= averageOfCoeffs;
    // }

    double[] roots = new double[0];

    double scanStep = Math.pow(10, scanStepPowerOfTen);

    boolean lastPos = false;
    boolean lastNeg = false;
    boolean curPos = false;
    boolean curNeg = false;

    for (double x = min; x <= max; x += scanStep){
      x = Malo.roundDouble(x, scanStepPowerOfTen);
      double value = apply(x);
      if (value < 0){
        curNeg = true;
        curPos = false;
      }else if (value > 0){
        curNeg = false;
        curPos = true;
      }else if (value == 0){
        roots = Malo.aryAppend(roots, x);
        curPos = false;
        curNeg = false;
      }

      if (value != 0){
        if ((curPos && lastNeg) || (curNeg && lastPos)){
          double xLast = Malo.roundDouble(x - scanStep, scanStepPowerOfTen);
          double r = binarySearchForRoot(xLast, x, margin);
          roots = Malo.aryAppend(roots, r);
        }
      }

      lastNeg = curNeg;
      lastPos = curPos;
    }

    if (roots.length < degree()){
      double[] dRoots = derivative().doubleRoots(min, max, scanStepPowerOfTen, margin); //derivative's roots
      for (int i = 0; i < dRoots.length; i++){
        if (Malo.roughlyEquals(apply(dRoots[i]), 0, margin)){
          roots = Malo.aryAppend(roots, dRoots[i]);
        }
      }
    }

    return roots;
  }

  private double binarySearchForRoot(double lo, double hi, double margin){
    if (lo >= hi){
      throw new IllegalArgumentException
        ("lo bound (" + lo + ") is greater than or equal to hi bound (" + hi + ")");
    }
    boolean done = false;
    double loVal, midVal, hiVal;
    double mid = 0.5*(lo+hi);
    while (! done){
      mid = 0.5*(lo + hi);
      midVal = apply(mid);
      if (Malo.roughlyEquals(midVal, 0, margin)){
        break;
      }
      loVal = apply(lo);
      hiVal = apply(hi);
      if (loVal < 0 && hiVal > 0){
        if (midVal > 0){
          hi = mid;
        }else{
          lo = mid;
        }
      }else{
        if (midVal > 0){
          lo = mid;
        }else{
          hi = mid;
        }
      }
    }
    return mid;
  }

  // FACTORING //

  public ArrayList<Polynomial> factor(){
    // System.out.println("factoring "+this);
    Fraction[] roots = this.roots();
    ArrayList<Polynomial> factors = new ArrayList<Polynomial>();
    Polynomial rootProduct = one(this.var);
    factors.add(one(this.var));
    Polynomial xMinusR;
    for (int i = 0; i < roots.length; i++){
      xMinusR = new Polynomial(new Fraction[] {roots[i].mult(-1), Fraction.one()}, this.var);
      rootProduct = rootProduct.mult(xMinusR);
      factors.add(xMinusR);
    }
    if (roots.length != rootProduct.degree()){
      throw new RuntimeException("uh oh");
    }
    if (degree() == rootProduct.degree()){
      if (factors.size() > 0){
        factors.set(0, factors.get(0).scale(coeffs[degree()].divide(rootProduct.coeffs[degree()])));
      }
    }else{
      Fraction[][] systemAry = new Fraction[degree()+1][degree()+2];
      for (int r = 0; r <= degree(); r++){
        for (int i = 0; i < systemAry[r].length; i++){
          systemAry[r][i] = Fraction.zero();
        }
        systemAry[r][systemAry[r].length-1] = this.coeffs[r];
        for (int p = 0; p <= rootProduct.degree(); p++){
          try{
            if (r-p <= degree()-rootProduct.degree()){
              systemAry[r][r-p] = rootProduct.coeffs[p];
            }
          }catch(ArrayIndexOutOfBoundsException e){

          }
        }
      }
      SystemOfEquations system = new SystemOfEquations(new Matrix(systemAry));
      Polynomial factoredIn = new Polynomial(system.solutionAsArray(), this.var);
      factors.add(factoredIn);
    }
    // System.out.println("factors: "+factors+"\n");
    return factors;
  }

  // toString //

  public String allTermsToString(){
    String result = "";
    for (int p = coeffs.length-1; p >= 0; p--){
      result += coeffs[p] + var+"^" + p;
      if (p > 0){
        result += " + ";
      }
    }
    return result;
  }

  public String toString(){
    String result = "";
    if (isZero()){
      return "0";
    }
    double rounded;
    for (int p = degree(); p >= 0; p--){
      if (! coeffs[p].isZero()){
        if (p == degree() && coeffs[p].isNegative()){
          result += "-";
        }
        if (! (coeffs[p].equals(1) || coeffs[p].equals(-1)) || p == 0){
          if (coeffs[p].isPositive() || coeffs[p].isZero()){
            result += coeffs[p];
          }else{
            result += coeffs[p].mult(-1);
          }
        }
        if (p != 0){
          if (p != 1){
            result += var+"^" + p;
          }else{
            result += var;
          }
        }
        if (p > 0){
          if (coeffs[p-1].isPositive() || coeffs[p-1].isZero()){
            result += " + ";
          }else{
            result += " - ";
          }
        }
      }
    }
    if (result.length() >= 3){
      if (result.substring(result.length()-3).equals(" + ")){
        result = result.substring(0, result.length()-3);
      }
    }
    return result;
  }

  // RANDOM //

  public Polynomial random(double min, double max, int p, String sym){
    Fraction[] random = new Fraction[coeffs.length];
    for (int i = 0; i < random.length; i++){
      random[i] = new Fraction(Malo.randomDouble(min, max), p, "round");
    }
    return new Polynomial(random, sym);
  }

  public Polynomial random(double min, double max, int p){
    return random(min, max, p, "x");
  }

  public Polynomial random(double min, double max, String sym){
    return random(min, max, Mathematic.DEFAULT_ROUND, sym);
  }

  public Polynomial random(double min, double max){
    return random(min, max, Mathematic.DEFAULT_ROUND, "x");
  }

  public Polynomial random(int p, String sym){
    return random(Mathematic.RANDOM_LOWER, Mathematic.RANDOM_UPPER, p, sym);
  }

  public Polynomial random(int p){
    return random(Mathematic.RANDOM_LOWER, Mathematic.RANDOM_UPPER, p, "x");
  }

  public Polynomial random(String sym){
    return random(Mathematic.RANDOM_LOWER, Mathematic.RANDOM_UPPER, Mathematic.DEFAULT_ROUND, sym);
  }

  public Polynomial random(){
    return random("x");
  }

}
