package inferenceEngine;

import java.util.LinkedList;

public class HornClause {
  private LinkedList<String> literals;
  private String entailedLiteral;
  
  public HornClause(String sentence) {
    literals = new LinkedList<String>();
    
    String[] entailment = sentence.split("=>");
    
    String[] conjunction = entailment[0].split("&");
    
    for (int i = 0; i < conjunction.length; i++) {
      literals.addLast(conjunction[i]);
    }
    
    entailedLiteral = entailment[1];
  }
  
  public LinkedList<String> getLiterals() {
    return literals;
  }
  
  public String getLiteralsAtIdx(int idx) {
    try {
      if (idx >= 0 && idx < literals.size()) {
        return literals.get(idx);
      } else {
        throw new IndexOutOfBoundsException();
      }
    } catch (IndexOutOfBoundsException e) {
      e.printStackTrace();
      System.exit(3);
      
      return null;
    }
  }
  
  public int literalCount() {
    return literals.size();
  }
  
  public void removeLiteral(String literal) {
    literals.remove(literal);
  }
  
  public String getEntailedLiteral() {
    return entailedLiteral;
  }
}
