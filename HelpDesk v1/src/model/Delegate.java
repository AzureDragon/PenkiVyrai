package model;

import java.io.Serializable;

public class Delegate implements Serializable,Cloneable{

	int ID;
	int clientID;
	String firstName;
	String lastName;
	String phone;
	String mail;
	int rights;
	boolean active;
	
	
	
	public Delegate(int iD, int clientID, String firstName, String lastName,
			String phone, String mail, boolean active, int rights) {
		super();
		ID = iD;
		this.clientID = clientID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.mail = mail;
		this.active = active;
		this.rights = rights;
	}
	
	public int getRights() {
		return rights;
	}

	public void setRights(int rights) {
		this.rights = rights;
	}

	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getClientID() {
		return clientID;
	}
	public void setClientID(int clientID) {
		this.clientID = clientID;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}

    public static Delegate clone(Delegate user){

        try {
                return (Delegate)user.clone();
        } catch (CloneNotSupportedException e) {
                //not possible
        }
        return null;
}
	
}
