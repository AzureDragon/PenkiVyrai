package mainControllers;

import model.Delegate;
import model.Task;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;

import services.AppelationServiceImpl;

import addtionalControllers.TaskStatements;

public class ClientViewController  extends SelectorComposer<Component>{
@Wire
Label name, code, address, clientRef;
@Wire
Listbox telephoneListBox;
@Wire 
Listbox clientTasks, delegatesListbox;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		int clientId = Integer.parseInt(Executions.getCurrent().getParameter("id"));
		System.out.println(clientId);
		TaskStatements ts = new TaskStatements();
		name.setValue(ts.getClientName((clientId)));
		code.setValue(ts.getClientCode(clientId));
		address.setValue(ts.getClientAddress(clientId));
		Executions.getCurrent().getParameter("id");
		clientRef.setTooltip(Executions.getCurrent().getParameter("id"));
		AppelationServiceImpl ap = new AppelationServiceImpl();
		clientTasks.setModel(new ListModelList<Task>(ap.searchClientTasks(" ", clientId)));
		ClientProfileViewController cp = new ClientProfileViewController();
		delegatesListbox.setModel(new ListModelList<Delegate>(cp.getDelegates(clientId)));
		
	}

	@Listen("onClick=#clientRef")
	public void sendRedirectToClient (){
		
		Executions.sendRedirect("client.zul?id="+ clientRef.getTooltip());
		
		
		
	}
	
}
