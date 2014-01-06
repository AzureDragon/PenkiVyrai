package mainControllers;

import java.sql.Connection;
import java.sql.Statement;

import model.Authentication;
import model.Clasifiers;
import model.Client;

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
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

import services.AuthenticationService;
import services.AuthenticationServiceImpl;
import services.UserInfoService;
import services.UserInfoServiceImpl;



public class ClientProfileViewController extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;

	private Connection connect = null;
	private Statement statement = null;

	// wire components
	@Wire
	Label account;
	@Wire
	Textbox Name;
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
	Textbox Address;
	@Wire
	Listbox delegatesListbox;

	// services
	AuthenticationService authService = new AuthenticationServiceImpl();

	UserInfoService userInfoService = new UserInfoServiceImpl();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
System.out.print("LABAS");
		if (!authService.isLoggedIn())
		{
			System.out.print("nerado");
			Executions.sendRedirect("login.zul");
		}
		else
		{
			refreshProfileView();
			System.out.print("rado");
		}
	}

	@Listen("onClick=#saveProfile")
	public void doSaveProfile() throws Exception {
		Authentication cre = authService.getUserCredential();
		Client client = userInfoService.findClient(cre);
		if (client == null) {
			// TODO handle un-authenticated access
			return;
		}

		// apply component value to bean

		client.setName(Name.getValue());
		client.setAddress(Address.getValue());

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

		Clients.showNotification("Jūsų profilis atnaujintas sėkmingai.");

		connect = Clasifiers.getConnection();
		statement = connect.createStatement();
		statement.executeUpdate("UPDATE client SET Name='" + Name.getValue()
				+ "',Address='" + Address.getValue() + "',Mails='" + null
				+ "', Phones='" + null + "' WHERE id='" + client.getId()
				+ "'");
		statement.close();

		Executions.sendRedirect("index.zul");
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
		Client client = userInfoService.findClient(cre);
		if (client == null) {
			// TODO handle un-authenticated access
			return;
		}
		// apply bean value to UI components
		account.setValue(cre.getLoginName());
		Name.setValue(client.getName());
		Code.setValue(client.getCode());
		Address.setValue(client.getAddress());
		rights.setValue(userInfoService.rightsValue(client.getRights()));
		
		//delegatesListbox.
	}
}