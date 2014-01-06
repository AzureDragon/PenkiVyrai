package mainControllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import model.Authentication;
import model.Clasifiers;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventQueue;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Textbox;

import addtionalControllers.TaskStatements;

import services.AuthenticationService;
import services.AuthenticationServiceImpl;

public class EmployeeRegistrationController extends SelectorComposer<Component>{
	
	
@Wire
Button createEmployee;
@Wire
Textbox name, surname, phone, email;
@Wire
Combobox role;
private Connection connect = null;
private Statement statement = null;
private PreparedStatement preparedStatement = null;
@SuppressWarnings({ "unused", "rawtypes" })
private EventQueue eq;
AuthenticationService authService = new AuthenticationServiceImpl();

@Listen ("onClick=#createEmployee")
public void createEmployee () throws Exception{

	try {
		TaskStatements ts = new TaskStatements();
		Date date = new Date();
		
		AuthenticationService authService = new AuthenticationServiceImpl();
		Authentication cre = authService.getUserCredential();
	
		Class.forName("com.mysql.jdbc.Driver");
		connect = Clasifiers.getConnection();
		preparedStatement = connect
				.prepareStatement("INSERT INTO employee values ("
						+ "default," 
						+ " '"+ Clasifiers.getRoleCode(role.getValue()) +"',"
						+ " '"+ name.getValue() + "',"
						+ " '"+ surname.getValue()+ "',"
						+ " '"+ email.getValue()+"',"
						+ " '"+ phone.getValue()+ "');");
		preparedStatement.executeUpdate();
		Executions.getCurrent().sendRedirect("");
	
		
		
	
	
	} catch (Exception e) {
		throw e;
	} finally {

		
		Event closeEvent = new Event("onClose", this.getSelf(), null);
		Events.postEvent(closeEvent);
	}
}

}
