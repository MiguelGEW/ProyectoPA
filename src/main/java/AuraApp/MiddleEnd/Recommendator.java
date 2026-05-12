package AuraApp.MiddleEnd;


import AuraApp.BackEnd.Algorithms.Algorithm;
import AuraApp.BackEnd.Algorithms.LikedItemNotFoundException;
import AuraApp.BackEnd.Matrix.Table;
import AuraApp.BackEnd.Matrix.TableWithLabels;
import AuraApp.BackEnd.Metrics.Distance;
import AuraApp.BackEnd.Recommendations.RecSys;

import java.util.List;

public class Recommendator {


    public static List<String> getRecommendations(String songName, Algorithm<TableWithLabels,List<Double>,Integer> algorithm, TableWithLabels trainData,TableWithLabels testData, List<String> testItemNames, int numRec) throws LikedItemNotFoundException {

        RecSys<TableWithLabels> terminator = new RecSys<>(algorithm);

        terminator.train(trainData);
        terminator.initialise(testData,testItemNames);


        return terminator.recommend(songName,numRec);
    }
}
