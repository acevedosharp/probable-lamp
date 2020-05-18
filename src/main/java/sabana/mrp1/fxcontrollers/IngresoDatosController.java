package sabana.mrp1.fxcontrollers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import sabana.mrp1.events.SceneChangeEvent;
import sabana.mrp1.repositories.RegistroVentasRepository;

@Component
public class IngresoDatosController {

    private final ApplicationContext context;
    private final RegistroVentasRepository registroVentasRepository;


    public IngresoDatosController(ApplicationContext context, RegistroVentasRepository registroVentasRepository) {
        this.context = context;
        this.registroVentasRepository = registroVentasRepository;
    }


    public void initialize() {
        System.out.println(registroVentasRepository.findAll().get(0));
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
