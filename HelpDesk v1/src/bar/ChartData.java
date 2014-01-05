package bar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;

import model.Clasifiers;

import org.zkoss.zul.CategoryModel;
import org.zkoss.zul.SimpleCategoryModel;

public class ChartData {

	private static Connection connect = null;
	private static PreparedStatement preparedStatement = null;
	
	public static CategoryModel getModel() throws Exception{
		
		
		Class.forName("com.mysql.jdbc.Driver");
		connect = Clasifiers.getConnection();
		CategoryModel model = new SimpleCategoryModel();
		
		preparedStatement = connect
				.prepareStatement("SELECT name,surname,mamamyje.ReceiverId,mamamyje.count FROM employee E JOIN (SELECT * FROM (select ReceiverId, count(*) as count from task t JOIN taskAssignments ON t.Id = taskAssignments.TaskId WHERE t.status=3 AND taskAssignments.Id = (SELECT MAX(taskAssignments.Id) FROM taskAssignments WHERE taskAssignments.TaskId = t.Id) group by ReceiverId ORDER BY t.Id DESC) AS aleliuja ORDER BY aleliuja.count DESC LIMIT 5) AS mamamyje ON E.Id = ReceiverId;");
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			model.setValue("Išspręsta kreipinių", rs.getString("name"), rs.getInt("count"));
		}
		
		return model;
	}
}
