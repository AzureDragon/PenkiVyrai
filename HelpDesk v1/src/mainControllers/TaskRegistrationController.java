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

public class TaskRegistrationController extends
		SelectorComposer<Component> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public EmployeeService employeeService;
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	@SuppressWarnings({ "unused", "rawtypes" })
	private EventQueue eq;
	AuthenticationService authService = new AuthenticationServiceImpl();

	@Wire
	Combobox priskirti;
	@Wire
	Combobox klientas;
	@Wire
	Combobox gavimoBudas;
	@Wire
	Combobox tipas;
	@Wire
	Combobox paslauga;
	@Wire
	Datebox issprestiIkiDateBox;
	@Wire
	Textbox tema;
	@Wire
	Textbox aprasymas;
	@Wire
	Button sukurtiKreipini;

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		AuthenticationService authService = new AuthenticationServiceImpl();
		employeeService = new EmployeeServiceImpl();
		priskirti.setModel(new ListModelList<Employee>(employeeService
				.getEmployeeList()));
		klientas.setModel(new ListModelList<Client>(getClientsList()));
	}

	@Listen("onClick=#sukurtiKreipini")
	public void sukurtiKreipini() throws Exception {

		if (!(tema.getValue().equals("") || aprasymas.getValue().equals("")
				|| klientas.getValue().equals("")
				|| gavimoBudas.getValue().equals("")
				|| tipas.getValue().equals("")
				|| paslauga.getValue().equals("")
				|| priskirti.getValue().equals("") || issprestiIkiDateBox
				.getValue().equals(""))) {
			try {
				Date date = new Date();
				SimpleDateFormat todayDate = new SimpleDateFormat(
						"yyyy/MM/dd HH:mm");
				SimpleDateFormat issprestiIkiDate = new SimpleDateFormat(
						"yyyy/MM/dd HH:mm");
				AuthenticationService authService = new AuthenticationServiceImpl();
				Authentication cre = authService.getUserCredential();
				TaskStatements ts =  new TaskStatements ();
				int ID = ts.getLastTaskId();
				Class.forName("com.mysql.jdbc.Driver");
				connect = Clasifiers.getConnection();
				preparedStatement = connect
						.prepareStatement("INSERT INTO taskAssignments values ("
								+ "default," 
								+ " '"+ ID +"',"
								+ " '"+ cre.getEmployeeId() + "',"
								+ " '"+ Integer.parseInt(Clasifiers.getEmployeeIdByNameAndSurname(priskirti.getValue()))+ "',"
								+ " '"+ todayDate.format(date)+ "',"
								+ " '"+ "0000-00-00"+ "',"
								+ " null,"
								+ "'1',"
								+ " null);");
				
				preparedStatement.executeUpdate();
				preparedStatement = connect
						.prepareStatement("INSERT INTO task values ("
								+ " '"+ ID +"'," 
								+ " '"+ Integer.parseInt(Clasifiers.getClientIdByName(klientas.getValue())) + "',"
								+ "'1'," 
								+ " '"+ Clasifiers.getTypeCode(tipas.getValue()) + "',"
								+ " '"+ todayDate.format(date)+ "',"
								+ " '"+ issprestiIkiDate.format(issprestiIkiDateBox.getValue())+ "',"
								+ " '"+ "0000-00-00"+ "',"
								+ " '"+ 0 + "',"
								+ // Previous task
								" '"+ 0 + "',"
								+ // Service type
								" '"+ 0 + "',"
								+ // Task Evaluation
								" '"+ Clasifiers.getReceiveSourceCode(gavimoBudas.getValue())+ "',"
								+ " '"+ aprasymas.getValue() + "',"
								+ " '"+ tema.getValue()+ "');");
				
				preparedStatement.executeUpdate();
				
				
			
			
			} catch (Exception e) {
				throw e;
			} finally {

				System.out.println("Priskyriau");
				Event closeEvent = new Event("onClose", this.getSelf(), null);
				Events.postEvent(closeEvent);
			}
		} else {
			if (priskirti.getValue().equals("")) {
				priskirti.setErrorMessage("Priskirkite kreipinį darbuotojui");
				priskirti.getErrorMessage();
			}
			if (klientas.getValue().equals("")) {
				klientas.setErrorMessage("Pasirinkite klientą");
				klientas.getErrorMessage();
			}
			if (gavimoBudas.getValue().equals("")) {
				gavimoBudas.setErrorMessage("Pasirinkite gavimo būdą");
				gavimoBudas.getErrorMessage();
			}
			if (tipas.getValue().equals("")) {
				tipas.setErrorMessage("Pasirinkite kreipinio tipą");
				tipas.getErrorMessage();
			}
			if (paslauga.getValue().equals("")) {
				paslauga.setErrorMessage("Pasirinkite paslaugą");
				paslauga.getErrorMessage();
			}
			if (tema.getValue().equals("")) {
				tema.setErrorMessage("Įveskite temą");
				tema.getErrorMessage();
			}
			if (aprasymas.getValue().equals("")) {
				aprasymas.setErrorMessage("Įveskite aprašymą");
				aprasymas.getErrorMessage();
			}
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
