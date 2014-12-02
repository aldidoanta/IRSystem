package retrieval;

import indexing.InvFileTF;
import indexing.InvFileTF_Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import core.DocumentContainer;
import core.QueryContainer;

public class SimilarityCalculator {
	
	//subclass for tuple
	public class DocSimTuple{
		public int docnumber;
		public double sim;
		
		public DocSimTuple(){
			docnumber = 0;
			sim = 0;
		}
		public DocSimTuple(int new_docnumber, double new_sim){
			docnumber = new_docnumber;
			sim = new_sim;
		}
	}
	
	public HashMap<Integer, ArrayList<DocSimTuple>> similarityResult;
	
	public SimilarityCalculator(){
		similarityResult = new HashMap<Integer, ArrayList<DocSimTuple>>();
	}
	
	//get document weight
	public double getDocumentWeight(DocumentContainer dc, String docWord, int docNumber){
		double result = 0;
		
		ArrayList<InvFileTF> found_structurequery = dc.invFile.get(docWord);
		
		boolean found = false;
		int idx = 0;
		while((!found) && (idx < found_structurequery.size())){
			InvFileTF elmt = found_structurequery.get(idx);
			if(docNumber == elmt.docnum){ //if found
				result = elmt.TF;
				found = true;
			}
			idx++;
		}
		
		return result;
	}
	
	//get query weight
	public double getQueryWeight(QueryContainer qc, String queryWord, int queryNumber){
		double result = 0;
		
		ArrayList<InvFileTF_Query> found_structurequery = qc.invFile.get(queryWord);
		
		boolean found = false;
		int idx = 0;
		while((!found) && (idx < found_structurequery.size())){
			InvFileTF_Query elmt = found_structurequery.get(idx);
			if(queryNumber == elmt.docnum){ //if found
				result = elmt.TF;
				found = true;
			}
			idx++;
		}
		
		return result;
	}
	
	public void calculateSimilarity(DocumentContainer dc, QueryContainer qc){
		double wordweight_doc = 0;
		double wordweight_query = 0;
		double sim_temp;
		ArrayList<DocSimTuple> docsimtuple_list = new ArrayList<DocSimTuple>();
		//iterate over word in query list
		for(ArrayList<String> arr_query : qc.wordList){
			docsimtuple_list.clear(); //reset docsimtuple_list
			//iterate over doc in doc list
			for(ArrayList<String> arr_doc : dc.wordList){
				sim_temp = 0; //reset sim_temp
				for(String word_query : arr_query){
					//check if the word in the query was found in current document
					int found_index = arr_doc.indexOf(word_query); 
					if(found_index > -1 ){ //if word_query found
						wordweight_query = getQueryWeight(qc, word_query, qc.wordList.indexOf(arr_query)+1); //using indexOf(arr_query)+1 because the query numbering begins at 1
						wordweight_doc = getDocumentWeight(dc, word_query, dc.wordList.indexOf(arr_doc)+1); //using indexOf(arr_doc)+1 because the document numbering begins at 1
						sim_temp += wordweight_doc * wordweight_query; //the "dot product"
					}
				}
				if(sim_temp > 0){ //relevant if similarity > 0
					//insert the sim_temp value between query and doc
					DocSimTuple docsimtuple = new DocSimTuple(dc.wordList.indexOf(arr_doc)+1, sim_temp); //using indexOf(arr_doc)+1 because the document numbering begins at 1 
					docsimtuple_list.add(docsimtuple);
				}
			}
			similarityResult.put(qc.wordList.indexOf(arr_query), docsimtuple_list);  //using indexOf(arr_query)+1 because the query numbering begins at 1
		}
	}
	
	public void printSimilarity(){
		System.out.println("start print");
		Set<Integer> queryNumbers = similarityResult.keySet();
		ArrayList<Integer> queryNumberList = new ArrayList<Integer>(queryNumbers);
		for(int queryNumber = 0; queryNumber < queryNumberList.size(); queryNumber++){ //list of query numbers
			ArrayList<DocSimTuple> docsimtuple_list = similarityResult.get(queryNumber);
			for(int i = 0; i < docsimtuple_list.size(); i++){
				System.out.println((queryNumber+1)+"\t"+docsimtuple_list.get(i).docnumber+"\t"+docsimtuple_list.get(i).sim);
			}
		}
		System.out.println("end print");
	}
}
