package inferenceEngine;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class KnowledgeBase {
  private ArrayList<HornClause> clauses;
  private ArrayList<String> facts;
  
  public KnowledgeBase() {
    clauses = new ArrayList<HornClause>();
    facts = new ArrayList<String>();
  }
  
  public KnowledgeBase(BufferedReader reader) {
    clauses = new ArrayList<HornClause>();
    facts = new ArrayList<String>();
    
    readInput(reader);
  }
  
  public ArrayList<HornClause> getClauses() {
	    return clauses;
  }
	  
  public ArrayList<String> getFacts() {
    return facts;
  }
  
  public void readInput(BufferedReader reader) {
    try {
      reader.readLine();
     
      String tell = reader.readLine();
      
      tell = tell.replaceAll("\\s", "");
      
      String[] sentence = tell.split(";");
      
      for (int i = 0; i < sentence.length; i++) {
        if (sentence[i].contains("=>")) {
          clauses.add(new HornClause(sentence[i]));
        } else {
          facts.add(sentence[i]);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(2);
    }
  }
}
