package K;

import CSV.RowWithLabel;
import CSV.TableWithLabels;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class KNN {

    private TableWithLabels dataAnalysed;

    public void train(TableWithLabels data) {
        this.dataAnalysed = data;
    }

    public Integer estimate(List<Double> data) {
        if (dataAnalysed == null) throw new IllegalStateException("Modelo no entrenado");

        Integer chosenClass = -1;
        double minDistance = Double.MAX_VALUE;

        for (int i = 0; i < dataAnalysed.getRows().size(); i++) {

            RowWithLabel row = dataAnalysed.getRowAt(i);

            double currentDistance = euclideanDistance(row.getData(), data);

            if (currentDistance < minDistance) {
                minDistance = currentDistance;
                chosenClass = dataAnalysed.getLabelAsInteger(row.getLabel());
            }
        }
        return chosenClass;
    }


    private Double euclideanDistance(Collection<Double> obj1, Collection<Double> obj2) {

        //Se necesita la misma dimensión para poder medir las diferencias coordenada a coordenada.
        if (obj1.size() != obj2.size()) throw new IndexOutOfBoundsException();

        Iterator<Double> it1 = obj1.iterator();
        Iterator<Double> it2 = obj2.iterator();
        double sum = 0.0;

        while (it1.hasNext()) {
            double diff = it1.next() - it2.next();
            sum += diff * diff;
        }
        return Math.sqrt(sum);
    }
}