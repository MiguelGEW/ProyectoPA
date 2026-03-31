package K;

import CSV.Table;
import java.util.ArrayList;
import java.util.List;

public class RecSys {

    // Usar el tipo genérico sin especificar para que acepte tanto KNN (TableWithLabels) como KMeans (Table)
    private Algorithm algorithm;

    private Table testData;
    private List<String> testItemNames;

    public RecSys(Algorithm algorithm) {
        this.algorithm = algorithm;
    }


    public void train(Table trainData) throws Exception {
        // Algoritmo inyectado (KNN o KMeans)
        algorithm.train(trainData);
    }

    public void initialise(Table testData, List<String> testItemNames) {
        this.testData = testData;
        this.testItemNames = testItemNames;
    }


    public List<String> recommend(String nameLikedItem, int numRecommendations) throws LikedItemNotFoundException {

        // Elemento que le gusta al usuario
        int itemIndex = testItemNames.indexOf(nameLikedItem);

        if (itemIndex == -1) {
            throw new LikedItemNotFoundException(nameLikedItem);
        }

        // Los datos del ítem
        List<Double> likedItemData = testData.getRowAt(itemIndex).getData();
        Integer likedItemClass = (Integer) algorithm.estimate(likedItemData);

        // Otros qu pertenezcan al mismo grupo
        List<String> recommendations = new ArrayList<>();

        for (int i = 0; i < testData.getRowCount(); i++) {

            if (i == itemIndex) {
                continue;
            }

            //Grupo del ítem
            List<Double> currentItemData = testData.getRowAt(i).getData();
            Integer currentItemClass = (Integer) algorithm.estimate(currentItemData);

            // Si coincide con el grupo del elemento que nos gusta
            if (currentItemClass.equals(likedItemClass)) {
                recommendations.add(testItemNames.get(i));

                if (recommendations.size() == numRecommendations) {
                    break;
                }
            }
        }

        return recommendations;
    }
}