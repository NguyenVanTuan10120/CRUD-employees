package com.crud.springbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crud.springbe.models.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{
    
}
