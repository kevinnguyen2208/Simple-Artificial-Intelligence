package inferenceEngine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {
	
	public static void main(String[] args) {
		try {
			BufferedReader inputFile = new BufferedReader(new FileReader(args[1]));

			KnowledgeBase kb = new KnowledgeBase(inputFile);

			inputFile.readLine();
			
			String ask = inputFile.readLine();

			Algorithm[] algorithms = { 
				new TruthTable(kb, ask),
				new ForwardChaining(kb, ask), 
				new BackwardChaining(kb, ask) 
			};
				
			int idx = 0;
			boolean getAlgorithm = true;

			do {
				if (algorithms[idx].getCode().equals(args[0])) { 
					getAlgorithm = false;

					System.out.println(algorithms[idx].getAlgorithmName());
					System.out.println(algorithms[idx].result());
				}

				idx++;
			} while (idx < algorithms.length && getAlgorithm);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(2);
		}
	}

}
