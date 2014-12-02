package indexing;

/**
 * A representation of "TF entry in an inverted file" for query 
 * @author aldidoanta
 *
 */
public class InvFileTF_Query {
	public int docnum; //document number
	public int TF_raw; //raw TF
	public double TF;
	
	//ctor
	public InvFileTF_Query(){
		docnum = 0;
		TF_raw = 0;
		TF = 0;
	}
}