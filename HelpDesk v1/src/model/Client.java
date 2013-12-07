package model;

import java.util.Vector;

public class Client implements Cloneable{
	private int id;
	private String name;
	private String code;
	private String address;
    private Vector<String> eMail = new Vector<String>();
    private Vector<String> phone = new Vector<String>();
    private int rights;
	
	public int getRights() {
		return rights;
	}

	public void setRights(int rights) {
		this.rights = rights;
	}

	public Client(int id, String name, String code, String address, Vector<String> eMail, Vector<String> phone, int rights) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.address = address;
		this.eMail = eMail;
		this.phone = phone;
		this.rights = rights;
	}
	
	public Vector<String> geteMail() {
		return eMail;
	}
	public void seteMail(Vector<String> eMail) {
		this.eMail = eMail;
	}
	public Vector<String> getPhone() {
		return phone;
	}
	public void setPhone(Vector<String> phone) {
		this.phone = phone;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
    public static Client clone(Client client){
        try {
                return (Client)client.clone();
        } catch (CloneNotSupportedException e) {
                //not possible
        }
        return null;
}

}
