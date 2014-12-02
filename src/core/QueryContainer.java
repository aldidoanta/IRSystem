package core;

import indexing.InvFileTF_Query;

import java.util.ArrayList;
import java.util.HashMap;

public class QueryContainer {
	public ArrayList<String> queryList = new ArrayList<String>(); //list of queries
	public ArrayList<ArrayList<String>> wordList; //container for the word token in each query
	public ArrayList<Double> querylengthList = new ArrayList<Double>(); //list of query length
	
	/*DATA STRUCTURE FOR "INVERTED FILE"*/
	//key-value for TF entry (Raw TF and TF)
	public HashMap<String, ArrayList<InvFileTF_Query>> invFile;
	
	public QueryContainer(){
		queryList = new ArrayList<String>();
		wordList = new ArrayList<ArrayList<String>>();
		querylengthList = new ArrayList<Double>();
		
		invFile = new HashMap<String, ArrayList<InvFileTF_Query>>();
	}
}
