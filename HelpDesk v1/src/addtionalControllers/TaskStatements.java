package addtionalControllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import services.AuthenticationService;
import services.AuthenticationServiceImpl;

import model.Authentication;
import model.Clasifiers;

public class TaskStatements {

	// Task
	public String getTaskName(String Id) throws Exception {

		String tekstas = "";
		tekstas = sql("SELECT Subject FROM task WHERE id=" + Id + ";");

		return tekstas;
	}
	
	public String getTaskSolution(String taskId) throws Exception {

		String tekstas = "";
		tekstas = sql("SELECT Solution task t JOIN taskAssignments ON t.Id = taskAssignments.TaskId WHERE id=" + taskId + " and taskAssignments.Id = (SELECT MAX(taskAssignments.Id) FROM taskAssignments WHERE taskAssignments.TaskId = t.Id;");

		return tekstas;

	}
	
	public String getTaskStatus(String Id) throws Exception {

		String tekstas = "";
		tekstas = sql("SELECT Status FROM task WHERE id=" + Id + ";");

		return tekstas;
	}
	public String getTaskregisteredDate(String Id) throws Exception {

		String tekstas = "";
		tekstas = sql("SELECT Registered FROM task WHERE id=" + Id + ";");

		return tekstas;
	}
	public String getTaskType(String Id) throws Exception {

		String tekstas = "";
		tekstas = sql("SELECT Type FROM task WHERE id=" + Id + ";");

		return tekstas;
	}
	public String getTaskSolveUntil(String Id) throws Exception {

		String tekstas = "";
		tekstas = sql("SELECT SolveUntil FROM task WHERE id=" + Id + ";");

		return tekstas;
	}
	public String getTaskAssigneeId(String Id) throws Exception {

		String tekstas = "";
		tekstas = sql("SELECT AssigneeId FROM task t JOIN taskAssignments ON t.Id = taskAssignments.TaskId WHERE t.id=" + Id + " and taskAssignments.Id = (SELECT MAX(taskAssignments.Id) FROM taskAssignments WHERE taskAssignments.TaskId = t.Id);");

		return tekstas;
	}
	
	public String getTaskReceiverId(String Id) throws Exception {

		String tekstas = "";
		tekstas = sql("SELECT ReceiverId FROM task t JOIN taskAssignments ON t.Id = taskAssignments.TaskId WHERE t.id=" + Id + " and taskAssignments.Id = (SELECT MAX(taskAssignments.Id) FROM taskAssignments WHERE taskAssignments.TaskId = t.Id);");
		return tekstas;
	}
	public String getTaskClientId(String Id) throws Exception {

		String tekstas = "";
		tekstas = sql("SELECT ClientID FROM task WHERE id=" + Id + ";");
		return tekstas;
	}


	public String getTaskDescription(String Id) throws Exception {

		String tekstas = "";
		tekstas = sql("SELECT Description FROM task WHERE id=" + Id + ";");

		return tekstas;
	}



	// Service
	public String getServiceName(int Id) throws Exception {

		String tekstas = "";
		tekstas = sql("SELECT Name FROM service WHERE id=" + Id + ";");

		return tekstas;
	}

	public String getServiceDescription(int Id) throws Exception {

		String tekstas = "";
		tekstas = sql("SELECT Description FROM service WHERE id=" + Id + ";");

		return tekstas;
	}

	// Employee
	public String getEmployeeId(String name, String surname) throws Exception {

		String id = "0";
		System.out.println("SELECT Id FROM employee WHERE Name='" + name
				+ "' AND Surname='" + surname + "';");
		id = sql("SELECT Id FROM employee WHERE Name='" + name
				+ "' AND Surname='" + surname + "';");

		return id;
	}

	public String getEmployeeName(int Id) throws Exception {

		String tekstas = "";
		tekstas = sql("SELECT Name FROM employee WHERE id=" + Id + ";");

		return tekstas;
	}

	public String getEmployeeSurname(int Id) throws Exception {

		String tektas = "";
		tektas = sql("SELECT Surname FROM employee WHERE id=" + Id + ";");

		return tektas;
	}

	public String getEmployeeElAddress(int Id) throws Exception {

		String tektas = "";
		tektas = sql("SELECT EmailAddress FROM employee WHERE id=" + Id + ";");

		return tektas;
	}

	public String getEmployeeNumber(int Id) throws Exception {

		String tektas = "";
		tektas = sql("SELECT TelephoneNumber FROM employee WHERE id=" + Id
				+ ";");

		return tektas;
	}

	// Comment
	public String getCommentText(int Id) throws Exception {

		String tektas = "";
		tektas = sql("SELECT CommentText FROM comment WHERE id=" + Id + ";");

		return tektas;
	}

	// Client
	public String getClientName(int Id) throws Exception {

		String tektas = "";
		tektas = sql("SELECT Name FROM client WHERE id=" + Id + ";");

		return tektas;
	}

	public String getClientIdByName(String name) throws NumberFormatException,
			Exception {

		String id = sql("SELECT ID FROM client WHERE name='" + name + "';");

		return id;
	}

	public String getClientCode(int Id) throws Exception {

		String tektas = "";
		tektas = sql("SELECT Code FROM client WHERE id=" + Id + ";");

		return tektas;
	}

	public String getClientAddress(int Id) throws Exception {

		String tektas = "";
		tektas = sql("SELECT Address FROM client WHERE id=" + Id + ";");

		return tektas;
	}

	public String sql(String SQL) throws Exception {

		String received = "";
		PreparedStatement stmt;
		ResultSet rs;
		Connection conn;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = Clasifiers.getConnection();
			// Do something with the Connection
			stmt = conn.prepareStatement(SQL);
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				received = rs.getString(1);
				System.out.println("Receivedbb  " + received);
			}
			return received;

		} catch (Exception ex) {
			throw ex;
		}
	}

	public void changeAssignee(String taskId, String name) throws Exception {

		Connection connect = null;
		PreparedStatement preparedStatement = null;
		AuthenticationService authService = new AuthenticationServiceImpl();
		Date date = new Date();
		SimpleDateFormat formatedDate = new SimpleDateFormat("yyyy-MM-dd");
		Authentication cre = authService.getUserCredential();
		TaskStatements ts = new TaskStatements();
		String surname = name.substring(name.lastIndexOf(" ") + 1);
		name = name.substring(0, name.lastIndexOf(" ") - 1);
		
		Class.forName("com.mysql.jdbc.Driver");
		connect = Clasifiers.getConnection();
		
		preparedStatement = connect
				.prepareStatement("INSERT INTO taskAssignments values ("
						+ "default," 
						+ " '"+ taskId+ "',"
						+ " '"+ cre.getEmployeeId() + "',"
						+ " '"+ ts.getEmployeeId(name, surname)+ "',"
						+ " '"+ formatedDate.format(date)+ "',"
						+ " '"+ "0000-00-00"+ "',"
						+ " null,"
						+ "'2',"
						+ " null);");
		preparedStatement.executeUpdate();

	}
	
	public void setSolution(String taskId, String solution, String status) throws Exception {

		Connection connect = null;
		PreparedStatement preparedStatement = null;
		Class.forName("com.mysql.jdbc.Driver");
		connect = Clasifiers.getConnection();
		preparedStatement = connect
				.prepareStatement("UPDATE taskAssignments SET Solution ='"
						+ solution
						+ "', Status="+ Clasifiers.getStatusCode(status) +" WHERE TaskId =" + taskId + "  and Id = (SELECT * FROM (SELECT MAX(taskAssignments.Id) FROM taskAssignments WHERE taskAssignments.TaskId = "+ taskId +") AS test);");
		preparedStatement.executeUpdate();
		preparedStatement = connect.prepareStatement("UPDATE task SET Status ="
				+ Clasifiers.getStatusCode(status) + " WHERE Id ="
				+ taskId + ";");
		preparedStatement.executeUpdate();

	}

	public void changeStatus(String taskId, String status) throws Exception {

		Connection connect = null;
		PreparedStatement preparedStatement = null;

		Class.forName("com.mysql.jdbc.Driver");
		connect = Clasifiers.getConnection();
		preparedStatement = connect
				.prepareStatement("UPDATE taskAssignments SET Status='"+ Clasifiers.getStatusCode(status)+"' WHERE TaskId =" + taskId + " and Id = (SELECT * FROM (SELECT MAX(taskAssignments.Id) FROM taskAssignments WHERE taskAssignments.TaskId = "+ taskId +") AS test);");
		preparedStatement.executeUpdate();
		preparedStatement = connect.prepareStatement("UPDATE task SET Status ="
				+ Clasifiers.getStatusCode(status) + " WHERE Id ="
				+ taskId + ";");
		preparedStatement.executeUpdate();



	}
	


	public static void main(String args[]) throws Exception {

		TaskStatements tsStatements = new TaskStatements();
		System.out.println("rez "
				+ tsStatements.getEmployeeId("Dovydas", "Petkus"));
	}
}
