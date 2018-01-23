package com.proyecto.controller;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.LinkedList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.proyecto.persistence.inventarioDAO;
import com.proyecto.transferObject.vacunoTO;

@Controller
public class inventarioController {
	@RequestMapping(value = "inventario")
	public ModelAndView ver(ModelAndView vista) throws SQLException, ParseException {
		inventarioDAO dao = new inventarioDAO();
		vista.addObject("lista", dao.readAllInven());
		vista.setViewName("inventario");
		return vista;
	}
	

	@RequestMapping(value = "/verInventa", method = RequestMethod.GET)
	public @ResponseBody LinkedList<vacunoTO> verInven(@RequestParam(value = "id", required = false, defaultValue = "World") int idVacuno) throws SQLException{
		inventarioDAO dao = new inventarioDAO();
		LinkedList<vacunoTO> list = dao.leeVacunos(idVacuno);
		return list;
	}
}
