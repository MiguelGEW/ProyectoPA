package K;

public class InvalidClusterNumberException extends Exception {

    private final int numberOfClusters;
    private final int totalDataSize;

    public InvalidClusterNumberException(int numberOfClusters, int totalDataSize) {
        super("El número de clusters (" + numberOfClusters + ") no puede ser mayor que el número de datos (" + totalDataSize + ").");
        this.numberOfClusters = numberOfClusters;
        this.totalDataSize = totalDataSize;
    }


    public int getNumberOfClusters() {
        return numberOfClusters;
    }

    public int getTotalDataSize() {
        return totalDataSize;
    }
}