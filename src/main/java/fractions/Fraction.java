package fractions;

import malo.*;

public class Fraction{

  // FIELDS //

  static final int DEFAULT_ROUND = -3;

  private long numerator;
  private long denominator;

  // CONSTRUCTORS //

  public Fraction(long num, long den){
    if (den == 0){
      throw new IllegalArgumentException("cannot construct fraction with denominator zero");
    }
    numerator = num;
    denominator = den;
    simplify();
  }

  public Fraction(long num){
    this(num, 1);
  }

  public Fraction(){
    this(1, 1);
  }

  public Fraction(double d, Fraction p){
    numerator = roundLong(d, p);
    denominator = p.denominator;
    simplify();
  }

  public Fraction(double d, int p, String mode){
    if (mode.equals("round")){
      if (p > 0){
        p = 0;
      }
      Fraction r = new Fraction(1, Malo.longPowerOfTen(p));
      numerator = roundLong(d, r);
      denominator = r.denominator;
      simplify();
    }else{
      throw new IllegalArgumentException("mode "+mode+
        " is not a valid mode for fraction construction");
    }
  }

  public Fraction(double d, String mode){
    if (mode.equals("round")){
      d = Malo.roundDoubleProportionally(d);
      Fraction p = new Fraction(1,
        Malo.longPowerOfTen((Malo.highestPlaceValue(d)+Mathematic.DEFAULT_PRO_ROUND)*(-1)));
      numerator = roundLong(d, p);
      denominator = p.denominator;
      simplify();
    }else{
      throw new IllegalArgumentException("mode "+mode+
        " is not a valid mode for fraction construction");
    }
  }

  public static Fraction powerOfTen(int p){
    if (p >= 0){
      return new Fraction(Malo.longPowerOfTen(p));
    }else{
      return new Fraction(1, Malo.longPowerOfTen(-1*p));
    }
  }

  // GETTERS //

  public double getValue(){
    return (double)(numerator) / (double)(denominator);
  }

  // CALCULATORS //

  private void simplify(){
    long gcf = Malo.gcf(numerator, denominator);
    if (denominator < 0 && numerator != 0){
      gcf *= -1;
    }
    numerator /= gcf;
    denominator /= gcf;
    if (numerator == 0){
      denominator = 1;
    }
  }

  public Fraction copy(){
    return new Fraction(numerator, denominator);
  }

  public static Fraction average(Fraction[] ary){
    Fraction mean = new Fraction(0);
    for (int i = 0; i < ary.length; i++){
      mean = mean.add(ary[i]);
    }
    mean = mean.divide(ary.length);
    return mean;
  }

  public static Fraction fractionPowerOfTen(int p){
    if (p >= 0){
      return new Fraction(Malo.longPowerOfTen(p), 1);
    }else{
      return new Fraction(1, Malo.longPowerOfTen(-1*p));
    }
  }

  // OPERATIONS //

  public Fraction mult(Fraction other){
    Fraction f1 = new Fraction(numerator, other.denominator);
    Fraction f2 = new Fraction(other.numerator, denominator);
    Fraction product = new Fraction();
    product.numerator = f1.numerator * f2.numerator;
    product.denominator = f1.denominator * f2.denominator;
    return product;
  }

  public Fraction mult(int i){
    Fraction f = new Fraction(i, denominator);
    Fraction product = new Fraction(1);
    product.numerator = f.numerator * this.numerator;
    product.denominator = f.denominator;
    return product;
  }

  public Fraction reciprocal(){
    if (numerator == 0){
      throw new IllegalStateException
        ("cannot reciprocate "+this.detailedToString()+" because it has numerator 0");
    }
    return new Fraction(denominator, numerator);
  }

  public Fraction divide(Fraction other){
    if (other.numerator == 0){
      throw new IllegalArgumentException("cannot divide "+this+" by "+other);
    }
    return this.mult(other.reciprocal());
  }

  public Fraction divide(int i){
    if (i == 0){
      throw new IllegalArgumentException("cannot divide fraction by 0");
    }
    return new Fraction(numerator, denominator*i);
  }

  public Fraction add(Fraction other){
    long commonDenominator = Malo.lcm(denominator, other.denominator);
    this.numerator *= commonDenominator / denominator;
    other.numerator *= commonDenominator / other.denominator;
    return new Fraction(numerator+other.numerator, commonDenominator);
  }

  public Fraction add(int i){
    return new Fraction(numerator+denominator*i, denominator);
  }

  public Fraction subtract(Fraction other){
    return this.add(other.mult(-1));
  }

  public Fraction subtract(int i){
    return this.add(-1*i);
  }

  public Fraction pow(int p){
    if (p < 0){
      return this.reciprocal().pow(-1*p);
    }else if (p == 0){
      return new Fraction(1);
    }else{
      return this.mult(pow(p-1));
    }
  }

  public Fraction abs(){
    return new Fraction(Malo.abs(numerator), Malo.abs(denominator));
  }

  // COMPARITIVE //

  public boolean isZero(){
    return (numerator == 0);
  }

  public boolean isPositive(){
    return (numerator > 0);
  }

  public boolean isNegative(){
    return (numerator < 0);
  }

  public boolean equals(Fraction other){
    return (numerator==other.numerator && denominator==other.denominator);
  }

  public boolean equals(int i){
    return (numerator == i && denominator == 1);
  }

  public boolean moreThan(Fraction other){
    long commonDenominator = Malo.lcm(denominator, other.denominator);
    long thisNum = numerator * commonDenominator / Malo.abs(denominator);
    long otherNum = other.numerator * commonDenominator / Malo.abs(other.denominator);
    return (thisNum > otherNum);
  }

  public boolean lessThan(Fraction other){
    long commonDenominator = Malo.lcm(denominator, other.denominator);
    long thisNum = numerator * commonDenominator / Malo.abs(denominator);
    long otherNum = other.numerator * commonDenominator / Malo.abs(other.denominator);
    return (thisNum < otherNum);
  }

  public boolean moreThanOrEqualTo(Fraction other){
    return equals(other) || moreThan(other);
  }

  public boolean lessThanOrEqualTo(Fraction other){
    return equals(other) || lessThan(other);
  }

  public boolean roughlyEquals(Fraction other, int n){
    return this.round(n).equals(other.round(n));
  }

  // ROUNDING //

  public Fraction round(int n){
    Fraction roundingFraction = new Fraction(1, Malo.longPowerOfTen(n));
    if (n < 0){
      return new Fraction(roundLong(getValue(), roundingFraction), Malo.longPowerOfTen(-1*n));
    }else{
      return new Fraction(roundLong(getValue(), roundingFraction), 1);
    }
  }

  private static long roundLong(double x, Fraction n){
    int power = Malo.logOfLong(n.denominator);
    x *= Math.pow(10, power);
    return (long)(Math.round(x));
  }

  // RANDOM //

  public static Fraction random(long min, long max){
    long num = Malo.randomLong(min, max);
    long den = Malo.randomLong(min, max);
    while(den == 0){
      den = Malo.randomLong(min, max);
    }
    return new Fraction(num, den);
  }

  public static Fraction randomInt(long min, long max){
    long num = Malo.randomLong(min, max);
    return new Fraction(num);
  }

  public static Fraction random(){
    return random(Mathematic.RANDOM_LOWER, Mathematic.RANDOM_UPPER);
  }

  public static Fraction randomInt(){
    return randomInt(Mathematic.RANDOM_LOWER, Mathematic.RANDOM_UPPER);
  }

  public static Fraction randomFromValue(double min, double max, int p){
    double randD = Malo.randomDouble(min, max);
    return new Fraction(randD, new Fraction(1, Malo.longPowerOfTen(p)));
  }

  public static Fraction randomFromValue(double min, double max){
    return randomFromValue(min, max, DEFAULT_ROUND);
  }

  public static Fraction randomFromValue(int p){
    return randomFromValue(Mathematic.RANDOM_LOWER, Mathematic.RANDOM_UPPER, p);
  }

  public static Fraction randomFromValue(){
    return randomFromValue(Mathematic.DEFAULT_ROUND);
  }

  // PRESETS //

  public static Fraction zero(){
    return new Fraction(0);
  }

  public static Fraction one(){
    return new Fraction(1);
  }

  public static Fraction negOne(){
    return new Fraction(-1);
  }

  // toString //

  public String toString(){
    if (numerator == 0){
      return "0";
    }else if (denominator == 1){
      return ""+numerator;
    }
    return numerator + "/" + denominator;
  }

  private String detailedToString(){
    return numerator + "/" + denominator;
  }

}
