package com.proyecto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class InicioController {
	@RequestMapping(value = "login")
	public ModelAndView goToLogin() {
		ModelAndView vista = new ModelAndView();
		vista.setViewName("index");

		return vista;
	}
}
