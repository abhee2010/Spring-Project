package com.abhee.login.controller;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abhee.login.configuration.BasicConfiguration;
import com.abhee.login.model.User;
import com.abhee.login.service.UserService;

@Controller
public class LoginController {

	private static final String REGISTRATION = "registration";

	@Autowired
	private UserService userService;
	
	@Value("${welcome.message}")
	private String welcomeMessage;
	
	@Autowired
	private BasicConfiguration configuration;



	private static final Logger logger = LogManager.getLogger(LoginController.class);

	@GetMapping(value = { "/login" })
	public ModelAndView login() {
		logger.info("Info logger inside login method");
		logger.debug("debug logger inside login method");
		logger.warn("warn logger inside login method");
		logger.error("error logger inside login method");
		logger.trace("trace logger inside login method");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("welcomeMessage", welcomeMessage+" "+configuration.getMessage());
		modelAndView.setViewName("login");
		return modelAndView;
	}

	@GetMapping(value = "/registration")
	public ModelAndView registration() {
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName(REGISTRATION);
		return modelAndView;
	}

	@PostMapping(value = "/registration")
	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		User userExists = userService.findUserByEmail(user.getEmail());
		if (userExists != null) {
			bindingResult.rejectValue("email", "error.user",
					"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName(REGISTRATION);
		} else {
			userService.saveUser(user);
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName(REGISTRATION);

		}
		return modelAndView;
	}

	@GetMapping(value = "/admin/home")
	public ModelAndView home() {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("userName",
				"Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
		modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
		modelAndView.setViewName("admin/home");
		return modelAndView;
	}

}
