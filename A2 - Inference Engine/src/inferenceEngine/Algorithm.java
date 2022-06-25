package inferenceEngine;

public abstract class Algorithm {
  private String algorithmCode;
  private String algorithmName;
  private KnowledgeBase kb;
  private String query;
  
  protected KnowledgeBase getKnowledgeBase()
  {
    return kb;
  }
  
  public void setKnowledgeBase(KnowledgeBase KnowledgeBase)
  {
    kb = KnowledgeBase;
  }
  
  public Algorithm(KnowledgeBase KnowledgeBase, String Query) {
    kb = KnowledgeBase;
    query = Query;
  }
  
  protected String getQuery()
  {
    return query;
  }
  
  public void setQuery(String Query)
  {
    query = Query;
  }
  
  public String getAlgorithmName()
  {
    return algorithmName;
  }

  public String getCode()
  {
    return algorithmCode;
  }
  
  protected void setAlgorithmName(String AlgorithmName)
  {
    algorithmName = AlgorithmName;
  }
  
  protected void setAlgorithmCode(String AlgorithmCode)
  {
    algorithmCode = AlgorithmCode;
  }
  
  abstract public String result();
  abstract public boolean resultPassed();
}