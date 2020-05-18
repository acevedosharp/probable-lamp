package sabana.mrp1.fxcontrollers;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import sabana.mrp1.events.SceneChangeEvent;

@Component
public class PrediccionController {
    private final ApplicationContext context;

    public PrediccionController(ApplicationContext context) {
        this.context = context;
    }
















    // Scene change menu logic
    public void goToProductosModule() {
        context.publishEvent(new SceneChangeEvent("fxml/productos.fxml"));
    }
    public void goToRegistroModule() {
        context.publishEvent(new SceneChangeEvent("fxml/ingreso_datos.fxml"));
    }
    public void goToPrediccionModule() {
        context.publishEvent(new SceneChangeEvent("fxml/prediccion.fxml"));
    }
}
