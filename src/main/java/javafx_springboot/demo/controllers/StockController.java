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
import javafx.util.StringConverter;
import javafx_springboot.demo.entities.Department;
import javafx_springboot.demo.entities.Product;
import javafx_springboot.demo.services.DepartmentService;
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
    @Autowired
    private DepartmentService departmentService;

    @FXML private TextField txtSearch;
    @FXML private ComboBox<Department> comboDeptFilter;
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
        setupDeptCombo();
        loadStock();
    }

    private void formatPriceColumn() {
        columnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        columnPrice.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty || price == null) setText(null);
                else setText(currencyFormat.format(price));
            }
        });
    }

    private void setupDeptCombo() {
        comboDeptFilter.setConverter(new StringConverter<>() {
            @Override public String toString(Department d) { return d == null ? "Todos os Departamentos" : d.getName(); }
            @Override public Department fromString(String string) { return null; }
        });
        comboDeptFilter.setItems(FXCollections.observableArrayList(departmentService.buscaTodos()));
    }

    @FXML
    public void loadStock() {
        obsList.setAll(productService.buscaTodos());
        filteredData = new FilteredList<>(obsList, p -> true);
        tableView.setItems(filteredData);
        updateTotals();
    }

    @FXML
    public void updateFilters() {
        String nameFilter = txtSearch.getText() == null ? "" : txtSearch.getText().toLowerCase();
        Department selectedDept = comboDeptFilter.getValue();

        filteredData.setPredicate(product -> {
            boolean matchesName = nameFilter.isEmpty() || product.getName().toLowerCase().contains(nameFilter);
            boolean matchesDept = (selectedDept == null) || 
                                 (product.getDepartment() != null && product.getDepartment().getId().equals(selectedDept.getId()));
            
            return matchesName && matchesDept;
        });
        updateTotals();
    }

    @FXML
    public void clearFilters() {
        txtSearch.clear();
        comboDeptFilter.setValue(null);
        updateFilters();
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