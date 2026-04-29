package AuraApp.BackEnd.Recommendations;

import AuraApp.BackEnd.Algorithms.Algorithm;
import AuraApp.BackEnd.Algorithms.InvalidClusterNumberException;
import AuraApp.BackEnd.Algorithms.LikedItemNotFoundException;
import AuraApp.BackEnd.Matrix.Table;
import java.util.ArrayList;
import java.util.List;

public class RecSys {
    // TODO: Cuidado porque la declaración rompe la abstracción genérica, ya que KNN trabajo con TableWithLabels
    // TODO: Funciona por el uso de los tipos raw en los tests, pero el diseño es inseguro
    // TODO: Por lo tanto, debéis parametrizar RecSys con los mismos genéricos de Algorithm o aceptar un tipo más general pero bien acotado

    // Usar el tipo genérico sin especificar para que acepte tanto KNN (TableWithLabels) como KMeans (Table)
    private Algorithm<Table,List<Double>,Integer> algorithm;

    // Nombres y sus clases/grupos estimados
    private List<String> testItemNames;
    private List<Integer> estimatedClasses;

    public RecSys(Algorithm<Table,List<Double>,Integer>  algorithm) {
        this.algorithm = algorithm;
    }

    public void train(Table trainData) throws InvalidClusterNumberException {
        // Algoritmo inyectado (KNN o KMeans)
        algorithm.train(trainData);
    }

    public void initialise(Table testData, List<String> testItemNames) {

        if (testData.getRows().size() != testItemNames.size())
            throw new IllegalArgumentException("no coincide el número de datos y sus etiquetas");
        this.testItemNames = testItemNames;
        this.estimatedClasses = new ArrayList<>();

        for (int i = 0; i < testData.getRowCount(); i++) {
            List<Double> rowData = testData.getRowAt(i).getData();
            Integer estimatedClass =  algorithm.estimate(rowData);
            this.estimatedClasses.add(estimatedClass);
        }
    }
    // TODO: Para hacer que los métodos sean más compactos, podéis distribuir la lógica en varios métodos (búsqueda del ítem, obtención de clase objetivo y filtrado de recomendaciones)

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