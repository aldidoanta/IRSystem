package indexing;

/**
 * A representation of TF entry in an inverted file 
 * @author aldidoanta
 *
 */
public class InvFileTF {
	public int docnum; //document number
	public int TF_raw; //raw TF
	public double TF;
	
	//ctor
	public InvFileTF(){
		docnum = 0;
		TF_raw = 0;
		TF = 0;
	}
}
