package services;

import model.Authentication;

public interface AuthenticationService {

	/**
	 * login with account and password
	 * 
	 * @throws Exception
	 **/
	public boolean login(String account, String password) throws Exception;

	/** logout current user **/
	public void logout();

	/** get current user credential **/
	public Authentication getUserCredential();
	
	public boolean isLoggedIn();

}