package core;

import indexing.Indexer;

import java.io.IOException;

public class Main {
	
	public static void main (String[] args) throws IOException{
		Indexer.readFile(Indexer.PATH_DOCUMENT_ADI,Indexer.DOCUMENT);
		Indexer.removeDocStopWord();
		Indexer.listDocumentWord();
		Indexer.calculateTF(Indexer.DOCUMENT_LOG_TF);
		Indexer.calculateDocLength();
		Indexer.applyNormalization();
		Indexer.printResult();
//		Indexer.printQueryList();
	}
}
