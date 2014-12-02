package evaluation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import retrieval.SimilarityCalculator;
import retrieval.SimilarityCalculator.DocSimTuple;

public class Evaluator {
	
	ArrayList<ArrayList<Integer>> relList;
	
	//for evaluation output for each query
	//the size of each ArrayList will be the same as the number of queries
	public ArrayList<Integer> relDocs_Total;
	public ArrayList<Integer> relDocs_Retrieved;
	public ArrayList<Integer> retrievedDocs;
	
	public Evaluator(){
		relList = new ArrayList<ArrayList<Integer>>();
		relDocs_Total = new ArrayList<Integer>();
		relDocs_Retrieved = new ArrayList<Integer>();
		retrievedDocs = new ArrayList<Integer>();
	}
	
	/**
	 * Read a relevance judgement file
	 */
	public void readRelevanceJudgement(String path){
		//parsing
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(path));
			for(String line = br.readLine(); line != null; line = br.readLine()){
				String[] linetoken = line.split(" +");
				if(Integer.parseInt(linetoken[0]) > relList.size()){
					//create a new ArrayList<Integer>
					relList.add(new ArrayList<Integer>());
					//add a new relevance judgement for existing query number
					relList.get(relList.size()-1).add(Integer.parseInt(linetoken[1]));
				}
				else{
					//add a new relevance judgement for existing query number
					relList.get(relList.size()-1).add(Integer.parseInt(linetoken[1]));
				}
			}
			for(ArrayList<Integer> ar : relList){
				relDocs_Total.add(ar.size()); //number of relevant docs for each query
				//System.out.println(ar.size());
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}			
	}
	
	public void calculateEvaluation(SimilarityCalculator sc){
		retrievedDocs = new ArrayList<Integer>(); //size will be the same as the size of relDocs_Total
		for(int i = 0; i < relDocs_Total.size(); i++){
			retrievedDocs.add(0); //init with 0
		}
		relDocs_Retrieved = new ArrayList<Integer>(); //size will be the same as the size of relDocs_Total
		for(int i = 0; i < relDocs_Total.size(); i++){
			relDocs_Retrieved.add(0); //init with 0
		}
		
		Set<Integer> queryNumbers = sc.similarityResult.keySet();
		ArrayList<Integer> queryNumberList = new ArrayList<Integer>(queryNumbers);
		Collections.sort(queryNumberList);
		
		for(Integer query_number : queryNumberList){
			ArrayList<DocSimTuple> docsimtuple_list = sc.similarityResult.get(query_number);
			retrievedDocs.set(query_number-1, docsimtuple_list.size()); //number of documents retrieved for the query

			
			for(DocSimTuple dst : docsimtuple_list){
				if(relList.get(query_number-1).indexOf(dst.docnumber) > -1){ //if retrieved document is the relevant one
					relDocs_Retrieved.set((query_number-1), (relDocs_Retrieved.get(query_number-1) + 1)); //increment retrieved relevant documents
				}
			}
			
			//evaluation result
			System.out.println("Query number: "+query_number);
			int current_relDocs_total = relDocs_Total.get(query_number-1);
			int current_relDocs_Retrieved = relDocs_Retrieved.get(query_number-1);
			int current_retrievedDocs = retrievedDocs.get(query_number-1);
			System.out.println("Total Relevant Documents in Collection: "+current_relDocs_total);
			System.out.println("Relevant Documents Retrieved: "+current_relDocs_Retrieved);
			System.out.println("Retrieved Documents: "+current_retrievedDocs);
			float recall = (float)current_relDocs_Retrieved / (float)current_relDocs_total;
			float precision = (float)current_relDocs_Retrieved / (float)current_retrievedDocs;
			System.out.println("Recall: "+recall);
			System.out.println("precision: "+precision+"\n");
		}
	}
}
