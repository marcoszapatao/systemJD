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
//import java.sql.Date;
import java.util.LinkedList;
import java.util.Locale;
import java.util.TimeZone;

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
import com.proyecto.persistence.grupoDAO;
import com.proyecto.persistence.inventarioDAO;
import com.proyecto.persistence.vacunoDAO;
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
			@RequestParam(value = "fechaT") String fechaT) throws ParseException, SQLException, MalformedURLException, IOException, DocumentException {
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
		
		if(tipo ==1) {
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
		Paragraph vacio2 = new Paragraph();
		vacio1.add("\n");

		// Declaramos un texto como Paragraph. Le podemos dar formato alineado, tamaño,
		// color, etc.
		Paragraph titulo = new Paragraph();
		Paragraph saltolinea = new Paragraph();
		Paragraph mensaje = new Paragraph();
		Paragraph fecha = new Paragraph();

		Paragraph totalV = new Paragraph();
		Paragraph tituloTipo = new Paragraph();
		Paragraph tipoV = new Paragraph();
		Paragraph tipoVa = new Paragraph();
		Paragraph tipoT= new Paragraph();
		Paragraph tituloEstado = new Paragraph();
		Paragraph estadoEn = new Paragraph();
		Paragraph estadoP = new Paragraph();
		Paragraph estadoV = new Paragraph();
		Paragraph footer = new Paragraph();
		Paragraph linea = new Paragraph();

		Image imagen = Image
				.getInstance("C:\\Users\\Marcosz\\Desktop\\DOC_Tesis\\logoJD.jpg");
		//http://www.carnesjd.cl/src/img/facebook/face_4.jpg
		
		imagen.scalePercent(10f);
		imagen.setAlignment(Element.ALIGN_LEFT);

		titulo.setFont(FontFactory.getFont("Times New Roman", 15, Font.BOLD));

		titulo.add("Sistema de gestión de engorda de vacunos");

		titulo.setAlignment(Element.ALIGN_CENTER);
        
		mensaje.setFont(FontFactory.getFont("Times New Roman",18,Font.BOLD));
		mensaje.add("Reporte: Inventario de animales");
		mensaje.setAlignment(Element.ALIGN_CENTER);
		
		linea.add("-----------------------------------------------------------------------------"
				+ "--------------------------------------------------------");
		String[] parts = fechaI.split("-");
		String part1 = parts[0]; 
		String part2 = parts[1]; 
		String part3 = parts[2];
		String fi = part3+"/"+part2+"/"+part1;
		
		String[] part = fechaT.split("-");
		String par1 = part[0]; 
		String par2 = part[1]; 
		String par3 = part[2];
		String fin = par3+"/"+par2+"/"+par1;
		fecha.setFont(FontFactory.getFont("Times New Roman",12));
		fecha.add("Datos considerados entre el período "+fi+" al "+fin+".");
		
		vacunoDAO vacuno = new vacunoDAO();
		int nro = vacuno.totalVacunos();
		
		int nroV = vacuno.totalVacunosTipo("Vacuno");
		int nroVa = vacuno.totalVacunosTipo("Vaquilla");
		int nroT = vacuno.totalVacunosTipo("Toro");
		int totalTipo = nroV+nroVa+nroT;
		
		
		grupoDAO grupo = new grupoDAO();
		int nroEs = grupo.totalVacunosEstado("Engorda");
		int nroEsP = grupo.totalVacunosEstado("Pradera");
		int nroEsV = grupo.totalVacunosEstado("Pradera");
		int totalnro=nroEs+nroEsP+nroEsV;

		PdfPTable table = new PdfPTable(2);
		table.setSpacingBefore(10);

		table.addCell("Tipo de Animal            Cantidad\n\n"
	             +"Vacuno                  :         "+nroV+"\n\n"
	             +"Vaquilla                 :         "+nroVa+"\n\n"
	             +"Toro                      :         "+nroT+"\n"
	             +"-----------------------------------------------------\n"
	             +"Total                     :         "+totalTipo+"\n");

		table.addCell("N° de animales por estado   Cantidad\n\n"
		             +"Engorda                         :        "+nroEs+"\n\n"
		             +"Pradera                          :        "+nroEsP+"\n\n"
		             +"Vendido                         :        "+nroEsV+"\n"
		             +"-----------------------------------------------------\n"
		             +"Total                              :        "+totalnro+"\n");
 

	
		


		
		// creamos la tabla con 3 columnas
		PdfPTable tabla = new PdfPTable(4);
		// añadimos contenido a las celdas

		// Cabecera
		tabla.addCell(new Paragraph("Nombre", FontFactory.getFont("arial", 14, Font.BOLD)));
		tabla.addCell(new Paragraph("Estado", FontFactory.getFont("arial", 14, Font.BOLD)));
		tabla.addCell(new Paragraph("Fecha Ingreso", FontFactory.getFont("arial", 14, Font.BOLD)));
		tabla.addCell(new Paragraph("Nro. de animales", FontFactory.getFont("arial", 14, Font.BOLD)));

		// Contenido
		int total = 0;
		for (int i = 0; i < inven.size(); i++) {
			tabla.addCell(new Paragraph(inven.get(i).getNombre()));
			tabla.addCell(new Paragraph(inven.get(i).getEstado()));
			tabla.addCell(new Paragraph(("" + inven.get(i).getFecha_ingreso())));
			tabla.addCell(new Paragraph("" + inven.get(i).getNroAnimales()));
			int totalAnimales = inven.get(i).getNroAnimales();
			total = total + totalAnimales ;
		}

		PdfPCell celdaFinal = new PdfPCell(new Paragraph("Total"));

		// Indicamos cuantas columnas ocupa la celda
		celdaFinal.setColspan(3);
		tabla.addCell(celdaFinal);
		tabla.addCell(new Paragraph("" + total));

		try {
			// Agregamos el texto al documento.
			documento.add(imagen);
			//documento.add(vacio1);

			//documento.add(saltolinea);
		    
		    documento.add(mensaje);
			documento.add(vacio2);
			documento.add(titulo);
			documento.add(saltolinea);
			documento.add(linea);
			documento.add(saltolinea);
			documento.add(fecha);
			documento.add(saltolinea);
			documento.add(table);
			documento.add(totalV);
			documento.add(saltolinea);
			documento.add(saltolinea);
			documento.add(tituloTipo);
			documento.add(saltolinea);
			documento.add(tipoV);
			documento.add(saltolinea);
			documento.add(tipoVa);
			documento.add(saltolinea);
			documento.add(tipoT);
			documento.add(saltolinea);
			documento.add(tituloEstado);
			documento.add(saltolinea);
			documento.add(estadoEn);
			documento.add(saltolinea);
			documento.add(estadoP);
			documento.add(saltolinea);
			documento.add(estadoV);
			
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
		}else {
			vista.setViewName("generarReporte");
			return vista;
		}
	}
}
