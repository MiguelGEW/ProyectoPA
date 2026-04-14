package K;

import CSV.Table;

/**
 * Interfaz genérica para los algoritmos de Machine Learning.
 * @param <T> Tipo de la tabla de datos para entrenar (Table o TableWithLabels)
 * @param <U> Tipo del dato a estimar
 * @param <V> Tipo del valor devuelto en la estimación
 */

// TODO: La excepción es demasiado amplia y dificulta detectar errores
// TODO: Para mejorar la calidad deberias usar una excepción más concreta
// El method train puede lanzar excepciones concretas de los algoritmos

public interface Algorithm<T extends Table, U, V> {

    // El method train puede lanzar excepciones concretas de los algoritmos
    void train(T datos) throws Exception;

    V estimate(U dato);
}
