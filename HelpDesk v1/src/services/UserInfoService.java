package services;

import model.Authentication;
import model.Client;
import model.Delegate;
import model.Employee;



	public interface UserInfoService {

		public Client findClientByID(int id) throws Exception;
	/**
	 * find user by account
	 * 
	 * @throws Exception
	 **/
	public Authentication findLogin(String account) throws Exception;

	/**
	 * find user by id
	 * 
	 * @throws Exception
	 **/
	public Employee findEmployee(Authentication u) throws Exception;

	/**
	 * find client by id
	 * 
	 * @throws Exception
	 **/
	public Client findClient(Authentication u) throws Exception;

	/***
	 * 
	 * find and return delegate
	 * 
	 * @param u authentification
	 * @return delegate
	 * @throws Exception
	 */
	public Delegate findDelegate(Authentication u) throws Exception;
	
	/** find rights by id **/
	public String rightsValue(int index);
}