package mainControllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import model.Clasifiers;
import model.Employee;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

public class EmployeeSearchController extends SelectorComposer<Component> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	public List<Employee> employeeList;

	public EmployeeSearchController() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		connect = Clasifiers.getConnection();
		statement = connect.createStatement();
		resultSet = statement.executeQuery("select * from employee");
		writeResultSet(resultSet);
	}

	private void writeResultSet(ResultSet resultSet) throws Exception {
		employeeList = new LinkedList<Employee>();
		  while (resultSet.next()) {
			  	    
		     	
		     	
		    	employeeList.add(new Employee(
		    			resultSet.getInt("Id"),
		    			Clasifiers.getRole(resultSet.getInt("RoleId")),
		    			resultSet. getString("Name"),
		    			resultSet.getString("Surname"), Clasifiers.isNull(resultSet.getString("EmailAddress")), Clasifiers.isNull(resultSet.getString("TelephoneNumber"))));
		    }
	}
	public List<Employee> search(String keyword){
		List<Employee> result = new LinkedList<Employee>();
		if (keyword==null || "".equals(keyword)){
			result = employeeList;
		}else{
			for (Employee c: employeeList){
				System.out.println(c);
				if (c.getFirstName().toLowerCase().contains(keyword.toLowerCase())){
					result.add(c);
				}
			}
		}
		return result;
	}
	public List<Employee> getEmployeeList() {
		return  employeeList;
	}

	@Wire
	private Listbox employeeListbox;
	@Wire
	private Textbox keywordBox;
	
	@Listen("onClick = #searchButton")
	public void search() throws Exception{
		
		employeeListbox.setModel(new ListModelList<Employee>(getEmployeeList()));
		String keyword = keywordBox.getValue();
		List<Employee> result = search(keyword);
		employeeListbox.setModel(new ListModelList<Employee>(result));
	}
	
	@Listen("onSelect = #employeeListbox")
	public void openEmployeeView() throws Exception {
		
			Employee selectedEmployee =  employeeListbox.getSelectedItem().getValue();
			Executions.sendRedirect("employee.zul?id=" + selectedEmployee.getId());
			
	}

	
}
