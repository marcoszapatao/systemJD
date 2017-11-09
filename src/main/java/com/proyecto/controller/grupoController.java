package com.proyecto.controller;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.LinkedList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.proyecto.persistence.grupoDAO;
import com.proyecto.persistence.inventarioDAO;
import com.proyecto.persistence.vacunoDAO;
import com.proyecto.transferObject.grupoTO;
import com.proyecto.transferObject.vacunoTO;

@Controller
public class grupoController {
	@RequestMapping(value = "adminGrupo")
	public ModelAndView ingresar(ModelAndView vista) throws SQLException, ParseException {
		grupoDAO dao = new grupoDAO();
		vista.addObject("lista", dao.readAllG());
		vista.setViewName("adminGrupos");
		return vista;
	}
	@RequestMapping(value = "createGrupo")
	public ModelAndView createV(ModelAndView vista) throws SQLException, ParseException {
		vacunoDAO dao = new vacunoDAO();
		vista.addObject("lista", dao.readAllV());
		vista.setViewName("IngresarGrupo");
		return vista;
	}
	/*Lo mismo de arriba con ajax*/
	@RequestMapping(value = "/crearGrupo", method = RequestMethod.GET)
	public @ResponseBody LinkedList<vacunoTO> crearG() throws SQLException{
		vacunoDAO dao = new vacunoDAO();
		LinkedList<vacunoTO> list = dao.readVsinG();
		return list;
	}
	/*Crear grupo usando ajax y url*/
	@RequestMapping(value="/nuevoGrupo", method = RequestMethod.GET)
	public String nuevoG(@RequestParam(value = "diio", required = false, defaultValue = "World") String [] diio)  {
	    String x = "si";
	    System.out.println("Entro en nuevo grupo");
	    
	    for(int i=0;i<=5;i++) {
	    	System.out.println(diio[i]);
	    }

	    return x;	
	}
	
	/*Crear grupo con otro metodo*/
	@RequestMapping(value="/newG", method = RequestMethod.POST)
	public ModelAndView newG(@RequestParam(value = "name", required = false, defaultValue = "World") String name,
			@RequestParam(value = "estado", required = false, defaultValue = "World") String estado,
			@RequestParam(value = "fecha", required = false, defaultValue = "World") String fecha,
			@RequestParam(value = "peso", required = false, defaultValue = "World") int peso,
			@RequestParam(value = "diio", required = false, defaultValue = "World") String [] diio,
			ModelAndView vista
			) throws SQLException  {
		/*
	    for(int i=0;i<=5;i++) {
	    	System.out.println(diio[i]);
	    }
        System.out.println(name+estado+fecha+peso);
	   	*/
		grupoDAO dao = new grupoDAO();
		grupoTO to = new grupoTO();
		java.sql.Date fechaI = java.sql.Date.valueOf(fecha);
		to.setNombre(name);
		to.setEstado(estado);
		to.setFecha_ingreso(fechaI);
		to.setPeso(peso);
		
		dao.createGrupo(to,diio);
		
        grupoDAO daoo = new grupoDAO();
		vista.addObject("lista", daoo.readAllG());
		vista.setViewName("adminGrupos");
		return vista;
	}
}
