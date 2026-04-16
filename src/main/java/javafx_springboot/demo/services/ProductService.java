package javafx_springboot.demo.services;

import javafx_springboot.demo.entities.Department;
import javafx_springboot.demo.entities.Product;
import javafx_springboot.demo.entitiesDto.ProductDto;
import javafx_springboot.demo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private DepartmentService departmentService;

    public Product salva(ProductDto dto) {
        Product product = new Product();
        product.setName(dto.name);
        product.setDescription(dto.description);
        product.setPrice(dto.price);
        
        Department dept = departmentService.buscaId(dto.departmentId);
        product.setDepartment(dept);
        
        return repository.save(product);
    }

    public List<Product> buscaTodos() {
        return repository.findAll();
    }

    public Product buscaId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    public Product atualiza(Long id, ProductDto dto) {
        Product product = buscaId(id);
        product.setName(dto.name);
        product.setDescription(dto.description);
        product.setPrice(dto.price);
        
        Department dept = departmentService.buscaId(dto.departmentId);
        product.setDepartment(dept);
        
        return repository.save(product);
    }

    public void deleta(Long id) {
        repository.deleteById(id);
    }
}
