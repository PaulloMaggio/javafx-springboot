package javafx_springboot.demo.controllers;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx_springboot.demo.entities.Department;
import javafx_springboot.demo.entitiesDto.DepartmentDto;
import javafx_springboot.demo.services.DepartmentService;

@Component
public class DepartmentController implements Initializable {

    @Autowired
    private DepartmentService service;

    @FXML private TextField txtName;
    @FXML private Button btnSave;
    @FXML private Button btnDelete;
    @FXML private TableView<Department> tableView;
    @FXML private TableColumn<Department, Long> columnId;
    @FXML private TableColumn<Department, String> columnName;

    private ObservableList<Department> obsList;
    private Department selectedDepartment;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        loadDepartments();
    }

    @FXML
    public void onBtnSaveAction() {
        try {
            String name = txtName.getText().trim();
            if (name.isEmpty()) {
                showAlert("Aviso", "Campo Vazio", "Digite o nome do departamento.", AlertType.WARNING);
                return;
            }
            DepartmentDto dto = new DepartmentDto(name);
            if (selectedDepartment == null) {
                service.salva(dto);
                showAlert("Sucesso", "Salvo", "Cadastrado com sucesso!", AlertType.INFORMATION);
            } else {
                service.atualiza(selectedDepartment.getId(), dto);
                showAlert("Sucesso", "Atualizado", "Alterado com sucesso!", AlertType.INFORMATION);
            }
            clearFields();
            loadDepartments(); 
        } catch (Exception e) {
            showAlert("Erro", "Erro ao processar", e.getMessage(), AlertType.ERROR);
        }
    }

    @FXML
    public void onBtnDeleteAction() {
        if (selectedDepartment != null) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmação");
            alert.setHeaderText("Excluir departamento?");
            alert.setContentText(selectedDepartment.getName());
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    service.deleta(selectedDepartment.getId());
                    loadDepartments();
                    clearFields();
                } catch (Exception e) {
                    showAlert("Erro", "Erro ao excluir", "Existem produtos vinculados a este departamento.", AlertType.ERROR);
                }
            }
        }
    }

    @FXML
    public void onTableMouseClicked() {
        selectedDepartment = tableView.getSelectionModel().getSelectedItem();
        if (selectedDepartment != null) {
            txtName.setText(selectedDepartment.getName());
            btnSave.setText("ATUALIZAR");
            btnDelete.setVisible(true);
        }
    }

    private void clearFields() {
        txtName.clear();
        selectedDepartment = null;
        btnSave.setText("CADASTRAR");
        btnDelete.setVisible(false);
        txtName.requestFocus();
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