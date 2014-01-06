package mainControllers;

import model.Clasifiers;
import model.Employee;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;

import services.AppelationServiceImpl;

import addtionalControllers.TaskStatements;

public class EmployeeViewController extends SelectorComposer<Component>{

	@Wire
	Label name, surname, role, phone, email;
	@Wire
	Listbox employeeTasks;
	
	
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		String employeeId = Executions.getCurrent().getParameter("id");
		TaskStatements ts = new TaskStatements();
		Employee employee = ts.getEmployee(Integer.parseInt(employeeId));
		System.out.println("Rights "+ employee.getRights2()+ employee.getFirstName());
		name.setValue(employee.getFirstName());
		surname.setValue(employee.getSurName());
		role.setValue(Clasifiers.getRole(Integer.parseInt(employee.getRights2())));
		phone.setValue(employee.getPhone());
		email.setValue(employee.geteMail());
	
	};
	
	
	

}
