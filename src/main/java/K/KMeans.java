package K;

import CSV.*;
import java.util.*;
import java.util.stream.IntStream;

public class KMeans implements Algorithm<Table, List<Double>, Integer>{

    private int numClusters, numIterations;
    private long seed;
    private List<List<Double>> centroids;

    public KMeans(int numClusters, int numIterations, long seed) {
        this.numClusters = numClusters;
        this.numIterations = numIterations;
        this.seed = seed;
        this.centroids = new ArrayList<>();
    }

    @Override
    public void train(Table datos) throws InvalidClusterNumberException {
        List<Row> rows = datos.getRows();

        // Comprobar la condición y lanzar excepción
        if (numClusters > rows.size()) {
            throw new InvalidClusterNumberException(numClusters, rows.size());
        }

        initializeRepresentatives(rows);

        for (int iter = 0; iter < numIterations; iter++) {
            List<List<Row>> clusters = new ArrayList<>();
            // Se vacían los clusters para volver a evaluar según los nuevos representantes
            for (int k = 0; k < numClusters; k++) {
                clusters.add(new ArrayList<>());
            }
            // Se clasifican los datos en los distintos clusters
            for (Row row : rows) {
                int clusterIndex = estimate(row.getData());
                clusters.get(clusterIndex).add(row);
            }

            // Se vuelve a reevalúar los representantes por cluster por el cálculo del centroide
            recalculateRepresentantives(clusters);
        }
    }

    private void initializeRepresentatives(List<Row> rows) {
        Random random = new Random(seed);
        List<Row> shuffledRows = new ArrayList<>(rows);
        Collections.shuffle(shuffledRows, random);

        // Se asignan los nuevos representantes
        this.centroids = new ArrayList<>();
        for (int i = 0; i < numClusters; i++) {
            this.centroids.add(new ArrayList<>(shuffledRows.get(i).getData()));
        }
    }

    private void recalculateRepresentantives(List<List<Row>> clusters) {
        for (int k = 0; k < numClusters; k++) {
            if (!clusters.get(k).isEmpty()) {
                this.centroids.set(k, computeCentroid(clusters.get(k)));
            }
        }
    }

    private List<Double> computeCentroid(List<Row> cluster) {

        if (cluster.isEmpty())
            throw new IllegalArgumentException();

        int dimensions = cluster.getFirst().getData().size();
        List<Double> centroid = new ArrayList<>(Collections.nCopies(dimensions, 0.0));

        for (Row row : cluster) {
            List<Double> data = row.getData();
            for (int i = 0; i < dimensions; i++) {
                centroid.set(i, centroid.get(i) + data.get(i));
            }
        }

        for (int i = 0; i < dimensions; i++) {
            centroid.set(i, centroid.get(i) / cluster.size());
        }

        return centroid;
    }

    private double computeDistance(List<Double> p1, List<Double> p2) {


        if (p1.size() != p2.size()) throw new IllegalArgumentException("Los dos vectores no tienen el mismo tamaño.");

        double sum = IntStream.range(0, p1.size())
                .mapToDouble(i -> Math.pow(p1.get(i) - p2.get(i),2 ) )
                .sum();

        return Math.sqrt(sum);
    }
    
    @Override
    public Integer estimate(List<Double> dato) {

        boolean notTrained = centroids.isEmpty();

        if (notTrained)
            throw new IllegalStateException("Modelo no ha sido entrenado. Usar método train() para entrenarlo.");

        return IntStream.range(0, numClusters)
                .reduce((k1, k2) -> computeDistance(dato, centroids.get(k1)) < computeDistance(dato, centroids.get(k2))
                                                                                  ? k1 : k2)
                .orElse(-1);
    }
}