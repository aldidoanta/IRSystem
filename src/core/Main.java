package core;

import indexing.Indexer;

import java.io.IOException;

public class Main {
	
	public static void main (String[] args) throws IOException{
		
		DocumentContainer dc = new DocumentContainer();
		
		boolean document_flagStopWord = true;
		boolean document_flagTF  = true;
		boolean document_flagIDF = true;
		boolean document_flagNormalization = true;
		
		int TF_choice = Indexer.DOCUMENT_LOG_TF;
		
		Indexer.readFile(Indexer.PATH_DOCUMENT_ADI,Indexer.DOCUMENT,dc);
		if(document_flagStopWord == true){
			Indexer.removeDocStopWord(dc);
		}
		Indexer.listDocumentWord(dc);
		if(document_flagTF == true){
			Indexer.calculateTF(TF_choice,dc);
		}
		else{
			Indexer.calculateTF(Indexer.DOCUMENT_RAW_TF, dc); //default
		}
		if(document_flagIDF == true){
			Indexer.calculateIDF(dc);
			Indexer.applyTFIDF(dc);
		}
		if(document_flagNormalization == true){
			Indexer.calculateDocLength(dc);
			Indexer.applyNormalization(dc);
		}
		Indexer.printResult(dc);
//		Indexer.printQueryList();
		
	}
}
