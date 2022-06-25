package inferenceEngine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class TruthTable extends Algorithm {

	private ArrayList < HornClause > clauses;
	private ArrayList < String > facts;
	private String query;
	private ArrayList < String > items;
	private int columns;
	private int rows;
	private boolean[][] grid;
	private boolean[] formulaResult;
	private int[][] literalIdx;
	private int[] factIdx;
	private int[] entailed;
	private boolean[] queryResult;
	private int queryIdx;
	private int count;
	
	public TruthTable(KnowledgeBase KnowledgeBase, String Query) {
    	super(KnowledgeBase, Query);

		setAlgorithmCode("TT");
		setAlgorithmName("Truth Table");
    
	    clauses = KnowledgeBase.getClauses();
		facts = KnowledgeBase.getFacts();
		query = Query;
		items = new ArrayList< String > ();

		createGrid();
	}
	
	@Override
	public String result()
	{
		String output = null;
		
		if (resultPassed())
		{
			output = "YES: " + count;
		}
		
		else
		{
			output = "NO";
		}
		
		return output;			
	}

	@Override
	public boolean resultPassed()
	{
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < factIdx.length; j++)
			{
				if (formulaResult[i])
				{
					if (! grid[i][queryIdx])
					{
						formulaResult[i] = false;
						
						queryResult[i] = false;
						
						break;
					}
					else
					{
						queryResult[i] = true;
					}
					
					formulaResult[i] = grid[i][ factIdx [j] ];
				}
				else
				{
					break;
				}
			}
		}

		for (int i = 0; i < rows; i++)
		{
			if (formulaResult[i])
			{
				for (int j = 0; j < literalIdx.length; j++)
				{
					if(clauses.get(j).literalCount() == 2)
					{
						if ((grid[i][ literalIdx[j][0] ] == true) 
						&&   (grid[i][ literalIdx[j][1] ] == true) 
					    &&   (grid[i][ entailed[j] ] == false))
						{
							formulaResult[i] = false;
						}
					}
					else
					{
						if ((grid[i][ literalIdx[j][0] ] == true) 
						&&   (grid[i][ entailed[j] ] == false))
						{
							formulaResult[i] = false;
						}
					}
				}
			}
		}
		
		for (int i = 0; i < rows; i++)
		{
			if (formulaResult[i])
			{
				count++;
			}
			
			if (queryResult[i] == false && formulaResult[i] == true)
			{
				return false;
			}
		}
		
		return true;
	}

	
	public void createGrid()
	{
		Set<String> hashset = new HashSet<>();

		for (int i = 0; i < clauses.size(); i++)
		{
			for (int j = 0; j < clauses.get(i).literalCount(); j++)
			{
				items.add(clauses.get(i).getLiteralsAtIdx(j));
			}
			items.add(clauses.get(i).getEntailedLiteral());
		}

		hashset.addAll(items);
		items.clear();
		items.addAll(hashset);

		columns = items.size();

		rows = ((int) Math.pow (2, (items.size())));

		grid = new boolean[rows][columns];

		formulaResult = new boolean[rows];
		
		for (int i = 0; i < rows; i++)
		{
			formulaResult[i] = true;
		}

		literalIdx = new int[clauses.size()][2];

		factIdx = new int[facts.size()];

		entailed = new int[clauses.size()];
		
		queryResult = new boolean[rows];
		
		queryIdx = 0;

		count = 0;
		
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < columns; j++)
			{
				int v = i & 1 << columns - 1 - j;

				grid[i][j] = (v == 0 ? true : false);
			}
		}

		getFactsColumnIdx();
		
		getLiteralsColumnIdx();
	}

	public void getFactsColumnIdx()
	{
		for (int i = 0; i < facts.size(); i++)
		{
			for (int j = 0; j < items.size(); j++)
			{
				if (facts.get(i).equals(items.get(j)))
				{
					factIdx[i] = j;
				}
				
				if (query.equals(items.get(j)))
				{
					queryIdx = j;
				}
				
			}
		}
	}

	public void getLiteralsColumnIdx()
	{
		for (int i = 0; i < items.size(); i++)
		{
			for (int j = 0; j < clauses.size(); j++)
			{
				for (int k = 0; k < clauses.get(j).literalCount(); k++)
				{
					if (clauses.get(j).getLiteralsAtIdx(k).equals(items.get(i)))
					{
						literalIdx[j][k] = i;
					}
				}
				
				if (clauses.get(j).getEntailedLiteral().equals(items.get(i)))
				{
					entailed[j] = i;
				}
			}
		}
	}
}