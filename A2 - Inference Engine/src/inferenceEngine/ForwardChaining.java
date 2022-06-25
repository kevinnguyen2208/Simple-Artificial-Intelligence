package inferenceEngine;

import java.util.ArrayList;

public class ForwardChaining extends Algorithm {
	
	private ArrayList<HornClause> clauses;
	private ArrayList<String> facts;
	private String query;
	private ArrayList<String> outputFacts;
	
	public ForwardChaining(KnowledgeBase KnowledgeBase, String Query) {
    	super(KnowledgeBase, Query);
    
		setAlgorithmCode("FC");
	    setAlgorithmName("Forward Chaining");

	    clauses = KnowledgeBase.getClauses();
		facts = KnowledgeBase.getFacts();
		query = Query;
		outputFacts = new ArrayList<String>();
	}
	
	@Override
	public boolean resultPassed(){
		
		while (!facts.isEmpty()){
			
			String aFact = facts.remove(0);
			
			outputFacts.add(aFact);
			
			if (aFact.equals(query))
			{
				return true;
			}

			for (int i = 0; i < clauses.size(); i++)
			{
				for (int j = 0; j < clauses.get(i).literalCount(); j++)
				{
					if (aFact.equals(clauses.get(i).getLiteralsAtIdx(j)))
					{
						clauses.get(i).removeLiteral(aFact);
					}
				}
			}
			
			for (int i = 0; i < clauses.size(); i++)
			{
				if (clauses.get(i).literalCount() == 0)
				{
					facts.add(clauses.get(i).getEntailedLiteral());
					
					clauses.remove(i);
				}
			}
		}
		return false;
	}

	@Override
	public String result()
	{
		String output = null;
		
		if (resultPassed())
		{
			output = "YES: ";
			
			for (int i = 0; i < outputFacts.size(); i++)
			{
				output += (outputFacts.get(i));
				
				if (i < outputFacts.size() - 1)
				{
					output += ", ";
				}
			}
		}
		else
		{
			output = "NO";
		}
		return output;				
	}
}
