package malo;

public abstract class Mathematical{

  public Mathematical(){}

  public static final int DEFAULT_ROUND = -3;
  public static final int DEFAULT_MARGIN = -6;
  public static final double RANDOM_LOWER = -10;
  public static final double RANDOM_UPPER = 10;
  public static final int DEFAULT_PRO_ROUND = -4;

  public abstract <T extends Mathematical> T add(T other);

  // public abstract Mathematical subtract(Mathematical other);
  //
  // public abstract Mathematical mult(Mathematical other);
  //
  // public abstract Mathematical mult(int i);
  //
  // public abstract Mathematical divide(Mathematical other);
  //
  // public abstract Mathematical divide(int i);
  //
  // public abstract Mathematical pow(int p);

  // public static Mathematical average(Mathematical[] ary);
  // // {
  // //   Mathematical mean = new Mathematical();
  // //   for (int i = 0; i < ary.length; i++){
  // //     mean = mean.add(ary[i]);
  // //   }
  // //   mean = mean.divide(ary.length);
  // //   return mean;
  // // }

  // public boolean equalsTo(T other);

  // public String toString();

  // public abstract Mathematical round(int p);
  //
  // public abstract boolean isZero();
  //
  // public abstract boolean equals(Mathematical other);
  // public abstract boolean lessThan(Mathematical other);
  // public abstract boolean moreThan(Mathematical other);
  //
  // public boolean moreThanOrEqualTo(Mathematical other){
  //   return equals(other) || moreThan(other);
  // }
  // public boolean lessThanOrEqualTo(Mathematical other){
  //   return equals(other) || lessThan(other);
  // }

  public abstract Mathematical copy();

  public abstract Mathematical random();

}
