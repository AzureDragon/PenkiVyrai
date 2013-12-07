package mainControllers;

import java.util.List;

import model.Client;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;


public class ClientsController extends SelectorComposer<Component>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Wire
	private Listbox clientsListbox;
	@Wire
	private Textbox keywordBox;
	
	@Listen("onClick = #searchButton")
	public void search() throws Exception{
		ClientsSearchController clients = new ClientsSearchController();
		clientsListbox.setModel(new ListModelList<Client>(clients.getClientList()));
		String keyword = keywordBox.getValue();
		List<Client> result = clients.search(keyword);
		clientsListbox.setModel(new ListModelList<Client>(result));
	}

	
	
}
