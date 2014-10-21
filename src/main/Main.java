package main;

import indexing.Indexer;

import java.io.IOException;

public class Main {
	
	public static void main (String[] args) throws IOException{
		Indexer.readFile(Indexer.PATH_QUERY_ADI,Indexer.QUERY);
		//Indexer.removeStopWord();
		//Indexer.listDocumentWord();
		//Indexer.calculateTF(Indexer.AUGMENTED_TF);
		//Indexer.printResult();
		Indexer.printQueryList();
	}
}
