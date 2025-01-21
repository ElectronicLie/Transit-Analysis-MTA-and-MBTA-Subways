package polynomials;

import java.util.ArrayList;
import malo.*;
import fractions.*;

public class VariableExpression{

  // FIELDS //

  ArrayList<AlgebraicTerm> terms;

  // CONSTRUCTORS //

  public VariableExpression(ArrayList<AlgebraicTerm> al){
    terms = al;
    removeZeroTerms();
  }

  public VariableExpression(){
    this(new ArrayList<AlgebraicTerm>());
  }

  public VariableExpression(AlgebraicTerm term){
    this(new ArrayList<AlgebraicTerm>());
    terms.add(term);
    removeZeroTerms();
  }

  public VariableExpression(String str){
    this(new ArrayList<AlgebraicTerm>());
    terms.add(new AlgebraicTerm(str));
    removeZeroTerms();
  }

  private void removeZeroTerms(){
    for (int i = 0; i < terms.size(); i++){
      if (terms.get(i).isZero()){
        terms.remove(i);
        i--;
      }
    }
  }

  // GETTERS //

  public int degree(){
    int maxDeg = 0;
    int curDeg;
    for (int i = 0; i < terms.size(); i++){
      curDeg = terms.get(i).degree();
      if (curDeg > maxDeg){
        maxDeg = curDeg;
      }
    }
    return maxDeg;
  }

  public String getSingleVariable(){
    if (terms.size() != 1){
      throw new IllegalStateException("more than one term");
    }
    return terms.get(0).getSingleVariable();
  }

  // CALCULATORS //

  public Polynomial plugIn(String[] varsOrder, Polynomial[] values){
    Polynomial sum = Polynomial.zero(values[0].getVar());
    for (int i = 0; i < terms.size(); i++){
      sum = sum.add(terms.get(i).plugIn(varsOrder, values));
    }
    return sum;
  }

  public Fraction plugIn(String[] varsOrder, Fraction[] values){
    Fraction sum = Fraction.zero();
    for (int i = 0; i < terms.size(); i++){
      sum = sum.add(terms.get(i).plugIn(varsOrder, values));
    }
    return sum;
  }

  public double plugIn(String[] varsOrder, double[] values){
    double sum = 0;
    for (int i = 0; i < terms.size(); i++){
      sum += terms.get(i).plugIn(varsOrder, values);
    }
    return sum;
  }

  public VariableExpression plugIn(String[] varsOrder, String[] values){
    ArrayList<AlgebraicTerm> al = new ArrayList<AlgebraicTerm>();
    for (int i = 0; i < terms.size(); i++){
      al.add(terms.get(i).plugIn(varsOrder, values));
    }
    return new VariableExpression(al);
  }

  // OPERATIONS //

  public void plus(VariableExpression other){
    int likeTerm;
    for (int i = 0; i < other.terms.size(); i++){
      likeTerm = -1;
      for (int j = 0; j < this.terms.size(); j++){
        if (AlgebraicTerm.likeTerms(other.terms.get(i), this.terms.get(j))){
          likeTerm = j;
          break;
        }
      }
      if (likeTerm == -1){
        this.terms.add(other.terms.get(i));
      }else{
        this.terms.get(likeTerm).addLikeTerm(other.terms.get(i));
      }
    }
    removeZeroTerms();
  }

  public void minus(VariableExpression other){
    this.plus(other.scale(AlgebraicTerm.negOne()));
  }

  public VariableExpression mult(VariableExpression other){
    VariableExpression product = new VariableExpression();
    for (int i = 0; i < other.terms.size(); i++){
      product.plus(this.scale(other.terms.get(i)));
    }
    // product.removeZeroTerms(); not needed because it is present in plus
    return product;
  }

  public VariableExpression scale(AlgebraicTerm k){
    VariableExpression copy = this.copy();
    for (int i = 0; i < copy.terms.size(); i++){
      copy.terms.set(i, copy.terms.get(i).mult(k));
    }
    return copy;
  }

  public static VariableExpression product(ArrayList<VariableExpression> al){
    VariableExpression prdct = one();
    for (int i = 0; i < al.size(); i++){
      if (al.get(i).isZero()){
        return zero();
      }
      prdct = prdct.mult(al.get(i));
    }
    return prdct;
  }

  // boolean methods //

  public boolean equals(VariableExpression other){
    if (this.terms.size() != other.terms.size()){
      return false;
    }
    int indexOf;
    for (int i = 0; i < other.terms.size(); i++){
      indexOf = Malo.arrayListIndexOfAlgebraicTerm(this.terms, other.terms.get(i));
      if (indexOf == -1){
        return false;
      }
    }
    return true;
  }

  public boolean isZero(){
    return (terms.size() == 0);
  }

  // PRESETS //

  public static VariableExpression zero(){
    return new VariableExpression(AlgebraicTerm.zero());
  }

  public static VariableExpression one(){
    return new VariableExpression(AlgebraicTerm.one());
  }

  // copy //

  public VariableExpression copy(){
    VariableExpression copy = new VariableExpression();
    copy.terms = Malo.algebraicTermArrayListDeepCopy(this.terms);
    return copy;
  }

  // toString //

  public String toString(){
    if (terms.size() == 0){
      return "";
    }
    String result = "";
    boolean nextNeg = false;
    for (int i = 0; i < terms.size()-1; i++){
      if (! terms.get(i).isZero()){
        if (nextNeg){
          result += terms.get(i).negToString();
        }else{
          result += terms.get(i).toString();
        }
        if (terms.get(i+1).posCoefficient()){
          result += " + ";
          nextNeg = false;
        }else{
          result += " - ";
          nextNeg = true;
        }
      }
    }
    if (nextNeg)
      result += terms.get(terms.size()-1).negToString();
    else
      result += terms.get(terms.size()-1).toString();
    return result;
  }

  public String parseableToString(){
    String result = "";
    for (int i = 0; i < terms.size(); i++){
      result += terms.get(i).parseableToString() + " + ";
    }
    return result;
  }

  // parse from String //

  // str must have been generated by parseableToString()
  public static VariableExpression parseVariableExpression(String str){
    ArrayList<AlgebraicTerm> al = new ArrayList<AlgebraicTerm>();
    int curTermEnd = str.indexOf("+");
    String curTerm;
    while (curTermEnd >= 0){
      curTerm = str.substring(0, curTermEnd);
      al.add(AlgebraicTerm.parseAlgebraicTerm(curTerm));
      str = str.substring(curTermEnd + 1, str.length());
      curTermEnd = str.indexOf("+");
    }
    return new VariableExpression(al);
  }

}
