package sabana.mrp1;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import sabana.mrp1.events.StageReadyEvent;

public class ClientApplication extends Application {

    private ConfigurableApplicationContext context;
    public static Stage window;


    @Override public void init() {
        context = new SpringApplicationBuilder(Mrp1Application.class).run();
    }

    @Override public void stop() {
        context.close();
        Platform.exit();
    }

    @Override public void start(Stage stage) {
        context.publishEvent(new StageReadyEvent(stage));
        window = stage;
    }

}
