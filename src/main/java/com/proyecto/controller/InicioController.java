package com.proyecto.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.proyecto.persistence.trabajadorDAO;
import com.proyecto.persistence.usuarioDAO;
import com.proyecto.transferObject.trabajadorTO;
import com.proyecto.transferObject.usuarioTO;

@Controller
public class InicioController {
	@RequestMapping(value = "login")
	public ModelAndView goToLogin() {
		ModelAndView vista = new ModelAndView();
		vista.setViewName("login");

		return vista;
	}
	
	
	@RequestMapping(value = "inicio", method = RequestMethod.POST)
	public ModelAndView verificarLogin(@RequestParam(value = "email", required = true) String email,
			@RequestParam(value = "password", required = true) String pass, ModelAndView vista,
			HttpServletRequest request, HttpServletResponse response) {

		usuarioDAO usuarioDAO = new usuarioDAO();
		usuarioTO usuarioTO = new usuarioTO();

		usuarioTO.setEmailUsuario(email);
		usuarioTO.setPasswordUsuario(pass);

		if (usuarioDAO.verificar(usuarioTO) != null) {
			//String nombre = usuarioDAO.verificar(usuarioTO).getNombre();
			String rol = usuarioDAO.verificar(usuarioTO).getRolUsuario();
            int idUsuario = usuarioDAO.verificar(usuarioTO).getIdUsuario();
			// Vista usuario
			if (rol.equalsIgnoreCase("usuario")) {

				vista.setViewName("indexUsuario");
			} else {

				// Vista adm
				if (rol.equalsIgnoreCase("Administrador")) {
					vista.setViewName("indexAdministrador");
				}
			}
            trabajadorDAO trabajadordao = new trabajadorDAO();
            trabajadorTO to = trabajadordao.obtieneTrabajador(idUsuario);
            
			HttpSession session = request.getSession(true);
			session.setAttribute("nombre", to.getNombreTrabajador());
			session.setAttribute("cargo",to.getCargoTrabajador());
			session.setAttribute("rol", rol);
			//session.setAttribute("id", usuarioDAO.getUser(rut, pass));

			return vista;
		} else {
			vista.addObject("errorUsuario", "Error");
			vista.setViewName("login");
			return vista;
		}
	}
}
