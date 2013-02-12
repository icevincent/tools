package org.universaal.tools.externalserviceintegrator.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TreeItem;

import swing2swt.layout.FlowLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.Combo;
import swing2swt.layout.BorderLayout;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Label;
import org.universAAL.ri.wsdlToolkit.wsdl.io.api.ComplexObject;
import org.universAAL.ri.wsdlToolkit.wsdl.io.api.NativeObject;
import org.universAAL.ri.wsdlToolkit.wsdl.io.api.ParsedWSDLDefinition;
import org.universAAL.ri.wsdlToolkit.wsdl.io.api.WSOperation;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class WSOperationDialog extends Dialog {
private ParsedWSDLDefinition theParsedDefinition;
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public WSOperationDialog(Shell parentShell,ParsedWSDLDefinition theParsedDefinition) {
		super(parentShell);
		this.theParsedDefinition=theParsedDefinition;
		
		
		
	}
	protected void configureShell(Shell shell) {
	      super.configureShell(shell);
	      shell.setText("Select a web service operation");
	   }
	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new BorderLayout(0, 0));
		
		final Composite composite_1 = new Composite(container, SWT.NONE);
		composite_1.setLayoutData(BorderLayout.CENTER);
		composite_1.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		final Tree tree = new Tree(composite_1, SWT.BORDER);
		
		Composite composite = new Composite(container, SWT.NONE);
		composite.setLayoutData(BorderLayout.NORTH);
		composite.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		Label lblSelectAnOperation = new Label(composite, SWT.NONE);
		lblSelectAnOperation.setText("Select an operation");
		
		final Combo combo = new Combo(composite, SWT.NONE|SWT.READ_ONLY);
		combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				tree.removeAll();
				calculateOperationTree(tree,combo.getSelectionIndex());
				
				
			}
		});
		String[] operationNames=new String[theParsedDefinition.getWsdlOperations()
		                   				.size()];
		for (int i = 0; i < theParsedDefinition.getWsdlOperations()
				.size(); i++) {
			operationNames[i]=(((WSOperation) theParsedDefinition
							.getWsdlOperations().get(i))
							.getOperationName());
		}
		combo.setItems(operationNames);
		combo.redraw();
		container.redraw();
		return container;
	}

	
	private void calculateOperationTree(Tree tree, int index){
		WSOperation oper=(WSOperation)theParsedDefinition.getWsdlOperations().get(index);
		TreeItem inputs = new TreeItem(tree, 0);
		inputs.setText("Inputs");
		calculateChildren(inputs,oper.getHasInput().getHasNativeOrComplexObjects());
		TreeItem outputs = new TreeItem(tree, 0);
		outputs.setText("Outputs");
		calculateChildren(outputs,oper.getHasOutput().getHasNativeOrComplexObjects());
	}
	
	private void calculateChildren(TreeItem treeItem, Vector vec){
		for(int i=0;i<vec.size();i++){
			if(vec.get(i) instanceof NativeObject){
				NativeObject no=(NativeObject)vec.get(i);
				TreeItem item=new TreeItem(treeItem, 0);
				item.setText(no.getObjectName().getLocalPart());
			}
			else
				if(vec.get(i) instanceof ComplexObject){
					ComplexObject co=(ComplexObject)vec.get(i);
					TreeItem item=new TreeItem(treeItem, 0);
					item.setText(co.getObjectName().getLocalPart());
					calculateChildren(item,co.getHasComplexObjects());
					calculateChildren(item,co.getHasNativeObjects());
				}
		}
		
	}
	
	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 300);
	}
}