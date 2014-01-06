package mainControllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import model.Clasifiers;
import model.Client;
import model.Contract;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

import addtionalControllers.TaskStatements;

public class ContractsSearchController extends SelectorComposer<Component>{

	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	public List<Contract> contractList;

	public ContractsSearchController() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		connect = Clasifiers.getConnection();
		statement = connect.createStatement();
		resultSet = statement.executeQuery("select * from contract");
		writeResultSet(resultSet);
	}

	private void writeResultSet(ResultSet resultSet) throws Exception {
		contractList = new LinkedList<Contract>();
		TaskStatements ts = new TaskStatements();
		while (resultSet.next()) {
		
			contractList.add(new Contract(
					resultSet.getString("ContractNumber"), resultSet
							.getString("Name"), ts.getClientName(resultSet.getInt("ClientId")),
					resultSet.getDate("BeginTime"), resultSet
							.getDate("EndTime")));
		}
	}

	public List<Contract> search(String keyword) {
		List<Contract> result = new LinkedList<Contract>();
		if (keyword == null || "".equals(keyword)) {
			result = contractList;
		} else {
			for (Contract c : contractList) {
				System.out.println(c);
				if (c.getName().toLowerCase().contains(keyword.toLowerCase())) {
					result.add(c);
				}
			}
		}
		return result;
	}

	public List<Contract> getContractList() {
		return contractList;
	}
	@Wire
	private Listbox contractsListbox;
	@Wire
	private Textbox keywordBox;
	
	@Listen("onClick = #searchButton")
	public void search() throws Exception{
		ContractsSearchController cs = new ContractsSearchController();
		contractsListbox.setModel(new ListModelList<Contract>(cs.getContractList()));
		String keyword = keywordBox.getValue();
		List<Contract> result = cs.search(keyword);
		contractsListbox.setModel(new ListModelList<Contract>(result));
	}
	@Listen("onSelect = #contractsListbox")
	public void openClientView() throws Exception {
		
			Contract selectedContract =  contractsListbox.getSelectedItem().getValue();
			Executions.sendRedirect("contract.zul?contract=" + selectedContract.getContractNumber());

	}
	


}
