package mainControllers;


import java.sql.Connection;
import java.util.List;

import model.Authentication;
import model.Clasifiers;
import model.Comment;
import model.Task;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;
import org.zkoss.zul.Textbox;

import services.AppelationService;
import services.AppelationServiceImpl;
import services.AuthenticationService;
import services.AuthenticationServiceImpl;
import services.EmployeeService;
import services.EmployeeServiceImpl;
import services.UserInfoService;
import services.UserInfoServiceImpl;

public class SelfServiceController extends SelectorComposer<Component> {
	public Task selected;
	private static final long serialVersionUID = 1L;


	
	@Wire
	private Textbox keywordBox;
	@Wire
	public Listbox clientTasks;
	@Wire
	private Label temaLabel;
	@Wire
	private Label aprasymasLabel;
	@Wire
	private Label tipasLabel;
	@Wire
	private Label dataLabel;
	@Wire
	private Label issprestiIkiLabel;
	@Wire
	private Label reporter;
	@Wire
	private Component detailBox;
	@Wire
	private Label busenaLabel;
	@Wire
	private Label priskirtaLabel;
	@Wire
	Grid commentsGrid;
	@Wire
	Button startProgress;
	@Wire
	Menuitem edit;
	@Wire 
	Menupopup msg;
	@Wire 
	Checkbox showClient, showDelegate;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		
		
	}
	@SuppressWarnings("unused")
	private Connection connect = null;

	public AppelationServiceImpl appelationService;

	@Listen("onClick = #searchButton")
	public void search() throws Exception {
		System.out.println("ohohohoh");
		UserInfoService us = new UserInfoServiceImpl();
		AuthenticationService authService = new AuthenticationServiceImpl();
		Authentication cre = authService.getUserCredential();
		appelationService = new AppelationServiceImpl();
		//String keyword = keywordBox.getValue();
		if (cre.getDelegateId()==0){
			clientTasks.setModel(new ListModelList<Task>(appelationService.searchClientTasks(" ", cre.getClientId())));
				
		} else {
			clientTasks.setModel(new ListModelList<Task>(appelationService.searchClientTasks(" ", us.findDelegate(cre).getClientID())));
		}
		
		
	}

	

	@Listen("onClick = #connectButton")
	public void connectToDatabase() throws Exception {
		
		Class.forName("com.mysql.jdbc.Driver");
		// Setup the connection with the DB

		connect = Clasifiers.getConnection();
	}

	public Task getSelectedTask() {
		
		return selected;
	}

	@Listen("onSelect = #taskListbox")
	public void showTaskInfo() throws Exception {
		
		showDetail();
		showComments();

	}

	public void showDetail() throws Exception {
		
		detailBox.setVisible(true);
		
			selected = clientTasks.getSelectedItem().getValue();
			temaLabel.setValue(selected.getSubject());
			busenaLabel.setValue(selected.getStatus() + "");
			aprasymasLabel.setValue(selected.getDescription());
			priskirtaLabel.setValue(Clasifiers.getEmployeeNameById(selected
					.getReceiverId())
					+ " "
					+ Clasifiers.getEmployeeSurnameById(selected
							.getReceiverId()));
			tipasLabel.setValue(Clasifiers.getTypeName(selected.getType()));
			dataLabel.setValue(selected.getRegistered().toString());
			issprestiIkiLabel.setValue(selected.getSolveUntil().toString());
			reporter.setValue(Clasifiers.getEmployeeNameById(selected.getAssigneeId()) +" " +Clasifiers.getEmployeeSurnameById(selected.getAssigneeId()));
		}	

	@Listen("onSelect = #comments")
	public void showComments() throws Exception {

		selected = clientTasks.getSelectedItem().getValue();
		CommentsController commentsController = new CommentsController();
		commentsGrid.setModel(new ListModelList<Comment>(commentsController
				.getTaskComments(selected.getId().toString())));

	}

	

	public void setSelected(Task selected) {
		this.selected = selected;
	}
}
