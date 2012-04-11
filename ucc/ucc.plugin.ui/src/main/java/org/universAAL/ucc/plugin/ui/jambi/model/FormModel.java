/*******************************************************************************
 * Copyright 2011 Universidad Politécnica de Madrid
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.universAAL.ucc.plugin.ui.jambi.model;

import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.ucc.plugin.ui.jambi.model.FormControl.GroupModel;

import com.trolltech.qt.gui.QFrame;
import com.trolltech.qt.gui.QMainWindow;
import com.trolltech.qt.gui.QWidget;

/**
 * Model for Forms, all {@link FormLAF} should extend this class
 * which will provide the available panels (Standard, submits and
 * IO).
 *
 * This model also provides other functionalities such as the
 * parent tree construction for subdialogs. And antecessor query
 * to check if a dialog is the parent tree of the form.
 *
 * @author <a href="mailto:amedrano@lst.tfo.upm.es">amedrano</a>
 * @see Form
 */
public abstract class FormModel {

    /**
     * Reference to RDF class
     */
    protected Form form;

    /**
     * The parent dialog of this sub dialog
     */
    private FormModel parent;

    /**
     * The depth level of the dialog.
     * if it not a SubDialog then the depth
     * is 0, else is the number of parents.
     */
    private int subDialogLevel;

    /**
     * the name set to IO panel
     */
    public static String IO_NAME = "Interaction";

    /**
     * the name set to Submits panel
     */
    public static String SUB_NAME = "Submits";

    /**
     * the name set to System buttons panel
     */
    public static String SYS_NAME = "System";

    /**
     * Constructor for a given {@link Form}.
     * Retrieves the parent (if there is any) and computes
     * the depth.
     * registers the form in the {@link FormModelMapper} to
     * be retrieved by successors.
     * @param f
     *     The {@link Form} for which the model is constructed.
     */
    protected FormModel(Form f) {
        form = f;
        if (form.isSubdialog()) {
            parent = FormModelMapper.getFromURI(form.getParentDialogURI());
            subDialogLevel = parent.subDialogLevel + 1;
        }
        else {
            subDialogLevel = 0;
            parent = null;
            //FormModelMapper.flush();
        }
        FormModelMapper.register(this);
    }

    /**
     * Construct the Frame that displays the {@link Form}
     * @return
     *     a {@link JFrame} that contains all the panels and
     * components of the {@link Form}
     */
    public abstract QMainWindow getFrame();

    /**
     * Terminate the dialog, closing the frame, and any
     * required procedure.
     */
    protected abstract void terminateDialog();

    /**
     * terminate the current dialog, close the frame, and
     * unregister from {@link FormModelMapper}.
     */
    public void finalizeForm() {
        /*
         * FIXME this will unregister parent forms URIs
         * making useless the registering and antecessor lookup.
         * find where to unRegister (maybe in SubmitModel), or
         * the map (FormModelMapper) will grow indefinitely!!!
         */
        FormModelMapper.unRegister(this);
        terminateDialog();
    }

    /**
     * Get the {@link Form} related to this {@link FormModel}
     * @return
     *         the {@link Form}
     */
    public Form getForm() {
        return form;
    }

    /**
     * Checks if the URI represents any antecessor {@link Form} of
     * the current {@link FormModel}.
     * @param uri
     *         candidate antecessor's URI
     * @return
     *         true if uri is equal or an antecessor of the current
     * {@link FormModel}.
     */
    public boolean isAntecessor(String uri) {
        FormModel search = this;
        while (search != null
                && uri != null
                && search.getForm().getDialogID()
                != uri) {
            search = search.parent;
        }
        return search != null;
    }

    /**
     * construct the IO Panel for the {@link Form}
     * @return
     *     a {@link JPanel} with all the components inside
     * {@link Form#getIOControls()} group.
     */
    protected QFrame getIOPanel() {
        QWidget jio = new GroupModel(form.getIOControls()).getComponent();
        jio.setAccessibleName(IO_NAME);
        return (QFrame) jio;
    }

    /**
     * construct the System buttons Panel for the {@link Form}
     * @return
     *     a {@link QFrame} with all the components inside
     * {@link Form#getStandardButtons()} group.
     */
    protected QFrame getSystemPanel() {
    	QWidget jsys = new GroupModel(form.getStandardButtons()).getComponent();
        jsys.setAccessibleName(SYS_NAME);
        return (QFrame) jsys;
    }

    /**
     * construct the Submit Panel for the {@link Form}
     * @return
     *     a {@link QFrame} with all the components inside
     * {@link Form#getSubmits()} group.
     */
    protected QFrame getSubmitPanel() {
    	QWidget jstd = new GroupModel(form.getSubmits()).getComponent();
        jstd.setAccessibleName(SUB_NAME);
        return (QFrame) jstd;
    }

    /**
     * get the {@link FormModel} parent of the current {@link FormModel}
     * that has the given depth.
     * @param depth
     *         the desired depth.
     * @return
     *         the {@link FormModel} for the parent with the depth.
     *         null if depth > current depth. or depth <0
     * @see FormModel#subDialogLevel
     */
    private FormModel getFormModelOfLevel(int depth) {
        if (depth > this.subDialogLevel
                || depth < 0) {
            return null;
        }
        FormModel levelForm = this;
        while (depth < levelForm.subDialogLevel) {
            levelForm = levelForm.parent;
        }
        return levelForm;
    }

    /**
     * construct the IO Panel for the parent with given depth.
     * @param depth
     *         the desired depth.
     * @return
     *     a {@link QFrame} with all the components inside
     * {@link Form#getIOControls()} group of the antecessor.
     * @see FormModel#subDialogLevel
     * @see FormModel#getIOPanel()
     */
    protected QFrame getIOPanel(int depth) {
        FormModel levelForm = getFormModelOfLevel(depth);
        return levelForm != null ?
                levelForm.getIOPanel()
                : null;
    }

    /**
     * construct the Submit Panel for the parent with given depth.
     * @param depth
     *         the desired depth.
     * @return
     *     a {@link QFrame} with all the components inside
     * {@link Form#getSubmits()} group of the antecessor.
     * @see FormModel#subDialogLevel
     * @see FormModel#getSubmitPanel()
     */
    protected QFrame getSubmitPanel(int depth) {
        FormModel levelForm = getFormModelOfLevel(depth);
        return levelForm != null ?
                levelForm.getSubmitPanel()
                : null;
    }

    /**
     * get the Depth of the current {@link FormModel}.
     * @return
     *         depth (A.K.A subdialogLevel)
     * @see FormModel#subDialogLevel
     */
    public int getSubdialogLevel() {
        return subDialogLevel;
    }
}
