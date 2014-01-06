package mainControllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import model.Authentication;
import model.Clasifiers;
import model.Client;
import model.Employee;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventQueue;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;

import addtionalControllers.TaskStatements;

import services.AuthenticationService;
import services.AuthenticationServiceImpl;

import services.EmployeeService;
import services.EmployeeServiceImpl;



import java.util.LinkedList;
import java.util.List;

public class ContractRegistrationController extends
		SelectorComposer<Component> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	@SuppressWarnings({ "unused", "rawtypes" })
	private EventQueue eq;
	AuthenticationService authService = new AuthenticationServiceImpl();

	@Wire Textbox name, code;
	@Wire Combobox client;
	@Wire Datebox validFrom, validTo;
	@Wire Button createContractButton, cancelContractButton;
	
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		AuthenticationService authService = new AuthenticationServiceImpl();
	
		client.setModel(new ListModelList<Client>(getClientsList()));
		
	}

	@Listen("onClick=#createContractButton")
	public void createContract() throws Exception {

		
			try {
				TaskStatements ts = new TaskStatements();
				Date date = new Date();
				SimpleDateFormat todayDate = new SimpleDateFormat(
						"yyyy/MM/dd HH:mm");
				SimpleDateFormat formatingDate = new SimpleDateFormat(
						"yyyy/MM/dd");
				AuthenticationService authService = new AuthenticationServiceImpl();
				Authentication cre = authService.getUserCredential();
			
				Class.forName("com.mysql.jdbc.Driver");
				connect = Clasifiers.getConnection();
				preparedStatement = connect
						.prepareStatement("INSERT INTO contract values ("
								+ "default," 
								+ " '"+ code.getValue() +"',"
								+ " '"+ name.getValue() + "',"
								+ " '"+ ts.getClientIdByName(client.getValue())+ "',"
								+ " '"+ formatingDate.format(validFrom.getValue())+ "',"
								+ " '"+ formatingDate.format(validTo.getValue())+ "');");
				preparedStatement.executeUpdate();
				
			
				
				
			
			
			} catch (Exception e) {
				throw e;
			} finally {

				
				Event closeEvent = new Event("onClose", this.getSelf(), null);
				Events.postEvent(closeEvent);
			}

		}
	

	public List<Client> getClientsList() throws Exception {
		
		ResultSet resultSet = null;
		connect = Clasifiers.getConnection();
		statement = connect.createStatement();
		resultSet = statement.executeQuery("select * from client");

		return writeResultSet(resultSet);

	}

	private List<Client> writeResultSet(ResultSet resultSet)
			throws SQLException {
		
		List<Client> clientList = new LinkedList<Client>();

		while (resultSet.next()) {

			clientList.add(new Client(resultSet.getInt("Id"), resultSet
					.getString("Name"), resultSet.getString("code"), resultSet
					.getString("Address"), null, null, 2));
			
		}
		return clientList;
	}

}
