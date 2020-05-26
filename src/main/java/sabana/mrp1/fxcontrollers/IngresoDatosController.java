package sabana.mrp1.fxcontrollers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
import sabana.mrp1.repositories.RegistroVentasPersistRepository;
import sabana.mrp1.repositories.RegistroVentasRepository;

import javax.swing.*;
import java.util.*;

@Component
public class IngresoDatosController {

    private final ApplicationContext context;
    private final RegistroVentasRepository registroVentasRepository;
    private final ProductoRepository productRepository;
    private final ComportamientoMesRepository comportamientoRepository;
    private final RegistroVentasPersistRepository registroVentasPersist;

    public @FXML
    VBox newRegistroDatos;
    public @FXML
    ComboBox<Producto> productos;
    public @FXML
    ComboBox<String> tipoRegistro;
    public @FXML
    ComboBox<Integer> mesRegistro;
    public @FXML
    ComboBox<String> tiempoRegistro;
    public @FXML
    Spinner<Double> numeroPedidos;
    private List<ComportamientoMes> pedidosPorMes;
    public @FXML
    TableView<ComportamientoMes> comportamientoProducto;
    public @FXML
    TableView<RegistroVentas> registroVentas;
    public @FXML
    TextField eventoDeterministico;
    public @FXML
    TextField eventoProbabilistico;

    private final ObservableList<RegistroVentas> registroVentasData = FXCollections.observableArrayList();
    private final ObservableList<Producto> productData = FXCollections.observableArrayList();
    private final ObservableList<ComportamientoMes> comportamientoData = FXCollections.observableArrayList();
    private final ObservableList<String> tipos = FXCollections.observableArrayList();
    private final ObservableList<String> tiempo = FXCollections.observableArrayList();
    private final ObservableList<Integer> meses = FXCollections.observableArrayList();
    private final ObservableList<RegistroVentas> registroData = FXCollections.observableArrayList();


    public IngresoDatosController(ApplicationContext context, RegistroVentasRepository registroVentasRepository, ProductoRepository productRepository, ComportamientoMesRepository comportamientoRepository, RegistroVentasPersistRepository registroVentasPersist) {
        this.context = context;
        this.registroVentasRepository = registroVentasRepository;
        this.productRepository = productRepository;
        this.comportamientoRepository = comportamientoRepository;
        this.registroVentasPersist = registroVentasPersist;
    }


    public void initialize() {
        /** System.out.println(registroVentasRepository.findAll().get(0));*/
        resetComboBox();
        setUpSpinners();
        setUpComportamientoTable();
        setUpRegistroProducto();
        updateRegistroData();

    }

    public @FXML
    void ingresarDatos() {

        newRegistroDatos.setVisible(true);
    }

    public @FXML
    void eliminarRegistros() {
        RegistroVentas registro = registroVentas.getSelectionModel().getSelectedItem();
        registroVentasRepository.delete(registro);
        updateRegistroData();
        resetComboBox();
        tipoRegistro.setDisable(false);
        tiempoRegistro.setDisable(false);

    }

    public @FXML
    void acceptNewRegister(ActionEvent actionEvent) {
        updateAllSpinnersTypedValues();


        if (comportamientoData.size() == 12) {
            RegistroVentasPersist ventas = new RegistroVentasPersist(
                    null,
                    tipoRegistro.getValue(),
                    tiempoRegistro.getValue(),
                    productos.getValue());


            Integer id = registroVentasPersist.saveAndFlush(ventas).getInventarioId();

            for (ComportamientoMes comportamiento : comportamientoData) {
                //noinspection OptionalGetWithoutIsPresent
                comportamiento.setRegistroVentas(registroVentasRepository.findById(id).get());
                comportamientoRepository.save(comportamiento);
            }


            registroVentasData.setAll(registroVentasRepository.findAll());
            updateComboBoxCompleto(productos.getValue());
            clearNewRegistroDatosAndHide();
            resetComboBox();
            productos.setDisable(false);


        } else {
            System.out.println("Complete todos los registros, hay: " + comportamientoData.size());
        }

        int contador = registroVentasData.size();

        if (contador == productData.size()) {

            registroVentasData.clear();
            updateComboBoxTipoTiempo(tipoRegistro.getValue(), tiempoRegistro.getValue());

        }

        System.out.println(comportamientoData.toString() + comportamientoData.size() + contador);

    }

    public @FXML
    void cancelNewRegister(ActionEvent actionEvent) {
        resetComboBoxAll();
        productos.setDisable(false);
        clearNewRegistroDatosAndHide();

    }

    public @FXML
    void addRegistro(ActionEvent actionEvent) {
        updateAllSpinnersTypedValues();
        if (mesRegistro.getValue() == null) {
            System.out.println("Seleccione el mes");
        } else if (tiempoRegistro.getValue() == null) {
            System.out.println("Seleccione el tiempo");
        } else if (tipoRegistro.getValue() == null) {
            System.out.println("Seleccione el tipo");
        } else if (productos.getValue() == null) {
            System.out.println("Seleccione el producto");
        } else {
            productos.setDisable(true);


            ComportamientoMes pxm = new ComportamientoMes(
                    null,
                    null,
                    mesRegistro.getValue(),
                    numeroPedidos.getValue());

            comportamientoData.add(pxm);
            updateComboBox(mesRegistro.getValue());
            System.out.println(comportamientoData.toString());

        }
    }

    public @FXML
    void proyectarDemanda() {

        RegistroVentas prediccionAnterior;
        RegistroVentas realAnterior;
        RegistroVentasPersist proyeccion;
        List<RegistroVentas> prediccionAnteriorProducto = new ArrayList<>();
        List<RegistroVentas> realAnteriorProducto = new ArrayList<>();
        List<ComportamientoMes> comportamientoProyeccion = new ArrayList<>();

        if(eventoDeterministico.getText().equals("") || eventoProbabilistico.getText().equals("")) {
            System.out.println("Ingrese el porcentaje de afectacion en la demanda para cada evento");
        }else{

            double ed=Double.parseDouble(eventoDeterministico.getText());
            double ep=Double.parseDouble(eventoProbabilistico.getText());
            double eventos=(ed+ep)/2;

            for (int i = 0; i < registroVentasRepository.count(); i++) {

                int id = registroVentasRepository.findAll().get(i).getInventarioId();

                if (registroVentasRepository.findById(id).get().getTipo().equals("Prediccion") && registroVentasRepository.findById(id).get().getTiempo().equals("Anterior")) {
                    prediccionAnterior = registroVentasRepository.findAll().get(i);
                    prediccionAnteriorProducto.add(prediccionAnterior);
                    System.out.println(prediccionAnterior.getProducto().getNombre() + prediccionAnterior.getTipo() + prediccionAnterior.getComportamientosMes());
                } else if (registroVentasRepository.findById(id).get().getTipo().equals("Real") && registroVentasRepository.findById(id).get().getTiempo().equals("Anterior")) {
                    realAnterior = registroVentasRepository.findAll().get(i);
                    realAnteriorProducto.add(realAnterior);
                    System.out.println(realAnterior.getProducto().getNombre() + realAnterior.getTipo() + realAnterior.getComportamientosMes());
                }
            }

            for (int i = 0; i < prediccionAnteriorProducto.size(); i++) {
                for (int j = 0; j < realAnteriorProducto.size(); j++) {
                    if (prediccionAnteriorProducto.get(i).getProducto().getNombre().equals(realAnteriorProducto.get(j).getProducto().getNombre())) {
                        for (int k = 0; k < 12; k++) {

                            double ventasProyectadas = ((prediccionAnteriorProducto.get(i).getComportamientosMes().get(k).getVentas() + realAnteriorProducto.get(j).getComportamientosMes().get(k).getVentas()) / 2)*eventos;
                            ComportamientoMes proyeccionComportamiento = new ComportamientoMes(null, null, prediccionAnteriorProducto.get(i).getComportamientosMes().get(k).getMes(), ventasProyectadas);
                            comportamientoProyeccion.add(proyeccionComportamiento);
                            comportamientoData.add(proyeccionComportamiento);
                        }

                        proyeccion = new RegistroVentasPersist(null, "Prediccion", "Futuro", prediccionAnteriorProducto.get(i).getProducto());


                        Integer idRegistro = registroVentasPersist.saveAndFlush(proyeccion).getInventarioId();

                        for (ComportamientoMes comportamiento : comportamientoData) {
                            //noinspection OptionalGetWithoutIsPresent
                            comportamiento.setRegistroVentas(registroVentasRepository.findById(idRegistro).get());
                            comportamientoRepository.save(comportamiento);
                        }

                        System.out.println("El comportamiento es:" + comportamientoData);
                        System.out.println("La proyeccion es:" + proyeccion + "----" + idRegistro);
                        comportamientoData.clear();
                    }


                    registroVentasData.setAll(registroVentasRepository.findAll());
                }
            }
        }

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

    private void setUpRegistroProducto() {
        TableColumn<RegistroVentas, String> NombreProducto = new TableColumn<>("Producto");
        NombreProducto.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
        NombreProducto.setPrefWidth(135);

        TableColumn<RegistroVentas, String> Tipo = new TableColumn<>("Tipo");
        Tipo.setCellValueFactory(new PropertyValueFactory<>("tipoRegistro"));
        Tipo.setPrefWidth(105);

        TableColumn<RegistroVentas, String> Tiempo = new TableColumn<>("Tiempo");
        Tiempo.setCellValueFactory(new PropertyValueFactory<>("tiempoRegistro"));
        Tiempo.setPrefWidth(90);

        TableColumn<RegistroVentas, String> Enero = new TableColumn<>("JAN");

        Enero.setCellValueFactory(new PropertyValueFactory<>("enero"));
        Enero.setPrefWidth(87);

        TableColumn<RegistroVentas, String> Febrero = new TableColumn<>("FEB");
        Febrero.setCellValueFactory(new PropertyValueFactory<>("febrero"));
        Febrero.setPrefWidth(87);

        TableColumn<RegistroVentas, String> Marzo = new TableColumn<>("MAR");
        Marzo.setCellValueFactory(new PropertyValueFactory<>("marzo"));
        Marzo.setPrefWidth(87);

        TableColumn<RegistroVentas, String> Abril = new TableColumn<>("ABR");
        Abril.setCellValueFactory(new PropertyValueFactory<>("abril"));
        Abril.setPrefWidth(87);

        TableColumn<RegistroVentas, String> Mayo = new TableColumn<>("MAY");
        Mayo.setCellValueFactory(new PropertyValueFactory<>("mayo"));
        Mayo.setPrefWidth(87);

        TableColumn<RegistroVentas, String> Junio = new TableColumn<>("JUN");
        Junio.setCellValueFactory(new PropertyValueFactory<>("junio"));
        Junio.setPrefWidth(87);

        TableColumn<RegistroVentas, String> Julio = new TableColumn<>("JUL");
        Julio.setCellValueFactory(new PropertyValueFactory<>("julio"));
        Julio.setPrefWidth(87);

        TableColumn<RegistroVentas, String> Agosto = new TableColumn<>("AUG");
        Agosto.setCellValueFactory(new PropertyValueFactory<>("agosto"));
        Agosto.setPrefWidth(87);

        TableColumn<RegistroVentas, String> Septiembre = new TableColumn<>("SEP");
        Septiembre.setCellValueFactory(new PropertyValueFactory<>("septiembre"));
        Septiembre.setPrefWidth(87);

        TableColumn<RegistroVentas, String> Octubre = new TableColumn<>("OCT");
        Octubre.setCellValueFactory(new PropertyValueFactory<>("octubre"));
        Octubre.setPrefWidth(87);

        TableColumn<RegistroVentas, String> Noviembre = new TableColumn<>("NOV");
        Noviembre.setCellValueFactory(new PropertyValueFactory<>("noviembre"));
        Noviembre.setPrefWidth(87);

        TableColumn<RegistroVentas, String> Diciembre = new TableColumn<>("DEC");
        Diciembre.setCellValueFactory(new PropertyValueFactory<>("diciembre"));
        Diciembre.setPrefWidth(87);

        //noinspection unchecked
        registroVentas.getColumns().addAll(NombreProducto, Tipo, Tiempo, Enero, Febrero, Marzo, Abril, Mayo, Junio, Julio, Agosto, Septiembre, Octubre, Noviembre, Diciembre);

        registroVentas.setItems(registroVentasData);


        if (registroVentasData.size() != 0)
            registroVentas.getSelectionModel().select(0);
    }

    private void updateRegistroData() {
        registroVentasData.setAll(registroVentasRepository.findAll());
        if (registroVentasData.size() != 0)
            registroVentas.getSelectionModel().select(0);
    }


    private void clearNewRegistroDatosAndHide() {

        newRegistroDatos.setVisible(false);
        setSpinnerValue(numeroPedidos, 50);
        comportamientoData.clear();
    }

    private void hideModal() {

        newRegistroDatos.setVisible(false);
        setSpinnerValue(numeroPedidos, 50);
    }

    private void setSpinnerValue(Spinner<Double> spinner, double value) {
        spinner.getValueFactory().setValue(value);
    }

    private void resetComboBox() {
        productos.setItems(productData);
        tipos.setAll("Prediccion", "Real");
        tiempo.setAll("Anterior");
        meses.setAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        tipoRegistro.setItems(tipos);
        mesRegistro.setItems(meses);
        tiempoRegistro.setItems(tiempo);
        productData.setAll(productRepository.findAll());
        productos.getSelectionModel().clearSelection();
    }

    private void resetComboBoxAll() {
        productos.setItems(productData);
        tipos.setAll("Prediccion", "Real");
        tiempo.setAll("Anterior", "futuro");
        meses.setAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);

        mesRegistro.setItems(meses);
        tiempoRegistro.setItems(tiempo);
        productData.setAll(productRepository.findAll());
        productos.getSelectionModel().clearSelection();
    }

    private void updateComboBoxCompleto(Producto p) {
        productData.remove(p);
        productos.getSelectionModel().clearSelection();
    }

    private void updateComboBoxTipoTiempo(String type, String time) {
        tipos.remove(type);
        tiempo.remove(time);
        tiempoRegistro.getSelectionModel().clearSelection();
        tipoRegistro.getSelectionModel().clearSelection();
    }

    private void setUpSpinners() {
        numeroPedidos.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 2147483647, 50, 50));

    }

    public Producto getProduct(String nombre) {
        Producto product = new Producto();
        for (int i = 0; i < productData.size(); i++) {
            if (nombre == productData.toString()) {
                product = productData.get(i);
            }
        }
        return product;
    }

    private void updateAllSpinnersTypedValues() {
        updateSpinnerTypedValue(numeroPedidos);
    }

    private void updateSpinnerTypedValue(Spinner<Double> spinner) {
        double typedValue = Integer.parseInt(spinner.getEditor().getText());

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
