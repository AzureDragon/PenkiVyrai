package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

import addtionalControllers.TaskStatements;


public class Clasifiers {
	// Map <Integer, String> type = new HashMap<Integer, String>();

	public static java.sql.Connection conn;
	public static Date date;
	public static String getTypeName(int type) {

		if (type == 0) {
			return "Užklausimas";
		} else {
			return "Incidentas";
		}
	}

	public static Connection getConnection() throws Exception {
		
		if ( conn == null) {
			try {
				conn = DriverManager
						.getConnection("jdbc:mysql://5.199.172.135/psi?"
								+ "user=PSI&password=gl59lmkis8&characterEncoding=utf8&zeroDateBehavior=convertToNull");
				//conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/phpmyadmin/psi?"+"user=root&characterEncoding=utf8");

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				throw e;
			}
			return conn;
		} else {
			if (conn.isClosed()){
				//conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/phpmyadmin/psi?"+"user=root&characterEncoding=utf8");

				conn = DriverManager
						.getConnection("jdbc:mysql://5.199.172.135/psi?"
								+ "user=PSI&password=gl59lmkis8&characterEncoding=utf8");
			
			return conn;
			} else return conn;
		}
		
	}

	public static String getTypeName(String type) {

		if (type.equals("0")) {
			return "Užklausimas";
		} else {
			return "Incidentas";
		}
	}

	public static int getTypeCode(String type) {
		if (type.equals("Užklausimas")) {
			return 0;
		} else
			return 1;
	}

	public static int getRoleCode(String role) {
		switch (role) {
		case "Inžinierius":
			return 1;
		case "Administratorius":
			return 2;
		case "Vadovas":
			return 3;
		case "Svečias":
			return 4;
		case "Kliento atstovas":
			return 5;
		default:
			return 6;
		}
	}
	public static String getRole(int role) {
		switch (role) {
		case 1:
			return "Inžinierius";
		case 2:
			return "Administratorius";
		case 3:
			return "Vadovas";
		case 4:
			return "Svečias";
		case 5:
			return "Kliento atstovas";
		case 6:
			return "Nenurodyta";
		
		default:
			return "Klaida! toks statusas neegzistuoja";
		}
	}
	public static String getStatusName(int status) {
		switch (status) {
		case 1:
			return "Užregistruota";
		case 2:
			return "Sprendžiama";
		case 3:
			return "Išspręsta";
		case 4:
			return "Grąžintas neišspręstas";
		case 5:
			return "Atsisakyta spręsti";
		default:
			return "Klaida! toks statusas neegzistuoja";
		}
	}

	public static int getStatusCode(String status) {
		switch (status) {
		case "Užregistruota":
			return 1;
		case "Sprendžiama":
			return 2;
		case "Išspręsta":
			return 3;
		case "Grąžintas neišspręstas":
			return 4;
		case "Atsisakyta spręsti":
			return 5;
		default:
			return 0;
		}
	}
	public static int getReceiveSourceCode (String receiveSource){
		switch (receiveSource) {
		case "Telefonu":
			return 1;
		case "El. paštu":
			return 2;
		case "Savitarna":
			return 3;
		default:
			return 0;
		}
	}

	public static String isNull (String tmp){
		
		if (tmp==null || tmp.equals("null")){
			return "--";
		} else {
			return tmp;
		}
	}
	public static String getReceiveSourceName (int receiveSource){
		switch (receiveSource) {
		case 1:
			return "Telefonu";
		case 2:
			return "El. paštu";
		case 3:
			return "Savitarna";
		default:
			return "Nerasta";
		}
	}

	public static String getEmployeeIdByNameAndSurname(String value) throws Exception {
		
		String [] vardasPavarde = value.split("\\ ");

		TaskStatements ts = new TaskStatements();
		String id = ts.getEmployeeId(vardasPavarde[0], vardasPavarde[1]);
		
		if (id.equals("0")|| id.equals("") || id.equals(null)) {
			throw new Exception("Id nebuvo surastas");
		}
		
		
		return id;
	}
	public static String getEmployeeSurnameById(int id) throws Exception{
		TaskStatements ts = new TaskStatements();
		
		try {
			return ts.getEmployeeSurname(id);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Cliento vardas nerastas");
			e.printStackTrace();
		}
		return "Klaida";
	}
	
	public static String getEmployeeNameById(int id) throws Exception{
		TaskStatements ts = new TaskStatements();
		
		try {
			return ts.getEmployeeName(id);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Cliento vardas nerastas");
			e.printStackTrace();
		}
		return "Klaida";
	}
	public static String getClientIdByName(String name) throws NumberFormatException, Exception{
		TaskStatements ts = new TaskStatements();
		return ts.getClientIdByName(name);
	}
	public static String getClientNameById(int id) throws Exception{
		TaskStatements ts = new TaskStatements();
		
		try {
			
			return ts.getClientName(id);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Cliento vardas nerastas");
			e.printStackTrace();
		}
		return "Klaida";
	}
}