package linalg;

public class AdjacencyNetwork<N extends Node> extends Network<N>{

  public AdjacencyNetwork(){
    super();
  }

  boolean isAdjacency(){
    return true;
  }

  boolean isEven(){
    return false;
  }

  boolean hasLoops(){
    return false;
  }

}
