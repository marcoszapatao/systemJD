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

import com.proyecto.persistence.dietaDAO;
import com.proyecto.persistence.grupoDAO;
import com.proyecto.persistence.insumoDAO;
import com.proyecto.transferObject.dietaTO;
import com.proyecto.transferObject.resultadoDietaTO;
import com.proyecto.transferObject.vacunoTO;


@Controller
public class dietaController {
	@RequestMapping(value = "dietas")
	public ModelAndView admin(ModelAndView vista) throws SQLException, ParseException {
        
		grupoDAO dao = new grupoDAO();
		insumoDAO daov = new insumoDAO();
		vista.addObject("listaI",daov.readAllAlimento());
		vista.addObject("lista", dao.readAllG());
		vista.setViewName("calcularDieta");
		return vista;
	}
	@RequestMapping(value="calculando", method = RequestMethod.POST)
	public @ResponseBody LinkedList<resultadoDietaTO> calculo(
			@RequestParam(value = "grupo", required = false, defaultValue = "World")int idGrupo,
			@RequestParam(value = "semana", required = false, defaultValue = "World") int semana,
			@RequestParam(value = "insumos", required = false, defaultValue = "World") String [] insumos,
			ModelAndView vista
			) throws SQLException  {
		dietaDAO dao = new dietaDAO();
		LinkedList<resultadoDietaTO> list=dao.calculandoDieta(idGrupo, semana, insumos);
		return list;
        
	}
	@RequestMapping(value="descontar", method = RequestMethod.POST)
	public @ResponseBody int descuenta(@RequestParam(value = "valores", required = false, defaultValue = "World")String valores) throws SQLException{
		
		String[] parts = valores.split("iii");
		dietaDAO dao = new dietaDAO();
		int devolver = dao.descuentaInsumos(parts);
		return devolver;
	}
	
}
