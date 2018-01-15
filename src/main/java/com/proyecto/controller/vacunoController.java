package com.proyecto.controller;

//import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.proyecto.persistence.razaDAO;
import com.proyecto.persistence.vacunoDAO;

import com.proyecto.transferObject.vacunoTO;

@Controller
public class vacunoController {
	@RequestMapping(value = "admin")
	public ModelAndView ingresarV(ModelAndView vista) throws SQLException, ParseException {
		vacunoDAO dao = new vacunoDAO();
		razaDAO raza = new razaDAO();
		vista.addObject("raza",raza.readAll());
		vista.addObject("lista", dao.readAllV());
		vista.setViewName("adminVacunos");
		return vista;
	}
	@RequestMapping(value = "create")
	public ModelAndView createV() throws SQLException, ParseException {
		ModelAndView vista=new ModelAndView();
		vista.setViewName("IngresarVacuno2");
		return vista;
	}
	
	@RequestMapping(value = "/saveVacuno", method = RequestMethod.POST)
	public ModelAndView login(
			@RequestParam(value = "diio", required = false, defaultValue = "World") String diio,
			@RequestParam(value = "raza", required = false, defaultValue = "World") String raza,
			@RequestParam(value = "tipo", required = false, defaultValue = "World") String tipo,
			@RequestParam(value = "sexo", required = false, defaultValue = "World") String sexo,
			@RequestParam(value = "fecha_in", required = false, defaultValue = "World") String fecha_in,
			ModelAndView vista) throws SQLException, ParseException {
		    /*
			// Validar fechas
			DateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
			Date date1 = parser.parse(fecha_in);
			Date fechaSistema = new Date();
	
			if (date1.getDate() > fechaSistema.getDate() || date1.getMonth() > fechaSistema.getMonth()
					|| date1.getYear() > fechaSistema.getYear()) {
				vista.addObject("validaF", "fecha ingresada posterior a la fecha actual");
				
				vacunoDAO daoo = new vacunoDAO();
		        razaDAO razaV = new razaDAO();
		        vista.addObject("raza",razaV.readAll());
				vista.addObject("lista", daoo.readAllV());
				vista.setViewName("adminVacunos");
				return vista;
				// hasta aqui se valida fecha
	
			}*/
		
			vacunoDAO dao = new vacunoDAO();
			vacunoTO to = new vacunoTO();
			java.sql.Date fecha = java.sql.Date.valueOf(fecha_in);
			to.setFechaIngreso(fecha);
			to.setDiio(diio);
			to.setRaza(raza);
			to.setTipo(tipo);
			to.setSexo(sexo);
			// to.setFecha(dateMenu);
	
			int j = dao.createVacuno(to);
	        if(j==0) {
		        vacunoDAO daoo = new vacunoDAO();
		        razaDAO razaV = new razaDAO();
		        vista.addObject("correcto","insercion correcta");
		        vista.addObject("raza",razaV.readAll());
				vista.addObject("lista", daoo.readAllV());
				vista.setViewName("adminVacunos");
				return vista;
	        }else {
		        vacunoDAO daoo = new vacunoDAO();
		        razaDAO razaV = new razaDAO();
		        vista.addObject("incorrecto","insercion incorrecta");
		        vista.addObject("raza",razaV.readAll());
				vista.addObject("lista", daoo.readAllV());
				vista.setViewName("adminVacunos");
				return vista;
	        }
	}
	
	@RequestMapping(value = "/deleteVacuno", method = RequestMethod.GET)
	public ModelAndView eliminaV(
			@RequestParam(value = "id", required = false, defaultValue = "World") String diio, ModelAndView vista) throws SQLException {
		vacunoDAO dao = new vacunoDAO();
        vacunoTO vacuno = dao.read(diio);
        dao.delete(vacuno);
		
        vacunoDAO daoo = new vacunoDAO();
        razaDAO razaV = new razaDAO();
        vista.addObject("raza",razaV.readAll());
		vista.addObject("lista", daoo.readAllV());
		vista.setViewName("adminVacunos");
		return vista;
	}
	
	@RequestMapping(value = "/editarV", method = RequestMethod.GET)
	public ModelAndView editarV(
			@RequestParam(value = "id", required = false, defaultValue = "World") String diio, ModelAndView vista) throws SQLException {
        vacunoDAO dao = new vacunoDAO();
        vacunoTO task = dao.read(diio);
		
		vista.addObject("update", task);
		vista.setViewName("editarVacuno");
		return vista;
	}
	
	//Prueba de JQUERY
	@RequestMapping(value = "/editarVa", method = RequestMethod.GET)
	@ResponseBody
	public vacunoTO editarVa(
			@RequestParam(value = "id", required = false, defaultValue = "World") String diio, ModelAndView vista) throws SQLException {

		vacunoDAO dao = new vacunoDAO();
        vacunoTO vacuno = dao.read(diio);
		return vacuno;
	}
	
	@RequestMapping(value = "/actualizarVacuno", method = RequestMethod.POST)
	public ModelAndView actualizarV(
			@RequestParam(value = "diioo", required = false, defaultValue = "World") String diio, 
			@RequestParam(value = "raza", required = false, defaultValue = "World") String raza,
			@RequestParam(value = "tipo", required = false, defaultValue = "World") String tipo,
			@RequestParam(value = "sexoV", required = false, defaultValue = "World") String sexo,
			@RequestParam(value = "fecha_in", required = false, defaultValue = "World") String fecha_in,
			ModelAndView vista) throws SQLException {
	        System.out.println("diio en actualizar " +diio);
	        vacunoDAO dao = new vacunoDAO();
	        vacunoTO to = new vacunoTO();
			java.sql.Date fecha = java.sql.Date.valueOf(fecha_in);
			to.setFechaIngreso(fecha);
			to.setDiio(diio);
			to.setRaza(raza);
			to.setTipo(tipo);
			to.setSexo(sexo);
			dao.update(to);
			
	        vacunoDAO daoo = new vacunoDAO();
	        razaDAO razaV = new razaDAO();
	        vista.addObject("raza",razaV.readAll());
			vista.addObject("lista", daoo.readAllV());
			vista.setViewName("adminVacunos");
			return vista;
	}
	
	
	
}


