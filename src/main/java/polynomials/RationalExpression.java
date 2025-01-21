package polynomials;

import fractions.Fraction;
import malo.Malo;
import java.util.ArrayList;

public class RationalExpression{

  private ArrayList<Polynomial> numeratorFactors;
  private Polynomial numerator;
  private ArrayList<Polynomial> denominatorFactors;
  private Polynomial denominator;
  private String var;

  public RationalExpression(String symbol){
    numeratorFactors = new ArrayList<Polynomial>();
    denominatorFactors = new ArrayList<Polynomial>();
    numerator = new Polynomial(new int[] {1}, symbol);
    denominator = new Polynomial(new int[] {1}, symbol);
    var = symbol;
  }

  public RationalExpression(){
    this("x");
  }

  public RationalExpression(Polynomial num, String symbol){
    this(symbol);
    addNumeratorFactor(num);
    // System.out.println("numerator factors: "+numeratorFactors);
    // System.out.println(factorsToString());
  }

  public RationalExpression(Polynomial num){
    this(num, "x");
  }

  public RationalExpression(Polynomial num, Polynomial den, String symbol){
    this(num, symbol);
    addDenominatorFactor(den);
  }

  public RationalExpression(Polynomial num, Polynomial den){
    this(num, den, "x");
  }

  public Polynomial getDenominator(){
    return denominator;
  }

  public Polynomial getNumerator(){
    return numerator;
  }

  public void addNumeratorFactor(Polynomial numerF){
    if (! numerF.var.equals(this.var)){
      throw new IllegalArgumentException
        ("cannot add Polynomial with variable symbol "+numerF.var+" to numerator");
    }
    // if (numerF.roots().length > 1){
    //   throw new IllegalArgumentException
    //     ("cannot add polynomial "+numerF+" to numerator because it has more than one root");
    // }
    if (numerF.isZero()){
      numerator = new Polynomial(new int[] {0}, this.var);
      numeratorFactors.add(numerF);
      // System.out.println("zero:"+numerF);
    }else{
      boolean inDenominator = false;
      for (int i = 0; i < denominatorFactors.size(); i++){
        Fraction k = numerF.scalarRatio(denominatorFactors.get(i));
        if (! k.isZero()){
          // System.out.println(k);
          inDenominator = true;
          denominatorFactors.remove(i);
          if (! k.equals(new Fraction(1))){
            numeratorFactors.add(new Polynomial(new Fraction[] {k}, this.var));
          }
          calculateNumerator();
          break;
        }
      }
      if (! inDenominator){
        this.numeratorFactors.add(numerF);
        numerator = numerator.mult(numerF);
      }
    }
  }

  public void addDenominatorFactor(Polynomial denomF){
    if (denomF.isZero()){
      throw new IllegalArgumentException("divide by zero error");
    }
    if (! denomF.var.equals(this.var)){
      throw new IllegalArgumentException
        ("cannot add Polynomial with variable symbol "+denomF.var+" to denominator");
    }
    // if (denomF.roots().length > 1){
    //   throw new IllegalArgumentException
    //     ("cannot add polynomial "+denomF+" to denominator because it has more than one root");
    // }
    boolean inNumerator = false;
    for (int i = 0; i < numeratorFactors.size(); i++){
      Fraction k = denomF.scalarRatio(numeratorFactors.get(i));
      if (! k.isZero()){
        // System.out.println(k);
        inNumerator = true;
        numeratorFactors.remove(i);
        if (! k.equals(new Fraction(1))){
          denominatorFactors.add(new Polynomial(new Fraction[] {k}, this.var));
        }
        calculateNumerator();
        break;
      }
    }
    if (! inNumerator){
      this.denominatorFactors.add(denomF);
      denominator = denominator.mult(denomF);
    }
  }

  public void addNumeratorFactors(ArrayList<Polynomial> numerFs){
    for (int i = 0; i < numerFs.size(); i++){
      addNumeratorFactor(numerFs.get(i));
    }
  }

  public void addDenominatorFactors(ArrayList<Polynomial> denomFs){
    for (int i = 0; i < denomFs.size(); i++){
      addDenominatorFactor(denomFs.get(i));
    }
  }

  private void calculateNumerator(){
    Polynomial product = new Polynomial(new int[] {1}, this.var);
    for (int i = 0; i < numeratorFactors.size(); i++){
      product = product.mult(numeratorFactors.get(i));
    }
    this.numerator = product;
  }

  private void calculateDenominator(){
    Polynomial product = new Polynomial(new int[] {1}, this.var);
    for (int i = 0; i < denominatorFactors.size(); i++){
      product = product.mult(denominatorFactors.get(i));
    }
    this.denominator = product;
  }

  private void expand(){
    calculateNumerator();
    calculateDenominator();
  }

  public RationalExpression mult(RationalExpression other){
    if (! this.var.equals(other.var)){
      throw new IllegalArgumentException
        ("cannot combine RationalExpressions with different variable symbols: "+this.var+" and "+other.var);
    }
    RationalExpression result = this.copy();
    for (int i = 0; i < other.numeratorFactors.size(); i++){
      result.addNumeratorFactor(other.numeratorFactors.get(i));
    }
    for (int i = 0; i < other.denominatorFactors.size(); i++){
      result.addDenominatorFactor(other.denominatorFactors.get(i));
    }
    result.expand();
    return result;
  }

  public RationalExpression mult(Polynomial other){
    return mult(new RationalExpression(other));
  }

  public RationalExpression mult(int k){
    RationalExpression product = this.copy();
    product.numerator = product.numerator.scale(k);
    product.addNumeratorFactor(new Polynomial(new int[] {k}, this.var));
    product.expand();
    return product;
  }

  public RationalExpression reciprocal(){
    RationalExpression result = new RationalExpression(this.var);
    result.numeratorFactors = this.denominatorFactors;
    result.denominatorFactors = this.numeratorFactors;
    result.expand();
    return result;
  }

  public RationalExpression divide(RationalExpression other){
    return this.mult(other.reciprocal());
  }

  public RationalExpression divide(Polynomial other){
    return divide(new RationalExpression(other, other.var));
  }

  public RationalExpression add(RationalExpression other){
    if (! this.var.equals(other.var)){
      throw new IllegalArgumentException
        ("cannot combine RationalExpressions with different variable symbols: "+this.var+" and "+other.var);
    }

    RationalExpression sum = new RationalExpression(this.var);
    RationalExpression thisCopy = this.copy();
    RationalExpression otherCopy = other.copy();

    ArrayList<Polynomial> commonDenominatorFactors =
      lcm(thisCopy.denominatorFactors, otherCopy.denominatorFactors);
    // System.out.println("common denominator factors: "+commonDenominatorFactors);

    sum.addDenominatorFactors(commonDenominatorFactors);

    ArrayList<Polynomial> missingFromThis =
      missing(thisCopy.denominatorFactors, commonDenominatorFactors);
    // System.out.println("factors missing from first: "+missingFromThis);
    ArrayList<Polynomial> missingFromOther =
      missing(otherCopy.denominatorFactors, commonDenominatorFactors);
    // System.out.println("factors missing from second: "+missingFromOther);

    thisCopy.addNumeratorFactors(missingFromThis);
    otherCopy.addNumeratorFactors(missingFromOther);

    ArrayList<Polynomial> numeratorFactorsInCommon =
      gcf(thisCopy.numeratorFactors, otherCopy.numeratorFactors);

    sum.addNumeratorFactors(numeratorFactorsInCommon);

    thisCopy.calculateNumerator();
    otherCopy.calculateNumerator();

    Polynomial sumNum = thisCopy.numerator.add(otherCopy.numerator);

    // sum.addNumeratorFactors(sumNum.factor());
    sum.addNumeratorFactor(sumNum);

    sum.expand();

    return sum;
  }

  public RationalExpression scale(Fraction k){
    RationalExpression result = copy();
    result.addNumeratorFactor(new Polynomial(new Fraction[] {k}, this.var));
    result.expand();
    return result;
  }

  public RationalExpression scale(int k){
    return scale(new Fraction(k));
  }

  public static RationalExpression zero(String symbol){
    Polynomial num = new Polynomial(new int[] {0}, symbol);
    Polynomial den = new Polynomial(new int[] {1}, symbol);
    return new RationalExpression(num, den, symbol);
  }

  public static RationalExpression zero(){
    return zero("x");
  }

  public Fraction[] roots(){
    Fraction[] denRoots = denominator.roots();
    Fraction[] numRoots = numerator.roots();
    ArrayList<Fraction> alRoots = new ArrayList<Fraction>();
    for (int i = 0; i < numRoots.length; i++){
      if (! Malo.aryContains(denRoots, numRoots[i])){
        alRoots.add(numRoots[i]);
      }
    }
    Fraction[] roots = new Fraction[alRoots.size()];
    for (int i = 0; i < roots.length; i++){
      roots[i] = alRoots.get(i);
    }
    return roots;
  }

  public RationalExpression copy(){
    RationalExpression copy = new RationalExpression(this.var);
    copy.numeratorFactors = Malo.arrayListCopy(numeratorFactors);
    copy.denominatorFactors = Malo.arrayListCopy(denominatorFactors);
    copy.numerator = numerator.copy();
    copy.denominator = denominator.copy();
    return copy;
  }

  public boolean equals(RationalExpression other){
    return this.numerator.equals(other.numerator) && this.denominator.equals(other.denominator);
  }

  public boolean isZero(){
    return numerator.isZero();
  }

  private static ArrayList<Polynomial> gcf(ArrayList<Polynomial> a, ArrayList<Polynomial> b){
    ArrayList<Polynomial> gcf = new ArrayList<Polynomial>();
    // ArrayList<Polynomial> ac = Malo.arrayListDeepCopy(a);
    // ArrayList<Polynomial> bc = Malo.arrayListDeepCopy(b);
    int indexOf;
    for (int i = 0; i < a.size(); i++){
      indexOf = Malo.arrayListIndexOfPolynomial(b, a.get(i));
      if (indexOf != -1){
        gcf.add(a.get(i));
        b.remove(indexOf);
        a.remove(i);
        i--;
      }
    }
    if (gcf.size() == 0){
      gcf.add(Polynomial.one(a.get(0).var));
    }
    return gcf;
  }

  private static ArrayList<Polynomial> lcm(ArrayList<Polynomial> a, ArrayList<Polynomial> b){
    ArrayList<Polynomial> lcm = new ArrayList<Polynomial>();
    ArrayList<Polynomial> ac = Malo.arrayListDeepCopy(a);
    ArrayList<Polynomial> bc = Malo.arrayListDeepCopy(b);
    int indexOf;
    for (int i = 0; i < ac.size(); i++){
      indexOf = Malo.arrayListIndexOfPolynomial(bc, ac.get(i));
      if (indexOf != -1){
        lcm.add(ac.get(i));
        // ac.remove(i);
        bc.remove(indexOf);
      }else{
        lcm.add(ac.get(i));
        // ac.remove(i);
      }
    }
    for (int i = 0; i < bc.size(); i++){
      lcm.add(bc.get(i));
    }
    return lcm;
  }

  // a is a subset of cd
  private static ArrayList<Polynomial> missing(ArrayList<Polynomial> a, ArrayList<Polynomial> cd){
    ArrayList<Polynomial> missing = new ArrayList<Polynomial>();
    ArrayList<Polynomial> ac = Malo.arrayListDeepCopy(a);
    ArrayList<Polynomial> cdc = Malo.arrayListDeepCopy(cd);
    int indexOf;
    for (int i = 0; i < cdc.size(); i++){
      indexOf = Malo.arrayListIndexOfPolynomial(ac, cdc.get(i));
      if (indexOf == -1){
        missing.add(cdc.get(i));
      }else{
        cdc.remove(i);
        i--;
      }
    }
    return missing;
  }

  public static RationalExpression one(String sym){
    return new RationalExpression(sym);
  }

  public String toString(){
    if (isZero()){
      return "0";
    }
    return "("+numerator.toString()+")" + " / " + "("+denominator.toString()+")";
  }

  public String factorsToString(){
    String num = "";
    String den = "";
    // System.out.println(numeratorFactors.size());
    for (int i = 0; i < numeratorFactors.size(); i++){
      num += "(" + numeratorFactors.get(i).toString() + ")";
      // System.out.println("factorsToString is working");
    }
    for (int i = 0; i < denominatorFactors.size(); i++){
      den += "(" + denominatorFactors.get(i).toString() + ")";
      // System.out.println("factorsToString is working");
    }
    return num + " / " + den;
  }

}
