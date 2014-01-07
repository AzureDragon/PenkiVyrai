package mainControllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import model.Clasifiers;
import model.Delegate;
import model.Task;

import org.joda.time.Seconds;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import services.EmployeeService;
import addtionalControllers.TaskStatements;
import addtionalControllers.TaskViewController;

public class SolveTaskController extends SearchController {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Task solvingTask;
	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	

	public EmployeeService darbuotojasService;
	protected TaskViewController ctrl; // overhanded per param
	@Wire
	Button solveButton;
	@Wire
	Window solveTaskWindow;
	@Wire
	Combobox priskirti;
	@Wire
	Textbox comment;
	@Wire
	Combobox status;
	@Wire
	Popup taskSolved;

	@Listen("onClick = #solve")
	public void solveTask() throws Exception {
		
		setSolvingTask((Task) taskListbox.getSelectedItem().getValue());
		System.out.println(solvingTask.getId());
	}

	@Listen("onClick=#solveButton")
	public void acceptSolving() throws WrongValueException, Exception {

		String solvingTask = solveTaskWindow.getAttribute("taskId").toString();
		
		TaskStatements ts = new TaskStatements();
		//ts.changeStatus(solvingTask, status.getValue());
		ts.setSolution(solvingTask, comment.getValue(), status.getValue());
		Event closeEvent = new Event("onClose", this.getSelf(), null);
		Events.postEvent(closeEvent);
		Executions.getCurrent().sendRedirect("");

		connect = Clasifiers.getConnection();
		statement = connect.createStatement();
		resultSet = statement
				.executeQuery("SELECT delegates.Id, delegates.ClientId, delegates.Name, delegates.Surname, delegates.Telephone , delegates.Mail, delegates.Active FROM delegates WHERE delegates.Id="
						+ solveTaskWindow.getAttribute("taskId"));

		Date RegistrationTime = null;
		
		while (resultSet.next()) {					
			RegistrationTime = resultSet.getDate("Registered");
		}
		statement.close();
		mainControllers.IndexController ic = new mainControllers.IndexController();  
		long diff = (RegistrationTime.getTime() - ic.getDate().getTime()) / (3600 * 1000) % 60;
		System.out.print(diff+"\n");
	}

	public Task getSolvingTask() {
		return solvingTask;
	}

	public void setSolvingTask(Task solvingTask) {
		this.solvingTask = solvingTask;
	}
}
