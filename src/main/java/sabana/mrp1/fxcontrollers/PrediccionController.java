package sabana.mrp1.fxcontrollers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import sabana.mrp1.entities.Ingrediente;
import sabana.mrp1.events.SceneChangeEvent;
import sabana.mrp1.repositories.IngredienteRepository;

import java.awt.*;

@Component
public class PrediccionController {
    private final ApplicationContext context;
    private final IngredienteRepository ingredienteRepository;

    @FXML TreeView<String> prediccionMeses;

    private final ObservableList<Ingrediente> ingredientesData = FXCollections.observableArrayList();

    public PrediccionController(ApplicationContext context, IngredienteRepository ingredienteRepository) {
        this.context = context;
        this.ingredienteRepository = ingredienteRepository;
    }

    public void initialize(){

        ingredientesData.setAll(ingredienteRepository.findAll());

        try{
            setUpPrediccionMesesTree();
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }

    private void setUpPrediccionMesesTree(){
        TreeItem<String> root, enero, febrero, marzo, abril, mayo, junio, julio, agosto, sept, octubre, nov, dic;

        root = new TreeItem<>("Predicción Meses");

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

        root.getChildren().addAll(enero, febrero, marzo, abril, mayo, junio, julio, agosto, sept, octubre, nov, dic);

        System.out.println(ingredientesData);

        for (Ingrediente ingrediente : ingredientesData){
            enero.getChildren().addAll(new TreeItem<>(ingrediente.getNombre()));
            febrero.getChildren().addAll(new TreeItem<>(ingrediente.getNombre()));
            marzo.getChildren().addAll(new TreeItem<>(ingrediente.getNombre()));
            abril.getChildren().addAll(new TreeItem<>(ingrediente.getNombre()));
            mayo.getChildren().addAll(new TreeItem<>(ingrediente.getNombre()));
            junio.getChildren().addAll(new TreeItem<>(ingrediente.getNombre()));
            julio.getChildren().addAll(new TreeItem<>(ingrediente.getNombre()));
            agosto.getChildren().addAll(new TreeItem<>(ingrediente.getNombre()));
            sept.getChildren().addAll(new TreeItem<>(ingrediente.getNombre()));
            octubre.getChildren().addAll(new TreeItem<>(ingrediente.getNombre()));
            nov.getChildren().addAll(new TreeItem<>(ingrediente.getNombre()));
            dic.getChildren().addAll(new TreeItem<>(ingrediente.getNombre()));
        }

        prediccionMeses.setRoot(root);
        prediccionMeses.setShowRoot(false);
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
