package javafx_springboot.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {

    @Value("classpath:/view/main.fxml")
    private Resource fxml;
    private final ApplicationContext context;

    public StageInitializer(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(fxml.getURL());
            
            // ESSENCIAL: Faz o Spring gerenciar os Controllers do JavaFX
            fxmlLoader.setControllerFactory(context::getBean);
            
            Parent parent = fxmlLoader.load();
            Stage stage = event.getStage();
            stage.setScene(new Scene(parent, 400, 300));
            stage.setTitle("Sistema de Cadastro - JavaFX + Spring");
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar o FXML", e);
        }
    }
}