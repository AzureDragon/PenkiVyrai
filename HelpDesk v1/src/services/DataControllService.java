package services;

import java.io.File;
import java.io.FileNotFoundException;

import com.lowagie.text.DocumentException;

public interface DataControllService {

	public void importData(String name);
	public File exportData() throws FileNotFoundException, DocumentException, Exception;
	void clearDB() throws Exception;

}
