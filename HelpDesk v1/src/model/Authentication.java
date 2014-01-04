package model;

public class Authentication {

	String Password;
	String LoginName;
	
	int id;
	int EmployeeId;
	int clientId;
	int delegateId;
	

	public Authentication(String password, String loginName, int id,
			int employeeId, int clientId, int delegateId) {
		Password = password;
		LoginName = loginName;
		this.id = id;
		EmployeeId = employeeId;
		this.clientId = clientId;
		this.delegateId = delegateId;
	}
	public int getDelegateId() {
		return delegateId;
	}
	public void setDelegateId(int delegateId) {
		this.delegateId = delegateId;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getLoginName() {
		return LoginName;
	}
	public void setLoginName(String loginName) {
		LoginName = loginName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getEmployeeId() {
		return EmployeeId;
	}
	public void setEmployeeId(int employeeId) {
		EmployeeId = employeeId;
	}
	public int getClientId() {
		return clientId;
	}
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	
    public static Authentication clone(Authentication user){
        try {
                return (Authentication)user.clone();
        } catch (CloneNotSupportedException e) {
                //not possible
        }
        return null;
}
	
}
