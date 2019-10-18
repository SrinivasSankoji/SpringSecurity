package com.jio.ngo.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController 
{
	
	@GetMapping("/")
	public String getSecurity()
	{
		return "Default -- Welcome Spring Security Authorization";
	}
	
	@GetMapping("/user")
	public String getUser()
	{
		return "User -- Welcome Spring Security Authorization";
	}
	
	@GetMapping("/admin")
	public String getAdmin()
	{
		return "Admin --  Welcome Spring Security Authorization";
	}

}
