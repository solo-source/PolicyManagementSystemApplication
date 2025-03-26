package com.pms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.pms.model.Department;
import com.pms.service.DepartmentService;

@RestController
public class DepartmentController {
	@Autowired
	private DepartmentService deptService;
	
	@PostMapping("/addDepartment")
    public ResponseEntity<Department> addDepartment(@RequestBody Department department) {
        return new ResponseEntity<>(deptService.addDepartment(department), HttpStatus.OK);
    }
	
	@GetMapping("/findByDeptId/{deptId}")
    public ResponseEntity<Department> findByDeptId(@PathVariable String deptId) {
        return new ResponseEntity<>(deptService.findByDeptId(deptId), HttpStatus.OK);
    }
	
	@GetMapping("/findByDeptNameAndHod/{deptName}/{hod}")
    public ResponseEntity<Department> findByDeptNameAndHod(@PathVariable String deptName, @PathVariable String hod) {
        Department department = deptService.findByDeptNameAndHod(deptName,hod);

		return new ResponseEntity<>(department, HttpStatus.OK);
    }
	
	 @GetMapping("/viewDepartmentByDeptName/{deptName}")
	    public ResponseEntity<Department> viewDepartmentByDeptName(@PathVariable String deptName) {
	        Department department = deptService.viewDepartmentByDeptName(deptName);
	        return new ResponseEntity<>(department, HttpStatus.OK);
	    }

}
