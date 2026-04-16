package javafx_springboot.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import javafx_springboot.demo.entities.Department;

public interface DepartmentRepository extends JpaRepository<Department,  Long >{}
