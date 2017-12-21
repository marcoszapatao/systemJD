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

import com.proyecto.persistence.insumoDAO;
import com.proyecto.persistence.trabajadorDAO;
import com.proyecto.persistence.usuarioDAO;
import com.proyecto.transferObject.insumoTO;
import com.proyecto.transferObject.trabajadorTO;
import com.proyecto.transferObject.usuarioTO;

@Controller
public class usuarioController {
	@RequestMapping(value = "adminUsuarios")
	public ModelAndView admin(ModelAndView vista) throws SQLException, ParseException {
		usuarioDAO usuarioDAO = new usuarioDAO();
		LinkedList<usuarioTO> lista = new LinkedList<>();
		
		trabajadorDAO trabajadorDAO = new trabajadorDAO();
		
		LinkedList<trabajadorTO> listaTrabajador = new LinkedList<>();
		if (!trabajadorDAO.obtieneTrabajadores().isEmpty()) {
			listaTrabajador = trabajadorDAO.obtieneTrabajadores();
			
			vista.addObject("cantidad", listaTrabajador);

			for (int i = 0; i < listaTrabajador.size(); i++) {
				int idUsuario=listaTrabajador.get(i).getIdUsuario();
				lista.add(usuarioDAO.read(idUsuario));

			}

			vista.addObject("lista", lista);
			vista.setViewName("adminUsuarios");
			
		} else {
			vista.setViewName("indexAdministrador");
			
		}

		return vista;
	}
	
	@RequestMapping(value = "/saveUsuario", method = RequestMethod.POST)
	public ModelAndView login(
			@RequestParam(value = "nombre", required = false, defaultValue = "World") String nombre,
			@RequestParam(value = "cargo", required = false, defaultValue = "World") String cargo,
			@RequestParam(value = "email", required = false, defaultValue = "World") String email,
			@RequestParam(value = "rol", required = false, defaultValue = "World") String rol,
			@RequestParam(value = "contrasena", required = false, defaultValue = "World") String contrasena,
			ModelAndView vista) throws SQLException, ParseException {
		
		
		usuarioDAO dao = new usuarioDAO();
		usuarioTO usuario = new usuarioTO();
		trabajadorTO trabajador = new trabajadorTO();
		
		usuario.setEmailUsuario(email);
		usuario.setRolUsuario(rol);
		usuario.setPasswordUsuario(contrasena);
		
		trabajador.setNombreTrabajador(nombre);
		trabajador.setCargoTrabajador(cargo);
		
		dao.create(trabajador,usuario);
		
		
		
		usuarioDAO usuarioDAO = new usuarioDAO();
		LinkedList<usuarioTO> lista = new LinkedList<>();
		
		trabajadorDAO trabajadorDAO = new trabajadorDAO();
		
		LinkedList<trabajadorTO> listaTrabajador = new LinkedList<>();
		if (!trabajadorDAO.obtieneTrabajadores().isEmpty()) {
			listaTrabajador = trabajadorDAO.obtieneTrabajadores();
			
			vista.addObject("cantidad", listaTrabajador);

			for (int i = 0; i < listaTrabajador.size(); i++) {
				int idUsuario=listaTrabajador.get(i).getIdUsuario();
				lista.add(usuarioDAO.read(idUsuario));

			}

			vista.addObject("lista", lista);
			vista.setViewName("adminUsuarios");
			
		} else {
			vista.setViewName("indexAdministrador");
			
		}
		return vista;
	}
	
	@RequestMapping(value = "/deleteUsuario", method = RequestMethod.GET)
	public ModelAndView eliminaU(
			@RequestParam(value = "id", required = false, defaultValue = "World") int id, ModelAndView vista) throws SQLException {
		usuarioDAO dao = new usuarioDAO();
		dao.delete(id);
		
		
		
		usuarioDAO usuarioDAO = new usuarioDAO();
		LinkedList<usuarioTO> lista = new LinkedList<>();
		
		trabajadorDAO trabajadorDAO = new trabajadorDAO();
		
		LinkedList<trabajadorTO> listaTrabajador = new LinkedList<>();
		if (!trabajadorDAO.obtieneTrabajadores().isEmpty()) {
			listaTrabajador = trabajadorDAO.obtieneTrabajadores();
			
			vista.addObject("cantidad", listaTrabajador);

			for (int i = 0; i < listaTrabajador.size(); i++) {
				int idUsuario=listaTrabajador.get(i).getIdUsuario();
				lista.add(usuarioDAO.read(idUsuario));

			}

			vista.addObject("lista", lista);
			vista.setViewName("adminUsuarios");
			
		} else {
			vista.setViewName("indexAdministrador");
			
		}
		return vista;
	}
	@RequestMapping(value = "/editarUser", method = RequestMethod.GET)
	@ResponseBody
	public trabajadorTO editarIn(
			@RequestParam(value = "id", required = false, defaultValue = "World") int id, ModelAndView vista) throws SQLException {

		trabajadorDAO dao = new trabajadorDAO();
        trabajadorTO trabajador = dao.obtieneTrabajadorXID(id);

		return trabajador;
	}
	
	@RequestMapping(value = "/editarU", method = RequestMethod.GET)
	@ResponseBody
	public usuarioTO editarU(
			@RequestParam(value = "id", required = false, defaultValue = "World") int id, ModelAndView vista) throws SQLException {

		usuarioDAO dao = new usuarioDAO();
        usuarioTO trabajador = dao.read(id);

		return trabajador;
	}
	
	@RequestMapping(value = "/actualizarUser", method = RequestMethod.POST)
	public ModelAndView actualizarV(
			@RequestParam(value = "idTraba", required = false, defaultValue = "World") int idTra, 
			@RequestParam(value = "idUser", required = false, defaultValue = "World") int idUser,
			@RequestParam(value = "nombreTra", required = false, defaultValue = "World") String nombre,
			@RequestParam(value = "cargoTra", required = false, defaultValue = "World") String cargo,
			@RequestParam(value = "emailU", required = false, defaultValue = "World") String email,
			@RequestParam(value = "rolU", required = false, defaultValue = "World") String rol,
			ModelAndView vista) throws SQLException {
		
		trabajadorDAO trabDao = new trabajadorDAO();
		trabajadorTO trabajador = new trabajadorTO();
		trabajador.setIdTrabajador(idTra);
		trabajador.setNombreTrabajador(nombre);
		trabajador.setCargoTrabajador(cargo);
		trabDao.update(trabajador);
		
		usuarioDAO userDao = new usuarioDAO();
		usuarioTO usuario = new usuarioTO();
		usuario.setIdUsuario(idUser);
		usuario.setEmailUsuario(email);
		usuario.setRolUsuario(rol);
		userDao.update(usuario);
		
		
		
		
		usuarioDAO usuarioDAO = new usuarioDAO();
		LinkedList<usuarioTO> lista = new LinkedList<>();
		
		trabajadorDAO trabajadorDAO = new trabajadorDAO();
		
		LinkedList<trabajadorTO> listaTrabajador = new LinkedList<>();
		if (!trabajadorDAO.obtieneTrabajadores().isEmpty()) {
			listaTrabajador = trabajadorDAO.obtieneTrabajadores();
			
			vista.addObject("cantidad", listaTrabajador);

			for (int i = 0; i < listaTrabajador.size(); i++) {
				int idUsuario=listaTrabajador.get(i).getIdUsuario();
				lista.add(usuarioDAO.read(idUsuario));

			}

			vista.addObject("lista", lista);
			vista.setViewName("adminUsuarios");
			
		} else {
			vista.setViewName("indexAdministrador");
			
		}
		return vista;
	}
	
}
