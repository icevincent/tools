package org.universAAL.ucc.controller.install;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.universAAL.ucc.frontend.api.IWindow;
import org.universAAL.ucc.frontend.api.impl.InstallProcessImpl;
import org.universAAL.ucc.model.AALService;
import org.universAAL.ucc.model.install.License;
import org.universAAL.ucc.windows.LicenceWindow;
import org.universAAL.ucc.windows.OptionsWindow;
import org.universAAL.ucc.windows.UccUI;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Tree;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Window.Notification;

public class LicenseController implements Property.ValueChangeListener, ClickListener {
	private final LicenceWindow win;
	private String base = "resources.ucc";
	private ResourceBundle res;
	private ArrayList<License> lix;
	private UccUI app;
	private AALService aal;
	private IWindow iw;
	
	public LicenseController(UccUI app, LicenceWindow win, ArrayList<License> lix, AALService aal) {
		res = ResourceBundle.getBundle(base);
		this.win = win;
		this.lix = lix;
		this.app = app;
		this.aal = aal;
		win.getGo().addListener((Button.ClickListener)this);
		win.getCancel().addListener((Button.ClickListener)this);
	}
	
	public void valueChange(ValueChangeEvent event) {
		
		if(event.getProperty() instanceof Tree) {
			Panel panel = new Panel();
			for(License l : lix) {
				for(File sla : l.getSlaList()) {
					if(l.getAppName().contains(event.getProperty().getValue().toString())) {
						System.err.println("SLA name: "+sla.getName());
						FileReader fr = null;
						try {
							fr = new FileReader(sla);
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
						BufferedReader reader = new BufferedReader(fr);
						String line = null;
						try {
							while((line = reader.readLine()) != null) {
								panel.addComponent(new Label(line));
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				for(File f : l.getLicense()) {
					if(f.getName().contains(event.getProperty().getValue().toString())) {
						FileReader fr = null;
						try {
							fr = new FileReader(f);
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
						BufferedReader reader = new BufferedReader(fr);
						String line = null;
						try {
							while((line = reader.readLine()) != null) {
								panel.addComponent(new Label(line));
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
			
			win.getVl().removeAllComponents();
			win.createSecondComponent(panel);
			
		} else {
			if(event.getProperty().getValue().toString().equals(res.getString("agree.radio"))) {
				win.getGo().setEnabled(true);
			} 
			if(event.getProperty().getValue().toString().equals(res.getString("dontAgree.radio"))) {
				win.getGo().setEnabled(false);
			}
		}
		
	}
	
	public void buttonClick(ClickEvent event) {
		if(event.getButton() == win.getCancel()) {
			app.getMainWindow().showNotification(res.getString("break.note"), Notification.TYPE_ERROR_MESSAGE);
			app.getMainWindow().removeWindow(win);
		}
		if(event.getButton() == win.getGo()) {
			app.getMainWindow().removeWindow(win);
			iw = new InstallProcessImpl();
			int appCounter = aal.getUaapList().size();
			System.err.println(appCounter);
			//Test, if uapps size greater than 0
			if(appCounter > 0) {
				iw.getDeployStratgyView(aal.getName(), aal.getUaapList().get(appCounter-1).getServiceId(), 
						aal.getUaapList().get(appCounter-1).getUappLocation(), appCounter-1, aal);
			} 
		}
		
	}

}