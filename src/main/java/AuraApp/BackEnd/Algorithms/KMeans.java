package AuraApp.BackEnd.Algorithms;

import AuraApp.BackEnd.Matrix.Row;
import AuraApp.BackEnd.Matrix.Table;
import AuraApp.BackEnd.Metrics.Distance;

import java.util.*;
import java.util.stream.IntStream;

public class KMeans implements Algorithm<Table, List<Double>, Integer>{

    private int numClusters, numIterations;
    private long seed;
    private List<List<Double>> centroids;
    private Distance distance;

    public KMeans(int numClusters, int numIterations, long seed, Distance distance) {
        this.numClusters = numClusters;
        this.numIterations = numIterations;
        this.seed = seed;
        this.centroids = new ArrayList<>();
        this.distance = distance;
    }

    @Override
    public void train(Table datos) throws InvalidClusterNumberException {
        List<Row> rows = datos.getRows();

        // Comprobar la condición y lanzar excepción
        if (numClusters > rows.size()) {
            throw new InvalidClusterNumberException(numClusters, rows.size());
        }

        //Inicialización
        initializeRepresentatives(rows);

        for (int iter = 0; iter < numIterations; iter++) {

            //Asignación
            List<List<Row>> clusters = assignDataToClusters(rows);

            //Recalculado
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


    private List<List<Row>> assignDataToClusters(List<Row> rows) {
        List<List<Row>> clusters = new ArrayList<>();

        for (int k = 0; k < numClusters; k++) {
            clusters.add(new ArrayList<>());
        }

        for (Row row : rows) {
            int clusterIndex = estimate(row.getData());
            clusters.get(clusterIndex).add(row);
        }

        return clusters;
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
        return distance.calculateDistance(p1, p2);
    }

    @Override
    public Integer estimate(List<Double> dato) {
        boolean notTrained = centroids == null || centroids.isEmpty();

        if (notTrained)
            throw new IllegalStateException("Modelo no ha sido entrenado. Usar método train() para entrenarlo.");

        return IntStream.range(0, numClusters)
                .reduce((k1, k2) -> computeDistance(dato, centroids.get(k1)) < computeDistance(dato, centroids.get(k2))
                        ? k1 : k2)
                .orElse(-1);
    }
}