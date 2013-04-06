package org.universAAL.ucc.windows;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import org.universAAL.ucc.controller.install.LicenseController;
import org.universAAL.ucc.model.AALService;
import org.universAAL.ucc.model.install.License;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;

public class LicenceWindow extends Window {
    private OptionGroup op;
    private Button cancel;
    private Button go;
    private String base = "resources.ucc";
    private ResourceBundle res;
    private List<String> modus;
    private LicenseController lic;
    private VerticalLayout vl;
    private HorizontalSplitPanel hp;
    private UccUI app;
    private Tree tree;

    public LicenceWindow(UccUI app, ArrayList<License> licenses, AALService aal)
	    throws IOException {
	res = ResourceBundle.getBundle(base);
	setCaption(res.getString("license.capt"));
	this.app = app;
	modus = Arrays.asList(new String[] { res.getString("agree.radio"),
		res.getString("dontAgree.radio") });
	vl = new VerticalLayout();
	vl.setSizeFull();
	vl.setSpacing(true);
	vl.setMargin(true);
	hp = new HorizontalSplitPanel();
	hp.setSplitPosition(150, Sizeable.UNITS_PIXELS);
	hp.setStyleName(Reindeer.SPLITPANEL_SMALL);
	hp.setLocked(true);

	hp.setSizeFull();
	tree = new Tree();
	tree.setImmediate(true);
	tree.setNullSelectionAllowed(true);
	tree.setNewItemsAllowed(false);
	for (License l : licenses) {
	    tree.addItem(l.getAppName());
	    tree.setChildrenAllowed(l.getAppName(), true);
	    tree.expandItemsRecursively(l.getAppName());
	    for (File f : l.getLicense()) {
		tree.addItem(f.getName());
		tree.setParent(f.getName(), l.getAppName());
		tree.setChildrenAllowed(f.getName(), false);
	    }
	}
	tree.select(licenses.get(0).getAppName());
	Panel panel = new Panel();
	panel.setHeight("400px");
	VerticalLayout layout = (VerticalLayout) panel.getContent();
	layout.setSpacing(true);
	layout.setMargin(true);
	for (License l : licenses) {
	    for (File slaFile : l.getSlaList()) {
		FileReader fr = new FileReader(slaFile);
		// SLA makes problems
		BufferedReader reader = new BufferedReader(fr);
		String line = null;
		while ((line = reader.readLine()) != null) {
		    panel.addComponent(new Label(line));
		}
	    }
	}
	hp.setFirstComponent(tree);
	vl.addComponent(panel);
	setContent(hp);
	op = new OptionGroup("", modus);
	op.setNullSelectionAllowed(false);
	op.select(res.getString("dontAgree.radio"));
	op.setImmediate(true);
	vl.addComponent(op);
	HorizontalLayout hl = new HorizontalLayout();
	hl.setSpacing(true);
	hl.setMargin(true);
	cancel = new Button(res.getString("cancel.button"));
	go = new Button(res.getString("finish.button"));
	go.setEnabled(false);
	hl.addComponent(cancel);
	hl.addComponent(go);
	vl.addComponent(hl);
	vl.setComponentAlignment(hl, Alignment.BOTTOM_RIGHT);
	setWidth("700px");
	setHeight("600px");
	setModal(true);
	center();
	hp.setSecondComponent(vl);
	setClosable(false);
	lic = new LicenseController(app, this, licenses, aal);
	tree.addListener(lic);
	op.addListener(lic);
    }

    public void createSecondComponent(Panel p) {
	modus = Arrays.asList(new String[] { res.getString("agree.radio"),
		res.getString("dontAgree.radio") });
	vl = new VerticalLayout();
	vl.setSizeFull();
	vl.setSpacing(true);
	vl.setMargin(true);
	p.setHeight("400px");
	VerticalLayout layout = (VerticalLayout) p.getContent();
	layout.setSpacing(true);
	layout.setMargin(true);
	vl.addComponent(p);
	op = new OptionGroup("", modus);
	op.setNullSelectionAllowed(false);
	op.select(res.getString("dontAgree.radio"));
	op.setImmediate(true);
	vl.addComponent(op);
	HorizontalLayout hl = new HorizontalLayout();
	hl.setSpacing(true);
	hl.setMargin(true);
	cancel = new Button(res.getString("cancel.button"));
	go = new Button(res.getString("finish.button"));
	cancel.addListener((Button.ClickListener) lic);
	go.addListener((Button.ClickListener) lic);
	go.setEnabled(false);
	hl.addComponent(cancel);
	hl.addComponent(go);
	vl.addComponent(hl);
	vl.setComponentAlignment(hl, Alignment.BOTTOM_RIGHT);
	op.addListener(lic);
	hp.setSecondComponent(vl);

    }

    public VerticalLayout getVl() {
	return vl;
    }

    public void setVl(VerticalLayout vl) {
	this.vl = vl;
    }

    public Button getGo() {
	return go;
    }

    public void setGo(Button go) {
	this.go = go;
    }

    public Button getCancel() {
	return cancel;
    }

    public void setCancel(Button cancel) {
	this.cancel = cancel;
    }

}
