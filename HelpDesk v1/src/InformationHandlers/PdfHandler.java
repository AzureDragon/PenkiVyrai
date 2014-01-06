package InformationHandlers;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.Clasifiers;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;

public class PdfHandler {

	private Connection connect = null;
	private PreparedStatement preparedStatement = null;
	
	@SuppressWarnings("unused")
	public void createOutPut(String query, int numberOfCells, String str[], String base[], float size[], String pav[], Document document, String text) throws Exception {

		try {
			HeaderFooter footer = new HeaderFooter(new Phrase(
					"Puslapio numeris: ", new Font()), true);
			footer.setBorder(Rectangle.NO_BORDER);
			footer.setAlignment(Element.ALIGN_RIGHT);
			document.setFooter(footer);

			// HeaderFooter header = new HeaderFooter(new
			// Phrase("Informacija apie authentification", new Font()), false);
			// header.setAlignment(Element.ALIGN_RIGHT);
			// document.setHeader(header);
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		}

		Paragraph paragraphs = new Paragraph(text);
		Paragraph paragraph = new Paragraph(text);
		paragraphs.setAlignment(Element.ALIGN_RIGHT);
		paragraphs.setFont(new Font(Font.COURIER, 20, Font.BOLD));

		paragraph.add(paragraphs);
		paragraph.add(insertTable(query, numberOfCells, str,
				base, size, pav));
		document.newPage();
		document.add(paragraph);
		Font font = new Font(Font.COURIER, 10, Font.BOLD);
		font.setColor(new Color(0x92, 0x90, 0x83));

	}

	@SuppressWarnings("unused")
	public PdfPTable insertTable(String query, int cells, String labels[],
			String type[], float[] size, String names[]) throws Exception {
		Class.forName("com.mysql.jdbc.Driver");

		connect = Clasifiers.getConnection();
		preparedStatement = connect.prepareStatement(query);

		ResultSet resultSet = preparedStatement
				.executeQuery("select * from authentificationservice");

		PdfPTable my_report_table = new PdfPTable(cells);

		my_report_table.setWidths(size);

		Font bfBold12 = new Font(Font.BOLD, 12);
		Font bf12 = new Font(12);

		for (int i = 0; i < cells; i++) {
			insertCell(my_report_table, names[i], Element.ALIGN_CENTER, 1,
					bfBold12, new Color(0xC0, 0xC0, 0xC0));
		}

		my_report_table.setHeaderRows(1);
		while (resultSet.next()) {
			for (int i = 0; i < cells; i++) {
				switch (type[i]) {
				case ("Int"):

					insertCell(my_report_table, Integer.toString(resultSet
							.getInt(labels[i])), Element.ALIGN_LEFT, 1, bf12,
							new Color(0xFF, 0xFF, 0xFF));
					break;
				case ("String"):
					insertCell(my_report_table, resultSet.getString(labels[i]),
							Element.ALIGN_LEFT, 1, bf12, new Color(0xFF, 0xFF,
									0xFF));
					break;
				case ("Date"):

					insertCell(my_report_table, resultSet.getDate(labels[i])
							.toString(), Element.ALIGN_LEFT, 1, bf12,
							new Color(0xFF, 0xFF, 0xFF));
					break;
				case ("Boolean"):
					insertCell(my_report_table, String.valueOf(resultSet
							.getBoolean(labels[i])), Element.ALIGN_LEFT, 1,
							bf12, new Color(0xFF, 0xFF, 0xFF));
					break;
				case ("Double"):
					insertCell(my_report_table, String.valueOf(Double
							.toString(resultSet.getDouble(labels[i]))),
							Element.ALIGN_LEFT, 1, bf12, new Color(0xFF, 0xFF,
									0xFF));
					break;
				default:
					insertCell(my_report_table, "neapibrėžtas tipas",
							Element.ALIGN_LEFT, 1, bf12, new Color(0xFF, 0xFF,
									0xFF));
					break;

				}
			}
		}

		connect.close();

		return my_report_table;
	}

	private void insertCell(PdfPTable table, String text, int align,
			int colspan, Font font, Color color) {

		PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
		cell.setHorizontalAlignment(align);
		cell.setColspan(colspan);
		cell.setBackgroundColor(color);
		if (text.trim().equalsIgnoreCase("")) {
			cell.setMinimumHeight(10f);
		}
		table.addCell(cell);

	}

	
}
