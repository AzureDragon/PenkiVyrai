package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import model.Clasifiers;
import model.Task;


public class AppelationServiceImpl implements AppelationService {

	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	// data model
	private LinkedList<Task> taskList ;

	public LinkedList<Task> getTaskList() {
		return taskList;
	}

	public void setTaskList(LinkedList<Task> taskList) {
		this.taskList = taskList;
	}
	public AppelationServiceImpl(String taskStatement) throws Exception {
		taskList = new LinkedList<Task>();
		Class.forName("com.mysql.jdbc.Driver");
		connect = Clasifiers.getConnection();
		statement = connect.createStatement();
		resultSet = statement.executeQuery(taskStatement);
		writeResultSet(resultSet);


	}
	
	// initialize book data
	public AppelationServiceImpl(String taskStatement, int currentPage) throws Exception {
		taskList = new LinkedList<Task>();
		Class.forName("com.mysql.jdbc.Driver");
		connect = Clasifiers.getConnection();
		statement = connect.createStatement();
		resultSet = statement.executeQuery(taskStatement);
		writeResultSetByPaging(resultSet, currentPage);


	}

	public AppelationServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	private void writeResultSetByPaging(ResultSet resultSet, int currentPage) throws Exception {
		// ResultSet is initially before the first data set
		int i=15*currentPage;
		int tmp, counter=0;
		if (currentPage == 0) { tmp=1;
		} else tmp = currentPage+1;
		
		
		
		while (resultSet.next()) {
			counter++;
			if (counter<i){
				taskList.add(new Task());
			} else {		
			if (i<=(15 * tmp)){
		
			resultSet.getRow();
			taskList.add(new Task(resultSet.getInt("TaskId"), resultSet
					.getString("Subject"), resultSet.getString("Description"),
					Clasifiers.getTypeName(resultSet.getInt("Type")),
					Clasifiers.getStatusName(resultSet.getInt("Status")),
					Clasifiers.getClientNameById(resultSet.getInt("ClientID")),
					resultSet.getDate("Registered"), resultSet
							.getInt("ReceiverId"), resultSet
							.getString("SolveUntil"), resultSet.getInt("AssigneeId")));
			i++;
			}
			
			else {
				taskList.add(new Task());
			}
			}
		}
	}
	private void writeResultSet(ResultSet resultSet) throws Exception {
		// ResultSet is initially before the first data set
	
		
		
		while (resultSet.next()) {
		
			
		
			resultSet.getRow();
			taskList.add(new Task(resultSet.getInt("TaskId"), resultSet
					.getString("Subject"), resultSet.getString("Description"),
					Clasifiers.getTypeName(resultSet.getInt("Type")),
					Clasifiers.getStatusName(resultSet.getInt("Status")),
					Clasifiers.getClientNameById(resultSet.getInt("ClientID")),
					resultSet.getDate("Registered"), resultSet
							.getInt("ReceiverId"), resultSet
							.getString("SolveUntil"), resultSet.getInt("AssigneeId")));
	
			}
			
		
			}
		

	public List<Task> findAll() {
		return taskList;
	}

	public List<Task> search(String keyword) throws Exception {

		PreparedStatement stmt;
		ResultSet rs;
		Connection conn;

		List<Task> result = new LinkedList<Task>();
		if (keyword == null || "".equals(keyword)) {
			result = taskList;
		} else {
			Class.forName("com.mysql.jdbc.Driver");
			conn = Clasifiers.getConnection();
			// Do something with the Connection
			stmt = conn
					.prepareStatement("select * from task t JOIN taskAssignments ON t.Id = taskAssignments.TaskId WHERE Subject LIKE '%"
							+ keyword + "%' and taskAssignments.Id = (SELECT MAX(taskAssignments.Id) FROM taskAssignments WHERE taskAssignments.TaskId = t.Id) ORDER BY t.Id DESC");
			rs = stmt.executeQuery();
			while (rs.next()) {
				result.add(new Task(rs.getInt("Id"), rs.getString("Subject"),
						rs.getString("Description"), Clasifiers.getTypeName(rs
								.getInt("Type")), Clasifiers.getStatusName(rs
								.getInt("Status")), Clasifiers
								.getClientNameById(rs.getInt("ClientID")), rs
								.getDate("Registered"),
						rs.getInt("ReceiverId"), rs.getString("SolveUntil"), rs.getInt("AssigneeId")));
			}
		}

		return result;
	}

	public void startTaskProgress(String taskId) throws Exception {
		

	}

	public List<Task> search(String keyword, int employeeId) throws Exception {

		PreparedStatement stmt;
		ResultSet rs;
		Connection conn;

		List<Task> result = new LinkedList<Task>();
		if (keyword == null || "".equals(keyword)) {
			result = taskList;
		} else {
			Class.forName("com.mysql.jdbc.Driver");
			conn = Clasifiers.getConnection();
			// Do something with the Connection
			stmt = conn
					.prepareStatement("select * from task t JOIN taskAssignments ON t.Id = taskAssignments.TaskId WHERE ReceiverId="+employeeId+" AND taskAssignments.Id = (SELECT MAX(taskAssignments.Id) FROM taskAssignments WHERE taskAssignments.TaskId = t.Id) ORDER BY t.Id DESC");
			rs = stmt.executeQuery();
			while (rs.next()) {
				result.add(new Task(rs.getInt("Id"), rs.getString("Subject"),
						rs.getString("Description"), Clasifiers.getTypeName(rs
								.getInt("Type")), Clasifiers.getStatusName(rs
								.getInt("Status")), Clasifiers
								.getClientNameById(rs.getInt("ClientID")), rs
								.getDate("Registered"),
						rs.getInt("ReceiverId"), rs.getString("SolveUntil"), rs.getInt("AssigneeId")));
			}
		}

		return result;
	}

	public List<Task> searchClientTasks(String keyword, int clientId) throws Exception {

		PreparedStatement stmt;
		ResultSet rs;
		Connection conn;

		List<Task> result = new LinkedList<Task>();
		if (keyword == null || "".equals(keyword)) {
			result = taskList;
		} else {
			Class.forName("com.mysql.jdbc.Driver");
			conn = Clasifiers.getConnection();
			// Do something with the Connection
			stmt = conn
					.prepareStatement("select * from task t JOIN taskAssignments ON t.Id = taskAssignments.TaskId WHERE t.ClientId="+clientId+" AND taskAssignments.Id = (SELECT MAX(taskAssignments.Id) FROM taskAssignments WHERE taskAssignments.TaskId = t.Id) ORDER BY t.Id DESC");
			rs = stmt.executeQuery();
			while (rs.next()) {
			
				result.add(new Task(rs.getInt("Id"), rs.getString("Subject"),
						rs.getString("Description"), Clasifiers.getTypeName(rs
								.getInt("Type")), Clasifiers.getStatusName(rs
								.getInt("Status")), Clasifiers
								.getClientNameById(rs.getInt("ClientID")), rs
								.getDate("Registered"),
						rs.getInt("ReceiverId"), rs.getString("SolveUntil"), rs.getInt("AssigneeId")));
			}
		}

		return result;
	}
}
