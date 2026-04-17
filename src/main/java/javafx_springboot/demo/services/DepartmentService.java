package javafx_springboot.demo.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javafx_springboot.demo.entities.Department;
import javafx_springboot.demo.entitiesDto.DepartmentDto;
import javafx_springboot.demo.repositories.DepartmentRepository;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository repository;

    public List<Department> buscaTodos() {
        return repository.findAll();
    }

    public Department buscaId(Long id) {
        Optional<Department> obj = repository.findById(id);
        return obj.orElseThrow(() -> new RuntimeException("Departamento não encontrado"));
    }

    public Department salva(DepartmentDto dto) {
        Department entity = new Department();
        entity.setName(dto.getName());
        return repository.save(entity);
    }

    public Department atualiza(Long id, DepartmentDto dto) {
        Department entity = buscaId(id);
        entity.setName(dto.getName());
        return repository.save(entity);
    }

    public void deleta(Long id) {
        repository.deleteById(id);
    }
}