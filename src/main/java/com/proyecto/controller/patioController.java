package com.proyecto.controller;

import java.sql.SQLException;
import java.text.ParseException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.proyecto.persistence.patioDAO;
import com.proyecto.persistence.proveedorDAO;
import com.proyecto.transferObject.patioTO;
import com.proyecto.transferObject.proveedorTO;


@Controller
public class patioController {
	@RequestMapping(value = "adminPatios")
	public ModelAndView verP(ModelAndView vista) throws SQLException, ParseException {
		patioDAO dao = new patioDAO();
		vista.addObject("lista", dao.readAllP());
		vista.setViewName("adminPatio");
		return vista;
	}
	
	@RequestMapping(value = "/savePatio", method = RequestMethod.POST)
	public ModelAndView save(
			@RequestParam(value = "nombre", required = false, defaultValue = "World") String nombre,
			@RequestParam(value = "nro", required = false, defaultValue = "World") int nro,
			ModelAndView vista) throws SQLException {
		
		patioDAO dao = new patioDAO();
		patioTO to = new patioTO();
		to.setNombrePatio(nombre);
		to.setNroNavesPatio(nro);
	    int r = dao.createPatio(to);
	    
	    if(r==0) {
	    	patioDAO daoo= new patioDAO();
	    	vista.addObject("lista",daoo.readAllP());
	    	vista.addObject("correcto","insercion correcta");
	    	vista.setViewName("adminPatio");
	    }else {
	    	proveedorDAO daoo= new proveedorDAO();
	    	vista.addObject("lista",daoo.readAllP());
	    	vista.addObject("incorrecto","insercion incorrecta");
	    	vista.setViewName("adminPatio");
	    }
		
		return vista;
	}
	
	@RequestMapping(value = "/deletePatio", method = RequestMethod.GET)
	public ModelAndView eliminaP(
			@RequestParam(value = "id", required = false, defaultValue = "World") int id, ModelAndView vista) throws SQLException {
		
		patioDAO dao = new patioDAO();
		patioTO to = dao.read(id);
		
		int r = dao.delete(to);
		if(r==0) {
			patioDAO daoo= new patioDAO();
	    	vista.addObject("lista",daoo.readAllP());
	    	vista.setViewName("adminPatio");
			return vista;
		}else {
			proveedorDAO daoo= new proveedorDAO();
			vista.addObject("elimino","Problema al eliminar");
	    	vista.addObject("lista",daoo.readAllP());
	    	vista.setViewName("adminPatio");
			return vista;
		}

	}
	@RequestMapping(value = "/editarPa", method = RequestMethod.GET)
	@ResponseBody
	public patioTO editarP(
			@RequestParam(value = "id", required = false, defaultValue = "World") int id, ModelAndView vista) throws SQLException {

		patioDAO dao = new patioDAO();
        patioTO patio = dao.read(id);
		return patio;
	}
	@RequestMapping(value = "actualizarPatio", method = RequestMethod.POST)
	public ModelAndView actualizarV(
			@RequestParam(value = "idP", required = false, defaultValue = "World") int id,
			@RequestParam(value = "nombreP", required = false, defaultValue = "World") String nombre, 
			@RequestParam(value = "nroP", required = false, defaultValue = "World") int nro,
			ModelAndView vista) throws SQLException {
		
		patioDAO dao = new patioDAO();
		patioTO to = new patioTO();
		to.setIdPatio(id);
		to.setNombrePatio(nombre);
		to.setNroNavesPatio(nro);
		
		dao.update(to);
		patioDAO daoo= new patioDAO();
    	vista.addObject("lista",daoo.readAllP());
    	vista.setViewName("adminPatio");
		return vista;
	}
}
