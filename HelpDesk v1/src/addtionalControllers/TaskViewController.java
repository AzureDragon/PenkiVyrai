package addtionalControllers;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;

import mainControllers.CommentsController;
import model.Clasifiers;
import model.Comment;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import services.AppelationServiceImpl;
import services.EmployeeService;

public class TaskViewController extends SelectorComposer<Component> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	EventQueue eq;
	@Wire
	Label subjectLabel;
	@Wire
	Label descriptionLabel;
	@Wire
	Label solveUntilLabel;
	@Wire
	Label registeredLabel;
	@Wire
	Label statusLabel;
	@Wire
	Label typeLabel;
	@Wire
	Label assignedLabel;
	@Wire
	Label clientLabel, noComments;
	@Wire
	Grid commentsGrid;
	@Wire
	Tab taskComments;
	@Wire
	public Label invisibleLabelId;
	@Wire
	Button addComment;
	@Wire
	Window commentPopup;
	@Wire
	Button addCommentText;
	@Wire
	Textbox commentText;
	@Wire
	Menuitem startProgress;
	@Wire
	Menuitem solve;
	@Wire
	Button solveButton;
	@Wire
	Combobox assignEmployeeCombobox;
	@Wire
	Label solution;
	@Wire
	Combobox status;
	@Wire
	Popup taskSolved;
	@Wire
	Label priskirtiLabel;
	@Wire
	Tab solutionInfo;
	@Wire
	Label solutionAuthor;
	@Wire
	Label createdLabel;
	@Wire
	Label isSolution; //Naudojamas, kad parayti sprendimą pateikė, arba spredimo nėra

	CommentsController commentsController = new CommentsController();

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		Clients.showBusy("Vykdoma");
		String id = Executions.getCurrent().getParameter("id");
		invisibleLabelId.setValue(id);
		TaskStatements ts = new TaskStatements();
		subjectLabel.setValue(ts.getTaskName(id));
		descriptionLabel.setValue(ts.getTaskDescription(id));
		solveUntilLabel.setValue(ts.getTaskSolveUntil(id));
		registeredLabel.setValue(ts.getTaskregisteredDate(id));
		statusLabel.setValue(Clasifiers.getStatusName(Integer.parseInt(ts
				.getTaskStatus(id))));
		typeLabel.setValue(Clasifiers.getTypeName(Integer.parseInt(ts
				.getTaskType(id))));
		assignedLabel.setValue(Clasifiers.getEmployeeNameById(Integer
				.parseInt(ts.getTaskReceiverId(id)))
				+ " "
				+ Clasifiers.getEmployeeSurnameById(Integer.parseInt(ts
						.getTaskReceiverId(id))));
		clientLabel.setValue(Clasifiers.getClientNameById(Integer.parseInt(ts
				.getTaskClientId(id))));
		createdLabel.setValue(Clasifiers.getEmployeeNameById(Integer.parseInt(ts.getTaskAssigneeId(id)))+ " "+Clasifiers.getEmployeeSurnameById(Integer.parseInt(ts.getTaskAssigneeId(id))));
		// EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
		// assignEmployeeCombobox.setModel(new ListModelList<>(new
		// ListModelList<Employee>(
		// employeeService.getEmployeeList())));
		showComments();
	
		if (ts.getTaskReceiverId(invisibleLabelId.getValue()) !=null){
		solutionAuthor.setValue(Clasifiers.getEmployeeNameById(Integer
				.parseInt(ts.getTaskReceiverId(invisibleLabelId.getValue())))
				+ " "
				+ Clasifiers.getEmployeeSurnameById(Integer.parseInt(ts
						.getTaskReceiverId(invisibleLabelId.getValue()))));
		
		solution.setValue(ts.getTaskSolution(invisibleLabelId.getValue()));
		} else {
			isSolution.setValue("Kreipinys neišspręstas");
			solution.setVisible(false);
			solutionAuthor.setVisible(false);
			
			
			
		}
		Clients.clearBusy();
	}

	@Listen("onSelect = #comments")
	public void showComments() throws Exception {
		if ((commentsController.getTaskComments(invisibleLabelId.getValue())).isEmpty()){
			noComments.setVisible(true);
			
			
		} else {
		commentsGrid.setModel(new ListModelList<Comment>(commentsController
				.getTaskComments(invisibleLabelId.getValue())));
		noComments.setVisible(false);
		}
	}

	@Listen("onClick = #addComment")
	public void showAddCommentWindow() throws Exception {
		commentPopup.doModal();

	}

	@Listen("onClick = #addCommentText")
	public void addCommentToList() throws Exception {
		System.out.println("Asdasds");
		commentsController.addComment(invisibleLabelId.getValue(),
				commentText.getValue());
		commentText.setValue("");
		commentPopup.setVisible(false);
		showComments();
	}

	@Listen("onClick = #startProgress")
	public void startProgress() throws Exception {

	
		Connection connect = null;
		PreparedStatement preparedStatement = null;
		Class.forName("com.mysql.jdbc.Driver");
		connect = Clasifiers.getConnection();
		preparedStatement = connect.prepareStatement("UPDATE task SET Status ="
				+ 2 + " WHERE ID=" + invisibleLabelId.getValue() + ";");
		preparedStatement.executeUpdate();
		Executions.getCurrent().sendRedirect("");

	}
	
	public EmployeeService darbuotojasService;

	@SuppressWarnings("unchecked")
	@Listen("onClick=#solve")
	public void acceptSolving() throws WrongValueException, Exception {
		@SuppressWarnings("rawtypes")
		HashMap map = new HashMap();
		map.put("TaskId", invisibleLabelId.getValue());
		Window solveTask = (Window) Executions.createComponents(
				"solveTask.zul", null, null);
		solveTask.setMaximizable(true);
		solveTask.setAttribute("taskId", invisibleLabelId.getValue());
		solveTask.doModal();
	}

	@Listen("onSelect=#solutionInfo")
	public void showSolutionInfo() throws Exception {
		
	}

}
