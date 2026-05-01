package AuraApp.BackEnd.Recommendations;

import AuraApp.BackEnd.Algorithms.Algorithm;
import AuraApp.BackEnd.Algorithms.InvalidClusterNumberException;
import AuraApp.BackEnd.Algorithms.LikedItemNotFoundException;
import AuraApp.BackEnd.Matrix.Table;
import java.util.ArrayList;
import java.util.List;

public class RecSys<T extends Table> {


    private Algorithm<T, List<Double>, Integer> algorithm;
    private List<String> testItemNames;
    private List<Integer> estimatedClasses;

    public RecSys(Algorithm<T, List<Double>, Integer> algorithm) {
        this.algorithm = algorithm;
    }

    public void train(T trainData) {
        // Algoritmo inyectado (KNN o KMeans)
        algorithm.train(trainData);
    }

    public void initialise(T testData, List<String> testItemNames) {

        if (testData.getRows().size() != testItemNames.size())
            throw new IllegalArgumentException("no coincide el número de datos y sus etiquetas");

        this.testItemNames = testItemNames;
        this.estimatedClasses = new ArrayList<>();

        for (int i = 0; i < testData.getRowCount(); i++) {
            List<Double> rowData = testData.getRowAt(i).getData();
            Integer estimatedClass = algorithm.estimate(rowData);
            this.estimatedClasses.add(estimatedClass);
        }
    }

    public List<String> recommend(String nameLikedItem, int numRecommendations) throws LikedItemNotFoundException {

        int itemIndex = findItemIndex(nameLikedItem);
        Integer likedItemClass = getTargetClass(itemIndex);

        return filterRecommendations(itemIndex, likedItemClass, numRecommendations);
    }


    private int findItemIndex(String nameLikedItem) throws LikedItemNotFoundException {
        int itemIndex = testItemNames.indexOf(nameLikedItem);
        if (itemIndex == -1) {
            throw new LikedItemNotFoundException(nameLikedItem);
        }
        return itemIndex;
    }


    private Integer getTargetClass(int itemIndex) {
        return estimatedClasses.get(itemIndex);
    }


    private List<String> filterRecommendations(int itemIndex, Integer targetClass, int numRecommendations) {
        List<String> recommendations = new ArrayList<>();

        for (int i = 0; i < estimatedClasses.size(); i++) {
            if (i == itemIndex) {
                continue;
            }

            // Si la clase precalculada coincide con la clase del elemento que nos gusta
            if (estimatedClasses.get(i).equals(targetClass)) {
                recommendations.add(testItemNames.get(i));

                if (recommendations.size() == numRecommendations) {
                    break;
                }
            }
        }

        return recommendations;
    }
}