package polynomials;

import java.util.ArrayList;
import malo.*;

public class ComplexFraction{

  // FIELDS //

  private ArrayList<VariableExpression> numerator;
  private ArrayList<VariableExpression> denominator;

  // CONSTRUCTORS //

  public ComplexFraction(){
    numerator = new ArrayList<VariableExpression>();
    denominator = new ArrayList<VariableExpression>();
  }

  public ComplexFraction(AlgebraicTerm term){
    this();
    numerator.add(new VariableExpression(term));
  }

  public ComplexFraction(String str){
    this();
    numerator.add(new VariableExpression(str));
  }

  private void makeZeroIfZero(){
    if (isZero()){
      denominator = new ArrayList<VariableExpression>();
      numerator = new ArrayList<VariableExpression>();
      numerator.add(VariableExpression.zero());
    }
  }

  // GETTERS //

  public String getSingleVariable(){
    if (numerator.size() != 1 || denominator.size() != 0){
      throw new IllegalStateException("does not have exactly one variable");
    }
    return numerator.get(0).getSingleVariable();
  }

  // MUTATORS //

  private void addNumeratorFactor(VariableExpression numF){
    int indexOf = Malo.arrayListIndexOfVariableExpression(denominator, numF);
    if (indexOf == -1){
      numerator.add(numF);
    }else{
      denominator.remove(indexOf);
    }
  }

  private void addNumeratorFactors(ArrayList<VariableExpression> numFs){
    for (int i = 0; i < numFs.size(); i++){
      addNumeratorFactor(numFs.get(i));
    }
  }

  private void addDenominatorFactor(VariableExpression denF){
    int indexOf = Malo.arrayListIndexOfVariableExpression(numerator, denF);
    if (indexOf == -1){
      denominator.add(denF);
    }else{
      numerator.remove(indexOf);
    }
  }

  // OPERATIONS //

  public ComplexFraction mult(ComplexFraction other){
    ComplexFraction product = this.copy();
    for (int i = 0; i < other.numerator.size(); i++){
      product.addNumeratorFactor(other.numerator.get(i));
    }
    for (int i = 0; i < other.denominator.size(); i++){
      product.addDenominatorFactor(other.denominator.get(i));
    }
    product.makeZeroIfZero();
    return product;
  }

  public ComplexFraction scale(AlgebraicTerm k){
    ComplexFraction prods = this.copy();
    prods.addNumeratorFactor(new VariableExpression(k));
    return prods;
  }

  public ComplexFraction scale(int k){
    return scale(new AlgebraicTerm(k));
  }

  public ComplexFraction reciprocal(){
    ComplexFraction recip = this.copy();
    recip.numerator = this.denominator;
    recip.denominator = this.numerator;
    return recip;
  }

  public ComplexFraction divide(ComplexFraction other){
    if (other.isZero()){
      throw new IllegalArgumentException("division by zero");
    }
    return this.mult(other.reciprocal());
  }

  public void plus(ComplexFraction other){
    ComplexFraction sum = this.copy();

    ComplexFraction copy = other.copy();

    ArrayList<VariableExpression> lcm = new ArrayList<VariableExpression>();
    // ArrayList<VariableExpression> missingFromThis = new ArrayList<VariableExpression>();
    // ArrayList<VariableExpression> missingFromOther = new ArrayList<VariableExpression>();
    int indexOf;
    for (int i = 0; i < copy.denominator.size(); i++){
      indexOf = Malo.arrayListIndexOfVariableExpression(denominator, copy.denominator.get(i));
      lcm.add(copy.denominator.get(i));
      if (indexOf >= 0){
        denominator.remove(indexOf);
      }else{
        numerator.add(copy.denominator.get(i));
      }
    }
    for (int i = 0; i < denominator.size(); i++){
      lcm.add(denominator.get(i));
      copy.numerator.add(denominator.get(i));
    }

    this.denominator = lcm;

    ArrayList<VariableExpression> gcf = new ArrayList<VariableExpression>();
    for (int i = 0; i < numerator.size(); i++){
      indexOf = Malo.arrayListIndexOfVariableExpression(copy.numerator, numerator.get(i));
      if (indexOf >= 0){
        gcf.add(numerator.get(i));
        numerator.remove(i);
        copy.numerator.remove(i);
        i--;
      }
    }

    // System.out.println("common numerator factors: "+gcf);

    VariableExpression numSum = VariableExpression.product(numerator);
    numSum.plus(VariableExpression.product(copy.numerator));

    numerator = new ArrayList<VariableExpression>();
    addNumeratorFactor(numSum);
    addNumeratorFactors(gcf);

    makeZeroIfZero();
  }

  public void minus(ComplexFraction other){
    this.plus(other.scale(AlgebraicTerm.negOne()));
  }

  // HELPERS //

  // boolean methods //

  public boolean isZero(){
    return VariableExpression.product(numerator).isZero();
  }

  // copy //

  public ComplexFraction copy(){
    ComplexFraction copy = new ComplexFraction();
    copy.numerator = Malo.variableExpressionArrayListDeepCopy(this.numerator);
    copy.denominator = Malo.variableExpressionArrayListDeepCopy(this.denominator);
    return copy;
  }

  // presets //

  public static ComplexFraction one(){
    return new ComplexFraction(AlgebraicTerm.one());
  }

  public static ComplexFraction zero(){
    return new ComplexFraction(AlgebraicTerm.zero());
  }

  // toString //

  public String toString(){
    if (isZero()){
      return "0";
    }
    String result = "";
    for (int i = 0; i < numerator.size(); i++){
      result += "(" + numerator.get(i).toString() + ")";
      // result += "[" + numerator.get(i).parseableToString() + "]";
    }
    if (numerator.size() == 0){
      result += "1";
    }
    result += " / ";
    for (int i = 0; i < denominator.size(); i++){
      result += "(" + denominator.get(i).toString() + ")";
    }
    if (denominator.size() == 0){
      result += "1";
    }
    return result;
  }

}
