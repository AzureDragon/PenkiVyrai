package pie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Clasifiers;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.zkoss.zul.PieModel;
import org.zkoss.zul.SimplePieModel;


public class PieChartData {

	private static Connection connect = null;
	private static PreparedStatement preparedStatement = null;
	
	public static PieModel getModel() throws Exception{
		
		PieModel model = new SimplePieModel();	
		
		int total = 0;
		
		Class.forName("com.mysql.jdbc.Driver");
		connect = Clasifiers.getConnection();
		
		
		preparedStatement = connect
				.prepareStatement("SELECT COUNT(id) FROM task;");
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			total = rs.getInt("COUNT(id)");
		}
		
	//PC.setTotal(total);
		
		preparedStatement = connect
				.prepareStatement("SELECT COUNT(id) FROM task Where Status=3;");
		
	    rs = preparedStatement.executeQuery();
		while (rs.next()) {
			model.setValue("Išspręstų kreipinių skaičius",
					rs.getInt("COUNT(id)") / (double)total);
		}
	//	PC.setValue1(rs.getInt("COUNT(id)"));
	preparedStatement = connect
			.prepareStatement("SELECT COUNT(id) FROM task Where Status=1;");

	rs = preparedStatement.executeQuery();
	while (rs.next()) {
		model.setValue("Užregistruotų kreipinių skaičius",
				rs.getInt("COUNT(id)") / (double)total);
	}
	//PC.setValue2(rs.getInt("COUNT(id)"));
	preparedStatement = connect
			.prepareStatement("SELECT COUNT(id) FROM task Where Status=5;");

	rs = preparedStatement.executeQuery();
	while (rs.next()) {
		model.setValue("Atsisakytų spręsti kreipinių skaičius",
				rs.getInt("COUNT(id)") / (double)total);
	}
	//PC.setValue3(rs.getInt("COUNT(id)"));
	preparedStatement = connect
			.prepareStatement("SELECT COUNT(id) FROM task Where Status=2;");

	rs = preparedStatement.executeQuery();
	while (rs.next()) {
		model.setValue("Sprendžiamų kreipinių skaičius",
				rs.getInt("COUNT(id)") / (double)total);
	}
	//PC.setValue4(rs.getInt("COUNT(id)"));
	preparedStatement = connect
			.prepareStatement("SELECT COUNT(id) FROM task Where Status=4;");

	rs = preparedStatement.executeQuery();
	while (rs.next()) {
		model.setValue("Grąžintų neišspręsti kreipinių skaičius",
				rs.getInt("COUNT(id)") / (double)total);
	}
	//PC.setValue5(rs.getInt("COUNT(id)"));

	return model;
}
}
