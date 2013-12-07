package services;

import java.util.List;

import model.Employee;
import model.Task;


public interface EmployeeService {

	public List<Employee> getEmployeeList();

	public List<Task> findAllCompletedAppelation();

	public List<Task> searchAllEmployee(String keyword);

	public void assignEmployee(Employee employee);

	public void changeEmployee(Employee employee);

}
