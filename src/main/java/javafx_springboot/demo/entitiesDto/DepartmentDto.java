package javafx_springboot.demo.entitiesDto;

public class DepartmentDto {
    
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