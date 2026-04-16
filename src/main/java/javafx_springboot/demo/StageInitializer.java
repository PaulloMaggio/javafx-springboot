package javafx_springboot.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javafx.stage.Stage;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {

    private final ApplicationContext context;

    public StageInitializer(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        try {
            Stage stage = event.getStage();
            stage.setTitle("Sistema de Cadastro de Produtos - JavaFX + Spring");
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}