package services;

import InformationHandlers.PdfHandler;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import model.Clasifiers;

import org.zkoss.zk.ui.util.Clients;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class DataControllServiceImpl implements DataControllService {

	PdfHandler pdfgenerator = new PdfHandler();
	
	private Connection connect = null;
	private PreparedStatement preparedStatement = null;
	private PreparedStatement preparedStatement2 = null;

	long term = 999_999_999_999_9L;

	public File exportData(boolean dataset1, boolean dataset2) throws Exception {

		String tempPath = System.getProperty("java.io.tmpdir")
				+ "informacija.pdf";
		
		PdfWriter writer = null;

		Document document = new Document(PageSize.A4, 50, 50, 50, 50);

		writer = PdfWriter
				.getInstance(document, new FileOutputStream(tempPath));

		document.open();
		if(dataset1)
		{		
		
		pdfgenerator.createOutPut("select c.Name as ClientName, CASE WHEN t.type = 0 THEN 'Užklausimas' ELSE 'Incidentas' END AS Tipas, CASE WHEN t.Status = 1 THEN 'Užregistruota' WHEN t.Status = 2 THEN 'Sprendžiama' WHEN t.Status = 3 THEN 'Išspręsta' WHEN t.Status = 4 THEN 'Grąžintas neišspręstas' WHEN t.Status = 5 THEN 'Atsisakyta spręsti' else '' END AS Status, CONCAT(e.Name ,' ' , e.Surname) as AssignedToDo, CONCAT(e2.Name ,' ' , e2.Surname) as ManagerToDo, t.Registered as Registered, t.SolveUntil as SolveUntill, t.Subject as Subject from task t JOIN taskAssignments ta ON t.Id = ta.TaskId JOIN client c ON c.Id = t.ClientId JOIN employee e ON e.Id = ta.ReceiverId JOIN employee e2 ON e2.Id = ta.AssigneeId WHERE (t.solveUntil <= current_date() and t.Solved = '0000-00-00') and ta.Id = (SELECT MAX(taskAssignments.Id) FROM taskAssignments WHERE taskAssignments.TaskId = t.Id) ORDER BY t.Id DESC",
				8, 
				new String[]{ "ClientName", "Tipas", "Status", "AssignedToDo", "ManagerToDo", "Registered", "SolveUntill", "Subject" },
				new String[]{ "Kliento Vardas", "Tipas", "Statusas", "Priskirtas", "Priskyrejas", "Registruotas", "Isspresti iki", "Tema" }, 
				new float[]{ 30f, 25f,25f,30f,30f,25f,25f,30f }, 
				new String[]{ "Kliento Vardas", "Tipas", "Statusas", "Priskirtas", "Priskyrejas", "Registruotas", "Isspresti iki", "Tema" }, 
				document, 
				"Kreipiniai, kuriuose nebuvo laikomasi terminų.");
		}
		
		if(dataset2)
		{		
		pdfgenerator.createOutPut("SELECT Username,Password,Case WHEN EmployeeId is not null THEN 'Darbuotojas' WHEN ClientId is not null THEN 'Klientas' WHEN DelegateId is not null THEN 'Atstovas' END AS Type FROM authentificationservice", 
				3, 
				new String[]{ "UserName", "Password", "Type"}, 
				new String[]{ "String", "String", "String"}, 
				new float[]{ 30f, 30f,25f }, 
				new String[]{ "Naudotojo prisijungimo vardas", "Slaptažodis", "Vartotojo tipas"}, 
				document, 
				"Sistemos naudotojų informacija");
		//pdfgenerator.createOutPut("select * from authentificationservice", 3, str,base, size, pav, document, "blablalb");
		}
			
		document.close();

		File f = new File(tempPath);
		return f;
	}


	public void importData(String name) {
		try {
			FileInputStream file = new FileInputStream(new File(name));

			XSSFWorkbook wb = new XSSFWorkbook(file);
			clearTableData("authentificationservice");
			clearTableData("service");
			readServices(getSheet(wb, "Paslaugos"));
			clearTableData("employee");
			readEmployees(getSheet(wb, "Darbuotojai"));
			clearTableData("client");
			readClients(getSheet(wb, "Klientai"));
			clearTableData("delegates");
			readRepresentatives(getSheet(wb, "Atstovai"));
			clearTableData("contract");
			readContracts(getSheet(wb, "Sutartys"));
			clearTableData("contractsServices");
			readServiceContracts(getSheet(wb, "SutPasl"));
			clearTableData("taskAssignments");
			readAssigments(getSheet(wb, "Paskyrimai"));
			clearTableData("task");
			readAppelations(getSheet(wb, "Kreipiniai"));

		} catch (Exception ioe) {
			ioe.printStackTrace();
		}
	}

	private XSSFSheet getSheet(XSSFWorkbook wb, String name) {

		for (int i = 0; i < wb.getNumberOfSheets(); i++) {
			XSSFSheet sheet = wb.getSheetAt(i);
			if (sheet.getSheetName().toString().equals(name)) {
				System.out.println("Rado" + name);
				return sheet;
			}

		}
		System.out.println("neRado");
		return null;
	}

	public void readServices(XSSFSheet sheet) throws Exception {
		String ServiceId;
		String ServiceName;
		int LH_INC;
		int LH_REQ;

		if (sheet != null) {
			String ID = "";
			for (int j = 1; j < sheet.getPhysicalNumberOfRows(); j++) {

				ServiceId = sheet.getRow(j).getCell(0).getStringCellValue();

				try {
					ServiceName = sheet.getRow(j).getCell(1)
							.getStringCellValue();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					ServiceName = "Nežinoma";
				}

				try {
					LH_INC = (int) sheet.getRow(j).getCell(2)
							.getNumericCellValue();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					LH_INC = 0;
				}

				try {
					LH_REQ = (int) sheet.getRow(j).getCell(3)
							.getNumericCellValue();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					LH_REQ = 0;
				}

				// System.out.print("PaslaugosId: "+ServiceId+" Pavadinimas: "+ServiceName+" LH_INC: "+LH_INC+" LH_REQ: "+LH_REQ+" \n");

				ID = ServiceId.substring(ServiceId.lastIndexOf("P") + 1);

				Class.forName("com.mysql.jdbc.Driver");
				connect = Clasifiers.getConnection();
				preparedStatement = connect
						.prepareStatement("INSERT INTO service values ('"
								+ Integer.parseInt(ID) + "','" + ServiceName
								+ "','" + ID + "','" + ServiceName + "','"
								+ LH_INC + "','" + LH_REQ + "');");

				preparedStatement.executeUpdate();

			}
		} else
			Clients.showNotification("Nerasta Duomenu apie Paslaugas!\n");
	}

	public void readEmployees(XSSFSheet sheet) throws Exception {
		int ID;
		String name;
		String surName;
		String position;
		String phone;
		String mail;

		if (sheet != null) {
			String pareigos = "";
			for (int j = 1; j < sheet.getPhysicalNumberOfRows(); j++) {

				ID = (int) sheet.getRow(j).getCell(0).getNumericCellValue();

				try {
					name = sheet.getRow(j).getCell(1).getStringCellValue();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					name = null;
				}

				try {
					surName = sheet.getRow(j).getCell(2).getStringCellValue();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					surName = null;
				}

				try {
					position = sheet.getRow(j).getCell(3).getStringCellValue();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					position = "I";
				}

				try {
					phone = sheet.getRow(j).getCell(4).getStringCellValue();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					phone = null;
				}

				try {
					mail = sheet.getRow(j).getCell(5).getStringCellValue();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					mail = null;
				}

				// System.out.print("DarboId: "+ID+" Vardas: "+firstName+" Pavarde: "+surName+" Pareigos: "+position+" Telefonas: "+phone+" Mail: "+mail+" \n");

				if (position.equalsIgnoreCase("A")) {
					pareigos = "2";
				}
				if (position.equalsIgnoreCase("V")) {
					pareigos = "3";
				} else {
					pareigos = "1"; // Kai I - inÅ¾inierius
				}

				Class.forName("com.mysql.jdbc.Driver");
				connect = Clasifiers.getConnection();
				preparedStatement = connect
						.prepareStatement("INSERT INTO employee values ('" + ID
								+ "','" + pareigos + "','" + name + "','"
								+ surName + "','" + mail + "','" + phone
								+ "');");

				preparedStatement.executeUpdate();

				preparedStatement2 = connect
						.prepareStatement("INSERT INTO authentificationservice (Username,Password,EmployeeId,ClientId, DelegateId) values ('"
								+ name
								+ surName
								+ "','"
								+ name
								+ surName
								+ "','" + ID + "'," + null + "," + null + ");");

				preparedStatement2.executeUpdate();

			}
		} else
			Clients.showNotification("Nerasta Duomenu apie Darbuotojus!\n");
	}

	public void readClients(XSSFSheet sheet) throws Exception {
		String clientID;
		String name;
		String address;

		if (sheet != null) {

			for (int j = 1; j < sheet.getPhysicalNumberOfRows(); j++) {

				clientID = sheet.getRow(j).getCell(0).getStringCellValue();

				try {
					name = sheet.getRow(j).getCell(1).getStringCellValue();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					name = null;
				}

				try {
					address = sheet.getRow(j).getCell(2).getStringCellValue();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					address = null;
				}

				// System.out.print("KlientoId: "+clientID+" Pavadinimas: "+name+" Adresas: "+address+" \n");

				String ID = clientID.substring(clientID.lastIndexOf("K") + 1);
				Class.forName("com.mysql.jdbc.Driver");
				connect = Clasifiers.getConnection();
				preparedStatement = connect
						.prepareStatement("INSERT INTO client values ('"
								+ Integer.parseInt(ID) + "','" + name
								+ "','0000000" + "','" + address + "','','');");
				// Traktuokim, kad kai im. k. 0000000 - mes neturim duomenu.
				preparedStatement.executeUpdate();

				StringBuffer loginNameBuffer = new StringBuffer();

				loginNameBuffer.append(name);
				loginNameBuffer.delete(loginNameBuffer.indexOf(","),
						loginNameBuffer.length());

				preparedStatement2 = connect
						.prepareStatement("INSERT INTO authentificationservice (Username,Password,EmployeeId,ClientId, DelegateId) values ('"
								+ loginNameBuffer.toString()
								+ "','"
								+ loginNameBuffer.toString()
								+ "',"
								+ null
								+ ",'"
								+ Integer.parseInt(ID)
								+ "',"
								+ null
								+ ");");

				preparedStatement2.executeUpdate();
			}
		} else
			Clients.showNotification("Nerasta Duomenu apie Klientus!\n");
	}

	public void readRepresentatives(XSSFSheet sheet) throws Exception {
		int ID;
		String repClient;
		String name;
		String surName;
		String mail;
		String phone;
		boolean active;

		if (sheet != null) {

			for (int j = 1; j < sheet.getPhysicalNumberOfRows(); j++) {

				ID = (int) sheet.getRow(j).getCell(0).getNumericCellValue();
				repClient = sheet.getRow(j).getCell(1).getStringCellValue();

				try {
					name = sheet.getRow(j).getCell(2).getStringCellValue();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					name = null;
				}

				try {
					surName = sheet.getRow(j).getCell(3).getStringCellValue();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					surName = null;
				}

				try {
					mail = sheet.getRow(j).getCell(5).getStringCellValue();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					mail = null;
				}

				try {
					phone = sheet.getRow(j).getCell(4).getStringCellValue();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					phone = null;
				}

				try {
					active = sheet.getRow(j).getCell(6).getBooleanCellValue();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					active = false;
				}

				// System.out.print("AtstovoId: "+ID+" AtstovaujaKlienta: "+repClient+" Vardas: "+firstName+" Pavarde: "+lastName+" ElPastas: "+mail+" Telefonas: "+phone+" Aktyvus: "+active+" \n");

				String clientId = repClient.substring(repClient
						.lastIndexOf("K") + 1);

				Class.forName("com.mysql.jdbc.Driver");
				connect = Clasifiers.getConnection();
				preparedStatement = connect
						.prepareStatement("INSERT INTO delegates values ('"
								+ ID + "','" + Integer.parseInt(clientId)
								+ "','" + name + "','" + surName + "','"
								+ phone + "','" + mail + "','" + active + "');");

				preparedStatement.executeUpdate();

				preparedStatement2 = connect
						.prepareStatement("INSERT INTO authentificationservice (Username,Password,EmployeeId,ClientId, DelegateId) values ('"
								+ name
								+ surName
								+ "','"
								+ name
								+ surName
								+ "'," + null + "," + null + ",'" + ID + "');");

				preparedStatement2.executeUpdate();

			}
		} else
			Clients.showNotification("Nerasta Duomenu apie Atstovus!\n");
	}

	public void readContracts(XSSFSheet sheet) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date from;
		Date to;
		int ID;
		String contractID;
		String name;
		String clientID;

		String till = null, fr = null;

		if (sheet != null) {

			for (int j = 1; j < sheet.getPhysicalNumberOfRows(); j++) {

				ID = (int) sheet.getRow(j).getCell(0).getNumericCellValue();

				try {
					contractID = sheet.getRow(j).getCell(1)
							.getStringCellValue();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					contractID = null;
				}

				try {
					name = sheet.getRow(j).getCell(2).getStringCellValue();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					name = "Nežinoma";
				}

				clientID = sheet.getRow(j).getCell(3).getStringCellValue();

				try {
					from = sheet.getRow(j).getCell(4).getDateCellValue();
					fr = sdf.format(from);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					from = new Date(term);
					fr = sdf.format(from);
				}

				try {
					to = sheet.getRow(j).getCell(5).getDateCellValue();
					till = sdf.format(from);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					to = new Date(term);
					till = sdf.format(from);
				}

				// System.out.print("SutartiesId: "+ ID +" SutartiesNr: "+
				// contractID +" Pavadinimas: "+ name +" KlientoId: "+ clientID
				// +" DataNuo: "+ sdf.format(from) +" DataIki: "+ sdf.format(to)
				// +" \n");

				String clientId = clientID
						.substring(clientID.lastIndexOf("K") + 1);

				Class.forName("com.mysql.jdbc.Driver");
				connect = Clasifiers.getConnection();
				preparedStatement = connect
						.prepareStatement("INSERT INTO contract values ('" + ID
								+ "','" + contractID + "','" + name + "','"
								+ Integer.parseInt(clientId) + "','" + fr
								+ "','" + till + "');");

				preparedStatement.executeUpdate();
			}
		} else
			Clients.showNotification("Nerasta Duomenu apie Sutartis!\n");
	}

	public void readServiceContracts(XSSFSheet sheet) throws Exception {
		int serviceID;
		String contractID;

		if (sheet != null) {

			for (int j = 1; j < sheet.getPhysicalNumberOfRows(); j++) {

				serviceID = (int) sheet.getRow(j).getCell(0)
						.getNumericCellValue();
				contractID = sheet.getRow(j).getCell(1).getStringCellValue();

				// System.out.print("SutartiesId: "+serviceID+" paslaugosId: "+contractID+" \n");

				String contractId = contractID.substring(contractID
						.lastIndexOf("P") + 1);

				Class.forName("com.mysql.jdbc.Driver");
				connect = Clasifiers.getConnection();
				preparedStatement = connect
						.prepareStatement("INSERT INTO contractsServices values ('"
								+ serviceID
								+ "','"
								+ Integer.parseInt(contractId) + "');");
				preparedStatement.executeUpdate();
			}
		} else
			Clients.showNotification("Nerasta Duomenu apie PasiraÅytas sutartis!\n");
	}

	public void readAppelations(XSSFSheet sheet) throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int ID;
		String clientID;
		String serviceID;
		String type;
		String source;
		String data;
		Date received;
		Date completed;
		String status;
		int rank;
		int previous;

		String recv = null;
		String comp = null;

		// Task
		if (sheet != null) {
			int rows = sheet.getPhysicalNumberOfRows();
			for (int j = 1; j < rows; j++) {

				ID = (int) sheet.getRow(j).getCell(0).getNumericCellValue();
				clientID = sheet.getRow(j).getCell(1).getStringCellValue();
				serviceID = sheet.getRow(j).getCell(2).getStringCellValue();

				try {
					type = sheet.getRow(j).getCell(3).getStringCellValue();
					if (type.equals("INC")) {
						type = "0";
					} else {
						type = "1";
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					type = null;
				}

				try {
					source = sheet.getRow(j).getCell(4).getStringCellValue();
					if (source.equals("T")) {
						source = "1";
					} else if (source.equals("S")) {
						source = "2";
					} else {
						source = "3";
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					source = null;
				}

				try {
					data = sheet.getRow(j).getCell(5).getStringCellValue();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					data = null;
				}

				try {
					received = sheet.getRow(j).getCell(6).getDateCellValue();
					recv = sdf.format(received);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					received = null;
				}

				try {
					completed = sheet.getRow(j).getCell(7).getDateCellValue();
					comp = sdf.format(completed);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					completed = null;
				}

				try {
					status = sheet.getRow(j).getCell(8).getStringCellValue();
					if (status.equals("N")) {
						status = "1";
					} else if (status.equals("P")) {
						status = "2";
					} else if (status.equals("I")) {
						status = "3";
					} else if (status.equals("G")) {
						status = "4";
					} else if (status.equals("A")) {
						status = "5";
					} else
						status = "6";
				} catch (Exception e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					status = null;
				}

				try {
					rank = (int) sheet.getRow(j).getCell(9)
							.getNumericCellValue();

					if (rank > 5 && rank <= 10)
						rank = rank / 2 + rank % 2;

				} catch (Exception e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					rank = 0;
				}

				try {
					previous = (int) sheet.getRow(j).getCell(10)
							.getNumericCellValue();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					previous = 0;
				}

				String clientId = clientID
						.substring(clientID.lastIndexOf("K") + 1);
				String serviceId = serviceID.substring(serviceID
						.lastIndexOf("P") + 1);

				// System.out.print("'Id: "+ ID +"','"+
				// Integer.parseInt(clientId) +"','"+ status +"','"+ type
				// +"','"+sdf.format(received)+"','"+"0000-00-00 00:00:00"+
				// "','"+sdf.format(completed)+
				// "','"+c+
				// "','"+Integer.parseInt(serviceId) +
				// "','"+rank+
				// "','"+source+
				// "','"+sheet.getRow(j).getCell(5).getStringCellValue()+
				// "','"+sheet.getRow(j).getCell(3).getStringCellValue()+"');\n");
				//
				Class.forName("com.mysql.jdbc.Driver");
				connect = Clasifiers.getConnection();
				preparedStatement = connect
						.prepareStatement("INSERT INTO task values ('" + ID
								+ "','" + Integer.parseInt(clientId) + "','"
								+ Integer.parseInt(status) + "','"
								+ Integer.parseInt(type) + "','" + recv + "','"
								+ sdf.format(new Date(term)) + "','" + comp
								+ "','" + previous + "','"
								+ Integer.parseInt(serviceId) + "','" + rank
								+ "','" + Integer.parseInt(source) + "','"
								+ data + "','" + data + "');");

				preparedStatement.executeUpdate();
			}
		} else
			Clients.showNotification("Nerasta Duomenu apie Kreipinius!\n");
	}

	public void readAssigments(XSSFSheet sheet) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int ID;
		int taskID;
		int source;
		int destination;
		Date assigned;
		Date returned;
		String data;
		String result;
		int time;

		int status = 0;
		String ret = null;
		String assig = null;

		if (sheet != null) {
			int rows = sheet.getPhysicalNumberOfRows();
			for (int j = 1; j < rows; j++) {

				ID = (int) sheet.getRow(j).getCell(0).getNumericCellValue();
				taskID = (int) sheet.getRow(j).getCell(1).getNumericCellValue();
				source = (int) sheet.getRow(j).getCell(2).getNumericCellValue();
				destination = (int) sheet.getRow(j).getCell(3)
						.getNumericCellValue();

				try {
					data = sheet.getRow(j).getCell(6).getStringCellValue();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					data = null;
				}

				try {
					result = sheet.getRow(j).getCell(7).getStringCellValue();
					if (result.equals("G")) {
						status = 1;
					} else if (result.equals("I")) {
						status = 2;
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					result = null;
				}

				time = (int) sheet.getRow(j).getCell(8).getNumericCellValue();

				try {
					assigned = sheet.getRow(j).getCell(4).getDateCellValue();
					assig = sdf.format(assigned);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					assigned = new Date(term);
					assig = sdf.format(assigned);
				}

				try {
					returned = sheet.getRow(j).getCell(5).getDateCellValue();
					ret = sdf.format(returned);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					returned = new Date(term);
					ret = sdf.format(returned);
				}

				// System.out.print("IPaskyrimoId: "+ +
				// "Kreipinys: "+ +
				// "Kas: "++
				// "Kam: "+ +
				// "Skirtas: "+sdf.format(assigned)+
				// "Grazintas: "+sdf.format(returned)+
				// "Tekstas: "+t+
				// "Rezultatas: "+status+
				// "SanaudosMin: "+sheet.getRow(j).getCell(8).getNumericCellValue()+"');\n");

				Class.forName("com.mysql.jdbc.Driver");
				connect = Clasifiers.getConnection();
				preparedStatement = connect
						.prepareStatement("INSERT INTO taskAssignments values ('"
								+ ID
								+ "','"
								+ taskID
								+ "','"
								+ source
								+ "','"
								+ destination
								+ "','"
								+ assig
								+ "','"
								+ ret
								+ "','"
								+ data
								+ "','"
								+ status
								+ "','"
								+ time
								+ "');");

				preparedStatement.executeUpdate();
			}
		} else
			Clients.showNotification("Nerasta Duomenu apie Paskyrimus!\n");
	}

	public int clearTableData(String name) throws Exception {
		// System.out.print("SET FOREIGN_KEY_CHECKS=0; TRUNCATE "+name+"; TRUNCATE "+name+"; SET FOREIGN_KEY_CHECKS=1;\n");
		setKey(0);
		Class.forName("com.mysql.jdbc.Driver");
		connect = Clasifiers.getConnection();
		preparedStatement = connect.prepareStatement("TRUNCATE " + name + ";");
		try {
			preparedStatement.executeUpdate();
			setKey(1);
			return 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			setKey(1);
			return 0;
		}
	}

	public int setKey(int i) throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		connect = Clasifiers.getConnection();
		preparedStatement = connect.prepareStatement("SET FOREIGN_KEY_CHECKS="
				+ i + ";");
		try {
			preparedStatement.executeUpdate();
			return 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	public void clearDB() throws Exception {
		clearTableData("service");
		clearTableData("activities");
		clearTableData("authentificationservice");
		clearTableData("employee");
		clearTableData("client");
		clearTableData("delegates");
		clearTableData("contract");
		clearTableData("contractsServices");
		clearTableData("task");
		clearTableData("taskAssignments");
		clearTableData("taskhistory");
		clearTableData("comment");
	}

}
