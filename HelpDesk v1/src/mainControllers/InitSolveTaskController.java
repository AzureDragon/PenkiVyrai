package mainControllers;

import model.Employee;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;

import services.EmployeeServiceImpl;



public class InitSolveTaskController extends SelectorComposer<Component> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Wire
	Combobox priskirti;

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
		priskirti.setModel(new ListModelList<>(new ListModelList<Employee>(
				employeeService.getEmployeeList())));

	}
}
