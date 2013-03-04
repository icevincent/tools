/*
	Copyright 2012 CERTH, http://www.certh.gr
	
	See the NOTICE file distributed with this work for additional 
	information regarding copyright ownership
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	  http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */
package org.universaal.tools.owl2uml.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class SampleHandler extends AbstractHandler {

	/**
	 * The constructor.
	 */
	public SampleHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {

		IStructuredSelection selection = (IStructuredSelection) HandlerUtil
				.getActiveMenuSelection(event);

		IStructuredSelection selectedFileSelection = (IStructuredSelection) selection;

		Object obj = selectedFileSelection.getFirstElement();
		IResource selectedFile = (IResource) obj;

		String selectedInput = selectedFile.getLocation().toString();
		// The path of the generated .uml file
		String umlOutput = selectedInput.substring(
				selectedInput.lastIndexOf("\\") + 1,
				selectedInput.lastIndexOf("."))
				+ ".uml";

		String replumlFile = umlOutput.replace("/", "\\");
		// XML file used as input for the definition of the additional
		// properties
		String xmlInput = selectedInput.substring(
				selectedInput.lastIndexOf("\\") + 1,
				selectedInput.lastIndexOf("."))
				+ ".xml";

		xmlInput.replace("/", "\\");

		org.universaal.tools.owl2uml.uml2.UML2Factory.XMLFilePath = xmlInput;

		String[] arg = { selectedInput, replumlFile, "UML2" };

		org.universaal.tools.owl2uml.OWL2UML.main(arg);
		return null;
	}

	protected String getPersistentProperty(IResource res, QualifiedName qn) {
		try {
			return res.getPersistentProperty(qn);
		} catch (CoreException e) {
			return "";
		}
	}
}