package mainControllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import model.Authentication;
import model.Clasifiers;
import model.Client;
import model.Delegate;
import model.Task;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
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

	private ResultSet resultSet = null;

	// services
	AuthenticationService authService = new AuthenticationServiceImpl();

	UserInfoService userInfoService = new UserInfoServiceImpl();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);


		if (!authService.isLoggedIn())
		{
		
			Executions.sendRedirect("login.zul");
		}
		else
		{ 
			Authentication cre = authService.getUserCredential();
			refreshProfileView();
			delegatesListbox.setModel(new ListModelList<Delegate>(getDelegates(cre.getClientId())));
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
		statement.executeUpdate("UPDATE client SET Name='" + Name.getValue()
				+ "',Address='" + Address.getValue() + "',Mails='" + null
				+ "', Phones='" + null + "' WHERE id='" + client.getId()
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
	
	
	public List<Delegate> getDelegates (int clientId) throws Exception{
		
		
		
		
		Class.forName("com.mysql.jdbc.Driver");
		connect = Clasifiers.getConnection();
		statement = connect.createStatement();
		System.out.println("select * from delegates WHERE ClientId="+ clientId+";");
		resultSet = statement.executeQuery("select * from delegates WHERE ClientId="+ clientId +";");
		
		
		return writeResultSet(resultSet);
	}
	private List<Delegate> writeResultSet(ResultSet resultSet) throws Exception {
		 List<Delegate> delegateList;
		delegateList = new LinkedList<Delegate>();
		  while (resultSet.next()) {
			  
			  	if (resultSet.getBoolean("Active")){
			  		delegateList.add(new Delegate(
			    			resultSet.getString("Name"),
			    			resultSet.getString("Surname"),
			    			resultSet. getString("Telephone"),
			    			resultSet.getString("Mail"), "Aktyvus"));
			   	
			  	} else{
			  		delegateList.add(new Delegate(
			    			resultSet.getString("Name"),
			    			resultSet.getString("Surname"),
			    			resultSet. getString("Telephone"),
			    			resultSet.getString("Mail"), "Neaktyvus"));
			   
			  	}
		  }
		    	 
		  return delegateList;
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