package javafx_springboot.demo.entitiesDto;

import io.swagger.v3.oas.annotations.media.Schema;

public class DepartmentDto {
    
    @Schema(description = "Nome do departamento", example = "Informática")
    private String name;

    public DepartmentDto() {
    }

    public DepartmentDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}