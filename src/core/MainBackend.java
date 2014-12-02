package core;

import indexing.Indexer;

import java.io.IOException;

public class MainBackend {
	
	public static boolean document_flagStopWord;
	public static boolean document_flagTF;
	public static boolean document_flagIDF;
	public static boolean document_flagNormalization;
	public static int document_TFType;
	
	public static void doMain () throws IOException{
		
		DocumentContainer dc = new DocumentContainer();
		
		Indexer.readFile(Indexer.PATH_DOCUMENT_ADI,Indexer.DOCUMENT,dc);
		if(document_flagStopWord == true){
			Indexer.removeDocStopWord(dc);
		}
		Indexer.listDocumentWord(dc);
		if(document_flagTF == true){
			Indexer.calculateTF(document_TFType,dc);
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
