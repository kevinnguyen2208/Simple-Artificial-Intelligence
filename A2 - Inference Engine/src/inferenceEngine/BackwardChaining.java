package inferenceEngine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class BackwardChaining extends Algorithm {
	
	private ArrayList<HornClause> clauses;
	private ArrayList<String> facts;
	private String query;
	private ArrayList<String> outputFacts;
	private ArrayList<String> queries;
	
	public BackwardChaining(KnowledgeBase KnowledgeBase, String Query) {
   			super(KnowledgeBase, Query);

			setAlgorithmCode("BC");
	    	setAlgorithmName("Backward Chaining");
    
	    	clauses = KnowledgeBase.getClauses();
			facts = KnowledgeBase.getFacts();
			query = Query;
			queries = new ArrayList<String>();
			outputFacts = new ArrayList<String>();
	}
	
	public boolean compareClauses(String Query)
	{
		boolean result = false;
		Set<String> hashset = new HashSet<>();

		for (int i = 0; i < clauses.size(); i++)
		{
			if (Query.equals(clauses.get(i).getEntailedLiteral()))
			{
				result = true;

				for (int j = 0; j < clauses.get(i).literalCount(); j++)
				{
					queries.add(clauses.get(i).getLiteralsAtIdx(j));
				}
			}
		}
		
		for (int i = 0; i < outputFacts.size(); i++)
		{
			for (int j = 0; j < queries.size(); j++)
			{
				if (outputFacts.get(i).equals(queries.get(j)))
				{
					queries.remove(j);
				}
			}
		}

		hashset.addAll(queries);
		queries.clear();
		queries.addAll(hashset);
		
		return result;
	}

	public boolean compareFacts(String Query)
	{
		for (int i = 0; i < facts.size(); i++)
		{
			if (Query.equals(facts.get(i)))
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean resultPassed(){
		
		queries.add(query);
		
		while (queries.size() > 0)
		{
			String currentQuery = queries.remove(0);

			outputFacts.add(currentQuery);

			if(! compareFacts(currentQuery))
			{
				if(! compareClauses(currentQuery))
				{
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public String result()
	{
		String output = null;

		if (resultPassed())
		{
			output = "YES: ";
			
			for (int i = outputFacts.size() - 1; i >= 0; i--)
			{
				output += (outputFacts.get(i));
				
				if (i != 0)
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
