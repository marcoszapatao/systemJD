package com.proyecto.controller;

import java.sql.SQLException;
import java.text.ParseException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.proyecto.persistence.razaDAO;
import com.proyecto.persistence.vacunoDAO;
import com.proyecto.transferObject.razaTO;
import com.proyecto.transferObject.vacunoTO;


@Controller
public class razaController {
	@RequestMapping(value = "adminRazas")
	public ModelAndView ingresarV(ModelAndView vista) throws SQLException, ParseException {
		razaDAO dao = new razaDAO();
		vista.addObject("lista", dao.readAll());
		vista.setViewName("adminRazas");
		return vista;
	}
	
	@RequestMapping(value = "/saveRaza", method = RequestMethod.POST)
	public ModelAndView save(
			@RequestParam(value = "nombre", required = false, defaultValue = "World") String nombre,
			@RequestParam(value = "descripcion", required = false, defaultValue = "World") String descripcion,
			ModelAndView vista) throws SQLException {
		    razaDAO dao = new razaDAO();
		    razaTO to = new razaTO();
		    to.setNombreRaza(nombre);
		    to.setDescripcionRaza(descripcion);
		    
		    int r = dao.createRaza(to);
		    
		    if(r==0) {
		    	razaDAO daoo= new razaDAO();
		    	vista.addObject("lista",daoo.readAll());
		    	vista.addObject("correcto","insercion correcta");
		    	vista.setViewName("adminRazas");
		    }else {
		    	razaDAO daoo= new razaDAO();
		    	vista.addObject("lista",daoo.readAll());
		    	vista.addObject("incorrecto","insercion incorrecta");
		    	vista.setViewName("adminRazas");
		    }
		return vista;
	}
	
	@RequestMapping(value = "/deleteRaza", method = RequestMethod.GET)
	public ModelAndView eliminaV(
			@RequestParam(value = "id", required = false, defaultValue = "World") int id, ModelAndView vista) throws SQLException {
		
		razaDAO dao = new razaDAO();
		razaTO to = dao.read(id);
		
		int r = dao.delete(to);
		if(r==0) {
			razaDAO daoo= new razaDAO();
	    	vista.addObject("lista",daoo.readAll());
	    	vista.setViewName("adminRazas");
			return vista;
		}else {
			razaDAO daoo= new razaDAO();
			vista.addObject("elimino","Problema al eliminar");
	    	vista.addObject("lista",daoo.readAll());
	    	vista.setViewName("adminRazas");
			return vista;
		}

	}
	
	@RequestMapping(value = "/editarRa", method = RequestMethod.GET)
	@ResponseBody
	public razaTO editarRa(
			@RequestParam(value = "id", required = false, defaultValue = "World") int id, ModelAndView vista) throws SQLException {

		razaDAO dao = new razaDAO();
        razaTO raza = dao.read(id);
		return raza;
	}
	
	@RequestMapping(value = "/actualizarRaza", method = RequestMethod.POST)
	public ModelAndView actualizarV(
			@RequestParam(value = "id", required = false, defaultValue = "World") int id,
			@RequestParam(value = "diioo", required = false, defaultValue = "World") String nombre, 
			@RequestParam(value = "descrip", required = false, defaultValue = "World") String descrip,
			ModelAndView vista) throws SQLException {
		
		razaDAO dao = new razaDAO();
		razaTO to = new razaTO();
		to.setIdRaza(id);
		to.setNombreRaza(nombre);
		to.setDescripcionRaza(descrip);
		
		dao.update(to);
		razaDAO daoo= new razaDAO();
    	vista.addObject("lista",daoo.readAll());
    	vista.setViewName("adminRazas");
		return vista;
	}
}
