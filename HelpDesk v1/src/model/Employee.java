package model;

import java.io.Serializable;
import java.util.List;
/**
 * User entity
 */
public class Employee implements Serializable,Cloneable {
        private static final long serialVersionUID = 1L;
        
        int id;
        String firstName;
        String surName;
        int rights;
        String eMail;
        String phone;
        public String getRights2() {
			return rights2;
		}

		public void setRights2(String rights2) {
			this.rights2 = rights2;
		}

		String rights2;



        public Employee(int id, String firstName, String surName, int rights, String eMail, String phone) {
                this.id = id;
                this.firstName = firstName;
                this.surName = surName;
                this.eMail = eMail;
                this.phone = phone;
                this.rights = rights;
        }

    	public Employee(int id, String rights2, String firstName, String surName,
    			String eMail, String phone) {
    		super();
    		this.id = id;
    		this.rights2 = rights2;
    		this.firstName = firstName;
    		this.surName = surName;
            this.eMail = eMail;
            this.phone = phone;

    	}
        
		public String geteMail() {
			return eMail;
		}

		public void seteMail(String eMail) {
			this.eMail = eMail;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}
        
		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public int getRights() {
			return rights;
		}

		public void setRights(int rights) {
			this.rights = rights;
		}

		public String getSurName(){
			return surName;
		}
		
		public void setSurName (String surName){
			this.surName = surName;
		}
		

        @Override
        public boolean equals(Object obj) {
                if (this == obj)
                        return true;
                if (obj == null)
                        return false;
                if (getClass() != obj.getClass())
                        return false;

                return true;
        }
        
        public static Employee clone(Employee user){
                try {
                        return (Employee)user.clone();
                } catch (CloneNotSupportedException e) {
                        //not possible
                }
                return null;
        }
}