package com.proyecto.controller;

import java.sql.SQLException;
import java.text.ParseException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.proyecto.persistence.grupoDAO;
import com.proyecto.persistence.vacunoDAO;

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
}
