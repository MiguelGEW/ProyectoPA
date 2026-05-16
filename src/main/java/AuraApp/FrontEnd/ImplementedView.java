package AuraApp.FrontEnd;

import AuraApp.BackEnd.Algorithms.Algorithm;
import AuraApp.BackEnd.Algorithms.KMeans;
import AuraApp.BackEnd.Algorithms.KNN;
import AuraApp.BackEnd.Algorithms.LikedItemNotFoundException;
import AuraApp.BackEnd.AskModel;
import AuraApp.BackEnd.Metrics.Distance;
import AuraApp.BackEnd.Metrics.EuclideanDistance;
import AuraApp.BackEnd.Metrics.ManhattanDistance;
import AuraApp.MiddleEnd.Controller;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
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
import java.util.function.Consumer;

public class ImplementedView implements InformView, AskView {
    private Controller controller;
    private AskModel model;
    private List<String> cancionesDesdeCSV;

    @Override
    public String getChosenSong() {
        return chosenSong;
    }

    @Override
    public int getRecommendSize() {
        return recommendSize;
    }
    @Override
    public Algorithm getSelectedAlgorithm() {
        return algorithm;
    }

    @Override
    public void modelUpdated() throws LikedItemNotFoundException {

        recommendations = model.viewModel();
        // Aquí actualizarías la interfaz con las nuevas recomendaciones
    }

    private  String chosenSong;
    private int recommendSize;
    private Algorithm algorithm;
    private List<String> recommendations;


    private Stage primaryStage;



    // Definición de colores
    private final String BACKGROUND_COLOR = "#000000";
    private final String ACCENT_COLOR = "#01ff95";    // El verde que te gusta
    private final String TEXT_COLOR = "#01ff95";       // Ahora el texto también es verde

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setModel(AskModel model) {
        this.model = model;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public ImplementedView(Stage primaryStage,List<String> canciones) {
        this.primaryStage = primaryStage;
        cancionesDesdeCSV = canciones;
    }



    public void createGUI() throws IOException, URISyntaxException {


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
        RadioButton rbGenre = new RadioButton("Género (KMeans)");
        rbGenre.setToggleGroup(recTypeGroup);
        rbGenre.setSelected(true);
        rbGenre.setStyle("-fx-text-fill: " + TEXT_COLOR + "; -jfx-selected-color: " + ACCENT_COLOR + ";");

        RadioButton rbSimilarities = new RadioButton("Similitudes (KNN)");
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



        Consumer<Boolean> blockParameters = (block) -> {
            songList.setDisable(block);
            rbGenre.setDisable(block);
            rbSimilarities.setDisable(block);
            metricCombo.setDisable(block);
            numRecsSpinner.setDisable(block);
            btnPlus5.setDisable(block);
            btnMinus5.setDisable(block);
        };

        // Definimos una función que recoja los datos actuales y llame al modelo para realizar el cálculo
        // En JavaFX, las acciones se suelen encapsular para reutilizarlas
        Runnable lanzarCalculo = () -> {
            chosenSong = songList.getSelectionModel().getSelectedItem();
            if (chosenSong != null) {
                RadioButton selectedRB = (RadioButton) recTypeGroup.getSelectedToggle();
                String algoritmo = selectedRB.getText();
                String metrica = metricCombo.getValue();
                recommendSize = numRecsSpinner.getValue();

                Distance metric = ( metrica.equals("Manhattan") ? new ManhattanDistance() : new EuclideanDistance());

                algorithm = ( algoritmo.equals("Género (KMeans)") ?
                        new KMeans(15,15,4331,metric)
                :
                     new KNN(metric)
                );

                blockParameters.accept(true); // Bloqueamos los parámetros mientras se calcula para evitar cambios durante el proceso



                // --- EJECUTAMOS EL CÁLCULO DEL MODELO EN UN HILO SEPARADO ---
                // Es necesario la separación de hilos porque el cálculo de KNN/KMeans puede ser pesado
                // y si lo ejecutamos en el hilo de la interfaz, esta se congelará hasta que termine,
                // lo que es una mala experiencia de usuario.

                // Task es una clase de JavaFX diseñada para ejecutar tareas en segundo plano y luego actualizar la interfaz de forma segura cuando terminen.
                Task<Void> task = getTask(recList, blockParameters);

                // Arrancamos el hilo secundario
                new Thread(task).start();

                System.out.println("\n[EJECUTANDO RECOMENDACIÓN]");
                System.out.println("Canción base: " + chosenSong);
                System.out.println("Estrategia: " + algoritmo);
                System.out.println("Distancia: " + metrica);
                System.out.println("Nº resultados: " + recommendSize);

            }
        };

        // Configuramos los listeners para que llamen a lanzarCalculo cuando haya cambios relevantes
        setUpListeners(btnGetRecommendations, lanzarCalculo, recTypeGroup, metricCombo, numRecsSpinner, btnPlus5, btnMinus5, btnClear, recList);

        Scene scene = new Scene(root, 1000, 700);
        primaryStage.setTitle("Aura");
        primaryStage.setScene(scene);
        primaryStage.show();


    }


    private Task<Void> getTask(ListView<String> recList, Consumer<Boolean> blockParameters) {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                // El cálculo pesado de KNN/KMeans se ejecuta fuera del hilo de la interfaz
                controller.updateModel();
                return null;
            }
        };

        // Cuando la tarea termine con éxito, actualizamos la lista de la interfaz
        task.setOnSucceeded(event -> {
            // Recordatorio: model.viewModel() ya se ha ejecutado dentro del controller.updateModel() y ha actualizado la variable 'recommendations' de la vista.
            recList.setItems(FXCollections.observableArrayList(recommendations));
            blockParameters.accept(false);
        });

        // Control de errores por si lanza la excepción de item no encontrado
        task.setOnFailed(event -> {
            Throwable e = task.getException();
            if (e instanceof LikedItemNotFoundException) {
                System.err.println("Error: Canción no encontrada de forma síncrona: " + e.getMessage());
            } else {
                e.printStackTrace();
            }
            blockParameters.accept(false);
        });
        return task;
    }

    private static void setUpListeners(Button btnGetRecommendations, Runnable lanzarCalculo, ToggleGroup recTypeGroup, ComboBox<String> metricCombo, Spinner<Integer> numRecsSpinner, Button btnPlus5, Button btnMinus5, Button btnClear, ListView<String> recList) {
        // --- LISTENERS QUE DISPARARÁN EL CÁLCULO AUTOMÁTICAMENTE ---
        // 1. El botón principal dispara el cálculo
        btnGetRecommendations.setOnAction(e -> lanzarCalculo.run());

        // 2. Listener para el cambio de Algoritmo (RadioButtons)
        recTypeGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                System.out.println("Cambio de algoritmo detectado...");
                lanzarCalculo.run();
            }
        });

        // 3. Listener para el cambio de Métrica (ComboBox)
        metricCombo.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Cambio de métrica detectado...");
            lanzarCalculo.run();
        });

        // 4. Listener para el cambio de Cantidad (Spinner)
        numRecsSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Cambio de cantidad detectado...");
            lanzarCalculo.run();
        });

        // --- LÓGICA DE LOS BOTONES +5 y -5 ---
        btnPlus5.setOnAction(e -> {
            int current = numRecsSpinner.getValue();
            numRecsSpinner.getValueFactory().setValue(Math.min(current + 5, 50));
            // El listener del spinner ya se encargará de llamar a lanzarCalculo
        });

        btnMinus5.setOnAction(e -> {
            int current = numRecsSpinner.getValue();
            numRecsSpinner.getValueFactory().setValue(Math.max(current - 5, 1));
        });

        // Lógica para el botón Borrar
        btnClear.setOnAction(e -> {
            recList.getItems().clear();
            System.out.println("Lista de recomendaciones limpiada.");
        });
    }



}
