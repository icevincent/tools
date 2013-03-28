package org.universAAL.ucc.controller.install;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import org.universAAL.ucc.model.AALService;
import org.universAAL.ucc.model.install.License;
import org.universAAL.ucc.windows.DeploymentInformationView;
import org.universAAL.ucc.windows.LicenceWindow;
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
//	private IWindow iw;
	private static int appCounter;
	
	public LicenseController(UccUI app, LicenceWindow win, ArrayList<License> lix, AALService aal) {
		res = ResourceBundle.getBundle(base);
		this.win = win;
		this.lix = lix;
		this.app = app;
		this.aal = aal;
		appCounter = aal.getUaapList().size();
//		iw = new InstallProcessImpl();
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
			File f = new File(System.getenv("systemdrive") + "/tempUsrvFiles/");
			deleteFiles(f);
		}
		if(event.getButton() == win.getGo()) {
			app.getMainWindow().removeWindow(win);
			//Test, if uapps size greater than 0
			if(appCounter > 0) {
				System.err.println("[LicenseController]: appCounter "+appCounter);
				//Load Infoview for Deployment of uapps
				DeploymentInformationView div = new DeploymentInformationView(app);
				DeploymentInfoController dic = new DeploymentInfoController(app, aal, div);
				app.getMainWindow().addWindow(div);
//				iw.getDeployStratgyView(aal.getName(), aal.getServiceId(), 
//						aal.getUaapList().get(0).getUappLocation(), appCounter, aal);
//				appCounter--;
			} 
		}
		
	}
	
	private void deleteFiles(File path) {
		File[] files = path.listFiles();
		for (File del : files) {
			if (del.isDirectory()) {
				deleteFiles(del);
			}
			del.delete();
		}

	}


}
