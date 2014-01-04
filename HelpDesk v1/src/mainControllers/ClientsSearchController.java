package mainControllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import model.Clasifiers;
import model.Client;


public class ClientsSearchController {

	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	public List<Client> clientList;

	public ClientsSearchController() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		connect = Clasifiers.getConnection();
		statement = connect.createStatement();
		resultSet = statement.executeQuery("select * from client");
		writeResultSet(resultSet);
	}

	private void writeResultSet(ResultSet resultSet) throws Exception {
		clientList = new LinkedList<Client>();
		  while (resultSet.next()) {
			  
		    	System.out.println(resultSet.getInt("Id")+ resultSet
						.getString("Name") + resultSet. getString("Code")+ resultSet.getString("Address"));
		    	
		    	clientList.add(new Client(
		    			resultSet.getInt("Id"),
		    			resultSet.getString("Name"),
		    			resultSet. getString("Code"),
		    			resultSet.getString("Address"), null, null, 2));
		    }
	}
	public List<Client> search(String keyword){
		List<Client> result = new LinkedList<Client>();
		if (keyword==null || "".equals(keyword)){
			result = clientList;
		}else{
			for (Client c: clientList){
				System.out.println(c);
				if (c.getName().toLowerCase().contains(keyword.toLowerCase())){
					result.add(c);
				}
			}
		}
		return result;
	}
	public List<Client> getClientList() {
		return  clientList;
	}

}
