package GUI;

import indexing.Indexer;

import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import core.MainBackend;

public class IndexerGUI extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	
	public static Button btnIndexing;
	
	public IndexerGUI(Composite parent, int style) {
		
		super(parent, style);
		setLayout(new GridLayout(3, false));
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Label lblDocument = new Label(this, SWT.NONE);
		lblDocument.setText("Document");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		final Button btnDocTF = new Button(this, SWT.CHECK);
		btnDocTF.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1));
		btnDocTF.setText("TF");
		
		final Button btnDocIDF = new Button(this, SWT.CHECK);
		btnDocIDF.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(btnDocIDF.getSelection()){
					MainBackend.document_flagIDF = true;
				}
				else{
					MainBackend.document_flagIDF = false;
				}
			}
		});
		btnDocIDF.setText("IDF");
		
		final Button btnDocNorm = new Button(this, SWT.CHECK);
		btnDocNorm.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(btnDocNorm.getSelection()){
					MainBackend.document_flagNormalization = true;
				}
				else{
					MainBackend.document_flagNormalization = false;
				}
			}
		});
		btnDocNorm.setText("Normalisasi");
		
		//document TF checkbox's radiobutton group
	    final Group groupDocTF = new Group(this, SWT.RADIO);
	    groupDocTF.setLayout(new RowLayout(SWT.VERTICAL));
	    
	    final Button btnBinaryTf = new Button(groupDocTF, SWT.RADIO);
	    btnBinaryTf.addSelectionListener(new SelectionAdapter() {
	    	@Override
	    	public void widgetSelected(SelectionEvent e) {
	    		MainBackend.document_TFType = Indexer.DOCUMENT_BINARY_TF;
	    	}
	    });
	    btnBinaryTf.setEnabled(false);
	    btnBinaryTf.setSelection(true);
	    btnBinaryTf.setText("Binary TF");
	    
	    final Button btnRawTf = new Button(groupDocTF, SWT.RADIO);
	    btnRawTf.addSelectionListener(new SelectionAdapter() {
	    	@Override
	    	public void widgetSelected(SelectionEvent e) {
	    		MainBackend.document_TFType = Indexer.DOCUMENT_RAW_TF;
	    	}
	    });
	    btnRawTf.setEnabled(false);
	    btnRawTf.setText("Raw TF");
	    
	    final Button btnLogTf = new Button(groupDocTF, SWT.RADIO);
	    btnLogTf.setEnabled(false);
	    btnLogTf.addSelectionListener(new SelectionAdapter() {
	    	@Override
	    	public void widgetSelected(SelectionEvent e) {
	    		MainBackend.document_TFType = Indexer.DOCUMENT_LOG_TF;
	    	}
	    });
	    
	    btnLogTf.setText("Log TF");
	    
	    final Button btnAugTf = new Button(groupDocTF, SWT.RADIO);
	    btnAugTf.addSelectionListener(new SelectionAdapter() {
	    	@Override
	    	public void widgetSelected(SelectionEvent e) {
	    		MainBackend.document_TFType = Indexer.DOCUMENT_AUGMENTED_TF;
	    	}
	    });
	    btnAugTf.setEnabled(false);
	    btnAugTf.setText("Augmented TF");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Label lblQuery = new Label(this, SWT.NONE);
		lblQuery.setText("Query");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		final Button btnQueryTF = new Button(this, SWT.CHECK);
		btnQueryTF.setText("TF");
		
		final Button btnQueryIDF = new Button(this, SWT.CHECK);
		btnQueryIDF.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(btnQueryIDF.getSelection()){
					MainBackend.query_flagIDF = true;
				}
				else{
					MainBackend.query_flagIDF = false;
				}
			}
		});
		btnQueryIDF.setText("IDF");
		
		final Button btnQueryNorm = new Button(this, SWT.CHECK);
		btnQueryNorm.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(btnQueryNorm.getSelection()){
					MainBackend.query_flagNormalization = true;
				}
				else{
					MainBackend.query_flagNormalization = false;
				}
			}
		});
		btnQueryNorm.setText("Normalisasi");
		
		//query TF checkbox's radiobutton group
	    final Group groupQueryTF = new Group(this, SWT.RADIO);
	    groupQueryTF.setLayout(new RowLayout(SWT.VERTICAL));
	    
	    final Button btnBinaryTf_2 = new Button(groupQueryTF, SWT.RADIO);
	    btnBinaryTf_2.addSelectionListener(new SelectionAdapter() {
	    	@Override
	    	public void widgetSelected(SelectionEvent e) {
	    		MainBackend.query_TFType = Indexer.DOCUMENT_BINARY_TF;
	    	}
	    });
	    btnBinaryTf_2.setEnabled(false);
	    btnBinaryTf_2.setSelection(true);
	    btnBinaryTf_2.setText("Binary TF");
	    
	    final Button btnRawTf_2 = new Button(groupQueryTF, SWT.RADIO);
	    btnRawTf_2.addSelectionListener(new SelectionAdapter() {
	    	@Override
	    	public void widgetSelected(SelectionEvent e) {
	    		MainBackend.query_TFType = Indexer.DOCUMENT_RAW_TF;
	    	}
	    });
	    btnRawTf_2.setEnabled(false);
	    btnRawTf_2.setText("Raw TF");
	    
	    final Button btnLogTf_2 = new Button(groupQueryTF, SWT.RADIO);
	    btnLogTf_2.setEnabled(false);
	    btnLogTf_2.addSelectionListener(new SelectionAdapter() {
	    	@Override
	    	public void widgetSelected(SelectionEvent e) {
	    		MainBackend.query_TFType = Indexer.DOCUMENT_LOG_TF;
	    	}
	    });
	    
	    btnLogTf_2.setText("Log TF");
	    
	    final Button btnAugTf_2 = new Button(groupQueryTF, SWT.RADIO);
	    btnAugTf_2.addSelectionListener(new SelectionAdapter() {
	    	@Override
	    	public void widgetSelected(SelectionEvent e) {
	    		MainBackend.query_TFType = Indexer.DOCUMENT_AUGMENTED_TF;
	    	}
	    });
	    btnAugTf_2.setEnabled(false);
	    btnAugTf_2.setText("Augmented TF");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		final Button btnStopWord = new Button(this, SWT.CHECK);
		btnStopWord.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(btnStopWord.getSelection()){
					MainBackend.document_flagStopWord = true;
				}
				else{
					MainBackend.document_flagStopWord = false;
				}
			}
		});
		btnStopWord.setText("English Stop Word");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		btnIndexing = new Button(this, SWT.PUSH);
		btnIndexing.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					MainBackend.doIndexing();
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnIndexing.setText("Indexing");
		
		/*Event Handling*/
		
		//btnDocTF event handling 
		btnDocTF.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(btnDocTF.getSelection()){
					groupDocTF.setEnabled(true);
					//ungrey out the radio buttons
					btnBinaryTf.setEnabled(true);
					btnRawTf.setEnabled(true);
					btnLogTf.setEnabled(true);
					btnAugTf.setEnabled(true);
					
					//backend
					MainBackend.document_flagTF = true;
				}
				else{
					groupDocTF.setEnabled(false);
					//grey out the radio buttons
					btnBinaryTf.setEnabled(false);
					btnRawTf.setEnabled(false);
					btnLogTf.setEnabled(false);
					btnAugTf.setEnabled(false);
					
					//backend
					MainBackend.document_flagTF = false;
				}
			}
		});
		
		//btnQueryTF event handling 
		btnQueryTF.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(btnQueryTF.getSelection()){
					groupQueryTF.setEnabled(true);
					//ungrey out the radio buttons
					btnBinaryTf_2.setEnabled(true);
					btnRawTf_2.setEnabled(true);
					btnLogTf_2.setEnabled(true);
					btnAugTf_2.setEnabled(true);
					
					//backend
					MainBackend.query_flagTF = true;
				}
				else{
					groupQueryTF.setEnabled(false);
					//grey out the radio buttons
					btnBinaryTf_2.setEnabled(false);
					btnRawTf_2.setEnabled(false);
					btnLogTf_2.setEnabled(false);
					btnAugTf_2.setEnabled(false);
					
					//backend
					MainBackend.query_flagTF = false;
				}
			}
		});

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
