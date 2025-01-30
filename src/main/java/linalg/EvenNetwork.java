package linalg;

public class EvenNetwork<N extends Node> extends Network<N>{

  private boolean includesSelf;

  public EvenNetwork(boolean is){
    super();
    includesSelf = is;
  }

  boolean isEven(){
    return true;
  }

  boolean isAdjacency(){
    return false;
  }

  boolean hasLoops(){
    return this.includesSelf;
  }

}
