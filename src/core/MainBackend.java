package core;

import indexing.Indexer;

import java.io.IOException;

import evaluation.Evaluator;
import retrieval.SimilarityCalculator;

public class MainBackend {
	
	public static boolean document_flagStopWord;
	public static boolean document_flagTF;
	public static boolean document_flagIDF;
	public static boolean document_flagNormalization;
	public static int document_TFType;
	
	public static boolean query_flagTF;
	public static boolean query_flagIDF;
	public static boolean query_flagNormalization;
	public static int query_TFType;
	
	public static DocumentContainer dc = new DocumentContainer();
	public static QueryContainer qc = new QueryContainer();
	public static SimilarityCalculator sc;
	public static Evaluator evaluator;
	
	
	//constants for the path of the document files
	public final static String PATH_DOCUMENT_ADI = "res/document/ADI/ADI.ALL";
	public final static String PATH_QUERY_ADI = "res/document/ADI/ADI.QRY";
	public final static String PATH_REL_ADI = "res/document/ADI/ADI.REL";
	public final static String PATH_DOCUMENT_CISI = "res/document/CISI/CISI.ALL";
	public final static String PATH_QUERY_CISI = "res/document/CACM/CISI.QRY";
	public final static String PATH_DOCUMENT_CACM = "res/document/CACM/cacm.all";
	public final static String PATH_QUERY_CACM = "res/document/CACM/query.text";
	
	public static void doIndexing () throws IOException{
		
		//Document Indexing Section
		Indexer.readFile(PATH_DOCUMENT_ADI,Indexer.DOCUMENT,dc,qc);
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
		Indexer.calculateIDF(dc); //always invoke this method 
		if(document_flagIDF == true){
			Indexer.applyTFIDF(dc);
		}
		Indexer.calculateDocLength(dc); //always invoke this method
		if(document_flagNormalization == true){
			Indexer.applyNormalization(dc);
		}
		
		//Query Indexing Section
		Indexer.readFile(PATH_QUERY_ADI,Indexer.QUERY,dc,qc);
		if(document_flagStopWord == true){
			Indexer.removeQueryStopWord(qc);
		}
		Indexer.listQueryWord(qc);;
		if(query_flagTF == true){
			Indexer.calculateTF_Query(query_TFType, qc);
		}
		else{
			Indexer.calculateTF_Query(Indexer.DOCUMENT_RAW_TF, qc); //default
		}
		//Indexer.calculateIDF(dc); //always invoke this method 
		if(query_flagIDF == true){
			Indexer.applyTFIDF_Query(dc, qc);
		}
		Indexer.calculateQueryLength(qc); //always invoke this method
		if(query_flagNormalization == true){
			Indexer.applyNormalization_Query(qc);
		}
		Indexer.printResult(dc);
//		Indexer.printResult_Query(dc, qc);
	}
	
	//retrieval (similarity calculation)
	public static void doRetrieval(){
		sc = new SimilarityCalculator();
		sc.calculateSimilarity(dc, qc);
//		sc.printSimilarity();
	}
	
	public static void doEvaluation(){
		evaluator = new Evaluator();
		evaluator.readRelevanceJudgement(PATH_REL_ADI);
		evaluator.calculateEvaluation(sc);
	}
}
