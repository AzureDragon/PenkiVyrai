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
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.zkoss.zk.ui.util.Clients;

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
		
		try {
		    POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(name));
		    HSSFWorkbook wb = new HSSFWorkbook(fs);
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
	
	private HSSFSheet getSheet(HSSFWorkbook wb, String name)
	{
		
		for(int i = 0; i < wb.getNumberOfSheets(); i++)
	    {
			HSSFSheet sheet = wb.getSheetAt(i);
			if(sheet.getSheetName().toString().equals(name))
			{
				System.out.println("Rado");
				return sheet;				
			}

	    }
		System.out.println("neRado");
		return null;
	}
	
	public void readServices(HSSFSheet sheet) throws Exception
	{
		if(sheet != null)
		{
		int rows = sheet.getPhysicalNumberOfRows();
        String ID = "";
		for(int j = 1; j < rows; j++){
			
			
		   System.out.print("PaslaugosId: "+sheet.getRow(j).getCell(0).getStringCellValue()+
				   			" Pavadinimas: "+sheet.getRow(j).getCell(1).getStringCellValue()+
				   			" LH_INC: "+sheet.getRow(j).getCell(2).getNumericCellValue()+
				   			" LH_REQ: "+sheet.getRow(j).getCell(3).getNumericCellValue()+" \n");
		   
		   ID = sheet.getRow(j).getCell(0).getStringCellValue().substring(sheet.getRow(j).getCell(0).getStringCellValue().lastIndexOf("P") + 1);
		   System.out.print(ID);
		   Class.forName("com.mysql.jdbc.Driver");
		   connect = Clasifiers.getConnection();
		   preparedStatement = connect
					.prepareStatement("INSERT INTO service values ('"+ Integer.parseInt(ID) +"','"+ sheet.getRow(j).getCell(1).getStringCellValue() +"','"
							+ sheet.getRow(j).getCell(0).getStringCellValue() +"','"+ sheet.getRow(j).getCell(1).getStringCellValue() +"','"
							+ sheet.getRow(j).getCell(2).getNumericCellValue() +"','"+ sheet.getRow(j).getCell(3).getNumericCellValue() +"');");

		   preparedStatement.executeUpdate();    
		   
		}
		}
		else
			Clients.showNotification("Nerasta Duomenu apie Paslaugas!\n");
	}
	
	public void readEmployees(HSSFSheet sheet) throws Exception
	{
		if(sheet != null)
		{
		int rows = sheet.getPhysicalNumberOfRows();
        String pareigos = "";
		for(int j = 1; j < rows; j++){
		   System.out.print("DarboId: "+sheet.getRow(j).getCell(0).getNumericCellValue()+
				   			" Vardas: "+sheet.getRow(j).getCell(1).getStringCellValue()+
				   			" Pavarde: "+sheet.getRow(j).getCell(2).getStringCellValue()+
				   			" Pareigos: "+sheet.getRow(j).getCell(3).getStringCellValue()+
				   			" Telefonas: "+sheet.getRow(j).getCell(4).getStringCellValue()+
				   			" Mail: "+sheet.getRow(j).getCell(5).getStringCellValue()+" \n");
		
		    if(sheet.getRow(j).getCell(3).getStringCellValue().equalsIgnoreCase("A")){
		    	pareigos = "2";
		    }
		    if(sheet.getRow(j).getCell(3).getStringCellValue().equalsIgnoreCase("V")){
		    	pareigos = "3";
		    }
		    else{
		    	pareigos = "1"; // Kai I - inžinierius 
		    }
		    Class.forName("com.mysql.jdbc.Driver");
			connect = Clasifiers.getConnection();
			preparedStatement = connect
					.prepareStatement("INSERT INTO employee values ('"+ sheet.getRow(j).getCell(0).getNumericCellValue() +"','"+ pareigos +"','"
							+ sheet.getRow(j).getCell(1).getStringCellValue() +"','"+ sheet.getRow(j).getCell(2).getStringCellValue() +"','"
							+ sheet.getRow(j).getCell(5).getStringCellValue() +"','"+ sheet.getRow(j).getCell(4).getStringCellValue() +"');");

			preparedStatement.executeUpdate();  
		   
		 
		}
		
		
		}
		else
			Clients.showNotification("Nerasta Duomenu apie Darbuotojus!\n");
	}
	
	public void readClients(HSSFSheet sheet) throws Exception
	{
		if(sheet != null)
		{
		int rows = sheet.getPhysicalNumberOfRows();

		for(int j = 1; j < rows; j++){
		   System.out.print("KlientoId: "+sheet.getRow(j).getCell(0).getStringCellValue()+
				    		" Pavadinimas: "+sheet.getRow(j).getCell(1).getStringCellValue()+
				    		" Adresas: "+sheet.getRow(j).getCell(2).getStringCellValue()+" \n");
		   
		   String ID = sheet.getRow(j).getCell(0).getStringCellValue().substring(sheet.getRow(j).getCell(0).getStringCellValue().lastIndexOf("K") + 1);
		   Class.forName("com.mysql.jdbc.Driver");
		   connect = Clasifiers.getConnection();
		   preparedStatement = connect
					.prepareStatement("INSERT INTO client values ('"+ Integer.parseInt(ID) +"','"+ sheet.getRow(j).getCell(1).getStringCellValue() +"','0000000" +
							"','"+ sheet.getRow(j).getCell(2).getStringCellValue() +"','','');");
           // Traktuokim, kad kai im. k. 0000000 - mes neturim duomenu.
		   preparedStatement.executeUpdate();
		}
		}
		else
			Clients.showNotification("Nerasta Duomenu apie Klientus!\n");
	}
	
	public void readRepresentatives(HSSFSheet sheet) throws Exception
	{
		if(sheet != null)
		{
		int rows = sheet.getPhysicalNumberOfRows();

		for(int j = 1; j < rows; j++){
			   System.out.print("AtstovoId: "+sheet.getRow(j).getCell(0).getNumericCellValue()+
					   			" AtstovaujaKlienta: "+sheet.getRow(j).getCell(1).getStringCellValue()+
					   			" Vardas: "+sheet.getRow(j).getCell(2).getStringCellValue()+
					   			" Pavarde: "+sheet.getRow(j).getCell(3).getStringCellValue()+
					   			" ElPastas: "+sheet.getRow(j).getCell(4).getStringCellValue()+
					   			" Telefonas: "+sheet.getRow(j).getCell(5).getStringCellValue()+
					   			" Aktyvus: "+sheet.getRow(j).getCell(6).getBooleanCellValue()+" \n");
			   
			   String clientId = sheet.getRow(j).getCell(1).getStringCellValue().substring(sheet.getRow(j).getCell(1).getStringCellValue().lastIndexOf("K") + 1);
			   
			   Class.forName("com.mysql.jdbc.Driver");
			   connect = Clasifiers.getConnection();
			   preparedStatement = connect
						.prepareStatement("INSERT INTO delegates values ('"+ sheet.getRow(j).getCell(0).getNumericCellValue() +"','"+ clientId +
								"','"+ sheet.getRow(j).getCell(2).getStringCellValue() +"','"+sheet.getRow(j).getCell(3).getStringCellValue()+
								"','"+sheet.getRow(j).getCell(5).getStringCellValue()+"','"+sheet.getRow(j).getCell(4).getStringCellValue()+
								"','"+sheet.getRow(j).getCell(6).getBooleanCellValue()+"');");
			   
			  
	           preparedStatement.executeUpdate();		   
			   
			}			   
		}
		else
			Clients.showNotification("Nerasta Duomenu apie Atstovus!\n");
	}
	
	public void readContracts(HSSFSheet sheet) throws Exception
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(sheet != null)
		{
		int rows = sheet.getPhysicalNumberOfRows();

		for(int j = 1; j < rows; j++){

		   System.out.print("priejo");
		   Date from;
	        try {
	        	from = sheet.getRow(j).getCell(4).getDateCellValue();
			} catch (Exception  e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				from = new Date(0);
			}	

			   Date to;
		        try {
		        	to = sheet.getRow(j).getCell(5).getDateCellValue();
				} catch (Exception  e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					to = new Date(0);
				}	
		        
		    		   System.out.print("SutartiesId: "+sheet.getRow(j).getCell(0).getNumericCellValue()+
		    				   			" SutartiesNr: "+sheet.getRow(j).getCell(1).getStringCellValue()+
		    				   			" Pavadinimas: "+sheet.getRow(j).getCell(2).getStringCellValue()+
		    				   			" KlientoId: "+sheet.getRow(j).getCell(3).getStringCellValue()+
		    				   			" DataNuo: "+sdf.format(from)+
		    				   			" DataIki: "+sdf.format(to)+" \n");
		    		   

		   String clientId = sheet.getRow(j).getCell(3).getStringCellValue().substring(sheet.getRow(j).getCell(3).getStringCellValue().lastIndexOf("K") + 1);
		   Class.forName("com.mysql.jdbc.Driver");
		   connect = Clasifiers.getConnection();
		   preparedStatement = connect
					.prepareStatement("INSERT INTO contract values ('"+ sheet.getRow(j).getCell(0).getNumericCellValue() +"','"+ sheet.getRow(j).getCell(1).getStringCellValue() +"','"
							+ sheet.getRow(j).getCell(2).getStringCellValue() +"','0','"+ Integer.parseInt(clientId) +"','"
							+ sdf.format(from) +"','"+ sdf.format(to) +"');");
           //Nebepamenu, kam mes groupId naudojam. Tai statinį nulį palikau
		   //Ištryniau serviceId, nes nesueis taip mums.
		   //Sukūriau lentelę ContractsServices, kuriuoje nurodoma sutartis ir paslaugos - viena sutartis gali turėti daug paslaugų (sakiau, kad reiks)
		   preparedStatement.executeUpdate();
		}
		}
		else
			Clients.showNotification("Nerasta Duomenu apie Sutartis!\n");
	}
	
	public void readServiceContracts(HSSFSheet sheet) throws Exception
	{
		if(sheet != null)
		{
		int rows = sheet.getPhysicalNumberOfRows();

		for(int j = 1; j < rows; j++){
			   System.out.print("SutartiesId: "+sheet.getRow(j).getCell(0).getNumericCellValue()+
			   			" paslaugosId: "+sheet.getRow(j).getCell(1).getStringCellValue()+" \n");
			   String serviceId = sheet.getRow(j).getCell(1).getStringCellValue().substring(sheet.getRow(j).getCell(1).getStringCellValue().lastIndexOf("P") + 1);
			   Class.forName("com.mysql.jdbc.Driver");
			   connect = Clasifiers.getConnection();
			   preparedStatement = connect
						.prepareStatement("INSERT INTO contractsServices values ('"+ sheet.getRow(j).getCell(0).getNumericCellValue() +"','"+ Integer.parseInt(serviceId)+"');");
	           preparedStatement.executeUpdate();
		}	   
		}
		else
			Clients.showNotification("Nerasta Duomenu apie Pasirašytas sutartis!\n");
	}
	
	public void readAppelations(HSSFSheet sheet) throws Exception
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		//Task
		if(sheet != null)
		{
		int rows = sheet.getPhysicalNumberOfRows();
		String type="";
		int status=-1;
		String source="";
		for(int j = 1; j < rows; j++){
	
			   Date received;
		        try {
		        	received = sheet.getRow(j).getCell(6).getDateCellValue();
		        	
				} catch (Exception  e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					received = new Date(0);
				}	
			
			   Date completed;
		        try {
		        	completed = sheet.getRow(j).getCell(7).getDateCellValue();
		        	
				} catch (Exception  e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					completed = new Date(0);
				}	
		        
		        String c;
		        try {
		        	c = sheet.getRow(j).getCell(10).getStringCellValue();
		        	
				} catch (Exception  e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					c = "-1";
				}	
		        
				   int rank;
			        try {
			        	rank = (int)sheet.getRow(j).getCell(9).getNumericCellValue();
			        	
					} catch (Exception  e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
						rank = -1;
					}	
			        
			        if(rank > 5 && rank <= 10)
			        	rank = rank / 2 + rank % 2;
			        else
			        	rank = -1;
			
		   String clientId = sheet.getRow(j).getCell(1).getStringCellValue().substring(sheet.getRow(j).getCell(1).getStringCellValue().lastIndexOf("K") + 1);
		   String serviceId = sheet.getRow(j).getCell(2).getStringCellValue().substring(sheet.getRow(j).getCell(2).getStringCellValue().lastIndexOf("P") + 1);
		   if(sheet.getRow(j).getCell(3).getStringCellValue().equals("INC")){
			   type = "1";
		   }else{
			   type = "2";
		   }
		   
		   if(sheet.getRow(j).getCell(8).getStringCellValue().equals("I")){
			   status = 1;
		   }
		   else if(sheet.getRow(j).getCell(8).getStringCellValue().equals("P")){
			   status = 2;
		   }
		   else if(sheet.getRow(j).getCell(8).getStringCellValue().equals("A")){
			   status = 3;
		   }
		   
		   if(sheet.getRow(j).getCell(4).getStringCellValue().equals("T")){
			   source = "1";
		   }
		   else if(sheet.getRow(j).getCell(4).getStringCellValue().equals("S")){
			   source = "2";
		   }else{
			   source = "3";
		   }
		   
		   System.out.print("'"+ sheet.getRow(j).getCell(0).getNumericCellValue() +
					"','"+ Integer.parseInt(clientId) +
					"','"+ status +
					"','"+ type +
					"','"+sdf.format(received)+
					"','"+"0000-00-00 00:00:00"+
					"','"+sdf.format(completed)+
					"','"+c+
					"','"+Integer.parseInt(serviceId) +
					"','"+rank+
					"','"+source+
					"','"+sheet.getRow(j).getCell(5).getStringCellValue()+
					"','"+sheet.getRow(j).getCell(3).getStringCellValue()+"');\n");
		   
		   Class.forName("com.mysql.jdbc.Driver");
		   connect = Clasifiers.getConnection();
		   preparedStatement = connect
					.prepareStatement("INSERT INTO task values ('"+ sheet.getRow(j).getCell(0).getNumericCellValue() +
							"','"+ Integer.parseInt(clientId) +
							"','"+ status +
							"','"+ type +
							"','"+sdf.format(received)+
							"','"+"0000-00-00 00:00:00"+
							"','"+sdf.format(completed)+
							"','"+c+
							"','"+Integer.parseInt(serviceId) +
							"','"+rank+
							"','"+source+
							"','"+sheet.getRow(j).getCell(5).getStringCellValue()+
							"','"+sheet.getRow(j).getCell(3).getStringCellValue()+"');");
		   
		  
           preparedStatement.executeUpdate();
		}   
		}
		else
			Clients.showNotification("Nerasta Duomenu apie Kreipinius!\n");
	}
	
	public void readAssigments(HSSFSheet sheet) throws Exception
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		if(sheet != null)
		{
		int rows = sheet.getPhysicalNumberOfRows();
		int status = -1;
		for(int j = 1; j < rows; j++){
		   Date assigned;
	        try {
	        	assigned = sheet.getRow(j).getCell(4).getDateCellValue();
	        	
			} catch (Exception  e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				assigned = new Date(0);
			}	

			   Date returned;
		        try {
		        	returned = sheet.getRow(j).getCell(5).getDateCellValue();
		        	
				} catch (Exception  e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					returned = new Date(0);
				}	

	        String c;
	        try {
	        	c = sheet.getRow(j).getCell(7).getStringCellValue();
	        	
			} catch (Exception  e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				c = "N";
			}	

	        String t;
	        try {
	        	t = sheet.getRow(j).getCell(6).getStringCellValue();
	        	
			} catch (Exception  e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				t = "-";
			}	

		   if(c.equals("G")){
			   status = 1;
		   }else
			   if(c.equals("I")){
			   status = 2;
		   }
			   else
				   status = -1;

		   System.out.print("IPaskyrimoId: "+ sheet.getRow(j).getCell(0).getNumericCellValue() +
							"Kreipinys: "+ sheet.getRow(j).getCell(1).getNumericCellValue() +
							"Kas: "+sheet.getRow(j).getCell(2).getNumericCellValue()+
							"Kam: "+ sheet.getRow(j).getCell(3).getNumericCellValue() +
							"Skirtas: "+sdf.format(assigned)+
							"Grazintas: "+sdf.format(returned)+
							"Tekstas: "+t+
							"Rezultatas: "+status+
							"SanaudosMin: "+sheet.getRow(j).getCell(8).getNumericCellValue()+"');\n");
		   
		   Class.forName("com.mysql.jdbc.Driver");
		   connect = Clasifiers.getConnection();
		   preparedStatement = connect             
					.prepareStatement("INSERT INTO taskAssignments values ('"+ sheet.getRow(j).getCell(0).getNumericCellValue() +
							"','"+ sheet.getRow(j).getCell(1).getNumericCellValue() +
							"','"+sheet.getRow(j).getCell(2).getNumericCellValue()+
							"','"+ sheet.getRow(j).getCell(3).getNumericCellValue() +
							"','"+sdf.format(assigned)+
							"','"+sdf.format(returned)+
							"','"+t+
							"','"+status+
							"','"+sheet.getRow(j).getCell(8).getNumericCellValue()+"');");
		  
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
	
}
