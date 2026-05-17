package AuraApp.FrontEnd;
import AuraApp.BackEnd.BackModel.ImplementedModel;
import AuraApp.BackEnd.Matrix.TableWithLabels;

import AuraApp.BackEnd.Reader.CSVLabeledFileReader;
import AuraApp.MiddleEnd.CSVNamesReader;
import AuraApp.MiddleEnd.ImplementedController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class Aura extends Application {

    // Definición de colores
    private final String BACKGROUND_COLOR = "#000000";
    private final String ACCENT_COLOR = "#01ff95";    // El verde
    private final String TEXT_COLOR = "#01ff95";       // El texto también es verde

    public static void main(String[] args) {
        launch(args);
    }


    public void start(Stage primaryStage) throws IOException, URISyntaxException {

        String separator = System.getProperty("file.separator");
        String songsFolder = "recsys/songs_files";
        List<String> cancionesDesdeCSV = CSVNamesReader.readNames(songsFolder +separator + "songs_test_names.csv");
        TableWithLabels trainTable = new CSVLabeledFileReader(songsFolder + separator + "songs_train.csv").readTableFromSource();
        TableWithLabels testTable = new CSVLabeledFileReader(songsFolder + separator + "songs_test.csv").readTableFromSource();

        ImplementedModel model = new ImplementedModel(trainTable,testTable,cancionesDesdeCSV);


        ImplementedView view = new ImplementedView(primaryStage,cancionesDesdeCSV);
        view.setPrimaryStage(primaryStage);

        ImplementedController controller = new ImplementedController();

        view.setController(controller);
        view.setModel(model);
        controller.setView(view);
        controller.setModel(model);
        model.setView(view);



        view.createGUI();

    }

}

