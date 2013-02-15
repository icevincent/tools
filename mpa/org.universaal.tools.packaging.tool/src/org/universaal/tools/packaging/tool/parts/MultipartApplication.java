package org.universaal.tools.packaging.tool.parts;

import java.util.ArrayList;
import java.util.List;

public class MultipartApplication {

	public static final String defaultURL = "http://www.webpage.com";
	public static final String defaultString = "asdf";
	public static final String defaultFile = "c:\file.txt";

	private Application application;
	private ApplicationCapabilities capabilities;
	private ApplicationRequirements requirements;
	private ApplicationManagement management;
	private List<Part> parts;

	public MultipartApplication(){
		this.application = new Application();
		this.capabilities = new ApplicationCapabilities();
		this.requirements = new ApplicationRequirements();
		this.management = new ApplicationManagement();
	}

	public Application getApplication() {
		return application;
	}
	public void setApplication(Application app) {
		this.application = app;
	}
	public ApplicationCapabilities getCapabilities() {
		return capabilities;
	}
	public void setCapabilities(ApplicationCapabilities capabilities) {
		this.capabilities = capabilities;
	}
	public ApplicationRequirements getRequirements() {
		return requirements;
	}
	public void setRequirements(ApplicationRequirements requirements) {
		this.requirements = requirements;
	}
	public ApplicationManagement getManagement() {
		return management;
	}
	public void setManagement(ApplicationManagement management) {
		this.management = management;
	}
	public List<Part> getParts() {
		if(parts == null)
			parts = new ArrayList<Part>();
		return parts;
	}
}