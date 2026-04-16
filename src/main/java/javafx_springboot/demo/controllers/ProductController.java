package javafx_springboot.demo.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx_springboot.demo.entities.Product;
import javafx_springboot.demo.entitiesDto.ProductDto;
import javafx_springboot.demo.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class ProductController implements Initializable {

    @Autowired
    private ProductService productService;

    @FXML
    private TextField txtName;
    @FXML
    private TextField txtDescription;
    @FXML
    private TextField txtPrice;

    @FXML
    private TableView<Product> tableView;
    @FXML
    private TableColumn<Product, Long> columnId;
    @FXML
    private TableColumn<Product, String> columnName;
    @FXML
    private TableColumn<Product, String> columnDescription;
    @FXML
    private TableColumn<Product, Double> columnPrice;

    private ObservableList<Product> obsList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        columnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        loadProducts();
    }

    @FXML
    public void onBtnSaveAction() {
        try {
            ProductDto dto = new ProductDto();
            dto.name = txtName.getText();
            dto.description = txtDescription.getText();
            dto.price = Double.parseDouble(txtPrice.getText());

            productService.salva(dto);
            
            loadProducts();
            clearFields();
            showAlert("Success", "Product saved!", Alert.AlertType.INFORMATION);
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid numeric format", Alert.AlertType.ERROR);
        } catch (Exception e) {
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void loadProducts() {
        obsList.setAll(productService.buscaTodos());
        tableView.setItems(obsList);
    }

    private void clearFields() {
        txtName.clear();
        txtDescription.clear();
        txtPrice.clear();
        txtName.requestFocus();
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}