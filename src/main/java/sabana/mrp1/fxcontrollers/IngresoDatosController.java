package sabana.mrp1.fxcontrollers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import sabana.mrp1.entities.*;
import sabana.mrp1.events.SceneChangeEvent;
import sabana.mrp1.repositories.ComportamientoMesRepository;
import sabana.mrp1.repositories.ProductoRepository;
import sabana.mrp1.repositories.RegistroVentasRepository;

import javax.swing.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@Component
public class IngresoDatosController {

    private final ApplicationContext context;
    private final RegistroVentasRepository registroVentasRepository;
    private final ProductoRepository productRepository;
    private final ComportamientoMesRepository comportamientoRepository;


    public @FXML VBox newRegistroDatos;
    public @FXML ComboBox<Producto> productos;
    public @FXML ComboBox<String> tipoRegistro;
    public @FXML ComboBox<Integer> mesRegistro;
    public @FXML ComboBox<String> tiempoRegistro;
    public @FXML Spinner<Integer> numeroPedidos;
    private List<ComportamientoMes> pedidosPorMes;
    public @FXML TableView<ComportamientoMes> comportamientoProducto;


    private final ObservableList<RegistroVentas> RegistroVentasData = FXCollections.observableArrayList();
    private final ObservableList<Producto> productData = FXCollections.observableArrayList();
    private final ObservableList<ComportamientoMes> comportamientoData=FXCollections.observableArrayList();
    private final ObservableList<String> tipos = FXCollections.observableArrayList();
    private final ObservableList<String> tiempo = FXCollections.observableArrayList();
    private final ObservableList<Integer> meses = FXCollections.observableArrayList();


    public IngresoDatosController(ApplicationContext context, RegistroVentasRepository registroVentasRepository, ProductoRepository productRepository, ComportamientoMesRepository comportamientoRepository) {
        this.context = context;
        this.registroVentasRepository = registroVentasRepository;
        this.productRepository=productRepository;
        this.comportamientoRepository=comportamientoRepository;
    }


    public void initialize() {
       /** System.out.println(registroVentasRepository.findAll().get(0));*/
        resetComboBox();
        setUpSpinners();
        setUpComportamientoTable();

    }



    public @FXML void ingresarDatos(){

        newRegistroDatos.setVisible(true);
    }

    public @FXML void acceptNewRegister(ActionEvent actionEvent) {
        /**RegistroVentas ventas= new RegistroVentas(
         null,
         tipoRegistro.getValue(),
         tiempoRegistro.getValue(),
         );*/

    }

    public @FXML void cancelNewRegister(ActionEvent actionEvent) { clearNewRegistroDatosAndHide(); }

    public @FXML void addRegistro(ActionEvent actionEvent) {
        updateAllSpinnersTypedValues();
        ComportamientoMes pxm= new ComportamientoMes(
                null,
                null,
                productos.getValue(),
                mesRegistro.getValue(),
                numeroPedidos.getValue());
        comportamientoData.add(pxm);
        updateComboBox(mesRegistro.getValue());

    }


    private void setUpComportamientoTable() {
        TableColumn<ComportamientoMes, String> mes = new TableColumn<>("Mes");
        mes.setCellValueFactory(new PropertyValueFactory<>("mes"));
        mes.setPrefWidth(248);

        TableColumn<ComportamientoMes, String> pedidos = new TableColumn<>("Pedidos");
        pedidos.setCellValueFactory(new PropertyValueFactory<>("ventas"));
        pedidos.setPrefWidth(248);

        //noinspection unchecked
        comportamientoProducto.getColumns().addAll(mes, pedidos);
        comportamientoProducto.setItems(comportamientoData);
    }


    private void clearNewRegistroDatosAndHide() {

        newRegistroDatos.setVisible(false);
    }

    private void setSpinnerValue(Spinner<Integer> spinner, int value) {
        spinner.getValueFactory().setValue(value);
    }

    private void resetComboBox() {
        productos.setItems(productData);
        tipos.setAll("Prediccion", "Real");
        tiempo.setAll("Anterior","futuro");
        meses.setAll(1,2,3,4,5,6,7,8,9,10,11,12);
        tipoRegistro.setItems(tipos);
        mesRegistro.setItems(meses);
        tiempoRegistro.setItems(tiempo);
        productData.setAll(productRepository.findAll());
        productos.getSelectionModel().clearSelection();
    }

    private void setUpSpinners() {
        numeroPedidos.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(50, 2147483647, 50, 50));

    }

    public Producto getProduct(String nombre){
        Producto product=new Producto();
        for(int i=0; i < productData.size();i++) {
            if (nombre == productData.toString()) {
                product=productData.get(i);
            }
        }
        return product;
    }

    private void updateAllSpinnersTypedValues() {
        updateSpinnerTypedValue(numeroPedidos);
    }

    private void updateSpinnerTypedValue(Spinner<Integer> spinner) {
        int typedValue = Integer.parseInt(spinner.getEditor().getText());

        spinner.getValueFactory().setValue(typedValue);
    }

    private void updateComboBox(Integer mes) {
        meses.remove(mes);
        mesRegistro.getSelectionModel().clearSelection();
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
