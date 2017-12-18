package com.proyecto.controller;

import java.sql.SQLException;
import java.text.ParseException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.proyecto.persistence.proveedorDAO;
import com.proyecto.persistence.razaDAO;
import com.proyecto.transferObject.proveedorTO;
import com.proyecto.transferObject.razaTO;

@Controller
public class proveedorController {
	@RequestMapping(value = "adminProveedor")
	public ModelAndView ingresarV(ModelAndView vista) throws SQLException, ParseException {
		proveedorDAO dao = new proveedorDAO();
		vista.addObject("lista", dao.readAllP());
		vista.setViewName("adminProveedor");
		return vista;
	}
	
	@RequestMapping(value = "/saveProveedor", method = RequestMethod.POST)
	public ModelAndView save(
			@RequestParam(value = "nombre", required = false, defaultValue = "World") String nombre,
			@RequestParam(value = "rubro", required = false, defaultValue = "World") String rubro,
			@RequestParam(value = "rut", required = false, defaultValue = "World") String rut,
			@RequestParam(value = "direccion", required = false, defaultValue = "World") String direccion,
			ModelAndView vista) throws SQLException {
		
		    proveedorDAO dao = new proveedorDAO();
		    proveedorTO to = new proveedorTO();
		    to.setNombreProveedor(nombre);
		    to.setRubroProveedor(rubro);
		    to.setRutProveedor(rut);
		    to.setDireccionProveedor(direccion);
		    
		    int r = dao.createProveedor(to);
		    
		    if(r==0) {
		    	proveedorDAO daoo= new proveedorDAO();
		    	vista.addObject("lista",daoo.readAllP());
		    	vista.addObject("correcto","insercion correcta");
		    	vista.setViewName("adminProveedor");
		    }else {
		    	proveedorDAO daoo= new proveedorDAO();
		    	vista.addObject("lista",daoo.readAllP());
		    	vista.addObject("incorrecto","insercion incorrecta");
		    	vista.setViewName("adminProveedor");
		    }
		return vista;
	}
	
	@RequestMapping(value = "/deleteProveedor", method = RequestMethod.GET)
	public ModelAndView eliminaV(
			@RequestParam(value = "id", required = false, defaultValue = "World") int id, ModelAndView vista) throws SQLException {
		
		proveedorDAO dao = new proveedorDAO();
		proveedorTO to = dao.read(id);
		
		int r = dao.delete(to);
		if(r==0) {
			proveedorDAO daoo= new proveedorDAO();
	    	vista.addObject("lista",daoo.readAllP());
	    	vista.setViewName("adminProveedor");
			return vista;
		}else {
			proveedorDAO daoo= new proveedorDAO();
			vista.addObject("elimino","Problema al eliminar");
	    	vista.addObject("lista",daoo.readAllP());
	    	vista.setViewName("adminProveedor");
			return vista;
		}

	}
	
	@RequestMapping(value = "/editarP", method = RequestMethod.GET)
	@ResponseBody
	public proveedorTO editarP(
			@RequestParam(value = "id", required = false, defaultValue = "World") int id, ModelAndView vista) throws SQLException {

		proveedorDAO dao = new proveedorDAO();
        proveedorTO proveedor = dao.read(id);
		return proveedor;
	}
	
	@RequestMapping(value = "/actualizarProveedor", method = RequestMethod.POST)
	public ModelAndView actualizarV(
			@RequestParam(value = "id", required = false, defaultValue = "World") int id,
			@RequestParam(value = "nombre", required = false, defaultValue = "World") String nombre, 
			@RequestParam(value = "rubro", required = false, defaultValue = "World") String rubro,
			@RequestParam(value = "rut", required = false, defaultValue = "World") String rut, 
			@RequestParam(value = "direccion", required = false, defaultValue = "World") String direccion,
			ModelAndView vista) throws SQLException {
		
		proveedorDAO dao = new proveedorDAO();
		proveedorTO to = new proveedorTO();
		to.setIdProveedor(id);
		to.setNombreProveedor(nombre);
		to.setRubroProveedor(rubro);
		to.setRutProveedor(rut);
		to.setDireccionProveedor(direccion);
		
		dao.update(to);
		proveedorDAO daoo= new proveedorDAO();
    	vista.addObject("lista",daoo.readAllP());
    	vista.setViewName("adminProveedor");
		return vista;
	}
	
}
