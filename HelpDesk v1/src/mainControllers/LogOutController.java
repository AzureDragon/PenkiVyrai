package mainControllers;



import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Toolbarbutton;

import services.AuthenticationServiceImpl;

public class LogOutController extends SelectorComposer<Component> {
	
@Wire Toolbarbutton LogOut;	

@Listen("onClick=#LogOut")
public void logOut (){
	
	AuthenticationServiceImpl auth = new AuthenticationServiceImpl();
	auth.logout();
	Executions.sendRedirect("login.zul");
	
}

}
