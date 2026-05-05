package AuraApp.FrontEnd;

import AuraApp.BackEnd.Matrix.Table;
import AuraApp.BackEnd.Reader.CSVLabeledFileReader;
import AuraApp.MiddleEnd.CSVNamesReader;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image; // Importante para el logo
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static java.io.File.separator;

public class Aura extends Application {

    // Definición de colores
    private final String BACKGROUND_COLOR = "#000000";
    private final String ACCENT_COLOR = "#01ff95";    // El verde que te gusta
    private final String TEXT_COLOR = "#01ff95";       // Ahora el texto también es verde

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException, URISyntaxException {
        // --- 1. AÑADIR EL LOGO EN LA BARRA DE TÍTULO ---
        // El archivo debe estar en src/main/resources/Aura.png
        try {
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/Aura.png")));
        } catch (Exception e) {
            System.out.println("No se pudo cargar el logo. Asegúrate de que esté en resources.");
        }

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: " + BACKGROUND_COLOR + ";");
        root.setPadding(new Insets(20));

        /// --- BARRA IZQUIERDA ---
        VBox leftPane = new VBox(15);
        leftPane.setPadding(new Insets(0, 20, 0, 0));
        leftPane.setAlignment(Pos.TOP_CENTER);
        leftPane.setPrefWidth(300);

        Label labelChooseSong = new Label("ELIGE LA CANCIÓN QUE TE GUSTE");
        labelChooseSong.setStyle("-fx-text-fill: " + TEXT_COLOR + "; -fx-font-weight: bold;");

        ListView<String> songList = new ListView<>();

        //---PANEL IZQUIERDA---
        // 1. CARGAR DATOS DEL CSV
        // Aquí llamarías a tu clase FileReader del backend. Por ahora, usamos un método simulado.


        String separator = System.getProperty("file.separator");
        String songsFolder = "recsys/songs_files";
        List<String> cancionesDesdeCSV = CSVNamesReader.readNames(songsFolder +separator + "songs_test_names.csv");
        songList.setItems(FXCollections.observableArrayList(cancionesDesdeCSV));

        songList.setStyle("-fx-background-color: #1a1a1a; " +
                "-fx-border-color: " + ACCENT_COLOR + "; " +
                "-fx-control-inner-background: #000000; " +
                "-fx-text-fill: " + TEXT_COLOR + ";");
        VBox.setVgrow(songList, Priority.ALWAYS);

        Button btnGetRecommendations = new Button("OBTENER RECOMENDACIONES");
        btnGetRecommendations.setMaxWidth(Double.MAX_VALUE);
        btnGetRecommendations.setStyle("-fx-background-color: " + ACCENT_COLOR + "; " +
                "-fx-text-fill: " + BACKGROUND_COLOR + "; " +
                "-fx-font-weight: bold; -fx-background-radius: 5;");
        btnGetRecommendations.setPadding(new Insets(10));

        // 2. DESHABILITAR EL BOTÓN SI NO HAY NADA SELECCIONADO
        // Esto "enlaza" la propiedad 'disable' del botón a la propiedad 'selectedItem' de la lista.
        // Si el elemento seleccionado es nulo (isNull), el botón se deshabilita automáticamente.
        btnGetRecommendations.disableProperty().bind(
                songList.getSelectionModel().selectedItemProperty().isNull()
        );

        // 3. GUARDAR EL NOMBRE AL PULSAR EL BOTÓN
        btnGetRecommendations.setOnAction(event -> {
            // Obtenemos el elemento que está seleccionado en ese momento
            String cancionSeleccionada = songList.getSelectionModel().getSelectedItem();

            // Aquí puedes "guardarlo" o hacer lo que necesites. Por ahora lo imprimimos por consola.
            System.out.println("Has seleccionado y guardado: " + cancionSeleccionada);

            // En el futuro, aquí llamaremos al controlador:
            // controlador.pedirRecomendaciones(cancionSeleccionada, ...);
        });

        leftPane.getChildren().addAll(labelChooseSong, songList, btnGetRecommendations);
        root.setLeft(leftPane);


        /// --- PANEL CENTRAL ---
        VBox centerPane = new VBox(20);
        centerPane.setPadding(new Insets(0, 0, 0, 20));

        Label labelTitle = new Label("SISTEMA DE RECOMENDACIÓN DE CANCIONES");
        labelTitle.setStyle("-fx-text-fill: " + TEXT_COLOR + "; -fx-font-size: 18; -fx-font-weight: bold;");
        labelTitle.setAlignment(Pos.CENTER);
        labelTitle.setMaxWidth(Double.MAX_VALUE);

        // Controles de tipo de recomendación y métrica
        HBox controlsBox = new HBox(40);
        controlsBox.setAlignment(Pos.CENTER_LEFT);

        VBox recTypeBox = new VBox(10);
        Label labelRecType = new Label("RECOMENDAR EN BASE A");
        labelRecType.setStyle("-fx-text-fill: " + TEXT_COLOR + "; -fx-font-weight: bold;");

        ToggleGroup recTypeGroup = new ToggleGroup();
        RadioButton rbGenre = new RadioButton("Género");
        rbGenre.setToggleGroup(recTypeGroup);
        rbGenre.setSelected(true);
        rbGenre.setStyle("-fx-text-fill: " + TEXT_COLOR + "; -jfx-selected-color: " + ACCENT_COLOR + ";");

        RadioButton rbSimilarities = new RadioButton("Similitudes");
        rbSimilarities.setToggleGroup(recTypeGroup);
        rbSimilarities.setStyle("-fx-text-fill: " + TEXT_COLOR + "; -jfx-selected-color: " + ACCENT_COLOR + ";");
        recTypeBox.getChildren().addAll(labelRecType, rbGenre, rbSimilarities);

        VBox metricBox = new VBox(10);
        Label labelExtra = new Label("DISTANCIA");
        labelExtra.setStyle("-fx-text-fill: " + TEXT_COLOR + "; -fx-font-weight: bold;");
        ComboBox<String> metricCombo = new ComboBox<>();
        metricCombo.setItems(FXCollections.observableArrayList("Manhattan", "Euclídea"));
        metricCombo.setValue("Manhattan");
        metricCombo.setStyle("-fx-background-color: #000000; -fx-border-color: " + ACCENT_COLOR + "; -fx-text-fill: " + TEXT_COLOR + ";");
        metricBox.getChildren().addAll(labelExtra, metricCombo);

        controlsBox.getChildren().addAll(recTypeBox, metricBox);

        // --- SECCIÓN AÑADIDA: NÚMERO DE RECOMENDACIONES Y BORRADO ---
        HBox numberAndClearBox = new HBox(15);
        numberAndClearBox.setAlignment(Pos.CENTER_LEFT);

        // Spinner para la cantidad (mínimo 1, máximo 50, por defecto 10)
        Spinner<Integer> numRecsSpinner = new Spinner<>(1, 50, 10);
        numRecsSpinner.setEditable(true);
        numRecsSpinner.setPrefWidth(100);
        // Estilo del Spinner
        numRecsSpinner.setStyle("-fx-background-color: #000000; -fx-border-color: " + ACCENT_COLOR + ";");

        // Botones de +5 y -5
        Button btnPlus5 = new Button("+5");
        btnPlus5.setStyle("-fx-background-color: transparent; -fx-border-color: " + ACCENT_COLOR + "; -fx-text-fill: " + TEXT_COLOR + "; -fx-border-radius: 3;");

        Button btnMinus5 = new Button("-5");
        btnMinus5.setStyle("-fx-background-color: transparent; -fx-border-color: " + ACCENT_COLOR + "; -fx-text-fill: " + TEXT_COLOR + "; -fx-border-radius: 3;");

        // Botón para borrar las recomendaciones
        Button btnClear = new Button("Borrar las recomendaciones");
        btnClear.setStyle("-fx-background-color: transparent; -fx-border-color: " + ACCENT_COLOR + "; -fx-text-fill: " + TEXT_COLOR + "; -fx-border-radius: 5;");
        btnClear.setPadding(new Insets(5, 10, 5, 10));

        numberAndClearBox.getChildren().addAll(numRecsSpinner, btnPlus5, btnMinus5, btnClear);
        // -------------------------------------------------------------

        // Recomendaciones (Lista de salida)
        Label labelRecommendations = new Label("TE RECOMENDAMOS LOS SIGUIENTES TÍTULOS");
        labelRecommendations.setStyle("-fx-text-fill: " + TEXT_COLOR + "; -fx-font-weight: bold;");

        ListView<String> recList = new ListView<>();
        // Datos de ejemplo para que veas cómo queda
        recList.setItems(FXCollections.observableArrayList(
                "HEADBANGERS:Dubstep/Riddim", "Lost Lands 2020", "Heavy Dubstep"
        ));
        recList.setStyle("-fx-background-color: #1a1a1a; -fx-border-color: " + ACCENT_COLOR + "; -fx-control-inner-background: #000000; -fx-text-fill: " + TEXT_COLOR + ";");
        VBox.setVgrow(recList, Priority.ALWAYS);

        // Agregamos también el numberAndClearBox al panel central
        centerPane.getChildren().addAll(labelTitle, controlsBox, numberAndClearBox, labelRecommendations, recList);
        root.setCenter(centerPane);

        Scene scene = new Scene(root, 1000, 700);
        primaryStage.setTitle("Aura");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

