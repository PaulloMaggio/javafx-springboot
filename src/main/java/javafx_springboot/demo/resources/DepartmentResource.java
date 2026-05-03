package javafx_springboot.demo.resources;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javafx_springboot.demo.entities.Department;
import javafx_springboot.demo.entitiesDto.DepartmentDto;
import javafx_springboot.demo.services.DepartmentService;

@RestController
@RequestMapping(value = "/department")
@Tag(name = "Departamentos", description = "Endpoints para gerenciamento de departamentos")
public class DepartmentResource {

    @Autowired
    private DepartmentService service;

    @Operation(summary = "Criar novo departamento")
    @PostMapping
    public ResponseEntity<Department> cria(@RequestBody DepartmentDto dto) {
        Department department = service.salva(dto);
        return ResponseEntity.ok().body(department);
    }

    @Operation(summary = "Listar todos os departamentos")
    @GetMapping
    public ResponseEntity<List<Department>> listaTodos() {
        List<Department> list = service.buscaTodos();
        return ResponseEntity.ok().body(list);
    }

    @Operation(summary = "Buscar departamento por ID")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Department> listaId(@PathVariable Long id) {
        Department department = service.buscaId(id);
        return ResponseEntity.ok().body(department);
    }

    @Operation(summary = "Atualizar departamento existente")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Department> atualiza(@PathVariable Long id, @RequestBody DepartmentDto dto) {
        Department department = service.atualiza(id, dto);
        return ResponseEntity.ok().body(department);
    }

    @Operation(summary = "Excluir departamento")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleta(@PathVariable Long id) {
        service.deleta(id);
        return ResponseEntity.noContent().build();
    }
}