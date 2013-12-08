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
	private LinkedList<Task> taskList = new LinkedList<Task>();

	// initialize book data
	public AppelationServiceImpl() throws Exception {

		Class.forName("com.mysql.jdbc.Driver");
		connect = Clasifiers.getConnection();
		statement = connect.createStatement();
		resultSet = statement.executeQuery("select * from task");
		writeResultSet(resultSet);

	}

	private void writeResultSet(ResultSet resultSet) throws Exception {
		// ResultSet is initially before the first data set
		while (resultSet.next()) {
			System.out.println(resultSet.getInt("Id")
					+ resultSet.getString("Subject")
					+ resultSet.getString("Description")
					+ resultSet.getString("Type")
					+ resultSet.getString("Status")
					+ resultSet.getInt("ClientID")
					+ resultSet.getDate("Registered")
					+ resultSet.getInt("ReceiverId")
					+ resultSet.getDate("SolveUntil"));

			taskList.add(new Task(resultSet.getInt("Id"), resultSet
					.getString("Subject"), resultSet.getString("Description"),
					Clasifiers.getTypeName(resultSet.getInt("Type")),
					Clasifiers.getStatusName(resultSet.getInt("Status")),
					Clasifiers.getClientNameById(resultSet.getInt("ClientID")),
					resultSet.getDate("Registered"), resultSet
							.getInt("ReceiverId"), resultSet
							.getDate("SolveUntil"), resultSet.getInt("AssigneeId")));

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
					.prepareStatement("SELECT * FROM task WHERE Subject LIKE '%"
							+ keyword + "%'");
			rs = stmt.executeQuery();
			while (rs.next()) {
				result.add(new Task(rs.getInt("Id"), rs.getString("Subject"),
						rs.getString("Description"), Clasifiers.getTypeName(rs
								.getInt("Type")), Clasifiers.getStatusName(rs
								.getInt("Status")), Clasifiers
								.getClientNameById(rs.getInt("ClientID")), rs
								.getDate("Registered"),
						rs.getInt("ReceiverId"), rs.getDate("SolveUntil"), rs.getInt("AssigneeId")));
			}
		}

		return result;
	}

	public void startTaskProgress(String taskId) throws Exception {
		Connection connect = null;
		PreparedStatement preparedStatement = null;
		Class.forName("com.mysql.jdbc.Driver");
		connect = Clasifiers.getConnection();
		preparedStatement = connect.prepareStatement("UPDATE task SET Status ="
				+ 2 + " WHERE ID=" + taskId + ";");
		preparedStatement.executeUpdate();

	}

}
