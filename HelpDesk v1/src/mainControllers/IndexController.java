package mainControllers;

import model.Authentication;

import org.jfree.text.TextBox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;

import services.AuthenticationService;
import services.AuthenticationServiceImpl;



public class IndexController extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;
	AuthenticationService authService = new AuthenticationServiceImpl();

	@Wire
	public Label profile;
	@Wire
	Label logOut;
	@Wire
	Label timer;
	@Wire
	TextBox name;

	@Override
	public void doAfterCompose(Component comp) throws Exception {

		super.doAfterCompose(comp);
		if (!authService.isLoggedIn())
			Executions.sendRedirect("login.zul");
	}

	@Listen("onClick = #logOut")
	public void logOut() {

		authService.logout();
		Executions.sendRedirect("login.zul");
	}

	@Listen("onMouseOver = #logOut")
	public void checkedLogOut() {

		logOut.setStyle("color:red;font-weight:bold;text-decoration:underline");
	}

	@Listen("onMouseOut = #logOut")
	public void unCheckedLogout() {

		logOut.setStyle("color:black");
	}

	@Listen("onMouseOver = #profile")
	public void checkedProfile() {

		profile.setStyle("color:red;font-weight:bold;text-decoration:underline");
	}

	@Listen("onMouseOut = #profile")
	public void UnCheckedProfile() {

		profile.setStyle("color:black");
	}

	@Listen("onClick = #profile")
	public void profileSwap() throws Exception {

		Authentication cre = authService.getUserCredential();
		if (cre.getClientId() > 0)
			Executions.sendRedirect("profileClient.zul");
		else
			Executions.sendRedirect("profileEmployee.zul");
	}

	public void changeName() {

		Authentication cre = authService.getUserCredential();
		profile.setValue(cre.getLoginName());
	}
}
