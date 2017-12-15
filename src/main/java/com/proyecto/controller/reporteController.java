package com.proyecto.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.proyecto.persistence.inventarioDAO;
import com.proyecto.transferObject.inventarioTO;

@Controller
public class reporteController {
	
	@RequestMapping(value = "generarInforme")
	public ModelAndView generarReporte(ModelAndView vista) {
		vista.setViewName("generarReporte");
		return vista;
   }
	
	@RequestMapping(value="descargaInforme")
	public ModelAndView descargaInforme(ModelAndView vista,  HttpSession sesion, HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "tipo") int tipo,
			@RequestParam(value = "fechaI") String fechaI,
			@RequestParam(value = "fechaT") String fechaT) throws ParseException, SQLException, BadElementException, MalformedURLException, IOException {
		System.out.println(tipo+" "+fechaI+" "+fechaT);
		
		if((tipo != 1 && tipo != 2)||(fechaI=="")||(fechaT=="")) {
			vista.setViewName("generarReporte");
			vista.addObject("seleccion", "Debe seleccionar los datos requeridos");
			return vista;
		}else {
		
		// Validar fechas
		DateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = parser.parse(fechaI);
		Date fechaSistema = new Date();

		if (date1.getDate() > fechaSistema.getDate() || date1.getMonth() > fechaSistema.getMonth()
				|| date1.getYear() > fechaSistema.getYear()) {
			vista.addObject("fechaAnteriorI", "fecha ingresada anterior a la fecha actual");
			vista.setViewName("generarReporte");
			return vista;
			// hasta aqui se valida fecha

		}
		
		// Validar fechas
		DateFormat parser2 = new SimpleDateFormat("yyyy-MM-dd");
		Date date2 = parser2.parse(fechaT);
		//Date fechaSistema2 = new Date();

		if (date2.getDate() > fechaSistema.getDate() || date2.getMonth() > fechaSistema.getMonth()
				|| date2.getYear() > fechaSistema.getYear()) {
			vista.addObject("fechaAnteriorT", "fecha ingresada anterior a la fecha actual");
			vista.setViewName("generarReporte");
			return vista;
			// hasta aqui se valida fecha

		}
		}
		/*AQUI COMIENZA GENERACION DE REPORTE*/
		inventarioDAO inventario = new inventarioDAO();
		LinkedList<inventarioTO> inven = new LinkedList<inventarioTO>();
		inven = inventario.readAllInvenxFecha(fechaI, fechaT);
		
		
		
		// Escribir PDF

		String FILE_NAME = "informe.pdf";

		// Declaramos un documento como un objecto Document.
		Document documento = new Document(PageSize.LETTER);
		// writer es declarado como el método utilizado para escribir en el archivo.
		PdfWriter pdfWriter = null;

		try {
			// Obtenemos la instancia del archivo a utilizar.
			pdfWriter = PdfWriter.getInstance(documento, new FileOutputStream(new File(FILE_NAME)));
		} catch (FileNotFoundException | DocumentException ex) {
			ex.getMessage();
		}

		// Abrimos el documento a editar.
		documento.open();

		// Creamos un párrafo nuevo llamado "vacio1" para espaciar los elementos.
		Paragraph vacio1 = new Paragraph();
		vacio1.add("\n\n");

		// Declaramos un texto como Paragraph. Le podemos dar formato alineado, tamaño,
		// color, etc.
		Paragraph titulo = new Paragraph();
		Paragraph saltolinea = new Paragraph();
		Paragraph footer = new Paragraph();

		Image imagen = Image
				.getInstance("http://www.carnesjd.cl/src/img/facebook/face_4.jpg");

		imagen.scalePercent(75f);
		imagen.setAlignment(Element.ALIGN_JUSTIFIED);

		titulo.setFont(FontFactory.getFont("Times New Roman", 14, Font.BOLD));

		titulo.add("Informe Inventario de animales");

		titulo.setAlignment(Element.ALIGN_CENTER);

		// creamos la tabla con 3 columnas
		PdfPTable tabla = new PdfPTable(4);
		// añadimos contenido a las celdas

		// Cabecera
		tabla.addCell(new Paragraph("Nombre", FontFactory.getFont("arial", 14, Font.BOLD)));
		tabla.addCell(new Paragraph("Tipo", FontFactory.getFont("arial", 14, Font.BOLD)));
		tabla.addCell(new Paragraph("Precio", FontFactory.getFont("arial", 14, Font.BOLD)));
		tabla.addCell(new Paragraph("Total Vendidos", FontFactory.getFont("arial", 14, Font.BOLD)));

		// Contenido

		for (int i = 0; i < inven.size(); i++) {
			tabla.addCell(new Paragraph(inven.get(i).getNombre()));
			tabla.addCell(new Paragraph(inven.get(i).getEstado()));
			tabla.addCell(new Paragraph(("" + inven.get(i).getFecha_ingreso())));
			tabla.addCell(new Paragraph("" + inven.get(i).getNroAnimales()));
		}

		PdfPCell celdaFinal = new PdfPCell(new Paragraph("Total"));

		// Indicamos cuantas columnas ocupa la celda
		celdaFinal.setColspan(3);
		tabla.addCell(celdaFinal);
		tabla.addCell(new Paragraph("" + sesion.getAttribute("total")));

		try {
			// Agregamos el texto al documento.
			documento.add(imagen);

			documento.add(vacio1);

			documento.add(saltolinea);
			documento.add(titulo);
			documento.add(saltolinea);
			documento.add(vacio1);
			documento.add(tabla);
			documento.add(saltolinea);
			documento.add(footer);
			documento.add(vacio1);

		} catch (DocumentException ex) {
			ex.getMessage();
		}

		// Cerramos el documento.
		documento.close();
		// Cerramos el writer.
		pdfWriter.close();

		System.out.println("PDF Creado Correctamente");

		//// Descargar PDF////

		FileInputStream fis = new FileInputStream(FILE_NAME);

		int read = 0;
		String contentType = "application/pdf";
		response.setContentType(contentType);
		response.setHeader("Content-Disposition", "attachment;filename=\"" + FILE_NAME + "\"");

		ServletOutputStream out = response.getOutputStream();

		byte[] bytes = new byte[1000];

		while ((read = fis.read(bytes)) != -1) {
			out.write(bytes, 0, read);
		}

		out.flush();
		out.close();
		
		vista.setViewName("generarReporte");
		return vista;
	}
}
