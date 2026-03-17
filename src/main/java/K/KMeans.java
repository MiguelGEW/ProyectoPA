package K;
import CSV.*;

import java.util.*;

public class KMeans {

    private int numClusters, numIterations;
    long seed;

    public KMeans(int numClusters, int numIterations, long seed){
        this.numClusters = numClusters;
        this.numIterations = numIterations;
        this.seed = seed;
    }
    public void train (Table datos){

        List<Row> rows = datos.getRows();

        List<Integer> candidatos = new ArrayList<>();
        for (int i = 0; i < rows.size(); i++) {
            candidatos.add(i);
        }


        Random random = new Random(seed);
        Collections.shuffle(candidatos,random);

        List<Integer> representantes = candidatos.subList(0,numClusters);

        List<Set<Row>> clusters = new LinkedList<>();
        for (Integer i : representantes){
            HashSet<Row> nuevo = new HashSet<>();
            nuevo.add(rows.get(i));
            clusters.add(nuevo);
        }


    }

    //TODO: Hace falta una función que calcula las distancias entre objetos


    //TODO: Hace falta una función que calcule los centroides

    public Integer estimate(List<Double> dato){
        return 1;
    }
}
