package GUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

public class MainGUI {

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			
			Display display = Display.getDefault();
			
			Shell shlIrsystem = new Shell(); 
			shlIrsystem.setSize(516, 512);
			shlIrsystem.setText("IRSystem");
			shlIrsystem.setLayout(new GridLayout(1, false));
						
			final TabFolder tabFolder = new TabFolder(shlIrsystem, SWT.BORDER);
			
			//tab 1 - indexing
		    TabItem tab_indexing = new TabItem(tabFolder, SWT.NONE);
		    tab_indexing.setText("Indexing");
			//indexer composite
			IndexerGUI indexerGUI = new IndexerGUI(tabFolder, SWT.NONE);
			tab_indexing.setControl(indexerGUI);
			
			//tab 2 - invertedfile
			TabItem tab_invertedfile = new TabItem(tabFolder, SWT.NONE);
		    tab_invertedfile.setText("InvertedFile");

		    ScrolledComposite sc = new ScrolledComposite(tabFolder, SWT.BORDER | SWT.V_SCROLL);
			//invfile composite
			InvertedFileGUI invertedfileGUI = new InvertedFileGUI(sc, SWT.NONE);
			invertedfileGUI.setLayout(new FillLayout());
			sc.setContent(invertedfileGUI);
			sc.setExpandVertical(true);
			sc.setExpandHorizontal(true);
			sc.setMinSize(invertedfileGUI.computeSize(SWT.DEFAULT, SWT.DEFAULT));
			tab_invertedfile.setControl(sc);
			sc.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
			
			//tab 3 - retrieval
			TabItem tab_retrieval = new TabItem(tabFolder, SWT.NONE);
			tab_retrieval.setText("Retrieval");
			//retrieval composite
			RetrievalGUI retrievalGUI = new RetrievalGUI(tabFolder, SWT.NONE);
			tab_retrieval.setControl(retrievalGUI);
			
			shlIrsystem.open();
			shlIrsystem.layout();
			while (!shlIrsystem.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
