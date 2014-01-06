package mainControllers;

import java.sql.Connection;
import java.sql.Statement;

import model.Authentication;
import model.Clasifiers;
import model.Client;
import model.Delegate;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

import services.AuthenticationService;
import services.AuthenticationServiceImpl;
import services.UserInfoService;
import services.UserInfoServiceImpl;



public class DelegateProfileViewController extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;

	private Connection connect = null;
	private Statement statement = null;

	// wire components
	@Wire
	Label account;
	@Wire
	Textbox Name;
	@Wire
	Textbox surName;
	@Wire
	Textbox oldPassword;
	@Wire
	Textbox newPassword;
	@Wire
	Textbox newRepPassword;
	@Wire
	Label Code;
	@Wire
	Label rights;
	@Wire
	Label active;
	@Wire
	Textbox mails, phone;

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
		Delegate delegate = userInfoService.findDelegate(cre);
		if (delegate == null) {
			// TODO handle un-authenticated access
			return;
		}

		// apply component value to bean

		delegate.setFirstName(Name.getValue());

		if (!(oldPassword.getValue().isEmpty())) {
			if ((oldPassword.getValue().equals(cre.getPassword()))
					&& (newPassword.getValue().equals(newRepPassword))
					&& !(newPassword.getValue().isEmpty()))
				cre.setPassword(newPassword.getValue());

			connect = Clasifiers.getConnection();
			statement = connect.createStatement();
			statement
					.executeUpdate("UPDATE authentificationservice SET Password='"
							+ newPassword.getValue()
							+ "' WHERE id='"
							+ cre.getId() + "'");
			statement.close();
		}

		Clients.showNotification("Jūsų profilis atnaujintas sėkmingai.", Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 50000, true);

		connect = Clasifiers.getConnection();
		statement = connect.createStatement();
		statement.executeUpdate("UPDATE delegates SET Name='" + Name.getValue()
				+ "',Surname='" + surName.getValue()
				+ "',Telephone='" + phone.getValue() 
				+ "',Mail='" + mails.getValue()
				+ "',Active='" + delegate.isActive()
				+ "' WHERE id='" + delegate.getID()
				+ "'");
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
		Delegate delegate = userInfoService.findDelegate(cre);
		if (delegate == null) {
			// TODO handle un-authenticated access
			return;
		}
		// apply bean value to UI components
		account.setValue(cre.getLoginName());
		Name.setValue(delegate.getFirstName());
		surName.setValue(delegate.getLastName());
		Code.setValue(userInfoService.findClientByID(delegate.getClientID()).getName());
		rights.setValue(userInfoService.rightsValue(delegate.getRights()));
		mails.setValue(delegate.getMail());
		phone.setValue(delegate.getPhone());
		active.setValue(Boolean.toString(delegate.isActive()));

	}

}