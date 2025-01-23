package linalg;

public class Eigenvector extends Vector{

  protected double eigenval;
  protected SquareMatrix M;

  Eigenvector(double[] vals, double eigenvalue){
//    M = m;
//    eigenval = eigenvalue;
//    Matrix identity = new Identity(M.dim());
//    identity.scale(-1 * eigenval);
//    SquareMatrix characteristicEqMatrix = M.add(identity);
//    SystemOfEquations eq = new SystemOfEquations(characteristicEqMatrix);
//    eq.addZeroConstants();
//    eq.makeFirstVariableOne();
//    // System.out.println("system of equations to calculate eigenvector: \n"+eq);
//    eq.solve();
//    this.vals = eq.solution().vals;
    super(vals);
    this.eigenval = eigenvalue;
  }

  public double getEigenvalue(){
    return eigenval;
  }

  public String toString(){
    return "\n"+super.toString() + "eigenvalue: "+eigenval+"\n";
  }

  public Eigenvector copy(){
    double[] ary = getVals();
    double[] newAry = new double[ary.length];
    for (int i = 0; i < ary.length; i++){
      newAry[i] = ary[i];
    }
    return new Eigenvector(newAry, eigenval);
  }

}
