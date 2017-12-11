package com.proyecto.controller;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.proyecto.persistence.insumoDAO;
import com.proyecto.persistence.proveedorDAO;
import com.proyecto.persistence.vacunoDAO;
import com.proyecto.transferObject.insumoTO;
import com.proyecto.transferObject.vacunoTO;

@Controller
public class insumoController {
	@RequestMapping(value = "adminInsumo")
	public ModelAndView admin(ModelAndView vista) throws SQLException, ParseException {
		
		insumoDAO dao = new insumoDAO();
		vista.addObject("lista", dao.readAllI());
		vista.setViewName("adminInsumos");
		
	
		return vista;
	}
	
	@RequestMapping(value = "registrarCompra")
	public ModelAndView registra(ModelAndView vista) throws SQLException, ParseException {
		insumoDAO dao = new insumoDAO();
		vista.addObject("lista", dao.readAllI());
		
		proveedorDAO daop = new proveedorDAO();
		vista.addObject("proveedores",daop.readAllP());
		
		vista.setViewName("registraCompra");
		return vista;
	}
	
	@RequestMapping(value = "/saveInsumo", method = RequestMethod.POST)
	public ModelAndView login(
			@RequestParam(value = "nombre", required = false, defaultValue = "World") String nombre,
			@RequestParam(value = "descripcion", required = false, defaultValue = "World") String descripcion,
			@RequestParam(value = "tipo", required = false, defaultValue = "World") String tipo,
			ModelAndView vista) throws SQLException, ParseException {
		 
			insumoDAO dao = new insumoDAO();
			insumoTO to = new insumoTO();
			
            to.setNombre_insumo(nombre);
            to.setDescripcion_insumo(descripcion);
            to.setTipoInsumo(tipo);
			dao.createInsumo(to);
	
	        insumoDAO daoo = new insumoDAO();
			vista.addObject("lista", daoo.readAllI());
			vista.setViewName("adminInsumos");
			return vista;
	}
	
	@RequestMapping(value = "/deleteInsumo", method = RequestMethod.GET)
	public ModelAndView eliminaV(
			@RequestParam(value = "id", required = false, defaultValue = "World") int id, ModelAndView vista) throws SQLException {
		insumoDAO dao = new insumoDAO();
        dao.deleteInsumo(id);
		
        insumoDAO daoo = new insumoDAO();
		vista.addObject("lista", daoo.readAllI());
		vista.setViewName("adminInsumos");
		return vista;
	}
	
	@RequestMapping(value = "/editarIn", method = RequestMethod.GET)
	@ResponseBody
	public insumoTO editarIn(
			@RequestParam(value = "id", required = false, defaultValue = "World") int id, ModelAndView vista) throws SQLException {

		insumoDAO dao = new insumoDAO();
        insumoTO insumo = dao.read(id);
		//vista.addObject("update", task);
		//vista.setViewName("editarVacuno");
		return insumo;
	}
	
	
	@RequestMapping(value = "/actualizarInsumo", method = RequestMethod.GET)
	public ModelAndView actualizarV(
			@RequestParam(value = "idInsumo", required = false, defaultValue = "World") int id, 
			@RequestParam(value = "nombreInsumo", required = false, defaultValue = "World") String nombre,
			@RequestParam(value = "descripcion", required = false, defaultValue = "World") String descripcion,
			@RequestParam(value = "tipo", required = false, defaultValue = "World") String tipo,
			ModelAndView vista) throws SQLException {
	        System.out.println("Nombre -------"+tipo);
	       insumoDAO dao = new insumoDAO();
	        insumoTO to = new insumoTO();
			to.setId_insumo(id);
	        to.setNombre_insumo(nombre);
	        to.setDescripcion_insumo(descripcion);
	        to.setTipoInsumo(tipo);
	        dao.update(to);
			
	        insumoDAO daoo = new insumoDAO();
			vista.addObject("lista", daoo.readAllI());
			vista.setViewName("adminInsumos");
			return vista;
	}
	
	
	@RequestMapping(value = "/compra", method = RequestMethod.POST)
	public ModelAndView compra(
			@RequestParam(value = "insumo", required = false, defaultValue = "World") String insumo,
			@RequestParam(value = "proveedor", required = false, defaultValue = "World") String proveedor,
			@RequestParam(value = "fechaCompra", required = false, defaultValue = "World") String fecha,
			@RequestParam(value = "documento", required = false, defaultValue = "World") String documento,
			@RequestParam(value = "cantidad", required = false, defaultValue = "World") int cantidad,
			@RequestParam(value = "precio", required = false, defaultValue = "World") int precio,
			ModelAndView vista) throws SQLException {
		    insumoDAO dao = new insumoDAO();
		    java.sql.Date fechaC = java.sql.Date.valueOf(fecha);
		    dao.ingresaCompra(insumo,proveedor,fechaC,documento,cantidad,precio);

			insumoDAO daoi = new insumoDAO();
			vista.addObject("lista", daoi.readAllI());
			
			proveedorDAO daop = new proveedorDAO();
			vista.addObject("proveedores",daop.readAllP());
			vista.addObject("compraRegistrada","compraRegistrada");
		    vista.setViewName("registraCompra");
		    return vista;
	}
	

}
