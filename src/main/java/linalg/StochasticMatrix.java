package linalg;

import java.util.ArrayList;

public class StochasticMatrix extends SquareMatrix{

  public StochasticMatrix(int dim){
    super(dim);
  }

  public StochasticMatrix(double[][] vals){
    super(vals);
    stochasticize();
  }

  public StochasticMatrix(ArrayList<Vector> cols){
    super(cols);
    stochasticize();
  }

  public StochasticMatrix(Network network){
    super(network);
    stochasticize();
  }

  private void stochasticize(){
    if (hasNegativeEntries())
      throw new IllegalArgumentException("matrix has negative entries");
    double colSum = 0;
    for (int c = 0; c < n(); c++){
      for (int r = 0; r < m(); r++){
        colSum += get(r,c);
      }
      if (colSum == 0){
        throw new IllegalStateException("matrix has a zero column");
      }
      if (colSum != 1){
        for (int r = 0; r < m(); r++){
          vals[r][c] /= colSum;
        }
      }
      colSum = 0;
    }
  }

  private static boolean allNonNegative(double[][] vals){
    boolean result = true;
    for (int i = 0; i < vals.length; i++){
      for (int j = 0; j < vals[0].length; j++){
        if (vals[i][j] < 0){
          return false;
        }
      }
    }
    return result;
  }

  public StochasticMatrix mult(StochasticMatrix A){
    StochasticMatrix product = new StochasticMatrix(m());
    for (int r = 0; r < m(); r++){
      for (int c = 0; c < m(); c++){
        product.vals[r][c] = Vector.dot(row(r), A.col(c));
      }
    }
    return product;
  }

}
