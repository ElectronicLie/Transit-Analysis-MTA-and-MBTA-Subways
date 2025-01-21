package polynomials;

import java.util.Arrays;

public class SimpleRationalFraction{

  // protected Polynomial numerator;
  // protected Polynomial denominator;
  // protected String var;
  //
  // public SimpleRationalFraction(Polynomial num, Polynomial den, String sym){
  //   numerator = num;
  //   if (den.isZero()){
  //     throw new IllegalArgumentException("cannot make denominator 0");
  //   }
  //   denominator = den;
  //   var = sym;
  // }
  //
  // public SimpleRationalFraction(Polynomial num, Polynomial den){
  //   this(num, den, "x");
  // }
  //
  // public SimpleRationalFraction(Polynomial num, String sym){
  //   this(num, Polynomial.one(sym), sym);
  // }
  //
  // public SimpleRationalFraction(Polynomial num){
  //   this(num, "x");
  // }
  //
  // public SimpleRationalFraction(String sym){
  //   this(Polynomial.one(sym), sym);
  // }
  //
  // public SimpleRationalFraction(){
  //   this("x");
  // }
  //
  // public Polynomial getNumerator(){
  //   return numerator;
  // }
  //
  // public Polynomial getDenominator(){
  //   return denominator;
  // }
  //
  // public SimpleRationalFraction mult(SimpleRationalFraction other){
  //   if (! this.var.equals(other.var)){
  //     throw new IllegalArgumentException
  //       ("cannot multiply rational fraction with variable symbol "+other.var+
  //       " with one with variable symbol "+this.var);
  //   }
  //   SimpleRationalFraction product = this.copy();
  //   product.numerator = product.numerator.mult(other.numerator);
  //   product.denominator = product.denominator.mult(other.denominator);
  //   return product;
  // }
  //
  // public SimpleRationalFraction divide(SimpleRationalFraction other){
  //   if (! this.var.equals(other.var)){
  //     throw new IllegalArgumentException
  //       ("cannot divide rational fraction with variable symbol "+this.var+
  //       " by one with variable symbol "+other.var);
  //   }
  //   return this.mult(other.reciprocal());
  // }
  //
  // public SimpleRationalFraction reciprocal(){
  //   if (this.numerator.isZero()){
  //     throw new IllegalStateException("cannot reciprocate rational fraction with numerator 0");
  //   }
  //   SimpleRationalFraction result = new SimpleRationalFraction(
  //     this.denominator.copy(), this.numerator.copy(), this.var
  //   );
  //   return result;
  // }
  //
  // public SimpleRationalFraction add(SimpleRationalFraction other){
  //   if (! this.var.equals(other.var)){
  //     throw new IllegalArgumentException
  //       ("cannot add rational fraction with variable symbol "+other.var+
  //       " to one with variable symbol "+this.var);
  //   }
  //   Polynomial sumDenominator = this.denominator.mult(other.denominator);
  //   Polynomial sumNumerator =
  //     this.numerator.mult(other.denominator).add(other.numerator.mult(this.denominator));
  //   sumNumerator.correctZeros();
  //   return new SimpleRationalFraction(sumNumerator, sumDenominator, this.var);
  // }
  //
  // public SimpleRationalFraction scale(double k){
  //   SimpleRationalFraction result = new SimpleRationalFraction
  //     (this.numerator.scale(k), this.denominator, this.var);
  //   return result;
  // }
  //
  // public double[] roundedRoots(int n){
  //   double[] roots = roots();
  //   for (int i = 0; i < roots.length; i++){
  //     roots[i] = Polynomial.round(roots[i], n);
  //   }
  //   return roots;
  // }
  //
  // public double[] roundedRoots(){
  //   return roundedRoots(Polynomial.DEFAULT_ROUND);
  // }
  //
  // public double[] roots(){
  //   double[] numRoots = numerator.roots();
  //   double[] denRoots = denominator.roots();
  //   int noRoots = numRoots.length;
  //   for (int d = 0; d < denRoots.length; d++){
  //     if (Polynomial.aryContains(numRoots,denRoots[d],Math.pow(10,Polynomial.DEFAULT_MARGIN))){
  //       noRoots--;
  //     }
  //   }
  //   double[] roots = new double[noRoots];
  //   // System.out.println("numerator roots:\n"+Arrays.toString(numRoots));
  //   // System.out.println("denominator roots:\n"+Arrays.toString(denRoots));
  //   // System.out.println("number of roots: "+noRoots+"\n");
  //   int noRootsExcluded=0;
  //   for (int i = 0; i < numRoots.length; i++){
  //     // System.out.println("index: "+i);
  //     // System.out.println("noRootsExcluded: "+noRootsExcluded);
  //     if (Polynomial.aryContains(denRoots,numRoots[i],Math.pow(10,Polynomial.DEFAULT_MARGIN))){
  //       noRootsExcluded++;
  //       // System.out.println("one more excluded root: "+noRootsExcluded+"\n");
  //     }else{
  //       // System.out.println("this root not excluded: "+numRoots[i]+"\n");
  //       roots[i-noRootsExcluded] = numRoots[i];
  //     }
  //   }
  //   return roots;
  // }
  //
  // public SimpleRationalFraction copy(){
  //   SimpleRationalFraction copy = new SimpleRationalFraction(
  //     this.numerator.copy(), this.denominator.copy(), this.var
  //   );
  //   return copy;
  // }
  //
  // public SimpleRationalFraction round(int n){
  //   SimpleRationalFraction rounded = new SimpleRationalFraction
  //     (this.numerator.round(n), this.denominator.round(n), this.var);
  //   return rounded;
  // }
  //
  // public SimpleRationalFraction round(){
  //   return round(Polynomial.DEFAULT_ROUND);
  // }
  //
  // public boolean isZero(){
  //   return numerator.isZero();
  // }
  //
  // public String toString(int n){
  //   return "("+numerator.toString(n)+")" + " / " + "("+denominator.toString(n)+")";
  // }
  //
  // public String toString(){
  //   return toString(Polynomial.DEFAULT_ROUND);
  // }
  //
  // public String toStringUnRounded(){
  //   return "("+numerator.toStringUnRounded()+")" + " / " + "("+denominator.toStringUnRounded()+")";
  // }

}
