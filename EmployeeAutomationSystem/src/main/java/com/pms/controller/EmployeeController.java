package com.pms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pms.service.EmployeeService;
import com.pms.model.*;
@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	
@GetMapping("/welcome")
public String displayWelcomeMessage() {
    
	String msg=employeeService.displayWelcomeMessage();
    return msg;
}


@PostMapping("/addEmployee/{deptId}")
public ResponseEntity<List<Employee>> addEmployee(@PathVariable String deptId, @RequestBody List<Employee> employees) {
    List<Employee> savedEmployees = employeeService.addEmployee(deptId, employees);
    return new ResponseEntity<>(savedEmployees, HttpStatus.OK);
}

@GetMapping("/viewEmployeeByEmailId/{emailId}")
public ResponseEntity<Employee> viewEmployeeByEmailId(@PathVariable String emailId) {
    Employee employee = employeeService.viewEmployeeByEmailId(emailId);
    return new ResponseEntity<>(employee, HttpStatus.OK);
}




}
