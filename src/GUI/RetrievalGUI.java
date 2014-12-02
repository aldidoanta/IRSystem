package GUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class RetrievalGUI extends Composite {
	private Text text_file;
	private Text text_manualinput;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public RetrievalGUI(Composite parent, int style) {
		super(parent, style);
		
		Label lblQuery = new Label(this, SWT.NONE);
		lblQuery.setBounds(41, 58, 55, 15);
		lblQuery.setText("Query");
		
		final Button btn_file = new Button(this, SWT.RADIO);
		btn_file.setSelection(true);
		btn_file.setBounds(41, 92, 90, 16);
		btn_file.setText("File");
		
		final Button btn_manualinput = new Button(this, SWT.RADIO);
		btn_manualinput.setBounds(41, 132, 90, 16);
		btn_manualinput.setText("Manual Input");
		
		text_file = new Text(this, SWT.BORDER);
		text_file.setBounds(142, 87, 190, 21);
		
		text_manualinput = new Text(this, SWT.BORDER);
		text_manualinput.setEnabled(false);
		text_manualinput.setBounds(142, 130, 190, 21);
		
		final Button btnOpen = new Button(this, SWT.PUSH);
		
		btnOpen.setBounds(349, 83, 75, 25);
		btnOpen.setText("Open...");
		
		Button btnRetrieve = new Button(this, SWT.PUSH);
		btnRetrieve.setBounds(197, 218, 75, 25);
		btnRetrieve.setText("Retrieve");
		
		//event handler for btn_file and btn_manualinput
		btn_file.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(btn_file.getSelection()){
					text_file.setEnabled(true);
					btnOpen.setEnabled(true);
				}
				else{
					text_file.setEnabled(false);
					btnOpen.setEnabled(false);
				}
			}
		});
		
		btn_manualinput.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(btn_manualinput.getSelection()){
					text_manualinput.setEnabled(true);
				}
				else{
					text_manualinput.setEnabled(false);
				}
			}
		});

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
