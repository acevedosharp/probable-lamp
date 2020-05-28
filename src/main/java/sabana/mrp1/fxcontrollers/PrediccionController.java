package sabana.mrp1.fxcontrollers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import sabana.mrp1.entities.Ingrediente;
import sabana.mrp1.entities.OrdenCompraMes;
import sabana.mrp1.events.SceneChangeEvent;
import sabana.mrp1.repositories.IngredienteRepository;
import sabana.mrp1.services.InventoryPlanningService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class PrediccionController {
    private final ApplicationContext context;
    private final IngredienteRepository ingredienteRepository;
    private final InventoryPlanningService inventoryPlanningService;

    public @FXML Button predictButton;
    public @FXML TreeView<String> prediccionMeses;

    private final ObservableList<Ingrediente> ingredientesData = FXCollections.observableArrayList();
    private final TreeItem<String> root = new TreeItem<>("Predicción Meses");

    public PrediccionController(ApplicationContext context, IngredienteRepository ingredienteRepository, InventoryPlanningService inventoryPlanningService) {
        this.context = context;
        this.ingredienteRepository = ingredienteRepository;
        this.inventoryPlanningService = inventoryPlanningService;
    }

    public void initialize() {
        ingredientesData.setAll(ingredienteRepository.findAll());

        setUpPrediccionMesesTree();
    }

    public @FXML void executePrediction() {
        // Execute service this way so UI doesn't freeze
        Platform.runLater(inventoryPlanningService::planUsingCurrentFuturePrediction);

        Platform.runLater(this::updateUIWithOrdenes);
    }

    private void setUpPrediccionMesesTree() {
        TreeItem<String> enero, febrero, marzo, abril, mayo, junio, julio, agosto, sept, octubre, nov, dic;

        enero = new TreeItem<>("Predicción Enero");
        febrero = new TreeItem<>("Predicción Febrero");
        marzo = new TreeItem<>("Predicción Marzo");
        abril = new TreeItem<>("Predicción Abril");
        mayo = new TreeItem<>("Predicción Mayo");
        junio = new TreeItem<>("Predicción Junio");
        julio = new TreeItem<>("Predicción Julio");
        agosto = new TreeItem<>("Predicción Agosto");
        sept = new TreeItem<>("Predicción Septiembre");
        octubre = new TreeItem<>("Predicción Octubre");
        nov = new TreeItem<>("Predicción Noviembre");
        dic = new TreeItem<>("Predicción Diciembre");

        root.setExpanded(true);

        //noinspection unchecked
        root.getChildren().setAll(enero, febrero, marzo, abril, mayo, junio, julio, agosto, sept, octubre, nov, dic);

        updateUIWithOrdenes();

        prediccionMeses.setRoot(root);
        prediccionMeses.setShowRoot(false);
    }

    private void updateUIWithOrdenes() {
        predictButton.setDisable(true);
        if (inventoryPlanningService.existOrdenes()) {

            Map<Integer, List<OrdenCompraMes>> ordenes = inventoryPlanningService.groupedByMonth();

            ordenes.forEach((integer, ordenesMes) ->
                    root.getChildren().get(integer - 1).getChildren().setAll(
                            ordenesMes.stream().map(
                                    ordenCompraMes ->
                                            new TreeItem<>(ordenCompraMes.getIngrediente().getNombre() + " -  x" + ordenCompraMes.getCantidad() + " " + ordenCompraMes.getIngrediente().getMetrica())
                            ).collect(Collectors.toList())

                    )
            );

            root.getChildren().forEach(stringTreeItem -> stringTreeItem.setExpanded(true));
        } else {
            predictButton.setDisable(false);
            root.getChildren().forEach(item -> item.getChildren().clear());

            ingredientesData.forEach(ingrediente ->
                    root.getChildren().forEach(stringTreeItem ->
                            stringTreeItem.getChildren().add(new TreeItem<>(ingrediente.getNombre() + "- Aún no hay orden de compra")))
            );
        }
    }

    public void clearOrdenes() {
        inventoryPlanningService.clearOrdenes();

        Platform.runLater(this::updateUIWithOrdenes);
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
