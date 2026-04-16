package javafx_springboot.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javafx_springboot.demo.entities.Department;
import javafx_springboot.demo.entitiesDto.DepartmentDto;
import javafx_springboot.demo.repositories.DepartmentRepository;

@Service
public class DepartmentService {
	
	@Autowired
	private DepartmentRepository repository;
	
	public Department salva(DepartmentDto dto) {
		Department department = new Department();
		department.setName(dto.name);
		return repository.save(department);
	}

	public List<Department> buscaTodos() {
		List<Department> list = repository.findAll();
		return list;
	}
	
	public Department buscaId(Long id) {
		 return  repository.findById(id)
				 .orElseThrow(() -> new RuntimeException("Id não encontrado"));
	}
	
	public Department atualiza(Long id, DepartmentDto dto) {
		Department department = buscaId(id);
		department.setName(dto.name);
		return repository.save(department);
	}
	
	public void deleta(Long id) {
		repository.deleteById(id);
	}
}
