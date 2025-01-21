package malo;

public interface Mathematic<T>{

  static final int DEFAULT_ROUND = -3;
  static final int DEFAULT_MARGIN = -6;
  static final int DEFAULT_ZERO_MARGIN = -7;
  static final long RANDOM_LOWER = -10;
  static final long RANDOM_UPPER = 10;
  static final int DEFAULT_PRO_ROUND = -4;

  public T add(T other);

  public T subtract(T other);

  public T mult(T other);

  // public T divide(T other);

  // public boolean equalsTo(T other);

  // public String toString();

  public T round(int p);

  public boolean isZero();

  public T copy();

  public T pow(int p);

  public T random(double min, double max, int p);

  public T random(double min, double max);

  public T random(int p);

  public T random();

}
