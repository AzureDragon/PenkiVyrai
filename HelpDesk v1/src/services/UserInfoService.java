package services;

import model.Authentication;
import model.Client;
import model.Employee;

public interface UserInfoService {

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

	/** find rights by id **/
	public String rightsValue(int index);
}