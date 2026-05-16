package AuraApp.BackEnd;

import AuraApp.BackEnd.Algorithms.Algorithm;
import AuraApp.BackEnd.Algorithms.LikedItemNotFoundException;
import AuraApp.BackEnd.Matrix.TableWithLabels;
import AuraApp.BackEnd.Reader.CSVLabeledFileReader;
import AuraApp.BackEnd.Recommendations.RecSys;
import AuraApp.FrontEnd.InformView;
import AuraApp.MiddleEnd.CSVNamesReader;
import javafx.collections.FXCollections;


import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class ImplementedModel implements AskModel, ModelChange {


    private TableWithLabels trainData;
    private TableWithLabels testData;
    private List<String> testItemNames;


    private Algorithm algorithm;
    private RecSys<TableWithLabels> recSys;
    private String songName;
    private int numRec;

    public void setView(InformView view) {
        this.view = view;
    }

    private InformView view;

    boolean modelTrained = false;



    public ImplementedModel(TableWithLabels trainData, TableWithLabels testData, List<String> testItemNames) {
        this.trainData = trainData;
        this.testData = testData;
        this.testItemNames = testItemNames;
    }

    @Override
    public void updateModel(String songName, Algorithm<TableWithLabels,List<Double>,Integer> algorithm, int numRec) throws LikedItemNotFoundException {
        this.songName = songName;
        this.algorithm = algorithm;
        this.numRec = numRec;

        this.recSys = new RecSys<>(algorithm);


        modelTrained = true;
        view.modelUpdated();
        System.out.println("Model Updated");
    }

    public List<String> viewModel() throws LikedItemNotFoundException {


        if (!modelTrained) {
            throw new IllegalStateException("Model not trained yet. Please update the model before viewing recommendations.");
        }

        recSys.train(trainData);
        recSys.initialise(testData,testItemNames);



        return recSys.recommend(songName,numRec);
    }




}
