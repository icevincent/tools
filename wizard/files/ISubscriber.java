/*TAG:PACKAGE*/

import org.osgi.framework.BundleContext;
import org.persona.middleware.input.InputEvent;
import org.persona.middleware.input.InputSubscriber;

public class ISubscriber extends InputSubscriber{

	protected ISubscriber(BundleContext context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public void communicationChannelBroken() {
		// TODO Auto-generated method stub
		
	}

	public void dialogAborted(String dialogID) {
		// TODO Auto-generated method stub
		
	}

	public void handleInputEvent(InputEvent event) {
		// TODO Auto-generated method stub
		
	}

}
