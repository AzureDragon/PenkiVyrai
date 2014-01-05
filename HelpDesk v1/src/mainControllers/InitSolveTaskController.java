package mainControllers;

import model.Authentication;
import model.Clasifiers;
import model.Employee;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;

import services.AuthenticationService;
import services.AuthenticationServiceImpl;
import services.EmployeeServiceImpl;

public class InitSolveTaskController extends SelectorComposer<Component> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	
	

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
	
		AuthenticationService authService = new AuthenticationServiceImpl();
		Authentication cre = authService.getUserCredential();
		
		System.out.println(Executions.getCurrent().getDesktop().getAttribute("Id"));

	}
}
