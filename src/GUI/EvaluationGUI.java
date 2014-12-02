package GUI;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import core.MainBackend;

public class EvaluationGUI extends Composite {
	private Text text;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public EvaluationGUI(Composite parent, int style) {
		super(parent, style);
		
		Label lblRelevanceJudgementFile = new Label(this, SWT.NONE);
		lblRelevanceJudgementFile.setBounds(136, 64, 152, 15);
		lblRelevanceJudgementFile.setText("Relevance Judgement File");
		
		text = new Text(this, SWT.BORDER);
		text.setBounds(111, 85, 190, 21);
		
		Button button = new Button(this, SWT.NONE);
		button.setText("Open...");
		button.setBounds(307, 83, 75, 25);
		
		Button btnEvaluate = new Button(this, SWT.NONE);
		btnEvaluate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MainBackend.doEvaluation();
			}
		});
		btnEvaluate.setBounds(181, 132, 75, 25);
		btnEvaluate.setText("Evaluate");

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
