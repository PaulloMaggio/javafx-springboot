package javafx_springboot.demo.entitiesDto;

import io.swagger.v3.oas.annotations.media.Schema;

public class ProductDto {

    @Schema(description = "Nome do produto", example = "Teclado Mecânico RGB")
    public String name;

    @Schema(description = "Descrição detalhada do produto", example = "Switch Blue, Layout ABNT2")
    public String description;

    @Schema(description = "Preço unitário", example = "259.90")
    public Double price;

    @Schema(description = "ID do departamento vinculado", example = "1")
    public Long departmentId;

    public ProductDto() {
    }

    public ProductDto(String name, String description, Double price, Long departmentId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.departmentId = departmentId;
    }
}