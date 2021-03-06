package mainControllers;

import java.sql.Connection;
import java.sql.Statement;

import model.Authentication;
import model.Clasifiers;
import model.Employee;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

import services.AuthenticationService;
import services.AuthenticationServiceImpl;
import services.UserInfoService;
import services.UserInfoServiceImpl;



public class EmployeeProfileViewController extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;

	private Connection connect = null;
	private Statement statement = null;

	@Wire
	Label account;
	@Wire
	Textbox firstName;
	@Wire
	Textbox lastName;
	@Wire
	Textbox oldPassword;
	@Wire
	Textbox newPassword;
	@Wire
	Textbox newRepPassword;
	@Wire
	Label rights;

	// services
	AuthenticationService authService = new AuthenticationServiceImpl();
	UserInfoService userInfoService = new UserInfoServiceImpl();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		
		super.doAfterCompose(comp);
		if (!authService.isLoggedIn())
			Executions.sendRedirect("login.zul");
		else
			refreshProfileView();
	}

	@Listen("onClick=#saveProfile")
	public void doSaveProfile() throws Exception {

		Authentication cre = authService.getUserCredential();
		Employee user = userInfoService.findEmployee(cre);
		if (user == null) {
			return;
		}

		user.setFirstName(firstName.getValue());
		user.setSurName(lastName.getValue());

		if (!(oldPassword.getValue().isEmpty())) {
			if ((oldPassword.getValue().equals(cre.getPassword()))
					&& (newPassword.getValue().equals(newRepPassword.getValue()))
					&& !(newPassword.getValue().isEmpty()))
			{
				cre.setPassword(newPassword.getValue());

			connect = Clasifiers.getConnection();
			statement = connect.createStatement();
			statement
					.executeUpdate("UPDATE authentificationservice SET Password='"
							+ newPassword.getValue()
							+ "' WHERE id='"
							+ cre.getId() + "'");
			statement.close();
			Clients.showNotification("Jūsų profilis atnaujintas sėkmingai.", Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 50000, true);

					}
			else
				Clients.showNotification("Slaptažodis nebuvo pakeistas.", Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 50000, true);
			}
		else
			Clients.showNotification("Jūsų profilis atnaujintas sėkmingai.", Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 50000, true);

		connect = Clasifiers.getConnection();
		statement = connect.createStatement();
		statement.executeUpdate("UPDATE employee SET Name='"
				+ firstName.getValue() + "',Surname='" + lastName.getValue()
				+ "',EmailAddress='" + null + "',TelephoneNumber='"
				+ null + "' WHERE id='" + user.getId() + "'");
		statement.close();
	//	Executions.sendRedirect("index.zul");
	}

	@Listen("onClick=#reloadProfile")
	public void doReloadProfile() throws Exception {

		if (!authService.isLoggedIn())
			Executions.sendRedirect("login.zul");
		else
			refreshProfileView();
	}

	private void refreshProfileView() throws Exception {
		
		Authentication cre = authService.getUserCredential();
		Employee user = userInfoService.findEmployee(cre);

		if (user == null) {
			// TODO handle un-authenticated access
			return;
		}
		// apply bean value to UI components
		account.setValue(cre.getLoginName());
		firstName.setValue(user.getFirstName());
		lastName.setValue(user.getSurName());
		rights.setValue(userInfoService.rightsValue(user.getRights()));

	}
}