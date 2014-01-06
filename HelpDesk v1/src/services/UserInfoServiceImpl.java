package services;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import model.Authentication;
import model.Clasifiers;
import model.Client;
import model.Delegate;
import model.Employee;


public class UserInfoServiceImpl implements UserInfoService, Serializable {
	private static final long serialVersionUID = 1L;

	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	private ResultSet resultSet2 = null;
	private List<Authentication> userList = new LinkedList<Authentication>();

	public synchronized Authentication findLogin(String account)
			throws Exception {

		connect = Clasifiers.getConnection();
		statement = connect.createStatement();
		resultSet = statement
				.executeQuery("select * from authentificationservice");

		writeResultSet(resultSet);
		statement.close();

		int s = userList.size();

		for (int i = 0; i < s; i++) {
			Authentication u = userList.get(i);
			if (account.equals(u.getLoginName())) {
				return u;
			}
		}
		
		new Employee(1, "annonymous", "annonymous", 4 , "example@mail.com", "88888888"); 
		return new Authentication("", "Guest", 0,
				1, 0, 0);
		//return null;
	}

	public synchronized Employee findEmployee(Authentication u)
			throws Exception {

		connect = Clasifiers.getConnection();
		statement = connect.createStatement();
		resultSet2 = statement
				.executeQuery("SELECT employee.Name, employee.RoleId, employee.Surname, employee.EmailAddress,employee.TelephoneNumber FROM employee WHERE employee.id="
						+ u.getEmployeeId());

		while (resultSet2.next()) {
			return Employee.clone(new Employee(u.getEmployeeId(), resultSet2
					.getString("Name"), resultSet2.getString("surName"),
					resultSet2.getInt("RoleId"), resultSet2
							.getString("EmailAddress"), resultSet2
							.getString("TelephoneNumber")));
		}

		return null;
	}
	
	public synchronized Delegate findDelegate(Authentication u)
			throws Exception {

		connect = Clasifiers.getConnection();
		statement = connect.createStatement();
		resultSet2 = statement
				.executeQuery("SELECT delegates.Id, delegates.ClientId, delegates.Name, delegates.Surname, delegates.Telephone , delegates.Mail, delegates.Active FROM delegates WHERE delegates.Id="
						+ u.getDelegateId());

		while (resultSet2.next()) {
			System.out.print(u.getDelegateId()+" \n");
					System.out.print(resultSet2.getInt("ClientId")+"\n");
					System.out.print(resultSet.getString("Name")+"\n");
					System.out.print(resultSet.getString("Surname")+"\n");
					System.out.print(resultSet.getString("Telephone")+"\n");
					System.out.print(resultSet.getString("Mail")+"\n");
					System.out.print(resultSet.getBoolean("Active")+"\n");
					
			return Delegate.clone(new Delegate(u.getDelegateId(), resultSet2.getInt("ClientId"), resultSet
					.getString("Name"), resultSet.getString("Surname"), resultSet.getString("Telephone"), 
					resultSet.getString("Mail"), resultSet.getBoolean("Active"), 5));
		}
		return null;
	}

	public synchronized Client findClient(Authentication u) throws Exception {

		connect = Clasifiers.getConnection();
		statement = connect.createStatement();
		resultSet2 = statement
				.executeQuery("SELECT client.Name, client.Address, client.Code, client.Mails, client.Phones FROM client WHERE client.id="
						+ u.getClientId());

		while (resultSet2.next()) {
			return Client.clone(new Client(u.getClientId(), resultSet2
					.getString("Name"), resultSet2.getString("Code"),
					resultSet2.getString("Address"), getMail(resultSet2
							.getString("Mails")), getPhone(resultSet2
							.getString("Phones")), 6));
		}
		statement.close();

		return null;
	}
	
	public synchronized Client findClientByID(int id) throws Exception {

		connect = Clasifiers.getConnection();
		statement = connect.createStatement();
		resultSet2 = statement
				.executeQuery("SELECT client.Name, client.Address, client.Code, client.Mails, client.Phones FROM client WHERE client.id="
						+ id);

		while (resultSet2.next()) {
			return Client.clone(new Client(id, resultSet2
					.getString("Name"), resultSet2.getString("Code"),
					resultSet2.getString("Address"), getMail(resultSet2
							.getString("Mails")), getPhone(resultSet2
							.getString("Phones")), 6));
		}
		statement.close();

		return null;
	}

	public Vector<String> getPhone(String Phones) throws Exception {
		
		Vector<String> phoneVector = new Vector<String>();
		StringBuilder tmp = new StringBuilder();

		for (int i = 0; i < Phones.length(); i++) {
			if (Phones.charAt(i) != ';') {
				tmp.append(Phones.charAt(i));
			} else {
				phoneVector.add(tmp.toString());
				tmp.delete(0, i - 1);
			}
		}

		return phoneVector;
	}

	public Vector<String> getMail(String Mails) throws Exception {
		
		Vector<String> eMailVector = new Vector<String>();
		StringBuilder tmp = new StringBuilder();

		for (int i = 0; i < Mails.length(); i++) {
			if (Mails.charAt(i) != ';') {
				tmp.append(Mails.charAt(i));
			} else {
				eMailVector.add(tmp.toString());
				tmp.delete(0, i - 1);
			}
		}

		return eMailVector;
	}

	private void writeResultSet(ResultSet resultSet) throws SQLException {

		while (resultSet.next()) {
			userList.add(new Authentication(resultSet.getString("Password"),
					resultSet.getString("UserName"), resultSet.getInt("Id"),
					resultSet.getInt("EmployeeId"), resultSet
							.getInt("ClientId"), resultSet
							.getInt("DelegateId")));
		}
	}

	public String rightsValue(int index) {
		
		List<String> RightsValues = Arrays.asList("neapibreztas",
				"Inžinierius", "Administratorius", "Vadovas", "Svečias", "Atstovas", "Klientas");
		
		return (RightsValues).get(index);
	}
}