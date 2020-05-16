package sabana.mrp1.event_handlers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import sabana.mrp1.ClientApplication;
import sabana.mrp1.Mrp1Application;
import sabana.mrp1.events.SceneChangeEvent;

import java.io.IOException;
import java.net.URL;

@Component
public class SceneInitializer implements ApplicationListener<SceneChangeEvent> {

    private ApplicationContext applicationContext;

    @Autowired
    public SceneInitializer(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override public void onApplicationEvent(SceneChangeEvent sceneChangeEvent) {
        try {
            URL url = Mrp1Application.class.getClassLoader().getResource(sceneChangeEvent.getRoute());
            FXMLLoader loader = new FXMLLoader(url);
            loader.setControllerFactory(applicationContext::getBean);
            Parent parent = loader.load();

            ClientApplication.window.getScene().setRoot(parent);
        } catch (IOException e) {
            e.getMessage();
        }
    }
}