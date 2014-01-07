package services;

import java.util.List;

import model.Task;


public interface AppelationService {

	/**
	 * Retrieve all cars in the catalog.
	 * 
	 * @return all cars
	 */
	public List<Task> findAll();

	/**
	 * search cars according to keyword in model and make.
	 * 
	 * @param keyword
	 *            for search
	 * @return list of car that match the keyword
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	public List<Task> search(String keyword) throws Exception;
	public List<Task> search(String keyword, int employeeId) throws Exception;
	public List<Task> searchClientTasks(String keyword, int clientId) throws Exception;

}
