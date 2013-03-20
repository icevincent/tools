package org.universaal.tools.packaging.tool.gui;

import java.io.File;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.universaal.tools.packaging.impl.PageImpl;
import org.universaal.tools.packaging.tool.util.Dialog;
import org.universaal.tools.packaging.tool.validators.FileV;

public class StartPage extends PageImpl {

	private File destination;
	private Text name;

	private GUI g = GUI.getInstance();
	private List<IProject> parts;

	protected StartPage(String pageName) {
		super(pageName, "This is the starting page for the uAAL MPA packager");
	}

	public void createControl(final Composite parent) { 

		container = new Composite(parent, SWT.NULL);
		setControl(container);

		GridLayout layout = new GridLayout();
		container.setLayout(layout);

		layout.numColumns = 2;
		gd = new GridData(GridData.FILL_HORIZONTAL);

		parts = g.getParts();

		Label label1 = new Label(container, SWT.NULL);
		label1.setText("This wizard will guide you in creating a .UAPP file to upload your Multi Part Application to the uStore.");

		Label label10 = new Label(container, SWT.NULL);
		label10.setText("");

		Label label2 = new Label(container, SWT.NULL);
		label2.setText("Before starting it you should select all the parts you would like to include (CTRL-click on the desidered projects).");

		Label label20 = new Label(container, SWT.NULL);
		label20.setText("");

		Label label3 = new Label(container, SWT.NULL);
		label3.setText("");

		Label label30 = new Label(container, SWT.NULL);
		label30.setText("");

		Label label4 = new Label(container, SWT.NULL);
		label4.setText("At now you have selected "+parts.size()+" projects to be included in this MPA:");

		Label label40 = new Label(container, SWT.NULL);
		label40.setText("");

		for(int i = 0; i < parts.size(); i++){				

			Label part = new Label(container, SWT.NULL);
			part.setText("\tPart "+(i+1)+": "+parts.get(i).getName()); 

			FontData[] fD = part.getFont().getFontData();
			fD[0].setStyle(SWT.BOLD);
			part.setFont(new Font(container.getDisplay(), fD[0]));		

			Label part0 = new Label(container, SWT.NULL);
			part0.setText("");
		}

		if(parts.size() == 0){
			Label label5 = new Label(container, SWT.NULL);
			label5.setText("No parts have been selected, please select desidered parts and restart the procedure.");

			FontData[] fD = label5.getFont().getFontData();
			fD[0].setStyle(SWT.BOLD);
			label5.setFont(new Font(container.getDisplay(), fD[0]));	

			Label label50 = new Label(container, SWT.NULL);
			label50.setText("");

			setPageComplete(false);
		}

		Label label6 = new Label(container, SWT.NULL);
		label6.setText("");

		Label label60 = new Label(container, SWT.NULL);
		label60.setText("");

		Label label7 = new Label(container, SWT.NULL);
		label7.setText("Please specify where the .UAPP file will be created...");

		Label label70 = new Label(container, SWT.NULL);
		label70.setText("");

		name = new Text(container, SWT.BORDER | SWT.SINGLE);
		name.setText(app.getApplication().getName());			
		name.setLayoutData(gd);		
		name.addVerifyListener(new FileV());

		Button b1 = new Button(container, SWT.PUSH);
		b1.setText("Browse");
		b1.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				Dialog d = new Dialog();
				destination = d.open(parent.getShell(), new String[]{"*.uapp"});			

				if(!destination.getAbsolutePath().endsWith(".uapp"))
					destination = new File(destination+".uapp");

				name.setText(destination.getAbsolutePath());

				if(destination != null && destination.isAbsolute() && parts.size() > 0)
					setPageComplete(true);
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});	

		Label label9 = new Label(container, SWT.NULL);
		label9.setText("");

		Label label90 = new Label(container, SWT.NULL);
		label90.setText("");

		Label label100 = new Label(container, SWT.NULL);
		label100.setText("");

		Label label1000 = new Label(container, SWT.NULL);
		label1000.setText("");

		Label label8 = new Label(container, SWT.NULL);
		label8.setText("If your part selection is correct, please press the Next button to start the creation of the MPA.");

		Label label80 = new Label(container, SWT.NULL);
		label80.setText("");
	}

	@Override
	public boolean nextPressed() {

		g.setDestination(destination.getAbsolutePath());
		return true;
	}
}