package mainControllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import model.Clasifiers;
import model.Service;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;

import com.itextpdf.awt.geom.misc.Messages;

import addtionalControllers.TaskStatements;

public class ContractViewController extends SelectorComposer<Component> {
	@Wire
	Label name, code, client, validFrom, validTo;
	@Wire
	Listbox serviceListbox;
	@Wire Button addService;
	@Wire Combobox services;
	@Wire Label invisibleContractNumber;
	
	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	public List<Service> serviceList;

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		
		Clients.showBusy("Vykdoma");
		String contractNumber = Executions.getCurrent().getParameter("contract");
		invisibleContractNumber.setValue(contractNumber);
		TaskStatements ts = new TaskStatements();
		name.setValue(ts.getContractName(contractNumber));
		code.setValue(contractNumber);
		client.setValue(ts.getClientName(Integer.parseInt(ts.getContractClient(contractNumber))));
		validFrom.setValue(ts.getContractBeginTime(contractNumber));
		validTo.setValue(ts.getContractEndTime(contractNumber));
		serviceListbox.setModel(new ListModelList<Service>(getContractsServiceList()));
		services.setModel(new ListModelList<Service>(getServices()));
		Clients.clearBusy();
	}

	public List<Service> getContractsServiceList() throws Exception {

		ResultSet resultSet = null;
		connect = Clasifiers.getConnection();
		statement = connect.createStatement();
		resultSet = statement
				.executeQuery("SELECT c.ContractNumber as ContractNumber, "
						+ "c.Name as ContractName, s.Name as ServiceName, "
						+ "cl.Name as ClientName, c.BeginTime as BeginTime,"
						+ " c.EndTime as EndTime FROM contract c join "
						+ "contractsServices cs on cs.ContractId = c.ID "
						+ "join service s on cs.ServiceId = s.Id join client "
						+ "cl on cl.Id = c.ClientId where c.ContractNumber='"+ invisibleContractNumber.getValue()+"' "
						+ "order by c.ContractNumber");

		return writeResultSet(resultSet);

	}

	private List<Service> writeResultSet(ResultSet resultSet)
			throws SQLException {

		List<Service> serviceList = new LinkedList<Service>();

		while (resultSet.next()) {

			serviceList.add(new Service(resultSet.getString("ServiceName")));

		}
		return serviceList;
	}

	public List<Service> getServices() throws Exception {

		ResultSet resultSet = null;
		connect = Clasifiers.getConnection();
		statement = connect.createStatement();
		resultSet = statement.executeQuery("SELECT name from service");

		return writeResultSetServices(resultSet);

	}
	private List<Service> writeResultSetServices(ResultSet resultSet)
			throws SQLException {

		List<Service> serviceList = new LinkedList<Service>();

		while (resultSet.next()) {

			serviceList.add(new Service(resultSet.getString("Name")));

		}
		return serviceList;
	}
	
@Listen("onClick=#addService")
public void addService () throws Exception{
	Connection connect = null;
	Statement statement = null;
	PreparedStatement preparedStatement = null;
	if (services.getValue()!=""){
	TaskStatements ts = new TaskStatements();
	Class.forName("com.mysql.jdbc.Driver");
	connect = Clasifiers.getConnection();
	preparedStatement = connect
			.prepareStatement("INSERT INTO `contractsServices`(`ContractId`, `ServiceId`) VALUES ("+ ts.getContractIdByContractNumber(invisibleContractNumber.getValue())+","+ ts.getServiceIdByName(services.getValue())+")");
	System.out.println("INSERT INTO `contractsServices`(`ContractId`, `ServiceId`) VALUES ("+ ts.getContractIdByContractNumber(invisibleContractNumber.getValue())+","+ ts.getServiceIdByName(services.getValue())+");");
	preparedStatement.executeUpdate();
	Executions.sendRedirect("");
	} else{
		Messages.getString("sadfasdf");
	}
}

}
