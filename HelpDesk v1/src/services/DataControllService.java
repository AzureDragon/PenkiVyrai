package services;

import java.io.File;
import java.io.FileNotFoundException;

import com.lowagie.text.DocumentException;

public interface DataControllService {

	public void importData(String name);
	public File exportData(boolean dataset1, boolean dataset2) throws FileNotFoundException, DocumentException, Exception;
	void clearDB() throws Exception;

}
