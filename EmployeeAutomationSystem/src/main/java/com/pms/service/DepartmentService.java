package com.pms.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pms.model.Department;
import com.pms.repository.DepartmentRepository;



@Service
public class DepartmentService {
	@Autowired
	private DepartmentRepository deptRepository;
	

	public Department addDepartment(Department department) {
        return deptRepository.save(department);
    }
	
	  public Department viewDepartmentByDeptName(String deptName) {
	        return deptRepository.findByDeptName(deptName);
	    }
	  
	  public Department findByDeptId(String deptId) {
	        return deptRepository.findById(deptId).get();}
	   
	  
	  public Department findByDeptNameAndHod(String deptName,String hod) {
	        return deptRepository.findByDeptNameAndHod(deptName,hod);
	    }

	  
}
