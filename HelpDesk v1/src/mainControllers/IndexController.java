package mainControllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;

import model.Authentication;

import org.jfree.text.TextBox;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Toolbarbutton;

import com.lowagie.text.DocumentException;

import services.AuthenticationService;
import services.AuthenticationServiceImpl;
import services.DataControllService;
import services.DataControllServiceImpl;



public class IndexController extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;
	AuthenticationService authService = new AuthenticationServiceImpl();

	@Wire
	Toolbarbutton profile;
	@Wire
	Label logOut;
	@Wire
	Label timer;
	@Wire
	TextBox name;
	@Wire
	Button importFile;

	Media media;
	
	static Date date = new Date();

	public static Date getDate() {
		System.out.print(date+"\n");
		return date;
	}

	public static void setDate(Date date) {
		IndexController.date = date;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {

		super.doAfterCompose(comp);
		if (!authService.isLoggedIn())
			Executions.sendRedirect("login.zul");
	}

	public void ImportDataBase(String name)
	{
		DataControllService DCS = new DataControllServiceImpl();
		
		DCS.importData(name);
	}
	
	public File Export(boolean dataset1, boolean dataset2) throws Exception
	{
		DataControllService DCS = new DataControllServiceImpl();
		
		return DCS.exportData(dataset1, dataset2);
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

	public void profileSwap() throws Exception {

		Authentication cre = authService.getUserCredential();
		System.out.print("pasieke");
		if (cre.getClientId() > 0)
		{
			System.out.print("pasiekeKlienta");
			Executions.sendRedirect("profileClient.zul");
		}
		else
			if (cre.getEmployeeId() > 0)
			Executions.sendRedirect("profileEmployee.zul");
			else
				if (cre.getDelegateId() > 0)
					Executions.sendRedirect("profileDelegate.zul");
				else
					Executions.sendRedirect("login.zul");
	}

//	public void changeName() {
//
//		Authentication cre = authService.getUserCredential();
//		profile.setValue(cre.getLoginName());
//	}
}
