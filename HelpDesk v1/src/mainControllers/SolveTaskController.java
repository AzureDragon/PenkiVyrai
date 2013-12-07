package mainControllers;

import model.Task;

import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Textbox;

import services.*;

import addtionalControllers.TaskStatements;


public class SolveTaskController extends SearchController {
	private static Task solvingTask;
	
	public EmployeeService darbuotojasService;

	@Wire
	Button solveButton;
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
	@Listen ("onClick=#solveButton")
	public void acceptSolving () throws WrongValueException, Exception{
		CommentsController comments = new CommentsController();
		System.out.println("comment" + comment.getValue());
		System.out.println(solvingTask.getId());
		comments.addComment(solvingTask, comment.getValue());
		TaskStatements ts = new TaskStatements();
		ts.changeStatus(solvingTask, status.getValue());
		ts.changeAssignee(solvingTask, priskirti.getValue());
			Event closeEvent = new Event("onClose", this.getSelf(), null);
			Events.postEvent(closeEvent);
		
		
	
		
	}
	
	
	
	
	public Task getSolvingTask() {
		return solvingTask;
	}
	public void setSolvingTask(Task solvingTask) {
		this.solvingTask = solvingTask;
	}
}
