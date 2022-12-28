package ar.com.llegolaslutz.atencionpsicologica.utils;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import ar.com.llegolaslutz.atencionpsicologica.entity.HistoriaClinica;
import ar.com.llegolaslutz.atencionpsicologica.entity.Profesional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class GeneratePdfReport {

	private static final Logger logger = LoggerFactory.getLogger(GeneratePdfReport.class);

	public static ByteArrayInputStream historiaClinica(List<HistoriaClinica> historia, Profesional profesional ) {

		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		PdfWriter.getInstance(document, out);
		document.setPageSize(PageSize.LETTER);
		document.setMargins(20, 20, 40, 20);

		document.open();

		try {
			
			
			
			PdfPTable tablaEncabezado = new PdfPTable(1);
			tablaEncabezado.setSpacingAfter(10);
			
			Font fuenteEncabezado = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, new Color(102, 187, 106));

			PdfPCell historiaEncabezado = new PdfPCell(new Phrase("Historia Clinica", fuenteEncabezado));
			historiaEncabezado.setBorder(0);
			historiaEncabezado.setPadding(30);
			historiaEncabezado.setBackgroundColor(new Color(255, 167, 38));
			historiaEncabezado.setVerticalAlignment(Element.ALIGN_CENTER);
			historiaEncabezado.setHorizontalAlignment(Element.ALIGN_CENTER);

			tablaEncabezado.addCell(historiaEncabezado);
			document.add(tablaEncabezado);
			
			DateTimeFormatter formatters = DateTimeFormatter.ofPattern("d/MM/uuuu");

			for (HistoriaClinica historiaDiaria : historia) {

				Paragraph date = new Paragraph();
				
				date.add(historiaDiaria.getDate().format(formatters).toString());
				date.setAlignment(Element.ALIGN_LEFT);
				date.setSpacingAfter(5);
				document.add(date);

				Paragraph data = new Paragraph();
				data.add(historiaDiaria.getData());
				data.setAlignment(Element.ALIGN_JUSTIFIED);
				data.setSpacingAfter(10);
				document.add(data);
				
				Paragraph firma = new Paragraph();
				firma.add(profesional.getNombre() + " " + profesional.getApellido());
				firma.setAlignment(Element.ALIGN_RIGHT);
				firma.setSpacingAfter(10);
				document.add(firma);
				
				Paragraph matricula = new Paragraph();
				matricula.add(profesional.getMatricula());
				matricula.setAlignment(Element.ALIGN_RIGHT);
				matricula.setSpacingAfter(10);
				document.add(matricula);
				
			}

			document.close();

		} catch (DocumentException ex) {

			logger.error("Error occurred: {0}", ex);
		}

		return new ByteArrayInputStream(out.toByteArray());
	}
}
