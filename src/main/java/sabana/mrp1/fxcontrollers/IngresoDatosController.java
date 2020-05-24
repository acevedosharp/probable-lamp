package sabana.mrp1.fxcontrollers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import sabana.mrp1.entities.ComportamientoMes;
import sabana.mrp1.entities.Producto;
import sabana.mrp1.entities.RegistroVentas;
import sabana.mrp1.events.SceneChangeEvent;
import sabana.mrp1.repositories.ProductoRepository;
import sabana.mrp1.repositories.RegistroVentasRepository;

import javax.swing.*;

@Component
public class IngresoDatosController {

    private final ApplicationContext context;
    private final RegistroVentasRepository registroVentasRepository;
    private final ProductoRepository productRepository;

    public @FXML VBox newRegistroDatos;
    public @FXML ComboBox productos;
    public @FXML ComboBox tipoRegistro;
    public @FXML ComboBox mesRegistro;
    public @FXML Spinner<Integer> numeroPedidos;

    private final ObservableList<RegistroVentas> RegistroVentasData = FXCollections.observableArrayList();
    private final ObservableList<Producto> productData = FXCollections.observableArrayList();
    private final ObservableList<String> tipos = FXCollections.observableArrayList();
    private final ObservableList<String> meses = FXCollections.observableArrayList();

    public IngresoDatosController(ApplicationContext context, RegistroVentasRepository registroVentasRepository, ProductoRepository productRepository) {
        this.context = context;
        this.registroVentasRepository = registroVentasRepository;
        this.productRepository=productRepository;
    }


    public void initialize() {
       /** System.out.println(registroVentasRepository.findAll().get(0));*/
        resetComboBox();

    }

    public @FXML void ingresarDatos(){
        newRegistroDatos.setVisible(true);
    }

    public @FXML void acceptNewRegister(ActionEvent actionEvent) {
        RegistroVentas ventas= new RegistroVentas();
    }

    public @FXML void cancelNewRegister(ActionEvent actionEvent) { clearNewRegistroDatosAndHide(); }

    public @FXML void addRegistro(ActionEvent actionEvent) {
    }



    private void clearNewRegistroDatosAndHide() {


        newRegistroDatos.setVisible(false);
    }

    private void setSpinnerValue(Spinner<Integer> spinner, int value) {
        spinner.getValueFactory().setValue(value);
    }


    private void resetComboBox() {
        productos.setItems(productData);
        tipos.addAll("Prediccion Año anterior", "Ventas reales");
        meses.addAll("Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre");
        tipoRegistro.setItems(tipos);
        mesRegistro.setItems(meses);
        productData.setAll(productRepository.findAll());
        productos.getSelectionModel().clearSelection();
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
