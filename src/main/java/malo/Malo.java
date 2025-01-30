package malo;

import java.util.ArrayList;
import polynomials.*;
import fractions.Fraction;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Malo{

  public static double randomDouble(double min, double max){
    if (min >= max){
      throw new IllegalArgumentException("min ("+min+") is greater than or equal to max ("+max+")");
    }
    return (max - min) * Math.random() + min;
  }

  public static int randomInt(int min, int max){
    return (int)((max - min) * Math.random() + min);
  }

  public static long randomLong(long min, long max){
    return (long)((max - min) * Math.random() + min);
  }

  public static int intPowerOfTen(int p){
    if (p < 0){
      throw new IllegalArgumentException("cannot raise 10 to negative power and get int");
    }
    if (p == 0){
      return 1;
    }else{
      return 10 * intPowerOfTen(p-1);
    }
  }

  public static long longPowerOfTen(int p){
    if (p < 0){
      throw new IllegalArgumentException("cannot raise 10 to power of "+p+" and get long");
    }
    if (p == 0){
      return 1;
    }else{
      return 10 * longPowerOfTen(p-1);
    }
  }

  public static int logOfLong(long pot){
    String potStr = pot+"";
    if (potStr.charAt(0) != '1'){
      throw new IllegalArgumentException(pot+" is not an integer power of ten");
    }
    int power = 0;
    for (int i = 1; i < potStr.length(); i++){
      if (potStr.charAt(i) == '0'){
        power++;
      }else{
        throw new IllegalArgumentException(pot+" is not an integer power of ten");
      }
    }
    return power;
  }

  public static long pow(long b, int a){
    if (a < 0){
      throw new IllegalArgumentException("cannot raise "+b+" to the power of "+a+" and get a long");
    }
    if (a == 0){
      return 1;
    }else{
      return b*pow(b, a-1);
    }
  }

  // always returns positive
  public static int lcm(int a, int b){
    a = abs(a);
    b = abs(b);
    if (a == b){
      return a;
    }
    int bigger = max(a, b);
    int multiple;
    for (int m = 1; m < min(a, b); m++){
      multiple = m * bigger;
      if (multiple % a == 0 && multiple % b == 0){
        return multiple;
      }
    }
    return a*b;
  }

  // always returns positive
  public static long lcm(long a, long b){
    a = abs(a);
    b = abs(b);
    if (a == b){
      return a;
    }
    long bigger = max(a, b);
    long multiple;
    for (int m = 1; m < min(a, b); m++){
      multiple = m * bigger;
      if (multiple % a == 0 && multiple % b == 0){
        return multiple;
      }
    }
    return a*b;
  }

  // always returns positive
  public static int gcf(int a, int b){
    a = abs(a);
    b = abs(b);
    if (a == b){
      return a;
    }
    int smaller = min(a, b);
    int factor;
    for (int f = 1; f < smaller; f++){
      factor = smaller / f;
      if (a % factor == 0 && b % factor == 0){
        return factor;
      }
    }
    return 1;
  }

  // always returns positive
  public static long gcf(long a, long b){
    // System.out.println("finding gcf between "+a+" and "+b);
    a = abs(a);
    b = abs(b);
    if (a == b){
      return a;
    }
    if (a == 1 || b == 1){
      return 1;
    }
    long smaller = min(a, b);
    long factor;
    for (long f = 1; f < smaller; f++){
      factor = smaller / f;
      if (a % factor == 0 && b % factor == 0){
        return factor;
      }
    }
    return 1;
  }

  public static int max(int a, int b){
    if (a >= b){
      return a;
    }else{
      return b;
    }
  }

  public static long max(long a, long b){
    if (a >= b){
      return a;
    }else{
      return b;
    }
  }

  public static int min(int a, int b){
    if (a <= b){
      return a;
    }else{
      return b;
    }
  }

  public static long min(long a, long b){
    if (a <= b){
      return a;
    }else{
      return b;
    }
  }

  public static int abs(int a){
    if (a >= 0){
      return a;
    }else{
      return -1 * a;
    }
  }

  public static long abs(long a){
    if (a >= 0){
      return a;
    }else{
      return -1 * a;
    }
  }

  public static boolean roughlyOne(double x){
    return roundDoubleProportionally(x) == 1;
  }

  public static boolean roughlyZero(double x){
    return roundDoubleProportionally(x) == 0;
  }

  public static double roundDouble(double x, int n){
    // return Math.round(Math.pow(10, -1*n) * x) * Math.pow(10, n);
    BigDecimal b = new BigDecimal(Double.toString(x));
    b = b.setScale(-1*n, RoundingMode.HALF_UP);
    return b.doubleValue();
  }

  public static double roundDouble(double x){
    return roundDouble(x, Mathematic.DEFAULT_ROUND);
  }

  public static double roundDoubleProportionally(double x, int n){
    return roundDouble(x, highestPlaceValue(x)+n);
  }

  public static double roundDoubleProportionally(double x){
    return roundDoubleProportionally(x, Mathematic.DEFAULT_PRO_ROUND);
  }

  public static int highestPlaceValue(double x){
    if (x == 0){
      return 0;
    }
    if (x < 0){
      x *= -1;
    }
    if (x >= 1){
      String num = (int)(x)+"";
      return num.length()-1;
    }else if (x == 0){
      return 0;
    }else if (x >= 0.001){
      String num = x+"";
      // System.out.println(num);
      for (int i = 2; i < num.length(); i++){
        if (num.charAt(i) != '0' && num.charAt(i) != '.'){
          return (-1*i) + 1;
        }
      }
    }else{
      String num = x+"";
      return Integer.parseInt(num.substring(num.length()-2));
    }
    throw new RuntimeException("highestPlaceValue no work");
  }

  public static boolean numInRange(double x, double lo, double hi){
    return ((x >= lo) && (x <= hi));
  }

  public static boolean roughlyEquals(double x, double t, double margin){
    return numInRange(x, t - margin, t + margin);
  }

  public static boolean roughlyEquals(double x, double t){
    int p;
    if (t == 0){
      p = max(highestPlaceValue(x), highestPlaceValue(t)) + Mathematic.DEFAULT_ZERO_MARGIN;
    }else{
      p = max(highestPlaceValue(x), highestPlaceValue(t)) + Mathematic.DEFAULT_PRO_ROUND;
    }
    double margin = Math.pow(10, p);
    return roughlyEquals(x, t, margin);
  }

  public static boolean fractionInRange(Fraction x, Fraction lo, Fraction hi){
    return (x.moreThanOrEqualTo(lo) && x.lessThanOrEqualTo(hi));
  }

  public static boolean roughlyEquals(Fraction x, Fraction t, Fraction margin){
    return fractionInRange(x, t.subtract(margin), t.add(margin));
  }

  public static boolean roughlyEquals(Fraction x, Fraction t){
    Fraction margin = new Fraction(1, Malo.longPowerOfTen(Mathematic.DEFAULT_PRO_ROUND * -1));
    return roughlyEquals(x, t, margin);
  }

  public static boolean roughlyEquals(Fraction x, long t, Fraction margin){
    return roughlyEquals(x, new Fraction(t), margin);
  }

  public static boolean roughlyEquals(Fraction x, long t){
    return roughlyEquals(x, new Fraction(t));
  }

  public static int sign(double x){
    if (x < 0){
      return -1;
    }else if (x > 0){
      return 1;
    }else{
      return 0;
    }
  }

  // ARRAY //

  public static Fraction[] aryAppend(Fraction[] ary, Fraction newDouble){
    Fraction[] result = new Fraction[ary.length+1];
    for (int i = 0; i < ary.length; i++){
      result[i] = ary[i];
    }
    result[ary.length] = newDouble;
    return result;
  }

  public static double[] aryAppend(double[] ary, double newDouble){
    double[] result = new double[ary.length+1];
    for (int i = 0; i < ary.length; i++){
      result[i] = ary[i];
    }
    result[ary.length] = newDouble;
    return result;
  }

  public static int[] aryAppend(int[] ary, int newInt){
    int[] result = new int[ary.length+1];
    for (int i = 0; i < ary.length; i++){
      result[i] = ary[i];
    }
    result[ary.length] = newInt;
    return result;
  }

  public static int[][] aryAppend(int[][] ary, int[] newIntAry){
    if (ary.length == 0){
      int[][] result = new int[1][newIntAry.length];
      result[0] = newIntAry;
      return result;
    }else{
      int[][] result = new int[ary.length+1][ary[0].length];
      for (int i = 0; i < ary.length; i++){
        result[i] = ary[i];
      }
      result[ary.length] = newIntAry;
      return result;
    }
  }

  public static String[] aryAppend(String[] ary, String str){
    String[] result = new String[ary.length+1];
    for (int i = 0; i < ary.length; i++){
      result[i] = ary[i];
    }
    result[ary.length] = str;
    return result;
  }

  public static double[] aryPrepend(double[] ary, double d){
    double[] result = new double[ary.length+1];
    result[0] = d;
    for (int i = 1; i < result.length; i++){
      result[i] = ary[i-1];
    }
    return result;
  }


  public static Fraction[] aryPrepend(Fraction[] ary, Fraction f){
    Fraction[] result = new Fraction[ary.length+1];
    result[0] = f;
    for (int i = 1; i < result.length; i++){
      result[i] = ary[i-1];
    }
    return result;
  }

  public static String[] aryPrepend(String[] ary, String f){
    String[] result = new String[ary.length+1];
    result[0] = f;
    for (int i = 1; i < result.length; i++){
      result[i] = ary[i-1];
    }
    return result;
  }

  public static String[] aryRemove(String[] ary, int index){
    String[] result = new String[(ary.length-1)];
    for (int i = 0; i < index; i++){
      result[i] = ary[i];
    }
    for (int i = index+1; i < ary.length; i++){
      result[i-1] = ary[i];
    }
    return result;
  }

  public static int[] aryRemove(int[] ary, int index){
    int[] result = new int[(ary.length-1)];
    for (int i = 0; i < index; i++){
      result[i] = ary[i];
    }
    for (int i = index+1; i < ary.length; i++){
      result[i-1] = ary[i];
    }
    return result;
  }

  public static double[] aryRemove(double[] ary, int index){
    double[] result = new double[(ary.length-1)];
    for (int i = 0; i < index; i++){
      result[i] = ary[i];
    }
    for (int i = index+1; i < ary.length; i++){
      result[i-1] = ary[i];
    }
    return result;
  }

  public static boolean aryContains(Fraction[] ary, Fraction d){
    for (Fraction item : ary){
      if (item.equals(d)){
        return true;
      }
    }
    return false;
  }

  public static boolean aryContains(double[] ary, double d){
    for (double item : ary){
      if (item == d){
        return true;
      }
    }
    return false;
  }

  public static boolean aryContains(String[] ary, String s){
    for (String str : ary){
      if (str.equals(s)){
        return true;
      }
    }
    return false;
  }

  public static boolean aryContains(int[][] ary, int[] op){
    for (int[] arr : ary){
      for (int i = 0; i < arr.length; i++){
        if (arr[i] != op[i]){
          break;
        }
        if (i == arr.length-1){
          return true;
        }
      }
    }
    return false;
  }

  public static String[] aryCopy(String[] ary){
    String[] copy = new String[ary.length];
    for (int i = 0; i < ary.length; i++){
      copy[i] = ary[i];
    }
    return copy;
  }

  public static int[] aryCopy(int[] ary){
    int[] copy = new int[ary.length];
    for (int i = 0; i < ary.length; i++){
      copy[i] = ary[i];
    }
    return copy;
  }

  public static double[] uniformStochasticAry(int length){
    double[] result = new double[length];
    for (int i = 0; i < length; i++){
      result[i] = 1.0 / (double)(length);
    }
    return result;
  }

  public static double[] uniformPseudoStochasticAry(int trueLength){
    double[] result = new double[trueLength-1];
    for (int i = 0; i < result.length; i++){
      result[i] = 1.0 / (double)(trueLength);
    }
    return result;
  }

  public static Fraction[] uniformStochasticFractionAry(int length){
    Fraction[] result = new Fraction[length];
    for (int i = 0; i < length; i++){
      result[i] = new Fraction(1, length);
    }
    return result;
  }

  public static Fraction[] uniformPseudoStochasticFractionAry(int trueLength){
    Fraction[] result = new Fraction[trueLength-1];
    for (int i = 0; i < result.length; i++){
      result[i] = new Fraction(1, trueLength);
    }
    return result;
  }

  public static int aryIndexOf(String[] ary, String target){
    for (int i = 0; i < ary.length; i++){
      if (ary[i] != null){
        if (ary[i].equals(target)){
          return i;
        }
      }
    }
    return -1;
  }

  public static int arySum(int[] ary){
    int sum = 0;
    for (int i = 0; i < ary.length; i++){
      sum += ary[i];
    }
    return sum;
  }

  // ArrayList //

  public static boolean arrayListContainsPolynomial(ArrayList<Polynomial> al, Polynomial p){
    for (int i = 0; i < al.size(); i++){
      if (al.get(i).equals(p)){
        return true;
      }
    }
    return false;
  }

  public static int arrayListIndexOfPolynomial(ArrayList<Polynomial> al, Polynomial p){
    for (int i = 0; i < al.size(); i++){
      if (al.get(i).equals(p)){
        return i;
      }
    }
    return -1;
  }

  public static int arrayListIndexOfAlgebraicTerm(ArrayList<AlgebraicTerm> al, AlgebraicTerm p){
    for (int i = 0; i < al.size(); i++){
      if (al.get(i).equals(p)){
        return i;
      }
    }
    return -1;
  }

  public static int arrayListIndexOfVariableExpression(ArrayList<VariableExpression> al, VariableExpression p){
    for (int i = 0; i < al.size(); i++){
      if (al.get(i).equals(p)){
        return i;
      }
    }
    return -1;
  }

  public static ArrayList<Polynomial> arrayListCopy(ArrayList<Polynomial> al){
    ArrayList<Polynomial> copy = new ArrayList<Polynomial>();
    for (int i = 0; i < al.size(); i++){
      copy.add(al.get(i));
    }
    return copy;
  }

  public static ArrayList<Polynomial> arrayListDeepCopy(ArrayList<Polynomial> al){
    ArrayList<Polynomial> copy = new ArrayList<Polynomial>();
    for (int i = 0; i < al.size(); i++){
      copy.add(al.get(i).copy());
    }
    return copy;
  }

  public static ArrayList<AlgebraicTerm> algebraicTermArrayListDeepCopy(ArrayList<AlgebraicTerm> al){
    ArrayList<AlgebraicTerm> copy = new ArrayList<AlgebraicTerm>();
    for (int i = 0; i < al.size(); i++){
      copy.add(al.get(i).copy());
    }
    return copy;
  }

  public static ArrayList<VariableExpression> variableExpressionArrayListDeepCopy(ArrayList<VariableExpression> al){
    ArrayList<VariableExpression> copy = new ArrayList<VariableExpression>();
    for (int i = 0; i < al.size(); i++){
      copy.add(al.get(i).copy());
    }
    return copy;
  }

  // LOGICAL //

  public static boolean xor(boolean p, boolean q){
    return ((! p) && q) || (p && (! q));
  }

}
