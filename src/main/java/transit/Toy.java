package transit;

public class Toy {

  public static final String[] LINES = {"B","R","Y"};

  public static void addAllStops(TransitSystem toy){

    String[] blue, red, yellow;

    blue = new String[] {"1","2","3","4"};
    toy.addLine(blue, "B");

    red = new String[] {"7","3","8"};
    toy.addLine(red, "R");

    yellow = new String[] {"5","2","6"};
    toy.addLine(yellow, "Y");

  }

  // public static final String[] LINES = {"B","R"};
  //
  // public static void addAllStops(TransitSystem toy){
  //
  //   String[] blue, red;
  //
  //   blue = new String[] {"1","2","3"};
  //   toy.addLine(blue, "B");
  //
  //   red = new String[] {"4","2","5"};
  //   toy.addLine(red, "R");
  //
  // }

}
