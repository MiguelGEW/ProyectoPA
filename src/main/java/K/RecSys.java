package K;

import CSV.Table;
import java.util.ArrayList;
import java.util.List;

public class RecSys {

    // Usamos el tipo genérico sin especificar los diamantes de forma estricta
    // para que acepte tanto KNN (TableWithLabels) como KMeans (Table)

    private Algorithm algorithm;

    private Table testData;
    private List<String> testItemNames;

    public RecSys(Algorithm algorithm) {
        this.algorithm = algorithm;
    }


    public void train(Table trainData) throws Exception {
        // Entrenamos el algoritmo inyectado (sea KNN o KMeans)
        algorithm.train(trainData);
    }

    // Inicializa los datos sobre los que vamos a hacer recomendaciones
    public void initialise(Table testData, List<String> testItemNames) {
        this.testData = testData;
        this.testItemNames = testItemNames;
    }


    public List<String> recommend(String nameLikedItem, int numRecommendations) throws LikedItemNotFoundException {

        // 1. Buscamos el índice del elemento que le gusta al usuario
        int itemIndex = testItemNames.indexOf(nameLikedItem);

        // Si no existe, lanzamos la excepción
        if (itemIndex == -1) {
            throw new LikedItemNotFoundException(nameLikedItem);
        }

        // 2. Extraemos los datos del ítem que le gusta y calculamos a qué grupo/clase pertenece
        List<Double> likedItemData = testData.getRowAt(itemIndex).getData();
        Integer likedItemClass = (Integer) algorithm.estimate(likedItemData);

        // 3. Buscamos otros ítems que pertenezcan al mismo grupo
        List<String> recommendations = new ArrayList<>();

        for (int i = 0; i < testData.getRowCount(); i++) {
            // Saltamos el propio ítem que ya nos gusta (el test verifica que no se recomiende a sí mismo)
            if (i == itemIndex) {
                continue;
            }

            // Estimamos el grupo del ítem actual en el bucle
            List<Double> currentItemData = testData.getRowAt(i).getData();
            Integer currentItemClass = (Integer) algorithm.estimate(currentItemData);

            // Si coincide con el grupo del elemento que nos gusta, lo añadimos
            if (currentItemClass.equals(likedItemClass)) {
                recommendations.add(testItemNames.get(i));

                // Si hemos alcanzado el número de recomendaciones deseadas, paramos
                if (recommendations.size() == numRecommendations) {
                    break;
                }
            }
        }

        return recommendations;
    }
}