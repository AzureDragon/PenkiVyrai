package services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import model.Clasifiers;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.zkoss.zk.ui.util.Clients;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.lowagie.text.Anchor;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfWriter;

public class DataControllServiceImpl implements DataControllService{

	private Connection connect = null;
	private PreparedStatement preparedStatement = null;
	
	public File exportData() throws FileNotFoundException, DocumentException
	{
		Document document = new Document(PageSize.A4, 50, 50, 50, 50);

		String tempPath = System.getProperty("java.io.tmpdir")+"informacija.pdf";
		@SuppressWarnings("unused")
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(tempPath));

				document.open();
				
				Anchor anchorTarget = new Anchor("First page of the document.");
			      anchorTarget.setName("BackToTop");
			      Paragraph paragraph1 = new Paragraph();

			      paragraph1.setSpacingBefore(50);

			      paragraph1.add(anchorTarget);
			      document.add(paragraph1);

			document.add(new Paragraph("Some more text on the \first page with different color and font type.", 

			FontFactory.getFont(FontFactory.COURIER, 14, Font.BOLD,	new CMYKColor(0, 255, 0, 0))));
			
			document.close();
			File f = new File(tempPath);
			return f;
}
	public void importData(String name)
	{
//		File file = new File(".");
//		for(String fileNames : file.list()) System.out.println(fileNames);
		
	//	InputStream ExcelFileToRead = new FileInputStream("C:/Test.xlsx");
		//XSSFWorkbook  wb2 = new XSSFWorkbook(ExcelFileToRead);
		
		try {
			System.out.print("atejo\n");
		   // OPCPackage fs = new POIFSFileSystem(new FileInputStream(name));
			FileInputStream file = new FileInputStream(new File("C:/Users/Osvaldas/Desktop/eclipse-jee-indigo-SR2-win32-x86_64/eclipse/Testiniai_duomenys.xlsx"));
            
			//Get the workbook instance for XLS file 
			XSSFWorkbook wb = new XSSFWorkbook (file);
		    System.out.print("atejo4\n");
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
	    	clearTableData("task");
	    	readAppelations(getSheet(wb, "Kreipiniai"));
	    	clearTableData("taskAssignments");
	    	readAssigments(getSheet(wb, "Paskyrimai"));
		    
		} catch(Exception ioe) {
		    ioe.printStackTrace();
		}
	}
	
	private XSSFSheet getSheet(XSSFWorkbook wb, String name)
	{
		
		for(int i = 0; i < wb.getNumberOfSheets(); i++)
	    {
			XSSFSheet sheet = wb.getSheetAt(i);
			if(sheet.getSheetName().toString().equals(name))
			{
				System.out.println("Rado"+name);
				return sheet;				
			}

	    }
		System.out.println("neRado");
		return null;
	}
	
	public void readServices(XSSFSheet sheet) throws Exception
	{
		String ServiceId;
		String ServiceName;
		int LH_INC;
		int LH_REQ;
		
		if(sheet != null)
		{
        String ID = "";
		for(int j = 1; j < sheet.getPhysicalNumberOfRows(); j++){
			 
			ServiceId = sheet.getRow(j).getCell(0).getStringCellValue();
			
			try {
				 	ServiceName = sheet.getRow(j).getCell(1).getStringCellValue();
		        	
				} catch (Exception  e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					ServiceName = "Nežinoma";
				}
			
			 try {
				 	LH_INC = (int)sheet.getRow(j).getCell(2).getNumericCellValue();
		        	
				} catch (Exception  e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					LH_INC = 0;
				}	
			 
			 try {
				 	LH_REQ = (int)sheet.getRow(j).getCell(3).getNumericCellValue();
		        	
				} catch (Exception  e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					LH_REQ = 0;
				}	

		//   System.out.print("PaslaugosId: "+ServiceId+" Pavadinimas: "+ServiceName+" LH_INC: "+LH_INC+" LH_REQ: "+LH_REQ+" \n");
		   
		   ID = ServiceId.substring(ServiceId.lastIndexOf("P") + 1);

		   Class.forName("com.mysql.jdbc.Driver");
		   connect = Clasifiers.getConnection();
		   preparedStatement = connect
					.prepareStatement("INSERT INTO service values ('"+ Integer.parseInt(ID) +"','"+ ServiceName +"','"
							+ ID +"','"+ ServiceName +"','"
							+ LH_INC +"','"+ LH_REQ +"');");

		   preparedStatement.executeUpdate();    
		   
		}
		}
		else
			Clients.showNotification("Nerasta Duomenu apie Paslaugas!\n");
	}
	
	public void readEmployees(XSSFSheet sheet) throws Exception
	{
		int ID;
		String firstName;
		String surName;
		String position;
		String phone;
		String mail;
		
		if(sheet != null)
		{
        String pareigos = "";
		for(int j = 1; j < sheet.getPhysicalNumberOfRows(); j++){
			
			ID = (int) sheet.getRow(j).getCell(0).getNumericCellValue();
			
			 try {
				 firstName = sheet.getRow(j).getCell(1).getStringCellValue();
		        	
				} catch (Exception  e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					firstName = null;
				}	
			 
			 try {
				 surName = sheet.getRow(j).getCell(2).getStringCellValue();
		        	
				} catch (Exception  e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					surName = null;
				}	
			 
			 try {
				 position = sheet.getRow(j).getCell(3).getStringCellValue();
		        	
				} catch (Exception  e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					position = "I";
				}
			 
			 try {
				 phone = sheet.getRow(j).getCell(4).getStringCellValue();
		        	
				} catch (Exception  e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					phone = null;
				}
			 
			 try {
				 mail = sheet.getRow(j).getCell(5).getStringCellValue();
		        	
				} catch (Exception  e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					mail = null;
				}

		//   System.out.print("DarboId: "+ID+" Vardas: "+firstName+" Pavarde: "+surName+" Pareigos: "+position+" Telefonas: "+phone+" Mail: "+mail+" \n");
		
		    if(position.equalsIgnoreCase("A")){
		    	pareigos = "2";
		    }
		    if(position.equalsIgnoreCase("V")){
		    	pareigos = "3";
		    }
		    else{
		    	pareigos = "1"; // Kai I - inžinierius 
		    }
		    
		    Class.forName("com.mysql.jdbc.Driver");
			connect = Clasifiers.getConnection();
			preparedStatement = connect
					.prepareStatement("INSERT INTO employee values ('"+ ID +"','"
							+ pareigos +"','"
							+ firstName +"','"
							+ surName +"','"
							+ mail +"','"
							+ phone +"');");

			preparedStatement.executeUpdate();  
		   
		 
		}
		}
		else
			Clients.showNotification("Nerasta Duomenu apie Darbuotojus!\n");
	}
	
	public void readClients(XSSFSheet sheet) throws Exception
	{
		String clientID;
		String name;
		String address;
		
		if(sheet != null)
		{

		for(int j = 1; j < sheet.getPhysicalNumberOfRows(); j++){
			
			clientID = sheet.getRow(j).getCell(0).getStringCellValue();

			 try {
					name = sheet.getRow(j).getCell(1).getStringCellValue();
		        	
				} catch (Exception  e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					name = null;
				}

			 try {
				 address = sheet.getRow(j).getCell(2).getStringCellValue();
		        	
				} catch (Exception  e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					address = null;
				}
			
		   //System.out.print("KlientoId: "+clientID+" Pavadinimas: "+name+" Adresas: "+address+" \n");
		   
		   String ID = clientID.substring(clientID.lastIndexOf("K") + 1);
		   Class.forName("com.mysql.jdbc.Driver");
		   connect = Clasifiers.getConnection();
		   preparedStatement = connect
					.prepareStatement("INSERT INTO client values ('"+ Integer.parseInt(ID) +"','"
							+ name +"','0000000" +"','"
							+ address +"','','');");
           // Traktuokim, kad kai im. k. 0000000 - mes neturim duomenu.
		   preparedStatement.executeUpdate();
		}
		}
		else
			Clients.showNotification("Nerasta Duomenu apie Klientus!\n");
	}
	
	public void readRepresentatives(XSSFSheet sheet) throws Exception
	{
		int ID;
		String repClient;
		String firstName;
		String lastName;
		String mail;
		String phone;
		boolean active;
		
		if(sheet != null)
		{

		for(int j = 1; j < sheet.getPhysicalNumberOfRows(); j++){
			
				ID = (int) sheet.getRow(j).getCell(0).getNumericCellValue();
				repClient = sheet.getRow(j).getCell(1).getStringCellValue();
				
				 try {
					 firstName = sheet.getRow(j).getCell(2).getStringCellValue();
			        	
					} catch (Exception  e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
						firstName = null;
					}
				
				 try {
					 lastName = sheet.getRow(j).getCell(3).getStringCellValue();
			        	
					} catch (Exception  e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
						lastName = null;
					}
				
				 try {
					 mail = sheet.getRow(j).getCell(5).getStringCellValue();
			        	
					} catch (Exception  e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
						mail = null;
					}
				
				 try {
					 phone = sheet.getRow(j).getCell(4).getStringCellValue();
			        	
					} catch (Exception  e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
						phone = null;
					}
				
				 try {
					 active = sheet.getRow(j).getCell(6).getBooleanCellValue();
			        	
					} catch (Exception  e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
						active = false;
					}
			    
				//System.out.print("AtstovoId: "+ID+" AtstovaujaKlienta: "+repClient+" Vardas: "+firstName+" Pavarde: "+lastName+" ElPastas: "+mail+" Telefonas: "+phone+" Aktyvus: "+active+" \n");
			   
			   String clientId = repClient.substring(repClient.lastIndexOf("K") + 1);
			   
			   Class.forName("com.mysql.jdbc.Driver");
			   connect = Clasifiers.getConnection();
			   preparedStatement = connect
						.prepareStatement("INSERT INTO delegates values ('"+ ID +"','"
								+ Integer.parseInt(clientId) + "','"
								+ firstName + "','"
								+ lastName + "','"
								+ phone + "','"
								+ mail + "','"
								+ active + "');");
			   
			  
	           preparedStatement.executeUpdate();		   
			   
			}			   
		}
		else
			Clients.showNotification("Nerasta Duomenu apie Atstovus!\n");
	}
	
	public void readContracts(XSSFSheet sheet) throws Exception
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date from;
		Date to;
		int ID;
		String contractID;
		String name;
		String clientID;
		
		String till = null, fr = null;
		
		if(sheet != null)
		{
		
		for(int j = 1; j < sheet.getPhysicalNumberOfRows(); j++){

			ID = (int) sheet.getRow(j).getCell(0).getNumericCellValue();

	        try {
	        	contractID = sheet.getRow(j).getCell(1).getStringCellValue();
			} catch (Exception  e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				contractID = null;
			}	
			
	        try {
	        	name = sheet.getRow(j).getCell(2).getStringCellValue();
			} catch (Exception  e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				name = "Nežinoma";
			}	
	        
			clientID = sheet.getRow(j).getCell(3).getStringCellValue();
			
	        try {
	        	from = sheet.getRow(j).getCell(4).getDateCellValue();
	        	fr = sdf.format(from);
			} catch (Exception  e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				from = new Date(0);
			}	

			  
		        try {
		        	to = sheet.getRow(j).getCell(5).getDateCellValue();
		        	till = sdf.format(from);
				} catch (Exception  e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					to = new Date(0);
				}	
		        
		    		   //System.out.print("SutartiesId: "+ ID +" SutartiesNr: "+ contractID +" Pavadinimas: "+ name +" KlientoId: "+ clientID +" DataNuo: "+ sdf.format(from) +" DataIki: "+ sdf.format(to) +" \n");
		    		   

		   String clientId = clientID.substring(clientID.lastIndexOf("K") + 1);
		   
		   Class.forName("com.mysql.jdbc.Driver");
		   connect = Clasifiers.getConnection();
		   preparedStatement = connect
					.prepareStatement("INSERT INTO contract values ('"+ ID +"','"
							+ contractID +"','"
							+ name +"','"
							+ Integer.parseInt(clientId) +"','"
							+ fr +"','"+ till +"');");
         
		   preparedStatement.executeUpdate();
		}
		}
		else
			Clients.showNotification("Nerasta Duomenu apie Sutartis!\n");
	}
	
	public void readServiceContracts(XSSFSheet sheet) throws Exception
	{
		int serviceID;
		String contractID;
		
		if(sheet != null)
		{

		for(int j = 1; j < sheet.getPhysicalNumberOfRows(); j++){
			
			serviceID = (int) sheet.getRow(j).getCell(0).getNumericCellValue();
			contractID = sheet.getRow(j).getCell(1).getStringCellValue();
			
			   //System.out.print("SutartiesId: "+serviceID+" paslaugosId: "+contractID+" \n");
			 
			String contractId = contractID.substring(contractID.lastIndexOf("P") + 1);
			   
			Class.forName("com.mysql.jdbc.Driver");
			   connect = Clasifiers.getConnection();
			   preparedStatement = connect
						.prepareStatement("INSERT INTO contractsServices values ('"+ serviceID +"','"+ Integer.parseInt(contractId)+"');");
	           preparedStatement.executeUpdate();
		}	   
		}
		else
			Clients.showNotification("Nerasta Duomenu apie Pasirašytas sutartis!\n");
	}
	
	public void readAppelations(XSSFSheet sheet) throws Exception
	{

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
		
		//Task
		if(sheet != null)
		{
		int rows = sheet.getPhysicalNumberOfRows();
		for(int j = 1; j < rows; j++){
	
			ID = (int) sheet.getRow(j).getCell(0).getNumericCellValue();
			clientID = sheet.getRow(j).getCell(1).getStringCellValue();
			serviceID = sheet.getRow(j).getCell(2).getStringCellValue();		
			
	        try {
	        	type = sheet.getRow(j).getCell(3).getStringCellValue();
	 		   if(type.equals("INC")){
				   type = "1";
			   }else{
				   type = "2";
			   }
	        	
			} catch (Exception  e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				type = null;
			}	
			
	        try {
	        	source = sheet.getRow(j).getCell(4).getStringCellValue();
	 		   if(source.equals("T")){
				   source = "1";
			   }
			   else if(source.equals("S")){
				   source = "2";
			   }else{
				   source = "3";
			   }
	 		   
			} catch (Exception  e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				source = null;
			}	
			
	        try {
	        	data = sheet.getRow(j).getCell(5).getStringCellValue();
	        	
			} catch (Exception  e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				data = null;
			}	
			
	        try {
	        	received = sheet.getRow(j).getCell(6).getDateCellValue();
	        	recv = sdf.format(received);
			} catch (Exception  e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				received = null;
			}	
			
	        try {
	        	completed = sheet.getRow(j).getCell(7).getDateCellValue();
	        	comp = sdf.format(completed);
			} catch (Exception  e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				completed = null;
			}	
			
	        try {
	        	status = sheet.getRow(j).getCell(8).getStringCellValue();
	 		   if(status.equals("I")){
				   status = "1";
			   }
			   else if(status.equals("P")){
				   status = "2";
			   }
			   else if(status.equals("A")){
				   status = "3";
			   }
			   else
				   status = "4";
			} catch (Exception  e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				status = null;
			}	
			
	        try {
	        	rank = (int)sheet.getRow(j).getCell(9).getNumericCellValue();
	        	
		        if(rank > 5 && rank <= 10)
		        	rank = rank / 2 + rank % 2;
	        	
			} catch (Exception  e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				rank = 0;
			}	
			
	        try {
	        	previous = (int)sheet.getRow(j).getCell(10).getNumericCellValue();
	        	
			} catch (Exception  e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				previous = 0;
			}	
			
		   String clientId = clientID.substring(clientID.lastIndexOf("K") + 1);
		   String serviceId = serviceID.substring(serviceID.lastIndexOf("P") + 1);
		   
//		   System.out.print("'Id: "+ ID +"','"+ Integer.parseInt(clientId) +"','"+ status +"','"+ type +"','"+sdf.format(received)+"','"+"0000-00-00 00:00:00"+
//					"','"+sdf.format(completed)+
//					"','"+c+
//					"','"+Integer.parseInt(serviceId) +
//					"','"+rank+
//					"','"+source+
//					"','"+sheet.getRow(j).getCell(5).getStringCellValue()+
//					"','"+sheet.getRow(j).getCell(3).getStringCellValue()+"');\n");
//		   
		   Class.forName("com.mysql.jdbc.Driver");
		   connect = Clasifiers.getConnection();
		   preparedStatement = connect
					.prepareStatement("INSERT INTO task values ('"+ ID +"','"
							+ Integer.parseInt(clientId) +"','"
							+ Integer.parseInt(status) +"','"
							+ Integer.parseInt(type) +"','"
							+recv+"','"
							+"1970-01-01 00:00:00"+"','"
							+comp+"','"
							+previous+"','"
							+Integer.parseInt(serviceId) +"','"
							+rank+"','"
							+Integer.parseInt(source)+"','"
							+data+"','"
							+null+"');");

		  
           preparedStatement.executeUpdate();
		}   
		}
		else
			Clients.showNotification("Nerasta Duomenu apie Kreipinius!\n");
	}
	
	public void readAssigments(XSSFSheet sheet) throws Exception
	{
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
		
		if(sheet != null)
		{
		int rows = sheet.getPhysicalNumberOfRows();
		for(int j = 1; j < rows; j++){

			ID = (int) sheet.getRow(j).getCell(0).getNumericCellValue();
			taskID = (int) sheet.getRow(j).getCell(1).getNumericCellValue();
			source = (int) sheet.getRow(j).getCell(2).getNumericCellValue();
			destination = (int) sheet.getRow(j).getCell(3).getNumericCellValue();

	        try {
	        	data = sheet.getRow(j).getCell(6).getStringCellValue();
	        	
			} catch (Exception  e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				data = null;
			}	
			
	        try {
	        	result = sheet.getRow(j).getCell(7).getStringCellValue();
	 		   if(result.equals("G")){
				   status = 1;
			   }else
				   if(result.equals("I")){
				   status = 2;
			   }
	        	
			} catch (Exception  e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				result = null;
			}	
	        
			time = (int) sheet.getRow(j).getCell(8).getNumericCellValue();
			
	        try {
	        	assigned = sheet.getRow(j).getCell(4).getDateCellValue();
	        	assig = sdf.format(assigned);
			} catch (Exception  e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				assigned = new Date(0);
			}	

		        try {
		        	returned = sheet.getRow(j).getCell(5).getDateCellValue();
		        	ret = sdf.format(returned);
				} catch (Exception  e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					returned = new Date(0);
				}	

//		   System.out.print("IPaskyrimoId: "+  +
//							"Kreipinys: "+  +
//							"Kas: "++
//							"Kam: "+  +
//							"Skirtas: "+sdf.format(assigned)+
//							"Grazintas: "+sdf.format(returned)+
//							"Tekstas: "+t+
//							"Rezultatas: "+status+
//							"SanaudosMin: "+sheet.getRow(j).getCell(8).getNumericCellValue()+"');\n");
		   
		   Class.forName("com.mysql.jdbc.Driver");
		   connect = Clasifiers.getConnection();
		   preparedStatement = connect             
					.prepareStatement("INSERT INTO taskAssignments values ('"+ ID +"','"
							+ taskID +"','"
							+source+"','"
							+ destination +"','"
							+assig+"','"
							+ret+"','"
							+data+"','"
							+status+"','"
							+time+"');");

           preparedStatement.executeUpdate();
		}
		}
		else
			Clients.showNotification("Nerasta Duomenu apie Paskyrimus!\n");
	}
	
	public int clearTableData(String name) throws Exception
	{
	//	System.out.print("SET FOREIGN_KEY_CHECKS=0; TRUNCATE "+name+"; TRUNCATE "+name+"; SET FOREIGN_KEY_CHECKS=1;\n");
setKey(0);
			Class.forName("com.mysql.jdbc.Driver");
			connect = Clasifiers.getConnection();
			preparedStatement = connect
						.prepareStatement("TRUNCATE "+name+";");
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
	
	public int setKey(int i) throws Exception
	{
		Class.forName("com.mysql.jdbc.Driver");
		connect = Clasifiers.getConnection();
		preparedStatement = connect
					.prepareStatement("SET FOREIGN_KEY_CHECKS="+i+";");
        try {
			preparedStatement.executeUpdate();
			return 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}	
	}
	
	void clearDB() throws Exception
	{
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
