package AuraApp.BackEnd.Algorithms;

public class InvalidClusterNumberException extends RuntimeException {

    private final int numClusters;
    private final int totalDataSize;

    public InvalidClusterNumberException(int numClusters, int totalDataSize) {
        super("Número inválido de clústeres: se solicitaron " + numClusters + ", pero solo hay " + totalDataSize + " datos disponibles.");
        this.numClusters = numClusters;
        this.totalDataSize = totalDataSize;
    }

    public int getNumberOfClusters() {
        return numClusters;
    }

    public int getTotalDataSize() {
        return totalDataSize;
    }
}