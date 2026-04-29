package AuraApp.BackEnd.Algorithms;

import AuraApp.BackEnd.Matrix.Table;

/**
 * Interfaz genérica para los algoritmos de Machine Learning.
 * @param <T> Tipo de la tabla de datos para entrenar (Table o TableWithLabels)
 * @param <U> Tipo del dato a estimar
 * @param <V> Tipo del valor devuelto en la estimación
 */

// El method train puede lanzar excepciones concretas de los algoritmos

public interface Algorithm<T extends Table, U, V> {

    // TODO: La interfaz obliga a que cualquier algoritmo pueda lanzar InvalidClusterNumberException, aunque eso solo tiene sentido para KMeans
    // TODO: Esto penaliza en el diseño, lo recomendable es no declarar la excepción en la interfaz común o rediseñar la jerarquía para no imponer excepciones específicas a todos los algoritmos

    // El method train puede lanzar excepciones concretas de los algoritmos
    void train(T datos) throws InvalidClusterNumberException;

    V estimate(U dato);
}
