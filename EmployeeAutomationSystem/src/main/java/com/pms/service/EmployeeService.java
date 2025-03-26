package com.pms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import com.pms.model.Employee;
import com.pms.model.Department;
import com.pms.repository.DepartmentRepository;
import com.pms.repository.EmployeeRepository;

@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	
	public String displayWelcomeMessage() {
		return "welcome to Employee Automation System";
	}
	
	
	public List<Employee> addEmployee(String deptId, List<Employee> employees) {
	    Optional<Department> op = departmentRepository.findById(deptId);
	    
	    if (op.isPresent()) {
	        Department dept = op.get();
	        for (Employee emp : employees) {
	            emp.setDepartment(dept);
	            employeeRepository.save(emp);  // Save each employee
	        }
	}
        return employees;

}
	
	 public Employee viewEmployeeByEmailId(String emailId) {
	        return employeeRepository.findByEmailId(emailId);
	    }
}

