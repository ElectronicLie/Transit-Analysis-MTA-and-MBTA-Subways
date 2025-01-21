package polynomials;

import malo.*;
import fractions.*;

public class Monomial extends Polynomial{

  protected Fraction coeff;
  protected int power;

  public Monomial(Fraction coeffi, int poweri){
    super(new Fraction[] {coeffi});
    this.coeff = coeffi;
    this.power = poweri;
    this.coeffs = new Fraction[power+1];
    for (int p = 0; p <= power; p++){
      if (p == power){
        coeffs[p] = coeff;
      }else{
        coeffs[p] = new Fraction(0);
      }
    }
    this.var = "x";
  }

  public Monomial(Fraction coeffi, int poweri, String symbol){
    super(new Fraction[] {coeffi}, symbol);
    this.coeff = coeffi;
    this.power = poweri;
    coeffs = new Fraction[power+1];
    for (int p = 0; p <= power; p++){
      if (p == power){
        coeffs[p] = coeff;
      }else{
        coeffs[p] = new Fraction(0);
      }
    }
    this.var = symbol;
  }

  public Monomial times(Monomial other){
    if (! other.var.equals(this.var)){
      throw new IllegalArgumentException("cannot combine Monomials with different variable symbols");
    }
    return new Monomial(this.coeff.mult(other.coeff), this.power + other.power, this.var);
  }

  public Monomial scale(Fraction k){
    return new Monomial(coeff.mult(k), power, this.var);
  }

  public Monomial scale(int k){
    return new Monomial(coeff.mult(k), power, this.var);
  }

}
