package com.springboot.jwt.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeRestController {
	
	@GetMapping("/employees")
	public String getEmployees() {
		return "Employee";
	}
}
