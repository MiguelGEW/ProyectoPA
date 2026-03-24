package K;

import CSV.*;
import java.util.*;

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

    // Añadimos throws InvalidClusterNumberException a la firma
    @Override
    public void train(Table datos) throws InvalidClusterNumberException {
        List<Row> rows = datos.getRows();

        // Comprobamos la condición y lanzamos excepción
        if (numClusters > rows.size()) {
            throw new InvalidClusterNumberException(numClusters, rows.size());
        }

        Random random = new Random(seed);
        List<Row> shuffledRows = new ArrayList<>(rows);
        Collections.shuffle(shuffledRows, random);

        this.centroids = new ArrayList<>();
        for (int i = 0; i < numClusters; i++) {
            this.centroids.add(new ArrayList<>(shuffledRows.get(i).getData()));
        }

        for (int iter = 0; iter < numIterations; iter++) {
            List<List<Row>> clusters = new ArrayList<>();
            for (int k = 0; k < numClusters; k++) {
                clusters.add(new ArrayList<>());
            }

            for (Row row : rows) {
                int clusterIndex = estimate(row.getData());
                clusters.get(clusterIndex).add(row);
            }

            for (int k = 0; k < numClusters; k++) {
                if (!clusters.get(k).isEmpty()) {
                    this.centroids.set(k, calcularCentroide(clusters.get(k)));
                }
            }
        }
    }

    private List<Double> calcularCentroide(List<Row> cluster) {
        int dimensions = cluster.get(0).getData().size();
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

    private double calcularDistancia(List<Double> p1, List<Double> p2) {
        double sum = 0.0;
        for (int i = 0; i < p1.size(); i++) {
            double diff = p1.get(i) - p2.get(i);
            sum += diff * diff;
        }
        return Math.sqrt(sum);
    }
    @Override
    public Integer estimate(List<Double> dato) {
        int bestCluster = -1;
        double minDistance = Double.MAX_VALUE;

        for (int k = 0; k < numClusters; k++) {
            double dist = calcularDistancia(dato, centroids.get(k));
            if (dist < minDistance) {
                minDistance = dist;
                bestCluster = k;
            }
        }
        return bestCluster;
    }
}