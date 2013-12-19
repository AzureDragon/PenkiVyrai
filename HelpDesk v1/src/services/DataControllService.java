package services;

import java.io.File;
import java.io.FileNotFoundException;

import org.zkoss.util.media.Media;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfWriter;

public interface DataControllService {

	public void importData(String name);
	public File exportData() throws FileNotFoundException, DocumentException;
}
