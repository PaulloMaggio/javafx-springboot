package javafx_springboot.demo.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javafx_springboot.demo.entities.Product;
import javafx_springboot.demo.entitiesDto.ProductDto;
import javafx_springboot.demo.services.ProductService;

@RestController
@RequestMapping(value = "/product")
public class ProductResource {
    
    @Autowired
    private ProductService service;
    
    @PostMapping
    public ResponseEntity<Product> cria(@RequestBody ProductDto dto) {
        Product product = service.salva(dto);
        return ResponseEntity.ok().body(product);
    }
    
    @GetMapping
    public ResponseEntity<List<Product>> listaTodos() {
        return ResponseEntity.ok().body(service.buscaTodos());
    }
    
    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> listaId(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.buscaId(id));
    }
    
    @PutMapping(value = "/{id}")
    public ResponseEntity<Product> atualiza(@PathVariable Long id, @RequestBody ProductDto dto) {
    	Product product = service.atualiza(id, dto);
    	return ResponseEntity.ok().body(product);
    }
    
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleta(@PathVariable Long id) {
        service.deleta(id);
        return ResponseEntity.noContent().build();
    }
}