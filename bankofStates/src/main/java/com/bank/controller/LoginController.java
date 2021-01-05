package com.bank.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//Model database
//View Html+Css (React)
//Controller - Data flow for Model & View

//Servlet one type of controller
//Jdbc in 10years ago- grandparent 

@RestController
public class LoginController {
	
	@GetMapping("/")
	public String home() {
		return "<h1>My home </h1>";
	}
	
	@GetMapping("/user")
	public String user() {
		return "<h1>User Page </h1>";
	}

	@GetMapping("/manager")
	public String manager() {
		return "<h1>Manager Page </h1>";
	}
	
	@GetMapping("/admin")
	public String admin() {
		return "<h1>Admin Page </h1>";
	}	
}
