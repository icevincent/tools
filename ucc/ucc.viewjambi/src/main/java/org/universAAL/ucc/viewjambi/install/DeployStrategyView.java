package org.universAAL.ucc.viewjambi.install;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.framework.Bundle;
import org.universAAL.middleware.connectors.deploy.model.AalMpa;
import org.universAAL.middleware.connectors.deploy.model.DeploymentUnit;
import org.universAAL.middleware.connectors.deploy.model.Part;
import org.universAAL.middleware.interfaces.PeerCard;
import org.universAAL.middleware.managers.api.InstallationResults;
import org.universAAL.ucc.viewjambi.common.SubWindow;
import org.universAAL.ucc.viewjambi.impl.Activator;
import org.universAAL.ucc.viewjambi.impl.MainWindow;
import org.universAAL.ucc.viewjambi.juic.Ui_DeployStrategyView;

import com.trolltech.qt.gui.*;

public class DeployStrategyView extends SubWindow {

    private static Ui_DeployStrategyView ui = new Ui_DeployStrategyView();
    boolean defaultStrategy = true;
    String deployPath;
    MpaParser mpaParser;
    Map<String, PeerCard> peers;

    /**
     * 
     * @param path deployPath, i.e., path of the extracted files from .uaal
     */
    public DeployStrategyView(String path) {
        super(DeployStrategyView.ui);
        
        deployPath = path;
        mpaParser = new MpaParser(deployPath);
        // TODO: get application name from .mpa file
        //String appName = "MyApp";
        String appName = mpaParser.getAppName();
        // get the list of nodes in the AALspace
        
        //
        
        // initialization of the ui components
        ui.lineEdit_appName.setText(appName);
 	   	ui.lineEdit_appName.setReadOnly(true); // disable text input (display only)
 	   	ui.radioButton_all.setChecked(true);
        ui.radioButton_all.clicked.connect(this, "onAllNodes()");
        ui.radioButton_selected.clicked.connect(this, "onSelectedNodes()");

        ui.pushButton_cancel.clicked.connect(this, "cancel()");
        ui.pushButton_ok.clicked.connect(this, "ok()");
        //ui.setupUi(this);
    }
    
    private void onAllNodes() {
    	defaultStrategy = true;
    }
    
    private void onSelectedNodes() {
    	defaultStrategy = false;
    }
    
    private void ok() {   
    	// get peers from AALSpaceManager
    	peers = Activator.getInstaller().getPeers();
    	if (defaultStrategy) {
			
				Map config = buildDefaultInstallationLayout();
				InstallationResults results = Activator.getInstaller().requestToInstall((new File(deployPath)).toURI(), config);				
				switch (results)  {
				case SUCCESS: 
					QMessageBox.information(this, "Installation result", "The multi-part application has been successfully installed!");
					System.out.println("[DeployStrategyView.ok] The multi-part application has been successfully installed!");
					break;
					
				case FAILED:
					QMessageBox.information(this, "Installation result", "The installation of the multi-part application has been failed!");
					System.out.println("[DeployStrategyView.ok] The installation of the multi-part application has been failed!");
					break;
					
				case NO_AALSPACE_JOINED:
					QMessageBox.information(this, "Installation result", "Error in the installation of the multi-part application: no AALspace joined!");
					System.out.println("[DeployStrategyView.ok] Error in the installation of the multi-part application: no AALspace joined!");
					break;
					
				case MPA_URI_INVALID:
					QMessageBox.information(this, "Installation result", "Error in the installation of the multi-part application: MPA uri is invalid!");
					System.out.println("[DeployStrategyView.ok] Error in the installation of the multi-part application: MPA uri is invalid!");
					break;
					
				case DELEGATED:
					QMessageBox.information(this, "Installation result", "The installation of the multi-part application is delegated...");
					System.out.println("[DeployStrategyView.ok] The installation of the multi-part application is delegated...");
					break;
					
				case LOCALLY_DELEGATED:
					QMessageBox.information(this, "Installation result", "The installation of the multi-part application is locally delegated...");
					System.out.println("[DeployStrategyView.ok] The installation of the multi-part application is locally delegated...");
					break;
					
				default:
					break;
				}
				
    	}	
		else {
    		// allow user to select nodes to deploy
    		MainWindow.getInstance().removeSubWindow(this);
			MainWindow.getInstance().deployConfigure(deployPath, mpaParser, peers);
    	}
    		
    }
    
    /**
	 * Method to find the set of target peers according to the multipart application manifest
	 * @param mpa the MPA 
	 * @return map of PeerCard of the target peers
	 */
    private Map<PeerCard, Part> buildDefaultInstallationLayout() {
    	//TODO: Do we need to check AAL space first (aalSpaceCheck)?
    	Map<PeerCard, Part> mpaLayout = new HashMap<PeerCard, Part>();
    	Map<String, PeerCard> peersToCheck = new HashMap<String, PeerCard>();
		peersToCheck.putAll(peers);
    	for(Part part : mpaParser.getApplicationPart().getPart()){
    		//check: deployment units
    		for(String key: peersToCheck.keySet()){
    			PeerCard peer = peersToCheck.get(key);
    			if(checkDeployementUnit(part.getDeploymentUnit(), peer)){
    				mpaLayout.put(peer, part);
    				peersToCheck.remove(key);
    				break;
    			}
    		}
    	}
    	for (PeerCard key: mpaLayout.keySet()) {
    		   System.out.println("[DeployStrategyView.buildDefaultInstallationLayout] mpalayout: " + key.getPeerID() + "/" + mpaLayout.get(key).getPartId() );
    		}
    	return mpaLayout;
	}

    public static boolean checkDeployementUnit(List<DeploymentUnit> depoyementUnits, PeerCard peer){
    	String osUnit;
    	String pUnit;
		for(DeploymentUnit deployementUnit: depoyementUnits){
			//check the existence of: osUnit and platformUnit
			if(deployementUnit.getOsUnit()!= null){
				osUnit = deployementUnit.getOsUnit().value();
				if(osUnit == null || osUnit.isEmpty()){
					System.out.println("[DeployStrategyView.checkDeploymentUnit] OSunit is present but not consistent. OSUnit is null or empty");
					return false;
				}
				//Check if compatible?
				if (!osUnit.equals("any")) {
					// only considers equal definition
					//if (!osUnit.equalsIgnoreCase(peer.getOS())) return false;
					System.out.println("osUnit: " + osUnit);
					if (!(peer.getOS().contains(osUnit))) return false;
				}
			}else if (deployementUnit.getPlatformUnit() != null){
				pUnit = deployementUnit.getPlatformUnit().value();
				if(pUnit == null || pUnit.isEmpty()){
					System.out.println("[DeployStrategyView.checkDeploymentUnit] PlatformUnit is present but not consistent. Plaform is null or empty");
					return false;

				}
				//check if compatible?
				if (!pUnit.equalsIgnoreCase(peer.getPLATFORM_UNIT())) return false;
			}
			//TODO: check containerUnit?
		}
		return true;
	}
    
	private void cancel()  {
    	MainWindow.getInstance().removeSubWindow(this);    	
    }
    
    
   
}