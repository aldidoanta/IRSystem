package indexing;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Indexer {
	
	//constants for the path of the document files
	public final static String PATH_DOCUMENT_ADI = "res/document/ADI/ADI.ALL";
	public final static String PATH_QUERY_ADI = "res/document/ADI/ADI.QRY";
	public final static String PATH_DOCUMENT_CISI = "res/document/CISI/CISI.ALL";
	public final static String PATH_QUERY_CISI = "res/document/CACM/CISI.QRY";
	public final static String PATH_DOCUMENT_CACM = "res/document/CACM/cacm.all";
	public final static String PATH_QUERY_CACM = "res/document/CACM/query.text";
	//constant representation for document and query
	public final static int DOCUMENT = 1;
	public final static int QUERY = 2;
	 
	//containers for the parsing process
	//DOCUMENT CONTAINERS
	static ArrayList<String> titleList = new ArrayList<String>(); //list of doc title
	static ArrayList<String> authorList = new ArrayList<String>(); //list of doc author
	static ArrayList<String> contentList = new ArrayList<String>(); //list of doc content
	static ArrayList<String> stopwordList = new ArrayList<String>(); //list of stop words
	static ArrayList<ArrayList<String>> wordList = new ArrayList<ArrayList<String>>(); //container for the word token in each doc/query
	//QUERY CONTAINERS
	static ArrayList<String> queryList = new ArrayList<String>(); //list of queries
	
	//variables used in TF calculations
	static int TF_max = 0; //maximum raw TF, used in Augmented TF calculation
	
	//variables used in indexing method
	//TODO integrate with GUI
	public final static int DOCUMENT_BINARY_TF = 0;
	public final static int DOCUMENT_RAW_TF = 1;
	public final static int DOCUEMNT_LOG_TF = 2;
	public final static int DOCUMENT_AUGMENTED_TF = 3;
	static boolean useDocumentIDF = false;
	static boolean useDocumentNorm = false; //use normalization?
	
	/*DATA STRUCTURE FOR INVERTED FILE*/
	//key-value for TF entry (Raw TF and TF)
	static HashMap<String, ArrayList<InvFileTF>> TFList = new HashMap<String, ArrayList<InvFileTF>>();
	//list of IDF (per word)
	static ArrayList<Double> IDFList = new ArrayList<Double>();
	//list of "document length" (used in normalization)
	static ArrayList<Double> NormList = new ArrayList<Double>();
	
	//parses the document file, extracting the required info from each document
	public static void readFile(String path, int doc_or_query) throws IOException{
		
		//used for parsing process
		int idx = 0;
		char status = ' ';
		boolean firstline = true;
		
		//parsing
		BufferedReader br = new BufferedReader(new FileReader(path));
		for(String line = br.readLine(); line != null; line = br.readLine()){
			if((line.length() > 1) && (line.charAt(0) == '.')){
				switch (doc_or_query){
					case Indexer.DOCUMENT:
						switch (line.charAt(1)) {
							case 'I':
								idx = Integer.parseInt(line.substring(3)); //assign current doc number
								titleList.add("");
								authorList.add("");
								contentList.add("");
								break;
							case 'T':
								status = 'T';
								firstline = true;
								break;
							case 'A':
								status = 'A';
								firstline = true;
								break;
							case 'W':
								status = 'W';
								firstline = true;
								break;
							default:
								status = ' ';
								break;
							}
						break;
					case Indexer.QUERY:
						switch (line.charAt(1)) {
							case 'I':
								idx = Integer.parseInt(line.substring(3)); //assign current query number
								queryList.add("");
								break;
							case 'W':
								status = 'W';
								firstline = true;
								break;
							default:
								status = ' ';
								break;
						}
						break;
				}
			}
			else{
				String curr_line = line.replaceAll("[\\[\\](){},.;\"!?<>%]", ""); //trim punctuation
				curr_line = curr_line.toLowerCase(); //convert all characters to lower case
				
				switch (doc_or_query) {
					case Indexer.DOCUMENT:
						switch (status) {
							case 'T':
								if(firstline){
									titleList.set(idx-1,curr_line); //add a new title elmt
									firstline = false;
								}
								else{
									titleList.set(idx-1, titleList.get(idx-1)+" "+curr_line); //append to existing elmt
								}
								break;
							case 'A':
								if(firstline){
									authorList.set(idx-1,curr_line); //add a new author elmt
									firstline = false;
								}
								else{
									authorList.set(idx-1, authorList.get(idx-1)+" "+curr_line); //append to existing elmt
								}
								break;
							case 'W':
								if(firstline){
									contentList.set(idx-1,curr_line); //add a new content elmt
									firstline = false;
								}
								else{
									contentList.set(idx-1, contentList.get(idx-1)+" "+curr_line); //append to existing elmt
								}
								break;
		
							default:
								break;
						}
						break;
					case Indexer.QUERY:
						switch (status) {
							case 'W':
								if(firstline){
									queryList.set(idx-1,curr_line); //add a new content elmt
									firstline = false;
								}
								else{
									queryList.set(idx-1, queryList.get(idx-1)+" "+curr_line); //append to existing elmt
								}
								break;
		
							default:
								break;
						}
						break;

					default:
						break;
				}
			}
		}
		br.close();
	}
	
	//removes stop words in contentList based on a stop word list
	public static void removeStopWord() throws IOException{
		//open the stop word file
		BufferedReader br = new BufferedReader(new FileReader("res/stopword/1.txt"));
		for(String line = br.readLine(); line != null; line = br.readLine()){
			if(line.length() > 2){
				if((line.charAt(0) != '/') && (line.charAt(1) != '/')){ //skip comments
					stopwordList.add(line.trim());
				}
			}
			else{
				stopwordList.add(line.trim());
			}
		}
		br.close();
		
		//remove stop words from contentList
		for(int i = 0; i < contentList.size(); i++){
			String newcontent = contentList.get(i);	
			for(String str_stopword : stopwordList){
				newcontent = newcontent.replaceAll("\\b"+str_stopword+"\\b(?!-)", ""); //hyphen-separated words will be counted as one word
			}
			contentList.set(i, newcontent);
		}
		
		//delete the contents of stopwordList, freeing up resource
		stopwordList.clear();
	}
	
	//lists all the words in the contentList, then finds the matching documents
	//this method also calculates raw TF for each word in each matching document
	public static void listDocumentWord(){
		
		wordList.clear();
		//tokenize the words in each document
		for(String content : contentList){
			String[] words = content.trim().split(" +");

			ArrayList<String> temp = new ArrayList<String>();
			for(String word : words){
				temp.add(word);
			}			
			//insert tokens into wordList
			wordList.add(temp);
		}
		
		//delete the contents of contentList, freeing up resource
		contentList.clear();
		
		//deep copy the contents of wordList for indexing purpose
		ArrayList<ArrayList<String>> temp_wordList = new ArrayList<ArrayList<String>>(wordList);
		
		//enumerate words and its occurrence in the entire document file 
		for (int i = 0; i < temp_wordList.size(); i++) { 
			while(!(temp_wordList.get(i).isEmpty())){
				//take the first word
				String word = temp_wordList.get(i).get(0);
				if(TFList.containsKey(word)){
					temp_wordList.get(i).remove(0);
				}
				else{
					ArrayList<InvFileTF> InvFileTFList = new ArrayList<InvFileTF>();
					//create InvFileTF
					for (int idx_doc = i; idx_doc < temp_wordList.size(); idx_doc++) {
						ArrayList<String> examined_wordList_doc = temp_wordList.get(idx_doc);
						int occurrence = Collections.frequency(examined_wordList_doc, word); 
						if(occurrence > 0){ //if found in wordList
							InvFileTF itemTF = new InvFileTF();
							//set the attrib value of itemTF
							itemTF.docnum = idx_doc+1;
							itemTF.TF_raw = occurrence;
							if (itemTF.TF_raw > TF_max){
								TF_max = itemTF.TF_raw; //set the new value for TF_max
							}
							InvFileTFList.add(itemTF); //add to InvFileTFList
							temp_wordList.get(idx_doc).removeAll(Collections.singleton(word)); //remove all occurrences of word in wordList_doc
						}
					}
					TFList.put(word, InvFileTFList); //put to TFList HashTable
				}
			}
		}
	}
	
	//lists all the words in the queryList
	//this method also calculates raw TF for each word in each query
	public static void listQueryWord(){
		
		wordList.clear();
		//tokenize the words in each document
		for(String content : queryList){
			String[] words = content.trim().split(" +");

			ArrayList<String> temp = new ArrayList<String>();
			for(String word : words){
				temp.add(word);
			}			
			//insert tokens into wordList
			wordList.add(temp);
		}
		
		//delete the contents of contentList, freeing up resource
		queryList.clear();
		
		//deep copy the contents of wordList for indexing purpose
		ArrayList<ArrayList<String>> temp_wordList = new ArrayList<ArrayList<String>>(wordList);
		
		//TODO update so it matches with the query tokenizing requierement
		//enumerate words and its occurrence in the entire document file 
		for (int i = 0; i < temp_wordList.size(); i++) { 
			while(!(temp_wordList.get(i).isEmpty())){
				//take the first word
				String word = temp_wordList.get(i).get(0);
				if(TFList.containsKey(word)){
					temp_wordList.get(i).remove(0);
				}
				else{
					ArrayList<InvFileTF> InvFileTFList = new ArrayList<InvFileTF>();
					//create InvFileTF
					for (int idx_doc = i; idx_doc < temp_wordList.size(); idx_doc++) {
						ArrayList<String> examined_wordList_doc = temp_wordList.get(idx_doc);
						int occurrence = Collections.frequency(examined_wordList_doc, word); 
						if(occurrence > 0){ //if found in wordList
							InvFileTF itemTF = new InvFileTF();
							//set the attrib value of itemTF
							itemTF.docnum = idx_doc+1;
							itemTF.TF_raw = occurrence;
							if (itemTF.TF_raw > TF_max){
								TF_max = itemTF.TF_raw; //set the new value for TF_max
							}
							InvFileTFList.add(itemTF); //add to InvFileTFList
							temp_wordList.get(idx_doc).removeAll(Collections.singleton(word)); //remove all occurrences of word in wordList_doc
						}
					}
					TFList.put(word, InvFileTFList); //put to TFList HashTable
				}
			}
		}
	}

	
	//calculates TF based on user's choice (Binary,Raw,Logarithmic,Augmented)
	public static void calculateTF(int TFType){
		Set<String> words = TFList.keySet();
		ArrayList<String> words_list = new ArrayList<String>(words);
		Collections.sort(words_list);
		for(String word : words_list){
			ArrayList<InvFileTF> word_result = TFList.get(word);
			for(InvFileTF elmt : word_result){
				//calculate TF
				switch (TFType) {
					case DOCUMENT_BINARY_TF:
						elmt.TF = 1; //its value will be always 1, because the word exists in the doc
						break;
					case DOCUMENT_RAW_TF:
						elmt.TF = elmt.TF_raw;
						break;
					case DOCUEMNT_LOG_TF:
						elmt.TF = 1 + Math.log10(elmt.TF_raw); //using base 10 logarithm
						break;
					case DOCUMENT_AUGMENTED_TF:
						elmt.TF = 0.5 + (0.5 * elmt.TF_raw / TF_max);
						break;
					default:
						break;
				}
			}
		}
	}
	
	//calculate IDF for each word
	public static void calculateIDF(){
		Set<String> words = TFList.keySet();
		ArrayList<String> words_list = new ArrayList<String>(words);
		Collections.sort(words_list);
		for(String word : words_list){
			ArrayList<InvFileTF> occurrence = TFList.get(word);
			if(occurrence != null){
				IDFList.add(Math.log10(titleList.size() / occurrence.size())); //add IDF value to IDFList
			}
			else{
				IDFList.add(Math.log10(titleList.size() / 1));
			}
		}
	}
		
	public static void printResult(){
		System.out.println("Indexing result:");
		PrintWriter writer;
		try {
			writer = new PrintWriter("out.txt");
			/*BEGIN OF TEST*/
			Set<String> words = TFList.keySet();
			ArrayList<String> words_list = new ArrayList<String>(words);
			Collections.sort(words_list);
			for(String word : words_list){
				writer.println(word);
				ArrayList<InvFileTF> word_result = TFList.get(word);
				for(InvFileTF elmt : word_result){
					writer.println(elmt.docnum+"\t"+elmt.TF_raw+"\t"+elmt.TF);
				}
			}
			writer.println("maxTF = "+TF_max);
			/*END OF TEST*/
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void printQueryList(){
		System.out.println("query list\n\n");
		for (String query : queryList) {
			System.out.println(query);
		}
		System.out.println("\n\nsize= "+queryList.size());
	}
}
