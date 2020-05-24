package sabana.mrp1.fxcontrollers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import sabana.mrp1.entities.Ingrediente;
import sabana.mrp1.entities.IngredienteProducto;
import sabana.mrp1.entities.Producto;
import sabana.mrp1.entities.ProductoPersist;
import sabana.mrp1.events.SceneChangeEvent;
import sabana.mrp1.repositories.IngredienteProductoRepository;
import sabana.mrp1.repositories.IngredienteRepository;
import sabana.mrp1.repositories.ProductoPersistRepository;
import sabana.mrp1.repositories.ProductoRepository;

@Component
public class ProductosController {
    // Injected
    private final ApplicationContext context;
    private final ProductoRepository productoRepository;
    private final ProductoPersistRepository productoPersistRepository;
    private final IngredienteRepository ingredienteRepository;
    private final IngredienteProductoRepository ingredienteProductoRepository;

    // New Producto
    public @FXML VBox newProductoModal;
    public @FXML TextField nombreProductoField;
    public @FXML Spinner<Integer> precioVentaProductoSpinner, existenciasProductoSpinner, cantidadIngredienteSpinner;
    public @FXML ComboBox<Ingrediente> ingredienteComboBox;
    public @FXML TableView<IngredienteProducto> ingredientesProductoTable;
    public @FXML TableView<Producto> productosTable;

    // New Ingrediente
    public @FXML VBox newIngredienteModal;
    public @FXML TextField nombreIngredienteField, metricaIngredienteField;
    public @FXML Spinner<Integer> precioUnitarioIngredienteSpinner, existenciasIngredienteSpinner;

    // Support variables
    private final ObservableList<Producto> productData = FXCollections.observableArrayList(); // Immutable
    private final ObservableList<IngredienteProducto> ingredientesProductoData = FXCollections.observableArrayList(); // Mutable
    private final ObservableList<Ingrediente> availableIngredientes = FXCollections.observableArrayList(); // Mutable


    public ProductosController(ApplicationContext context, ProductoRepository productoRepository, ProductoPersistRepository productoPersistRepository, IngredienteRepository ingredienteRepository, IngredienteProductoRepository ingredienteProductoRepository) {
        this.context = context;
        this.productoRepository = productoRepository;
        this.productoPersistRepository = productoPersistRepository;
        this.ingredienteRepository = ingredienteRepository;
        this.ingredienteProductoRepository = ingredienteProductoRepository;
    }


    public void initialize() {

        setUpSpinners();

        resetComboBox();

        setUpProductosTable();
        setUpIngredientesProductoTable();

        updateProductosData();
    }


    // Producto related logic
    public @FXML void newProducto() {
        newProductoModal.setVisible(true);
    }
    public @FXML void deleteSelectedProduct() {
        Producto producto = productosTable.getSelectionModel().getSelectedItem();
        productoRepository.delete(producto);
        updateProductosData();
    }
    public @FXML void acceptNewProduct() {
        updateAllSpinnersTypedValues();

        ProductoPersist newProducto = new ProductoPersist(
                null,
                nombreProductoField.getText(),
                precioVentaProductoSpinner.getValue(),
                existenciasProductoSpinner.getValue()
        );

        Integer id = productoPersistRepository.saveAndFlush(newProducto).getProductoId();

        for (IngredienteProducto ingredienteProducto : ingredientesProductoData) {
            //noinspection OptionalGetWithoutIsPresent
            ingredienteProducto.setProducto(productoRepository.findById(id).get());
            ingredienteProductoRepository.save(ingredienteProducto);
        }

        resetComboBox();
        updateProductosData();
        clearNewProductoModalAndHide();
    }
    public @FXML void cancelNewProduct() {
        resetComboBox();
        clearNewProductoModalAndHide();
    }
    public @FXML void addIngrediente() {
        updateAllSpinnersTypedValues();

        Ingrediente ingredienteSnapshot = ingredienteComboBox.getValue();
        ingredientesProductoData.add(new IngredienteProducto(
                null,
                null,
                ingredienteSnapshot,
                cantidadIngredienteSpinner.getValue()
        ));

        updateComboBox(ingredienteSnapshot);
        setSpinnerValue(cantidadIngredienteSpinner, 1);
    }


    // New Ingrediente
    public @FXML void newIngrediente() {
        newIngredienteModal.setVisible(true);
    }
    public @FXML void acceptNewIngrediente() {
        updateAllSpinnersTypedValues();

        Ingrediente ingrediente = new Ingrediente(
                null,
                nombreIngredienteField.getText(),
                metricaIngredienteField.getText(),
                precioUnitarioIngredienteSpinner.getValue(),
                existenciasIngredienteSpinner.getValue()
        );

        ingredienteRepository.save(ingrediente);
        resetComboBox();
        clearNewIngredienteModalAndHide();
    }
    public @FXML void cancelNewIngrediente() {
        clearNewIngredienteModalAndHide();
    }


    // UI utilities
    private void setUpSpinners() {
        precioVentaProductoSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(50, 2147483647, 50, 50));
        existenciasProductoSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 2147483647, 0, 1));
        cantidadIngredienteSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 2147483647, 1, 1));

        existenciasIngredienteSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 2147483647, 0, 1));
        precioUnitarioIngredienteSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(50, 2147483647, 50, 50));
    }

    private void setUpProductosTable() {
        TableColumn<Producto, String> nombreCol = new TableColumn<>("Nombre");
        nombreCol.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        nombreCol.setPrefWidth(250);

        TableColumn<Producto, String> precioVentaCol = new TableColumn<>("Precio Venta");
        precioVentaCol.setCellValueFactory(new PropertyValueFactory<>("precioVenta"));
        precioVentaCol.setPrefWidth(145);

        TableColumn<Producto, String> existenciasCol = new TableColumn<>("Existencias");
        existenciasCol.setCellValueFactory(new PropertyValueFactory<>("existencias"));
        existenciasCol.setPrefWidth(140);

        TableColumn<Producto, String> ingredientesCol = new TableColumn<>("Ingredientes");
        ingredientesCol.setCellValueFactory(new PropertyValueFactory<>("ingredientesRepresentation"));
        ingredientesCol.setPrefWidth(580);

        //noinspection unchecked
        productosTable.getColumns().addAll(nombreCol, precioVentaCol, existenciasCol, ingredientesCol);
        productosTable.setItems(productData);

        if (productData.size() != 0)
            productosTable.getSelectionModel().select(0);
    }
    private void setUpIngredientesProductoTable() {
        TableColumn<IngredienteProducto, String> ingredienteCol = new TableColumn<>("Ingrediente");
        ingredienteCol.setCellValueFactory(new PropertyValueFactory<>("nombreIngrediente"));
        ingredienteCol.setPrefWidth(300);

        TableColumn<IngredienteProducto, String> cantidadCol = new TableColumn<>("Cantidad");
        cantidadCol.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        cantidadCol.setPrefWidth(100);

        TableColumn<IngredienteProducto, String> metricaCol = new TableColumn<>("Métrica");
        metricaCol.setCellValueFactory(new PropertyValueFactory<>("metricaIngrediente"));
        metricaCol.setPrefWidth(95);

        //noinspection unchecked
        ingredientesProductoTable.getColumns().addAll(ingredienteCol, cantidadCol, metricaCol);
        ingredientesProductoTable.setItems(ingredientesProductoData);
    }
    private void updateProductosData() {
        productData.setAll(productoRepository.findAll());
        if (productData.size() != 0)
            productosTable.getSelectionModel().select(0);
    }
    private void updateComboBox(Ingrediente ingrediente) {
        availableIngredientes.remove(ingrediente);
        ingredienteComboBox.getSelectionModel().clearSelection();
    }
    private void resetComboBox() {
        availableIngredientes.clear();
        ingredienteComboBox.setItems(availableIngredientes);
        availableIngredientes.setAll(ingredienteRepository.findAll());
        ingredienteComboBox.getSelectionModel().clearSelection();
    }
    private void updateAllSpinnersTypedValues() {
        updateSpinnerTypedValue(cantidadIngredienteSpinner);
        updateSpinnerTypedValue(existenciasProductoSpinner);
        updateSpinnerTypedValue(precioVentaProductoSpinner);
        updateSpinnerTypedValue(existenciasIngredienteSpinner);
        updateSpinnerTypedValue(precioUnitarioIngredienteSpinner);
    }
    private void updateSpinnerTypedValue(Spinner<Integer> spinner) {
        int typedValue = Integer.parseInt(spinner.getEditor().getText());

        spinner.getValueFactory().setValue(typedValue);
    }
    private void setSpinnerValue(Spinner<Integer> spinner, int value) {
        spinner.getValueFactory().setValue(value);
    }
    private void clearNewProductoModalAndHide() {
        nombreProductoField.setText("");
        setSpinnerValue(precioVentaProductoSpinner, 50);
        setSpinnerValue(existenciasProductoSpinner, 0);
        ingredientesProductoData.clear();

        newProductoModal.setVisible(false);
    }
    private void clearNewIngredienteModalAndHide() {
        nombreIngredienteField.setText("");
        metricaIngredienteField.setText("");
        setSpinnerValue(precioUnitarioIngredienteSpinner, 50);
        setSpinnerValue(existenciasIngredienteSpinner, 0);

        newIngredienteModal.setVisible(false);
    }



    // Scene change menu logic
    public @FXML void goToProductosModule() {
        context.publishEvent(new SceneChangeEvent("fxml/productos.fxml"));
    }
    public @FXML void goToRegistroModule() {
        context.publishEvent(new SceneChangeEvent("fxml/ingreso_datos.fxml"));
    }
    public @FXML void goToPrediccionModule() {
        context.publishEvent(new SceneChangeEvent("fxml/prediccion.fxml"));
    }

}