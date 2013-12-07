package services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import model.Clasifiers;
import model.Employee;
import model.Task;


public class EmployeeServiceImpl implements EmployeeService {

	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	// data model
	private List<Employee> employeeList;

	public EmployeeServiceImpl() {

		try {
			employeeList = new LinkedList<Employee>();
			Class.forName("com.mysql.jdbc.Driver");
			connect = Clasifiers.getConnection();
			statement = connect.createStatement();
			resultSet = statement.executeQuery("select * from employee");
			writeResultSet(resultSet);
		} catch (Exception e) {
			e.getCause();
		} finally {
			close();
		}
	}

	private void close() {

		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {

		}
	}

	private void writeResultSet(ResultSet resultSet) throws SQLException {

		while (resultSet.next()) {

			employeeList.add(new Employee(resultSet.getInt("Id"), resultSet
					.getString("Name"), resultSet.getString("Surname"),
					resultSet.getInt("RoleId"), resultSet
							.getString("EmailAddress"), resultSet
							.getString("TelephoneNumber")));
		}
	}

	@Override
	public List<Task> findAllCompletedAppelation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Task> searchAllEmployee(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void assignEmployee(Employee employee) {
		// TODO Auto-generated method stub
	}

	@Override
	public void changeEmployee(Employee employee) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<Employee> getEmployeeList() {
		// TODO Auto-generated method stub
		return employeeList;
	}
}
