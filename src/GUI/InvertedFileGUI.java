package GUI;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class InvertedFileGUI extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public InvertedFileGUI(Composite parent, int style) {
		super(parent, SWT.BORDER | SWT.V_SCROLL);
		
		setLayout(new FillLayout());
		
		//table
		Table table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
	    table.setHeaderVisible(true);
	    
	    //table columns
	    TableColumn tblclmnWord = new TableColumn(table, SWT.NONE);
	    tblclmnWord.setWidth(100);
	    tblclmnWord.setText("Word");
	    
	    TableColumn tblclmnDocument = new TableColumn(table, SWT.NONE);
	    tblclmnDocument.setWidth(85);
	    tblclmnDocument.setText("Document#");
	    
	    TableColumn tblclmnRawtf = new TableColumn(table, SWT.NONE);
	    tblclmnRawtf.setWidth(66);
	    tblclmnRawtf.setText("RawTF");
	    
	    TableColumn tblclmnIdf = new TableColumn(table, SWT.NONE);
	    tblclmnIdf.setWidth(76);
	    tblclmnIdf.setText("IDF");
	    
	    TableColumn tblclmnFinalWeight = new TableColumn(table, SWT.NONE);
	    tblclmnFinalWeight.setWidth(119);
	    tblclmnFinalWeight.setText("Final Weight");
	    
	    //table entries
	    BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("out.txt"));
			 String line;
		    while ((line = br.readLine()) != null) {
		       String[] tokens = line.split("\\s+");
		       //show entries from out.txt
		       TableItem item = new TableItem(table,SWT.NULL);
		       item.setText(0,tokens[0]);
		       item.setText(1,tokens[1]);
		       item.setText(2,tokens[2]);
		       item.setText(3,tokens[3]);
		       item.setText(4,tokens[4]);
		    }
		    br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
