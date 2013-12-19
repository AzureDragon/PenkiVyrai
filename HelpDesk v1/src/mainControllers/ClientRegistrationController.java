package mainControllers;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Textbox;

public class ClientRegistrationController extends SelectorComposer<Component> {

	@Wire
	Textbox id, clientName, clientCode, clientAddress;
	@Wire
	Grid dynamicEmails, dynamicPhones;
	@Wire
	Button createClientButton;
	
	
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
	}
	
	@Listen ("onClick=#createClientButton")
	public void createClient (){
		
		
		
		
	}

}
