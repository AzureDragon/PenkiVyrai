package services;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import model.Clasifiers;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.zkoss.zk.ui.util.Clients;

public class DataControllServiceImpl implements DataControllService{

	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	
	public void importData(String name)
	{
		File file = new File(".");
		for(String fileNames : file.list()) System.out.println(fileNames);
		
		try {
		    POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(name));
		    HSSFWorkbook wb = new HSSFWorkbook(fs);
	    	readServices(getSheet(wb, "Paslaugos"));
	    	readEmployees(getSheet(wb, "Darbuotojai"));
	    	readClients(getSheet(wb, "Klientai"));
	    	readRepresentatives(getSheet(wb, "Atstovai"));
	    	readContracts(getSheet(wb, "Sutartys"));
	    	readServiceContracts(getSheet(wb, "SutPasl"));
	    	readAppelations(getSheet(wb, "Kreipiniai"));
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
	
	@SuppressWarnings("null")
	public void readServices(HSSFSheet sheet) throws Exception
	{
		if(sheet != null)
		{
	    HSSFRow row = null;
		int rows = sheet.getPhysicalNumberOfRows();
        String ID = "";
		for(int j = 1; j < rows; j++){
			
			
		   System.out.print("PaslaugosId: "+sheet.getRow(j).getCell(0).getStringCellValue()+
				   			" Pavadinimas: "+sheet.getRow(j).getCell(1).getStringCellValue()+
				   			" LH_INC: "+sheet.getRow(j).getCell(2).getNumericCellValue()+
				   			" LH_REQ: "+sheet.getRow(j).getCell(3).getNumericCellValue()+" \n");
		   
		   ID = sheet.getRow(j).getCell(0).getStringCellValue().substring(sheet.getRow(j).getCell(0).getStringCellValue().lastIndexOf("P") + 1);
		   Class.forName("com.mysql.jdbc.Driver");
		   connect = Clasifiers.getConnection();
		   preparedStatement = connect
					.prepareStatement("INSERT INTO employee values ('"+ ID +"','"+ sheet.getRow(j).getCell(1).getStringCellValue() +"','"
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
	    HSSFRow row = null;
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
	
	public void readClients(HSSFSheet sheet)
	{
		if(sheet != null)
		{
	    HSSFRow row = null;
		int rows = sheet.getPhysicalNumberOfRows();

		for(int j = 1; j < rows; j++)
		   System.out.print("KlientoId: "+sheet.getRow(j).getCell(0).getStringCellValue()+
				    		" Pavadinimas: "+sheet.getRow(j).getCell(1).getStringCellValue()+
				    		" Adresas: "+sheet.getRow(j).getCell(2).getStringCellValue()+" \n");
		}
		else
			Clients.showNotification("Nerasta Duomenu apie Klientus!\n");
	}
	
	public void readRepresentatives(HSSFSheet sheet)
	{
		if(sheet != null)
		{
	    HSSFRow row = null;
		int rows = sheet.getPhysicalNumberOfRows();

		for(int j = 1; j < rows; j++)
			   System.out.print("AtstovoId: "+sheet.getRow(j).getCell(0).getNumericCellValue()+
					   			" AtstovaujaKlienta: "+sheet.getRow(j).getCell(1).getStringCellValue()+
					   			" Vardas: "+sheet.getRow(j).getCell(2).getStringCellValue()+
					   			" Pavarde: "+sheet.getRow(j).getCell(3).getStringCellValue()+
					   			" ElPastas: "+sheet.getRow(j).getCell(4).getStringCellValue()+
					   			" Telefonas: "+sheet.getRow(j).getCell(5).getStringCellValue()+
					   			" Aktyvus: "+sheet.getRow(j).getCell(6).getBooleanCellValue()+" \n");
		}
		else
			Clients.showNotification("Nerasta Duomenu apie Atstovus!\n");
	}
	
	public void readContracts(HSSFSheet sheet)
	{
		if(sheet != null)
		{
	    HSSFRow row = null;
		int rows = sheet.getPhysicalNumberOfRows();
		System.out.println(rows+" "+(sheet.getRow(1).getCell(0).getNumericCellValue()+
				sheet.getRow(1).getCell(1).getStringCellValue()+
				sheet.getRow(1).getCell(2).getStringCellValue()+
				sheet.getRow(1).getCell(3).getStringCellValue()+
				sheet.getRow(1).getCell(4).getDateCellValue()+
				sheet.getRow(1).getCell(5).getDateCellValue()));
		
		for(int j = 1; j < rows; j++)
		   System.out.print("SutartiesId: "+sheet.getRow(j).getCell(0).getNumericCellValue()+
				   			" SutartiesNr: "+sheet.getRow(j).getCell(1).getStringCellValue()+
				   			" Pavadinimas: "+sheet.getRow(j).getCell(2).getStringCellValue()+
				   			" KlientoId: "+sheet.getRow(j).getCell(3).getStringCellValue()+
				   			" DataNuo: "+sheet.getRow(j).getCell(4).getDateCellValue()+
				   			" DataIki: "+sheet.getRow(j).getCell(5).getDateCellValue()+" \n");
		}
		else
			Clients.showNotification("Nerasta Duomenu apie Sutartis!\n");
	}
	
	public void readServiceContracts(HSSFSheet sheet)
	{
		if(sheet != null)
		{
	    HSSFRow row = null;
		int rows = sheet.getPhysicalNumberOfRows();

		for(int j = 1; j < rows; j++)
			   System.out.print("SutartiesId: "+sheet.getRow(j).getCell(0).getStringCellValue()+
			   			" paslaugosId: "+sheet.getRow(j).getCell(1).getStringCellValue()+" \n");		}
		else
			Clients.showNotification("Nerasta Duomenu apie Pasirašytas sutartis!\n");
	}
	
	public void readAssigments(HSSFSheet sheet)
	{
		if(sheet != null)
		{
	    HSSFRow row = null;
		int rows = sheet.getPhysicalNumberOfRows();

		for(int j = 1; j < rows; j++)
		   System.out.print("PaskyrimoId: "+sheet.getRow(j).getCell(0).getNumericCellValue()+
				   			" Kreipinys: "+sheet.getRow(j).getCell(1).getNumericCellValue()+
				   			" Kas: "+sheet.getRow(j).getCell(2).getNumericCellValue()+
				   			" Kam: "+sheet.getRow(j).getCell(3).getNumericCellValue()+
				   			" Skirtas: "+sheet.getRow(j).getCell(4).getDateCellValue()+
				   			" Grazintas: "+sheet.getRow(j).getCell(5).getDateCellValue()+
				   			" Tekstas: "+sheet.getRow(j).getCell(6).getStringCellValue()+
				   			" Rezultatas: "+sheet.getRow(j).getCell(7).getStringCellValue()+
				   			" SanaudosMin: "+sheet.getRow(j).getCell(8).getNumericCellValue()+
				   			" \n");
		}
		else
			Clients.showNotification("Nerasta Duomenu apie Paskyrimus!\n");
	}
	
	public void readAppelations(HSSFSheet sheet)
	{
		if(sheet != null)
		{
	    HSSFRow row = null;
		int rows = sheet.getPhysicalNumberOfRows();

		for(int j = 1; j < rows; j++)
		   System.out.print("PaslaugosId: "+sheet.getRow(j).getCell(0).getStringCellValue()+
				   			" Tipas: "+sheet.getRow(j).getCell(1).getStringCellValue()+
				   			" Kanalas: "+sheet.getRow(j).getCell(2).getStringCellValue()+
				   			" KreipinioTekstas: "+sheet.getRow(j).getCell(3).getStringCellValue()+
				   			" Gauta: "+sheet.getRow(j).getCell(4).getDateCellValue()+
				   			" Baigta: "+sheet.getRow(j).getCell(5).getDateCellValue()+
				   			" Busena: "+sheet.getRow(j).getCell(6).getStringCellValue()+
				   			" Ivertinimas: "+sheet.getRow(j).getCell(7).getNumericCellValue()+
				   			" AnkstesnisKreipinys: "+sheet.getRow(j).getCell(8).getStringCellValue()+
				   			" \n");
		}
		else
			Clients.showNotification("Nerasta Duomenu apie Kreipinius!\n");
	}

	public void exportData()
	{
		
	}
}
