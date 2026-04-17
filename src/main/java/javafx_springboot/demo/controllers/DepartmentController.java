package javafx_springboot.demo.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx_springboot.demo.entities.Department;
import javafx_springboot.demo.entitiesDto.DepartmentDto;
import javafx_springboot.demo.services.DepartmentService;

@Component
public class DepartmentController implements Initializable {

    @Autowired
    private DepartmentService service;

    @FXML
    private TextField txtName;

    @FXML
    private TableView<Department> tableView;

    @FXML
    private TableColumn<Department, Long> columnId;

    @FXML
    private TableColumn<Department, String> columnName;

    private ObservableList<Department> obsList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeNodes();
        loadDepartments();
    }

    private void initializeNodes() {
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    @FXML
    public void onBtnSaveAction() {
        try {
            if (txtName.getText().trim().isEmpty()) {
                showAlert("Aviso", "Campo Vazio", "Por favor, digite o nome do departamento.", AlertType.WARNING);
                return;
            }

            DepartmentDto dto = new DepartmentDto(txtName.getText());
            service.salva(dto);
            
            txtName.clear();
            loadDepartments(); 
            showAlert("Sucesso", "Departamento Salvo", "O departamento foi cadastrado com sucesso!", AlertType.INFORMATION);
            
        } catch (Exception e) {
            showAlert("Erro", "Erro ao salvar", e.getMessage(), AlertType.ERROR);
        }
    }

    @FXML
    public void onBtnBackAction(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void loadDepartments() {
        List<Department> list = service.buscaTodos();
        obsList = FXCollections.observableArrayList(list);
        tableView.setItems(obsList);
    }

    private void showAlert(String title, String header, String content, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.show();
    }
}