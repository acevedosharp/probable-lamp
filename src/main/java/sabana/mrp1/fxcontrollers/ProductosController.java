package sabana.mrp1.fxcontrollers;

import javafx.event.ActionEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import sabana.mrp1.entities.IngredienteProducto;
import sabana.mrp1.entities.Producto;
import sabana.mrp1.events.SceneChangeEvent;
import sabana.mrp1.repositories.ProductoRepository;

@Component
public class ProductosController {
    private final ApplicationContext context;
    private final ProductoRepository productoRepository;

    public ProductosController(ApplicationContext context, ProductoRepository productoRepository) {
        this.context = context;
        this.productoRepository = productoRepository;
    }

    public void initialize() {
        Producto producto = productoRepository.findAll().get(0);
        System.out.println("Producto: " + producto.getNombre() + " | Ingredientes:");
        for (IngredienteProducto ingredienteProducto : producto.getIngredientesProducto()) {
            System.out.println(ingredienteProducto.getIngrediente().getNombre() + " - " + ingredienteProducto.getCantidad() + ingredienteProducto.getMetrica());
        }
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
