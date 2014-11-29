package core;

import indexing.InvFileTF;

import java.util.ArrayList;
import java.util.HashMap;

public class DocumentContainer {
	public ArrayList<String> titleList; //list of doc title
	public ArrayList<String> authorList; //list of doc author
	public ArrayList<String> contentList; //list of doc content
	public ArrayList<Double> doclengthList; //list of document length
	public ArrayList<ArrayList<String>> wordList; //container for the word token in each doc/query
	public ArrayList<Double> IDFList; //list of IDF (for each word)
	
	/*DATA STRUCTURE FOR INVERTED FILE*/
	//key-value for TF entry (Raw TF and TF)
	public HashMap<String, ArrayList<InvFileTF>> invFile;
	
	public DocumentContainer(){
		titleList = new ArrayList<String>();
		authorList = new ArrayList<String>();
		contentList = new ArrayList<String>();
		doclengthList = new ArrayList<Double>();
		wordList = new ArrayList<ArrayList<String>>();
		IDFList = new ArrayList<Double>();
		
		invFile = new HashMap<String, ArrayList<InvFileTF>>();
	}
}
