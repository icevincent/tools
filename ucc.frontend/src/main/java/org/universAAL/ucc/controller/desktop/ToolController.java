package org.universAAL.ucc.controller.desktop;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.universAAL.ucc.controller.install.AALServiceReceiver;
import org.universAAL.ucc.controller.install.UsrvInfoController;
import org.universAAL.ucc.model.AALService;
import org.universAAL.ucc.model.install.License;
import org.universAAL.ucc.windows.LicenceWindow;
import org.universAAL.ucc.windows.ToolWindow;
import org.universAAL.ucc.windows.UccUI;
import org.universAAL.ucc.windows.UsrvInformationWindow;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Upload.FailedEvent;
import com.vaadin.ui.Upload.FinishedEvent;
import com.vaadin.ui.Window.Notification;

public class ToolController implements Button.ClickListener, Upload.FinishedListener, Upload.FailedListener {
	private UccUI app;
	private ToolWindow toolWin;
	private Window installWindow;
	private String base;
	private ResourceBundle res;
	private final static String dir = "tempUsrvFiles";
	
	public ToolController(UccUI app, ToolWindow toolWin) {
		this.app = app;
		this.toolWin = toolWin;
		base = "resources.ucc";
		res = ResourceBundle.getBundle(base);
		File f = new File(System.getenv("systemdrive")+"/"+dir+"/");
		if(!f.exists()) {
			f.mkdir();
		}
	}
	
	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getButton() == toolWin.getuStoreButton()) {
			Embedded em = new Embedded("", new ExternalResource("https://srv-ustore.haifa.il.ibm.com/webapp/wcs/stores/servlet/TopCategories_10001_10001"));
			em.setType(Embedded.TYPE_BROWSER);
			em.setWidth("100%");
			em.setHeight("500px");
			Window w = new Window("uStore");
			w.setWidth("850px");
			w.setHeight("550px");
			VerticalLayout v = new VerticalLayout();
			w.center();
			v.addComponent(em);
			w.setContent(v);
			app.getMainWindow().removeWindow(toolWin);
			app.getMainWindow().addWindow(w);
		}
		if(event.getButton() == toolWin.getOpenAAL()) {
			Embedded em = new Embedded("", new ExternalResource("http://wiki.openaal.org"));
			em.setType(Embedded.TYPE_BROWSER);
			em.setWidth("100%");
			em.setHeight("500px");
			Window w = new Window("openAAL");
			w.setWidth("850px");
			w.setHeight("550px");
			VerticalLayout v = new VerticalLayout();
			w.center();
			v.addComponent(em);
			w.setContent(v);
			app.getMainWindow().removeWindow(toolWin);
			app.getMainWindow().addWindow(w);
		}
		if(event.getButton() == toolWin.getInstallButton()) {
			Upload up = new Upload("", new AALServiceReceiver());
			up.setButtonCaption(res.getString("install.button"));
			up.addListener((Upload.FinishedListener)this);
			up.addListener((Upload.FailedListener)this);
			installWindow = new Window(res.getString("install.win.caption"));
			installWindow.setResizable(false);
			installWindow.center();
			installWindow.setWidth("400px");
			VerticalLayout v = new VerticalLayout();
			v.setSizeFull();
			v.setSpacing(true);
			v.setMargin(true);
			v.addComponent(up);
			installWindow.setContent(v);
			app.getMainWindow().removeWindow(toolWin);
			app.getMainWindow().addWindow(installWindow);
		
		}
		if(event.getButton() == toolWin.getLogoutButton()) {
//			app.getMainWindow().removeComponent((app.getVs()));
//			app.getMainWindow().removeWindow(ToolWindow.getTooWindowInstance(app));
//			app.getMainWindow().setContent(app.getVLog());
			app.close();
//			app.createLogin();
		}
		
	}

	@Override
	public void uploadFailed(FailedEvent event) {
		app.getMainWindow().removeWindow(installWindow);
		app.getMainWindow().showNotification(res.getString("break.note"), Notification.TYPE_ERROR_MESSAGE);
		
	}

	@Override
	public void uploadFinished(FinishedEvent event) {
		app.getMainWindow().removeWindow(installWindow);
		File licenceFile = new File(System.getenv("systemdrive")+"/"+dir+"/config/hwo.usrv.xml");
		DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
		File l = null;
		LicenceWindow lw = null;
		String txt = "";
		String appName = "";
		String slaName = "";
		License license = null;
		ArrayList<License> licenseList = new ArrayList<License>();
		ArrayList<File> list = new ArrayList<File>();
		AALService aal = null;
		try {
			DocumentBuilder builder = fact.newDocumentBuilder();
			Document doc = builder.parse(licenceFile);
			for(int k = 0; k < doc.getElementsByTagName("usrv:srv").getLength(); k++) {
				aal = new AALService();
				System.err.println(doc.getElementsByTagName("usrv:description").item(0).getTextContent());
					aal.setName(doc.getElementsByTagName("usrv:name").item(0).getTextContent());
					aal.setProvider(doc.getElementsByTagName("usrv:serviceProvider").item(0).getTextContent());
					aal.setDescription(doc.getElementsByTagName("usrv:description").item(0).getTextContent());
					aal.setMajor(Integer.parseInt(doc.getElementsByTagName("usrv:major").item(0).getTextContent()));
					aal.setMinor(Integer.parseInt(doc.getElementsByTagName("usrv:minor").item(0).getTextContent()));
					aal.setMicro(Integer.parseInt(doc.getElementsByTagName("usrv:micro").item(0).getTextContent()));
					String h = doc.getElementsByTagName("usrv:tags").item(0).getTextContent();
					for(String t : h.split(",")) {
						aal.getTags().add(t);
					}
				license = new License();
				for(int s = 0; s < doc.getElementsByTagName("usrv:sla").getLength(); s++) {
					Node node = (Node) doc.getElementsByTagName("usrv:sla").item(s);
					NodeList nodeList = node.getChildNodes();
					for(int c = 0; c < nodeList.getLength(); c++) {
						if(nodeList.item(c).getNodeName().equals("usrv:name")) {
							slaName = nodeList.item(c).getTextContent();
							license.setAppName(slaName);
						}
						if(nodeList.item(c).getNodeName().equals("usrv:link")) {
							String link = nodeList.item(c).getTextContent();
							link = link.substring(link.lastIndexOf("/"));
							File file = new File(System.getenv("systemdrive")+"/"+dir+"/licenses"+link);
							license.getSlaList().add(file);
						}
					}
				}

			for(int i = 0; i < doc.getElementsByTagName("usrv:license").getLength(); i++) {
				Node n = (Node) doc.getElementsByTagName("usrv:license").item(i);
				NodeList nlist = n.getChildNodes();
				
				for(int j = 0; j < nlist.getLength(); j++) {
					if(nlist.item(j).getNodeName().equals("usrv:link")) {
						txt = nlist.item(j).getTextContent();
						txt = txt.substring(txt.lastIndexOf("/"));
						l = new File(System.getenv("systemdrive")+"/"+dir+"/licenses"+txt);
						list.add(l);
					}
					
				}
			}
			license.setLicense(list);
			licenseList.add(license);
			aal.setLicenses(license);
			}
			lw = new LicenceWindow(app, licenseList);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		UsrvInfoController infoController = new UsrvInfoController(aal, lw, app);
//		app.getMainWindow().addWindow(lw);
//		app.getMainWindow().addWindow(info);
		//ToDo: install AAL services with DeployManager and delete temp usrv file with unziped folders
		
	}


}