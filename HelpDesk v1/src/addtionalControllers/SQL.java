package addtionalControllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.Clasifiers;


public class SQL {

	// Sustri SQL metodai :D

	public String sql(String SQL, int Collumn) throws ClassNotFoundException {
		
		String received = "";
		PreparedStatement stmt;
		ResultSet rs;
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = Clasifiers.getConnection();
			// Do something with the Connection
			stmt = conn.prepareStatement(SQL);
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				received = rs.getString(Collumn);
			}

			return received;

		} catch (Exception ex) {
			ex.getCause();

		}
		
		return received;

	}
}
