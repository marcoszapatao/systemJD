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
import com.proyecto.transferObject.cantidadDisponibleTO;
import com.proyecto.transferObject.dietaTO;
import com.proyecto.transferObject.insumoTO;
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
	
	/*---------CRUD DE DIETAS-----------*/
	
	@RequestMapping(value = "adminDietas")
	public ModelAndView adminDieta(ModelAndView vista) throws SQLException, ParseException {
		insumoDAO daoi = new insumoDAO();
		vista.addObject("li", daoi.readAllAlimento());
		
		dietaDAO dietaDAO = new dietaDAO();
		LinkedList<dietaTO> lista = new LinkedList<>();
		LinkedList<insumoTO> listaMenu = new LinkedList<>();
		if (!dietaDAO.readAllD().isEmpty()) {
			lista = dietaDAO.readAllD();
	
			vista.addObject("lista", lista);

			for (int i = 0; i < lista.size(); i++) {
				int idInsumo=lista.get(i).getIdInsumo();
				listaMenu.add(insumoDAO.read(idInsumo));

			}

			vista.addObject("cantidad", listaMenu);
			vista.setViewName("adminDietas");
			
		} else {
			vista.setViewName("login");
			
		}
		return vista;
	}
	@RequestMapping(value = "/saveDieta", method = RequestMethod.POST)
	public ModelAndView dieta(
			@RequestParam(value = "insumo", required = false, defaultValue = "World") int idinsumo,
			@RequestParam(value = "semana", required = false, defaultValue = "World") int semana,
			@RequestParam(value = "proporcion", required = false, defaultValue = "World") float proporcion,
			ModelAndView vista) throws SQLException {
		
		dietaTO dieta = new dietaTO();
		dieta.setProporcionDieta(proporcion);
		dieta.setSemanaDieta(semana);
		dieta.setIdInsumo(idinsumo);
		
		dietaDAO dao = new dietaDAO();
		
		int j = dao.saveDieta(dieta);
		/*************************/
		insumoDAO daoi = new insumoDAO();
		vista.addObject("li", daoi.readAllAlimento());
		
		dietaDAO dietaDAO = new dietaDAO();
		LinkedList<dietaTO> lista = new LinkedList<>();
		LinkedList<insumoTO> listaMenu = new LinkedList<>();
		if (!dietaDAO.readAllD().isEmpty()) {
			lista = dietaDAO.readAllD();
	
			vista.addObject("lista", lista);

			for (int i = 0; i < lista.size(); i++) {
				int idInsumo=lista.get(i).getIdInsumo();
				listaMenu.add(insumoDAO.read(idInsumo));

			}

			vista.addObject("cantidad", listaMenu);
			vista.setViewName("adminDietas");
			
		} else {
			vista.setViewName("login");
			
		}
		return vista;
	}
	@RequestMapping(value = "/deleteDieta", method = RequestMethod.GET)
	public ModelAndView eliminaD(
			@RequestParam(value = "id", required = false, defaultValue = "World") int id, ModelAndView vista) throws SQLException {
		dietaDAO dao = new dietaDAO();
        dao.deleteDieta(id);
        
        
        
		insumoDAO daoi = new insumoDAO();
		vista.addObject("li", daoi.readAllAlimento());
		
		dietaDAO dietaDAO = new dietaDAO();
		LinkedList<dietaTO> lista = new LinkedList<>();
		LinkedList<insumoTO> listaMenu = new LinkedList<>();
		if (!dietaDAO.readAllD().isEmpty()) {
			lista = dietaDAO.readAllD();
	
			vista.addObject("lista", lista);

			for (int i = 0; i < lista.size(); i++) {
				int idInsumo=lista.get(i).getIdInsumo();
				listaMenu.add(insumoDAO.read(idInsumo));

			}

			vista.addObject("cantidad", listaMenu);
			vista.setViewName("adminDietas");
			
		} else {
			vista.setViewName("login");
			
		}
        return vista;
	}
	
	@RequestMapping(value = "/editarD", method = RequestMethod.POST)
	@ResponseBody
	public dietaTO editarIn(
			@RequestParam(value = "id", required = false, defaultValue = "World") int id, ModelAndView vista) throws SQLException {

		dietaDAO dao = new dietaDAO();
        dietaTO dieta = dao.read(id);
		return dieta;
	}
	
	@RequestMapping(value = "/actualizarDieta", method = RequestMethod.GET)
	public ModelAndView actualizarV(
			@RequestParam(value = "idDieta", required = false, defaultValue = "World") int id, 
			@RequestParam(value = "semana", required = false, defaultValue = "World") int semana,
			@RequestParam(value = "proporcion", required = false, defaultValue = "World") float pro,
			ModelAndView vista) throws SQLException {
		dietaDAO dao = new dietaDAO();
		dietaTO to = new dietaTO();
		to.setIdDieta(id);
		to.setSemanaDieta(semana);
		to.setProporcionDieta(pro);
		dao.update(to);
		
		insumoDAO daoi = new insumoDAO();
		vista.addObject("li", daoi.readAllAlimento());
		
		dietaDAO dietaDAO = new dietaDAO();
		LinkedList<dietaTO> lista = new LinkedList<>();
		LinkedList<insumoTO> listaMenu = new LinkedList<>();
		if (!dietaDAO.readAllD().isEmpty()) {
			lista = dietaDAO.readAllD();
	
			vista.addObject("lista", lista);

			for (int i = 0; i < lista.size(); i++) {
				int idInsumo=lista.get(i).getIdInsumo();
				listaMenu.add(insumoDAO.read(idInsumo));

			}

			vista.addObject("cantidad", listaMenu);
			vista.setViewName("adminDietas");
			
		} else {
			vista.setViewName("login");
			
		}
        return vista;
	}
	
}
