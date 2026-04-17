package javafx_springboot.demo.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx_springboot.demo.entities.Department;
import javafx_springboot.demo.entities.Product;
import javafx_springboot.demo.entitiesDto.ProductDto;
import javafx_springboot.demo.services.DepartmentService;
import javafx_springboot.demo.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

@Component
public class ProductController implements Initializable {

    @Autowired
    private ProductService productService;
    @Autowired
    private DepartmentService departmentService;

    @FXML private TextField txtName;
    @FXML private TextField txtDescription;
    @FXML private TextField txtPrice;
    @FXML private ComboBox<Department> comboDepartment;
    @FXML private Button btnDelete;
    @FXML private Button btnSave;
    @FXML private TableView<Product> tableView;
    @FXML private TableColumn<Product, Long> columnId;
    @FXML private TableColumn<Product, String> columnName;
    @FXML private TableColumn<Product, String> columnDescription;
    @FXML private TableColumn<Product, Double> columnPrice;
    @FXML private TableColumn<Product, String> columnDepartment;

    private ObservableList<Product> obsList = FXCollections.observableArrayList();
    private ObservableList<Department> obsDept = FXCollections.observableArrayList();
    private Product selectedProduct;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        columnDepartment.setCellValueFactory(new PropertyValueFactory<>("department"));
        formatPriceColumn();
        initializeComboBoxDepartment();
        loadProducts();
        loadDepartments();
    }

    private void formatPriceColumn() {
        columnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        columnPrice.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty || price == null) setText(null);
                else setText(currencyFormat.format(price));
            }
        });
    }

    private void initializeComboBoxDepartment() {
        comboDepartment.setConverter(new StringConverter<>() {
            @Override public String toString(Department d) { return d == null ? null : d.getName(); }
            @Override public Department fromString(String string) { return null; }
        });
    }

    @FXML
    public void onBtnSaveAction() {
        try {
            if (comboDepartment.getSelectionModel().getSelectedItem() == null) {
                showAlert("Erro", "Selecione um departamento", Alert.AlertType.WARNING);
                return;
            }
            ProductDto dto = new ProductDto();
            dto.name = txtName.getText();
            dto.description = txtDescription.getText();
            dto.price = Double.parseDouble(txtPrice.getText().replace(",", "."));
            dto.departmentId = comboDepartment.getSelectionModel().getSelectedItem().getId();

            if (selectedProduct == null) {
                productService.salva(dto);
                showAlert("Sucesso", "Produto cadastrado!", Alert.AlertType.INFORMATION);
            } else {
                productService.atualiza(selectedProduct.getId(), dto);
                showAlert("Sucesso", "Produto atualizado!", Alert.AlertType.INFORMATION);
            }
            loadProducts();
            clearFields();
        } catch (Exception e) {
            showAlert("Erro", "Verifique os dados: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void onBtnDeleteAction() {
        if (selectedProduct != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação");
            alert.setHeaderText("Excluir Produto");
            alert.setContentText("Deseja excluir: " + selectedProduct.getName() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                productService.deleta(selectedProduct.getId());
                loadProducts();
                clearFields();
            }
        }
    }

    @FXML
    public void onTableMouseClicked() {
        selectedProduct = tableView.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            txtName.setText(selectedProduct.getName());
            txtDescription.setText(selectedProduct.getDescription());
            txtPrice.setText(String.valueOf(selectedProduct.getPrice()));
            comboDepartment.getSelectionModel().select(selectedProduct.getDepartment());
            btnDelete.setVisible(true);
            btnSave.setText("ATUALIZAR");
        }
    }

    @FXML
    public void loadProducts() {
        obsList.setAll(productService.buscaTodos());
        tableView.setItems(obsList);
    }

    public void loadDepartments() {
        obsDept.setAll(departmentService.buscaTodos());
        comboDepartment.setItems(obsDept);
    }

    private void clearFields() {
        txtName.clear();
        txtDescription.clear();
        txtPrice.clear();
        comboDepartment.getSelectionModel().clearSelection();
        selectedProduct = null;
        btnDelete.setVisible(false);
        btnSave.setText("SALVAR");
        txtName.requestFocus();
    }

    @FXML
    public void onBtnBackAction(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}