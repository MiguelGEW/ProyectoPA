package K;

import CSV.Table;
import java.util.ArrayList;
import java.util.List;

public class RecSys {
    // TODO: Usais algorithm en crudo y así podeis perder la seguridad de los tipos
    // TODO: Para evitarlo podriais usar tipos genéricos de Algorithm para evitar cast durante la ejecución
    // Usar el tipo genérico sin especificar para que acepte tanto KNN (TableWithLabels) como KMeans (Table)
    private Algorithm algorithm;

    // Nombres y sus clases/grupos estimados
    private List<String> testItemNames;
    private List<Integer> estimatedClasses;

    public RecSys(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    // TODO: La excepción es demasiado amplia y dificulta detectar errores
    // TODO: Para mejorar la calidad deberias usar una excepción más concreta
    public void train(Table trainData) throws Exception {
        // Algoritmo inyectado (KNN o KMeans)
        algorithm.train(trainData);
    }
    // TODO: No valida que testData y testItemNames tengan el mismo tamaño
    // TODO: En el guión se indica que la correspondencia entre posiciones debe ser la misma en ambos
    // TODO: Para evitar errores deberíais hacer una comprobación antes
    public void initialise(Table testData, List<String> testItemNames) {
        this.testItemNames = testItemNames;
        this.estimatedClasses = new ArrayList<>();

        for (int i = 0; i < testData.getRowCount(); i++) {
            List<Double> rowData = testData.getRowAt(i).getData();
            Integer estimatedClass = (Integer) algorithm.estimate(rowData);
            this.estimatedClasses.add(estimatedClass);
        }
    }

    public List<String> recommend(String nameLikedItem, int numRecommendations) throws LikedItemNotFoundException {

        // Buscar el elemento que le gusta al usuario
        int itemIndex = testItemNames.indexOf(nameLikedItem);

        if (itemIndex == -1) {
            throw new LikedItemNotFoundException(nameLikedItem);
        }

        // Obtener la clase/grupo del ítem
        Integer likedItemClass = estimatedClasses.get(itemIndex);

        // Buscar otros elementos
        List<String> recommendations = new ArrayList<>();

        for (int i = 0; i < estimatedClasses.size(); i++) {

            if (i == itemIndex) {
                continue;
            }

            // Si la clase precalculada coincide con la clase del elemento que nos gusta
            if (estimatedClasses.get(i).equals(likedItemClass)) {
                recommendations.add(testItemNames.get(i));

                if (recommendations.size() == numRecommendations) {
                    break;
                }
            }
        }

        return recommendations;
    }
}