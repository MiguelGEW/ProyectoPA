package P.KNNandCSV;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static java.lang.Math.sqrt;
import static java.lang.Math.pow;

public class KNN {

    private TableWithLabels dataAnalysed;


    public void train(TableWithLabels data) {
        dataAnalysed = data;
    }

    public Integer estimate(List<Double> data) {

        Integer chosenClass = -1;
        Double similarity = Double.MAX_VALUE; //We consider better a smaller value

        for (int i = 0; i < dataAnalysed.getRows().size(); i++) {

            Double similarityRow = EuclideanDistance(dataAnalysed.getRowAt(i).getData(), data);
            if (similarityRow < similarity) {
                chosenClass = i;
                similarity = similarityRow;
            }
        }
        return chosenClass;
    }


    private Double EuclideanDistance(Collection<Double> obj1, Collection<Double> obj2) {

        if (obj1.size() != obj2.size()) throw new IndexOutOfBoundsException();

        Iterator<Double> itObj1 = obj1.iterator();
        Iterator<Double> itObj2 = obj2.iterator();
        Double squaredDifferences = 0.0;

        while (itObj1.hasNext() && itObj2.hasNext())
            squaredDifferences += Math.pow(itObj1.next() - itObj2.next(), 2);


        return sqrt(squaredDifferences);

    }
}