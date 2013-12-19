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
	
	@Wire
	Combobox priskirti;

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
		priskirti.setModel(new ListModelList<>(new ListModelList<Employee>(
				employeeService.getEmployeeList())));
		AuthenticationService authService = new AuthenticationServiceImpl();
		Authentication cre = authService.getUserCredential();
		priskirti.setValue(Clasifiers.getEmployeeNameById(cre.getEmployeeId()) +" "+Clasifiers.getEmployeeSurnameById(cre.getEmployeeId()));
		System.out.println(Executions.getCurrent().getDesktop().getAttribute("Id"));

	}
}
