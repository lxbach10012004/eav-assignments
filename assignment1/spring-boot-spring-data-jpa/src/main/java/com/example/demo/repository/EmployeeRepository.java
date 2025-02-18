package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import com.example.demo.modal.Employee;

// @Repository is a Spring annotation that
// indicates that the decorated class is a repository.
@Repository 
public interface EmployeeRepository extends JpaRepository<Employee, Long>{
    @Query("SELECT e FROM Employee e")
    ArrayList<Employee> findAllEmployee();
}
