package mainControllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import services.AuthenticationService;
import services.AuthenticationServiceImpl;

import model.Authentication;
import model.Clasifiers;
import model.Comment;

	public class CommentsController {
		
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;

	public boolean addComment(String taskId, String comment) throws Exception {
		
		Date date = new Date();
		SimpleDateFormat todayDate = new SimpleDateFormat("yyyy/MM/dd HH:mm");

		AuthenticationService authService = new AuthenticationServiceImpl();
		Authentication cre = authService.getUserCredential();

		Class.forName("com.mysql.jdbc.Driver");
		connect = Clasifiers.getConnection();
		preparedStatement = connect
				.prepareStatement("INSERT INTO comment values (" + "default,"
						+ " '" + taskId + "'," + " " + " '"
						+ cre.getEmployeeId() + "'," + " '" + comment + "',"
						+ " '" + todayDate.format(date) + "');");

		preparedStatement.executeUpdate();

		return true;

	}

	public List<Comment> getTaskComments(String taskId) throws Exception {
		
		ResultSet resultSet;
		Class.forName("com.mysql.jdbc.Driver");
		connect = Clasifiers.getConnection();
		statement = connect.createStatement();
		resultSet = statement
				.executeQuery("SELECT * from comment WHERE TaskId='"
						+ taskId + "'");

		return writeResultSet(resultSet);

	}

	private List<Comment> writeResultSet(ResultSet resultSet) throws Exception {

		List<Comment> commentsList = new LinkedList<Comment>();
		while (resultSet.next()) {

			commentsList.add(new Comment(Clasifiers
					.getEmployeeNameById(resultSet.getInt("EmployeeId")),
					resultSet.getDate("TimeOfComment"), resultSet
							.getString("CommentText")));

		}
		return commentsList;
	}

}
