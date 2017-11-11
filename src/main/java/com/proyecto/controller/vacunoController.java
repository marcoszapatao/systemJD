package com.proyecto.controller;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


import com.proyecto.persistence.vacunoDAO;

import com.proyecto.transferObject.vacunoTO;

@Controller
public class vacunoController {
	@RequestMapping(value = "admin")
	public ModelAndView ingresarV(ModelAndView vista) throws SQLException, ParseException {
		vacunoDAO dao = new vacunoDAO();
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
			@RequestParam(value = "fecha_in", required = false, defaultValue = "World") String fecha_in,
			ModelAndView vista) throws SQLException, ParseException {
		 
			vacunoDAO dao = new vacunoDAO();
			vacunoTO to = new vacunoTO();
			java.sql.Date fecha = java.sql.Date.valueOf(fecha_in);
			to.setFechaIngreso(fecha);
			to.setDiio(diio);
			to.setRaza(raza);
			to.setTipo(tipo);
			// to.setFecha(dateMenu);
	
			dao.createVacuno(to);
	
	        vacunoDAO daoo = new vacunoDAO();
			vista.addObject("lista", daoo.readAllV());
			vista.setViewName("adminVacunos");
			return vista;
	}
	
	@RequestMapping(value = "/deleteVacuno", method = RequestMethod.GET)
	public ModelAndView eliminaV(
			@RequestParam(value = "id", required = false, defaultValue = "World") String diio, ModelAndView vista) throws SQLException {
		vacunoDAO dao = new vacunoDAO();
        vacunoTO vacuno = dao.read(diio);
        dao.delete(vacuno);
		
        vacunoDAO daoo = new vacunoDAO();
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
		System.out.println("En ediat : "+vacuno.getDiio());
		//vista.addObject("update", task);
		//vista.setViewName("editarVacuno");
		return vacuno;
	}
	
	@RequestMapping(value = "/actualizarVacuno", method = RequestMethod.GET)
	public ModelAndView actualizarV(
			@RequestParam(value = "diioo", required = false, defaultValue = "World") String diio, 
			@RequestParam(value = "raza", required = false, defaultValue = "World") String raza,
			@RequestParam(value = "tipo", required = false, defaultValue = "World") String tipo,
			@RequestParam(value = "fecha_in", required = false, defaultValue = "World") String fecha_in,
			ModelAndView vista) throws SQLException {
	        
	        vacunoDAO dao = new vacunoDAO();
	        vacunoTO to = new vacunoTO();
			java.sql.Date fecha = java.sql.Date.valueOf(fecha_in);
			System.out.println(diio);
			System.out.println(raza);
			System.out.println(tipo);
			System.out.println(fecha);
			to.setFechaIngreso(fecha);
			to.setDiio(diio);
			to.setRaza(raza);
			to.setTipo(tipo);
			dao.update(to);
			
	        vacunoDAO daoo = new vacunoDAO();
			vista.addObject("lista", daoo.readAllV());
			vista.setViewName("adminVacunos");
			return vista;
	}
	
	
	
}


