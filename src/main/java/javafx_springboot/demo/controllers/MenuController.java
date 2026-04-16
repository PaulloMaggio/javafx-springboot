package javafx_springboot.demo.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MenuController {

    @Autowired
    private ApplicationContext context;

    @FXML
    private Button btnProduct;

    @FXML
    private Button btnDepartment;

    @FXML
    private Button btnStock;

    @FXML
    public void onBtnProductAction() {
        loadView("/view/main.fxml", "Cadastro de Produtos");
    }

    @FXML
    public void onBtnDepartmentAction() {
        loadView("/view/department.fxml", "Cadastro de Departamentos");
    }

    @FXML
    public void onBtnStockAction() {
        loadView("/view/stock.fxml", "Estoque Geral");
    }

    private void loadView(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            loader.setControllerFactory(context::getBean);
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}