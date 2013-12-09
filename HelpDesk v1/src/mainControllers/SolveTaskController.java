package mainControllers;

import model.Authentication;
import model.Task;

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

import services.AuthenticationService;
import services.AuthenticationServiceImpl;
import services.EmployeeService;
import addtionalControllers.TaskStatements;
import addtionalControllers.TaskViewController;

public class SolveTaskController extends SearchController {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Task solvingTask;
	
	

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
		AuthenticationService authService = new AuthenticationServiceImpl();
		Authentication cre = authService.getUserCredential();

		String solvingTask = solveTaskWindow.getAttribute("taskId").toString();
	
		CommentsController comments = new CommentsController();
		System.out.println("comment" + comment.getValue());
		
		TaskStatements ts = new TaskStatements();
		ts.changeStatus(solvingTask, status.getValue());
		ts.changeAssignee(solvingTask, priskirti.getValue());
		ts.setSolution(solvingTask, comment.getValue(), cre.getEmployeeId());
		Event closeEvent = new Event("onClose", this.getSelf(), null);
		Events.postEvent(closeEvent);
		Executions.getCurrent().sendRedirect("");

	}

	public Task getSolvingTask() {
		return solvingTask;
	}

	public void setSolvingTask(Task solvingTask) {
		this.solvingTask = solvingTask;
	}
}
