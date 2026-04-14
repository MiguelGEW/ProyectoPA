package Metrics;

import java.util.List;
import java.util.stream.IntStream;

public class EuclideanDistance implements Distance{

    @Override
    public double calculateDistance(List<Double> p, List<Double> q){
        if (p.size() != q.size()) throw new IllegalArgumentException("Los dos vectores no tienen el mismo tamaño.");

        double sum = IntStream.range(0, p.size())
                .mapToDouble(i -> Math.pow(p.get(i) - q.get(i),2 ) )
                .sum();

        return Math.sqrt(sum);
    }

}
