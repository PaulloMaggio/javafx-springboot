package javafx_springboot.demo.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javafx_springboot.demo.entities.Department;
import javafx_springboot.demo.entitiesDto.DepartmentDto;
import javafx_springboot.demo.services.DepartmentService;

@RestController
@RequestMapping(value = "/department")
public class DepartmentResource {

	@Autowired
	private DepartmentService service;
	
	@GetMapping
	public ResponseEntity<Department> cria(@RequestBody DepartmentDto dto) {
		Department department = service.salva(dto);
		return ResponseEntity.ok().body(department);
	}
	
	@GetMapping
	public ResponseEntity<List<Department>> listaTodos() {
		List list = service.buscaTodos();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Department> listaId(@PathVariable Long id) {
		Department department = service.buscaId(id);
		return ResponseEntity.ok().body(department);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Department> atualiza(@PathVariable Long id, @RequestBody DepartmentDto dto) {
		Department department = service.atualiza(id, dto);
		return ResponseEntity.ok().body(department);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleta(@PathVariable Long id) {
		service.deleta(id);
		return ResponseEntity.noContent().build();
	}
}
