package org.universAAL.ucc.model;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.ucc.api.model.IModel;

public class Activator implements BundleActivator {
	public static BundleContext context=null;
	
	private static IModel model;

	public void start(BundleContext context) throws Exception {
		Activator.context=context;
		
		model = new Model();
		
		context.registerService(new String[] { IModel.class.getName() }, model, null);
	}

	public void stop(BundleContext arg0) throws Exception {
		
	}
	
	public static IModel getModel() {
		return model;
	}

}
