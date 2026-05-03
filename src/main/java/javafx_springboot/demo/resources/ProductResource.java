package javafx_springboot.demo.resources;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javafx_springboot.demo.entities.Product;
import javafx_springboot.demo.entitiesDto.ProductDto;
import javafx_springboot.demo.services.ProductService;

@RestController
@RequestMapping(value = "/product")
@Tag(name = "Produtos", description = "Endpoints para gerenciamento de produtos")
public class ProductResource {

    @Autowired
    private ProductService service;

    @Operation(summary = "Criar novo produto")
    @PostMapping
    public ResponseEntity<Product> cria(@RequestBody ProductDto dto) {
        Product product = service.salva(dto);
        return ResponseEntity.ok().body(product);
    }

    @Operation(summary = "Listar todos os produtos")
    @GetMapping
    public ResponseEntity<List<Product>> listaTodos() {
        return ResponseEntity.ok().body(service.buscaTodos());
    }

    @Operation(summary = "Buscar produto por ID")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> listaId(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.buscaId(id));
    }

    @Operation(summary = "Atualizar produto existente")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Product> atualiza(@PathVariable Long id, @RequestBody ProductDto dto) {
        Product product = service.atualiza(id, dto);
        return ResponseEntity.ok().body(product);
    }

    @Operation(summary = "Excluir produto")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleta(@PathVariable Long id) {
        service.deleta(id);
        return ResponseEntity.noContent().build();
    }
}