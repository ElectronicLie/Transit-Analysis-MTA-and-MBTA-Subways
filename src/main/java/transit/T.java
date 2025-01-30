package transit;

import linalg.*;
import java.util.Arrays;
import java.util.ArrayList;

public class T {

  // does not include busses, commuter rail, Amtrak

  public static final String[] LINES = new String[] {"BL","OL","RL","GL","SL"};

  public static void addIntxns(TransitSystem t){

        String[] blue, orange, red, green, sl3, sl4, sl5;

        blue = new String[] {
          "Airport","State","Gov't Center"
        };
        t.addLine(blue, "BL");

        orange = new String[] {
          "North Station","Haymarket","State","Downtown Crossing","Chinatown","Tufts Medical Ctr"
        };
        t.addLine(orange, "OL");

        red = new String[] {
          "Park St","Downtown Crossing","South Station","JFK/UMASS"
        };
        t.addLine(red, "RL");

        green = new String[] {
          "Lechmere","North Station","Haymarket","Gov't Center","Park St","Boylston","Copley", "Kenmore"
        };
        t.addLine(green, "GL");

        sl3 = new String[] {
          "Airport","South Station"
        };
        sl4 = new String[] {
          "South Station","Chinatown","Tufts Medical Ctr"
        };
        sl5 = new String[] {
          "Chinatown", "Downtown Crossing", "Boylston", "Tufts Medical Ctr"
        };
        t.addLine(sl3, "SL");
        t.addLine(sl4, "SL");
        t.addLine(sl5, "SL");

  }

  public static void addIntxnsAndTerminals(TransitSystem t){

    String[] blue, orange, red, rlm, glb, glc, gld1, gld2, gle, sl3, sl4, sl5;

    blue = new String[] {
      "Wonderland","Airport","State","Gov't Center","Bowdoin"
    };
    t.addLine(blue, "BL");

    orange = new String[] {
      "Oak Grove","North Station","Haymarket","State","Downtown Crossing","Chinatown","Tufts Medical Ctr",
      "Forest Hills"
    };
    t.addLine(orange, "OL");

    red = new String[] {
      "Alewife","Park St","Downtown Crossing","South Station","JFK/UMASS","Braintree"
    };
    rlm = new String[] {
      "JFK/UMASS","Mattapan"
    };
    t.addLine(red, "RL");
    t.addLine(rlm, "RL");

    gle = new String[] {
      "Medford/Tufts","Lechmere","North Station","Haymarket","Gov't Center","Park St","Boylston","Copley",
      "Heath St"
    };
    gld1 = new String[] {
      "Union Sq","Lechmere"
    };
    gld2 = new String[] {
      "Copley","Kenmore","Riverside"
    };
    glc = new String[] {
      "Kenmore","Cleveland Circle"
    };
    glb = new String[] {
      "Kenmore","Boston College"
    };
    t.addLine(gle, "GL");
    t.addLine(gld1, "GL");
    t.addLine(gld2, "GL");
    t.addLine(glc, "GL");
    t.addLine(glb, "GL");

    sl3 = new String[] {
      "Chelsea","Airport","South Station"
    };
    sl4 = new String[] {
      "South Station","Chinatown","Tufts Medical Ctr","Nubian"
    };
    sl5 = new String[] {
      "Chinatown", "Downtown Crossing", "Boylston", "Tufts Medical Ctr"
    };
    t.addLine(sl3, "SL");
    t.addLine(sl4, "SL");
    t.addLine(sl5, "SL");

  }

  public static void addAllStops(TransitSystem t){

    String[] blue, orange, red, rlm, glb, glc, gld1, gld2, gle, sl3, sl4, sl5;

    blue = new String[] {
      "Wonderland","Revere Beach","Beachmont","Suffolk Downs","Orient Heights","Wood Island","Airport",
      "Maverick","Aquarium","State","Gov't Center","Bowdoin"
    };
    t.addLine(blue, "BL");

    orange = new String[] {
      "Oak Grove","Malden Ctr","Wellington","Assembly","Sullivan Sq","Community College","North Station",
      "Haymarket","State","Downtown Crossing","Chinatown","Tufts Medical Ctr","Back Bay","Mass Ave",
      "Ruggles","Roxbury","Jackson Sq","Stony Brook","Green St","Forest Hills"
    };
    t.addLine(orange, "OL");

    red = new String[] {
      "Alewife","Davis","Porter","Harvard","Central","Kendall/MIT","Charles/MGH","Park St",
      "Downtown Crossing","South Station","Broadway","Andrew","JFK/UMASS","N Quincy","Wollaston",
      "Quincy Ctr","Braintree"
    };
    rlm = new String[] {
      "JFK/UMASS","Savin Hill","Fields Corner","Shawmut","Ashmont","Cedar Grove","Butler","Milton",
      "Central Ave","Valley Rd","Capen St","Mattapan"
    };
    t.addLine(red, "RL");
    t.addLine(rlm, "RL");

    gle = new String[] {
      "Medford/Tufts","Ball Sq","Magoun Sq","Gilman Sq","E Somerville","Lechmere","Science Park/W End",
      "North Station","Haymarket","Gov't Center","Park St","Boylston","Arlington","Copley","Prudential",
      "Symphony","Northeastern","Museum of Fine Arts","Longwood Medical Area","Brigham Cir","Fenwood Rd",
      "Mission Pk","Riverway","Back of the Hill","Heath St"
    };
    gld1 = new String[] {
      "Union Sq","Lechmere"
    };
    gld2 = new String[] {
      "Copley","Hynes Convention Ctr","Kenmore","Fenway","Longwood","Brookline Village","Brookline Hills",
      "Beaconsfield","Reservoir","Chestnut Hill","Newton Ctr","Newton Highlands","Eliot","Waban","Woodland",
      "Riverside"
    };
    glc = new String[] {
      "Kenmore","St. Mary's St","Hawes St","Kent St","St. Paul St","Coolidge Corner","Summit Ave",
      "Brandon Hall","Fairbanks St","Washington Sq","Tappan St","Dean Rd","Englewood Ave","Cleveland Circle"
    };
    glb = new String[] {
      "Kenmore","Blandfort St","BU E","BU Central","Amory St","Babcock St","Packards Corner","Harvard Ave",
      "Griggs St","Allston St","Warren St","Washington St","Sutherland Rd","Chiswick Rd","Chestnut Hill Ave",
      "South St","Boston College"
    };
    t.addLine(gle, "GL");
    t.addLine(gld1, "GL");
    t.addLine(gld2, "GL");
    t.addLine(glc, "GL");
    t.addLine(glb, "GL");

    sl3 = new String[] {
      "Chelsea","Bellingham Sq","Box District","Eastern Ave","Airport","Silver Line Way","World Trade Ctr",
      "Courthouse","South Station"
    };
    sl4 = new String[] {
      "South Station","Chinatown","Tufts Medical Ctr","Herald St","E Berkeley St","Union Park St",
      "Newton St","Worcester Sq","Mass Ave SL","Lenox St","Melnea Cass Blvd","Nubian"
    };
    sl5 = new String[] {
      "Chinatown", "Downtown Crossing", "Boylston", "Tufts Medical Ctr"
    };
    t.addLine(sl3, "SL");
    t.addLine(sl4, "SL");
    t.addLine(sl5, "SL");

  }

}
