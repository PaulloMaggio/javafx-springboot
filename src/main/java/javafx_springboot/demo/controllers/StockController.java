package javafx_springboot.demo.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx_springboot.demo.entities.Product;
import javafx_springboot.demo.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

@Component
public class StockController implements Initializable {

    @Autowired
    private ProductService productService;

    @FXML private TextField txtSearch;
    @FXML private TableView<Product> tableView;
    @FXML private TableColumn<Product, Long> columnId;
    @FXML private TableColumn<Product, String> columnName;
    @FXML private TableColumn<Product, String> columnDepartment;
    @FXML private TableColumn<Product, Double> columnPrice;
    
    @FXML private Label lblTotalItems;
    @FXML private Label lblTotalValue;

    private ObservableList<Product> obsList = FXCollections.observableArrayList();
    private FilteredList<Product> filteredData;
    private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnDepartment.setCellValueFactory(new PropertyValueFactory<>("department"));
        
        formatPriceColumn();
        loadStock();
    }

    private void formatPriceColumn() {
        columnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        columnPrice.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty || price == null) {
                    setText(null);
                } else {
                    setText(currencyFormat.format(price));
                }
            }
        });
    }

    @FXML
    public void loadStock() {
        obsList.setAll(productService.buscaTodos());
        filteredData = new FilteredList<>(obsList, p -> true);
        tableView.setItems(filteredData);
        updateTotals();
    }

    @FXML
    public void onTxtSearchKeyReleased() {
        String filter = txtSearch.getText().toLowerCase();
        filteredData.setPredicate(product -> {
            if (filter == null || filter.isEmpty()) return true;
            return product.getName().toLowerCase().contains(filter);
        });
        updateTotals();
    }

    private void updateTotals() {
        int items = filteredData.size();
        double totalValue = filteredData.stream()
                .mapToDouble(Product::getPrice)
                .sum();

        lblTotalItems.setText(String.valueOf(items));
        lblTotalValue.setText(currencyFormat.format(totalValue));
    }

    @FXML
    public void onBtnBackAction(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}