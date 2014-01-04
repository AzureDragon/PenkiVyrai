package mainControllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import model.Clasifiers;
import model.Client;
import model.Service;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

public class ServiceSearchController extends SelectorComposer<Component> {

	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	public List<Service> serviceList;

	public ServiceSearchController() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		connect = Clasifiers.getConnection();
		statement = connect.createStatement();
		resultSet = statement.executeQuery("select * from service");
		writeResultSet(resultSet);
	}
	int id;
	String name;
	String serviceCode;
	String description;
	int incidentTime;
	int requestTime;
	private void writeResultSet(ResultSet resultSet) throws Exception {
		serviceList = new LinkedList<Service>();
		while (resultSet.next()) {
		
			serviceList.add(new Service(
					resultSet.getString("Name"),
					resultSet.getString("ServiceCode"), 
					resultSet.getInt("IncidentTime"), 
					resultSet.getInt("RequestTime")));
		}
	}

	public List<Service> search(String keyword) {
		List<Service> result = new LinkedList<Service>();
		if (keyword == null || "".equals(keyword)) {
			result = serviceList;
		} else {
			for (Service c : serviceList) {
				System.out.println(c);
				if (c.getName().toLowerCase().contains(keyword.toLowerCase())) {
					result.add(c);
				}
			}
		}
		return result;
	}

	public List<Service> getServiceList() {
		return serviceList;
	}
	@Wire
	private Listbox serviceListbox;
	@Wire
	private Textbox keywordBox;
	
	@Listen("onClick = #searchButton")
	public void search() throws Exception{
		
		serviceListbox.setModel(new ListModelList<Service>(getServiceList()));
		String keyword = keywordBox.getValue();
		List<Service> result = search(keyword);
		serviceListbox.setModel(new ListModelList<Service>(result));
	}
	
	@Listen("onSelect = #contractsListbox")
	public void openClientView() throws Exception {
		
			Client selectedService =  serviceListbox.getSelectedItem().getValue();
			Executions.sendRedirect("service.zul?id=" + selectedService.getId());

	}

}
