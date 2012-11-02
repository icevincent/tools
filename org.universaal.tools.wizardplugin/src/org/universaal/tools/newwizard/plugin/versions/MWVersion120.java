package org.universaal.tools.newwizard.plugin.versions;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Implements IMWVersion for the 1.2.0 release of the MW. It extends 100 version
 * in order to reuse similar methods, but overrides everything in which it
 * differs from it.
 * 
 * @author alfiva
 */
public class MWVersion120 extends MWVersion110{
    
    @Override
    public int getMWVersionNumber() {
	return IMWVersion.VER_120;
    }

    //________CONSTANTS________
    
    protected static final String BASIC_DEPS = 
	    "		<dependency>\n"
	    + "			<groupId>org.apache.felix</groupId>\n"
	    + "			<artifactId>org.osgi.core</artifactId>\n"
	    + "			<version>1.0.1</version>\n"
	    + "		</dependency>\n"
	    + "		<dependency>\n"
	    + "			<groupId>org.universAAL.middleware</groupId>\n"
	    + "			<artifactId>mw.data.representation.osgi</artifactId>\n"
	    + "			<version>1.2.0</version>\n"
	    + "		</dependency>\n"
	    + "		<dependency>\n"
	    + "			<groupId>org.universAAL.middleware</groupId>\n"
	    + "			<artifactId>mw.bus.model.osgi</artifactId>\n"
	    + "			<version>1.2.0</version>\n"
	    + "		</dependency>\n"
	    + "		<dependency>\n"
	    + "			<groupId>org.universAAL.middleware</groupId>\n"
	    + "			<artifactId>mw.container.xfaces.osgi</artifactId>\n"
	    + "			<version>1.2.0</version>\n"
	    + "		</dependency>\n"
	    + "		<dependency>\n"
	    + "			<groupId>org.universAAL.middleware</groupId>\n"
	    + "			<artifactId>mw.container.osgi</artifactId>\n"
	    + "			<version>1.2.0</version>\n"
	    + "		</dependency>\n";	
    protected static final String SERVICE_DEPS =
	    "		<dependency>\n" 
	    + "			<groupId>org.universAAL.middleware</groupId>\n" 
	    + "			<artifactId>mw.bus.service.osgi</artifactId>\n" 
	    + "			<version>1.2.0</version>\n" 
	    + "		</dependency>\n";
    protected static final String CONTEXT_DEPS = 
	    "		<dependency>\n" 
	    + "			<groupId>org.universAAL.middleware</groupId>\n" 
	    + "			<artifactId>mw.bus.context.osgi</artifactId>\n" 
	    + "			<version>1.2.0</version>\n" 
	    + "		</dependency>\n";
    protected static final String UI_DEPS = 
	    "		<dependency>\n" 
	    + "			<groupId>org.universAAL.middleware</groupId>\n" 
	    + "			<artifactId>mw.bus.ui.osgi</artifactId>\n" 
	    + "			<version>1.2.0</version>\n" 
	    + "		</dependency>\n";
    protected static final String PHWORLD_DEPS = 
	    "		<dependency>\n" 
	    + "			<groupId>org.universAAL.ontology</groupId>\n" 
	    + "			<artifactId>ont.phWorld</artifactId>\n" 
	    + "			<version>1.2.0</version>\n" 
	    + "		</dependency>\n";
    protected static final String PROFILE_DEPS = 
	    "		<dependency>\n" 
	    + "			<groupId>org.universAAL.ontology</groupId>\n" 
	    + "			<artifactId>ont.profile</artifactId>\n" 
	    + "			<version>1.2.0</version>\n" 
	    + "		</dependency>\n";
    
    //________FILE MANIPULATION METHODS________
    
    /* (non-Javadoc)
     * @see org.universaal.tools.newwizard.plugin.versions.MWVersion100#customizePom(java.lang.String, java.io.InputStream, boolean[], boolean, boolean)
     */
    @Override
    public InputStream customizePom(String pack, InputStream input,
	    boolean[] checks, boolean phworld, boolean profile) throws IOException {
	BufferedReader reader = new BufferedReader(new InputStreamReader(input));
	StringBuilder output = new StringBuilder();
	String line;
	while ((line = reader.readLine()) != null) {
	    if (line.contains("</project>")) {
		output.append("	<packaging>bundle</packaging>\n");
		output.append("	<dependencies>\n");
		output.append(BASIC_DEPS);
		if (checks[0] || checks[1]) {
		    output.append(SERVICE_DEPS);
		}
		if (checks[3] || checks[4]) {
		    output.append(CONTEXT_DEPS);
		}
		if (checks[6] || checks[7]) {
		    output.append(UI_DEPS);
		}
		if (phworld) {
		    output.append(PHWORLD_DEPS);
		}
		if (profile) {
		    output.append(PROFILE_DEPS);
		}
		output.append("	</dependencies>\n");
		output.append(BUILD.replace(TAG_PACKAGE, pack));
		output.append(REPOS);
		output.append("</project>\n");
	    } else {
		output.append(line + "\n");
	    }
	}
	return new ByteArrayInputStream(output.toString().getBytes());
    }
    
    /* (non-Javadoc)
     * @see org.universaal.tools.newwizard.plugin.versions.MWVersion100#updatePom(java.io.InputStream, int)
     */
    @Override
    protected InputStream updatePom(InputStream input, int clstype) throws IOException {
	BufferedReader reader = new BufferedReader(new InputStreamReader(
		input));
	StringBuilder output = new StringBuilder();
	String line;
	boolean context = false;
	boolean service = false;
	boolean ui = false;
	while ((line = reader.readLine()) != null) {
	    if (line.contains("mw.bus.context")) { //$NON-NLS-1$
		context = true;
	    }
	    if (line.contains("mw.bus.service")) { //$NON-NLS-1$
		service = true;
	    }
	    if (line.contains("mw.bus.ui")) { //$NON-NLS-1$
		ui = true;
	    }
	    if (line.contains("</dependencies>")) { //$NON-NLS-1$
		StringBuilder outputnew = new StringBuilder();
		if (!context && (clstype == 0 || clstype == 1 || clstype == 2)) {
		    outputnew
			    .append(SERVICE_DEPS); //$NON-NLS-1$
		}
		if (!service
			&& (clstype == 3 || clstype == 4 )) {
		    outputnew
			    .append(CONTEXT_DEPS); //$NON-NLS-1$
		}
		if (!ui
			&& (clstype > 4)) {
		    outputnew
			    .append(UI_DEPS); //$NON-NLS-1$
		}
		outputnew.append("</dependencies>"); //$NON-NLS-1$
		line = line.replace("</dependencies>", outputnew.toString()); //$NON-NLS-1$
	    }
	    output.append(line + "\n"); //$NON-NLS-1$
	}
	return new ByteArrayInputStream(output.toString().getBytes());
    }
    
    
    //________HELPER METHODS________
    
    /* (non-Javadoc)
     * @see org.universaal.tools.newwizard.plugin.versions.MWVersion100#getMainFolder()
     */
    @Override
    public String getMainFolder() {
	return "files/1.1.0/";
    }
}
