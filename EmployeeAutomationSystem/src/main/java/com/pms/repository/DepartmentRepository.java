package com.pms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pms.model.Department;
@Repository
public interface DepartmentRepository extends JpaRepository<Department, String> {
    public Department  findByDeptName(String deptName);

	public Department findByDeptNameAndHod(String deptName, String hod);


}
