package org.universaal.tools.transformationcommand.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.universaal.tools.transformationcommand.activator.Activator;

public class Uml2JavaPreferencePage
extends FieldEditorPreferencePage
implements IWorkbenchPreferencePage {

	StringFieldEditor pathName;
	BooleanFieldEditor absoluteBoolean;

	public Uml2JavaPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Please enter a path for the files to be saved to. " +
				"If \" Use absolute path\" is not checked, the path refers to a folder " +
				"relative to the project folder.");
	}

	@Override
	public void init(IWorkbench workbench) {
	}

	@Override
	protected void createFieldEditors() {
		absoluteBoolean = new BooleanFieldEditor(PreferenceConstants.P_UML2JAVA_ABSOLUTE_BOOLEAN, "Use absolute path.", getFieldEditorParent());
		pathName = new StringFieldEditor(PreferenceConstants.P_UML2JAVA_PATH, "Please enter the desired path.", getFieldEditorParent());

		addField(absoluteBoolean);
		addField(pathName);


	}

}
