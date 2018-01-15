package com.proyecto.controller;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.Random;


/*
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
*/
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.proyecto.persistence.trabajadorDAO;
import com.proyecto.persistence.usuarioDAO;
import com.proyecto.transferObject.trabajadorTO;
import com.proyecto.transferObject.usuarioTO;

import org.apache.commons.codec.digest.DigestUtils;



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
			//@RequestParam(value = "contrasena", required = false, defaultValue = "World") String contrasena,
			ModelAndView vista) throws SQLException, ParseException {
		
		
		usuarioDAO dao = new usuarioDAO();
		usuarioTO usuario = new usuarioTO();
		trabajadorTO trabajador = new trabajadorTO();
		
		usuario.setEmailUsuario(email);
		usuario.setRolUsuario(rol);
		
		String[] parts = email.split("@");
		String pass = parts[0]; // 123
		
		
		/*Generar contraseña aleatoria
        char[] caracteres;
        caracteres = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
		String pass = "";
        for (int i = 0; i < 8; i++) {
            pass += caracteres[new Random().nextInt(62)];
        }
        System.out.println("La contraseña generada es: "+pass);*/
		/*----------------------------*/
        /*Generar email al usuario
        String destinatario =  "marcos.zapataota"; //A quien le quieres escribir.
        String asunto = "Correo de prueba enviado desde Java";
        String cuerpo = "Esta es una prueba de correo...";

        enviarConGMail(destinatario, asunto, cuerpo);
        /**/
		/*Aqui hay que aplicar hash*/
	    String encriptMD5=DigestUtils.md5Hex(pass);
	    System.out.println("md5:"+encriptMD5);
		/*------------9450476b384b32d8ad8b758e76c98a69-------------*/
	    
		usuario.setPasswordUsuario(encriptMD5);
		
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
	
	@RequestMapping(value = "/cambiarPass", method = RequestMethod.POST)
	public ModelAndView cambio(
			@RequestParam(value = "pass", required = false, defaultValue = "World") String pass,
			@RequestParam(value = "pass1", required = false, defaultValue = "World") String pass1,
			@RequestParam(value = "iduser", required = false, defaultValue = "World")int iduser,
			@RequestParam(value = "roluser", required = false, defaultValue = "World") String roluser,
			ModelAndView vista) throws SQLException {
		
        usuarioDAO usuario = new usuarioDAO();
        String encriptMD5=DigestUtils.md5Hex(pass);
        int j = usuario.cambiarPass(encriptMD5,iduser);
        if(j==0) {
        	vista.addObject("cambio","exito");
        }else {
        	vista.addObject("nocambio","no exito");
        }
		if(roluser.equalsIgnoreCase("usuario")) {			
			vista.setViewName("indexUsuario");
		}else {
			if (roluser.equalsIgnoreCase("Administrador")) {
				vista.setViewName("indexAdministrador");
			}
		}
        return vista;
	}
	
}
