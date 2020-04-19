package com.rahul.spring.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rahul.spring.batch.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {

}
