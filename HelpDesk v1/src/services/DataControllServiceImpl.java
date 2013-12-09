package services;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.zkoss.zk.ui.util.Clients;

public class DataControllServiceImpl implements DataControllService{

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
				return sheet;				

	    }
		return null;
	}
	
	@SuppressWarnings("null")
	public void readServices(HSSFSheet sheet)
	{
		if(sheet != null)
		{
	    HSSFRow row = null;
		int rows = sheet.getPhysicalNumberOfRows();

		for(int j = 1; j < rows; j++)
		   System.out.print("PaslaugosId: "+sheet.getRow(j).getCell(0).getStringCellValue()+" Pavadinimas: "+sheet.getRow(j).getCell(1).getStringCellValue()+" LH_INC: "+sheet.getRow(j).getCell(2)+" LH_REQ: "+sheet.getRow(j).getCell(3)+" \n");
		}
		else
			Clients.showNotification("Nerasta Duomenu apie Paslaugas!\n");
	}
	
	public void readEmployees(HSSFSheet sheet)
	{
		if(sheet != null)
		{
	    HSSFRow row = null;
		int rows = sheet.getPhysicalNumberOfRows();

		for(int j = 1; j < rows; j++)
		   System.out.print("PaslaugosId: "+sheet.getRow(j).getCell(0).getStringCellValue()+" Pavadinimas: "+sheet.getRow(j).getCell(1).getStringCellValue()+" LH_INC: "+sheet.getRow(j).getCell(2)+" LH_REQ: "+sheet.getRow(j).getCell(3)+" \n");
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
		   System.out.print("PaslaugosId: "+sheet.getRow(j).getCell(0).getStringCellValue()+" Pavadinimas: "+sheet.getRow(j).getCell(1).getStringCellValue()+" LH_INC: "+sheet.getRow(j).getCell(2)+" LH_REQ: "+sheet.getRow(j).getCell(3)+" \n");
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
		   System.out.print("PaslaugosId: "+sheet.getRow(j).getCell(0).getStringCellValue()+" Pavadinimas: "+sheet.getRow(j).getCell(1).getStringCellValue()+" LH_INC: "+sheet.getRow(j).getCell(2)+" LH_REQ: "+sheet.getRow(j).getCell(3)+" \n");
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

		for(int j = 1; j < rows; j++)
		   System.out.print("PaslaugosId: "+sheet.getRow(j).getCell(0).getStringCellValue()+" Pavadinimas: "+sheet.getRow(j).getCell(1).getStringCellValue()+" LH_INC: "+sheet.getRow(j).getCell(2)+" LH_REQ: "+sheet.getRow(j).getCell(3)+" \n");
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
		   System.out.print("PaslaugosId: "+sheet.getRow(j).getCell(0).getStringCellValue()+" Pavadinimas: "+sheet.getRow(j).getCell(1).getStringCellValue()+" LH_INC: "+sheet.getRow(j).getCell(2)+" LH_REQ: "+sheet.getRow(j).getCell(3)+" \n");
		}
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
		   System.out.print("PaslaugosId: "+sheet.getRow(j).getCell(0).getStringCellValue()+" Pavadinimas: "+sheet.getRow(j).getCell(1).getStringCellValue()+" LH_INC: "+sheet.getRow(j).getCell(2)+" LH_REQ: "+sheet.getRow(j).getCell(3)+" \n");
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
		   System.out.print("PaslaugosId: "+sheet.getRow(j).getCell(0).getStringCellValue()+" Pavadinimas: "+sheet.getRow(j).getCell(1).getStringCellValue()+" LH_INC: "+sheet.getRow(j).getCell(2)+" LH_REQ: "+sheet.getRow(j).getCell(3)+" \n");
		}
		else
			Clients.showNotification("Nerasta Duomenu apie Kreipinius!\n");
	}

	public void exportData()
	{
		
	}
}
