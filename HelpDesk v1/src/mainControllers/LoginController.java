package mainControllers;

import model.Authentication;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

import services.AuthenticationService;
import services.AuthenticationServiceImpl;

public class LoginController extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;

	int stat = 0;
	// wire components
	@Wire
	Textbox account;
	@Wire
	Textbox password;
	@Wire
	Label message;

	// services
	AuthenticationService authService = new AuthenticationServiceImpl();

//	@Listen("onClick = #password, #account")
//	public void clear() {
//		if (stat == 3) {
//			password.setValue("");
//			message.setValue("įveskite slaptažodį!");
//		}
//		if (stat == 2)
//			password.setValue("");
//		message.setValue("Blogai ivestas slaptažodis!");
//		if (stat == 1)
//			message.setValue("Tokio vartotojo nėra!");
//		account.setValue("");
//		if (stat == 0) {
//			password.setValue("");
//			account.setValue("");
//			message.setValue("norėdami prisijungti suveskite prisijungimo duomenis");
//		}
//	}

	@Listen("onClick=#login")
	public void doLogin() throws Exception {
		
		String nm = account.getValue();
		String pd = password.getValue();

		if (!authService.login(nm, pd)) {
			message.setValue("Vartotojo vardas arba slaptažodis yra blogi, mėginkite vėl");
			return;
		} else
			stat = 2;

		Authentication cre = authService.getUserCredential();
		message.setValue("Sveiki, " + cre.getLoginName());
		message.setSclass("");
Sessions.getCurrent().setAttribute("labas", "name");
		Executions.sendRedirect("index.zul");

	}
}
