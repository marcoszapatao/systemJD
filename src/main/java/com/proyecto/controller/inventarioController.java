package com.proyecto.controller;

import java.sql.SQLException;
import java.text.ParseException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.proyecto.persistence.inventarioDAO;

@Controller
public class inventarioController {
	@RequestMapping(value = "inventario")
	public ModelAndView ingresar(ModelAndView vista) throws SQLException, ParseException {
		inventarioDAO dao = new inventarioDAO();
		vista.addObject("lista", dao.readAllInven());
		vista.setViewName("inventario");
		return vista;
	}
}
