package sabana.mrp1.event_handlers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import sabana.mrp1.Mrp1Application;
import sabana.mrp1.events.SceneChangeEvent;
import sabana.mrp1.events.StageReadyEvent;

import java.io.IOException;
import java.net.URL;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {

    private URL url = Mrp1Application.class.getClassLoader().getResource("fxml/productos.fxml");
    private ApplicationContext context;

    @Autowired
    public StageInitializer(ApplicationContext context) {
        this.context = context;
    }

    @Override public void onApplicationEvent(StageReadyEvent stageReadyEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(url);
            loader.setControllerFactory(aClass -> context.getBean(aClass));
            Parent parent = loader.load();

            Stage stage = stageReadyEvent.getStage();
            stage.setScene(new Scene(parent));
            stage.setTitle("MRP1 probable-lamp");
            stage.getIcons().add(new Image("/static/icon_logo.png"));
            stage.show();
            stage.setMinWidth(1280);
            stage.setMinHeight(720);
        } catch (IOException e) {
            e.printStackTrace();
            // throw new RuntimeException();
        }
    }

}