package AuraApp.BackEnd.Algorithms;

import AuraApp.BackEnd.Matrix.Table;

/**
 * Interfaz genérica para los algoritmos de Machine Learning.
 * @param <T> Tipo de la tabla de datos para entrenar (Table o TableWithLabels)
 * @param <U> Tipo del dato a estimar
 * @param <V> Tipo del valor devuelto en la estimación
 */

public interface Algorithm<T extends Table, U, V> {

    // El method train puede lanzar excepciones concretas de los algoritmos
    void train(T datos);

    V estimate(U dato);
}
