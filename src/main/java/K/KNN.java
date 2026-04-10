package K;

import CSV.Row;
import CSV.RowWithLabel;
import CSV.TableWithLabels;

import java.util.*;

// 1. Implementamos la interfaz genérica
public class KNN implements Algorithm<TableWithLabels, List<Double>, Integer> {

    private TableWithLabels dataAnalysed;

    @Override
    public void train(TableWithLabels data) {
        this.dataAnalysed = data;
    }

    @Override
    public Integer estimate(List<Double> data) {
        if (dataAnalysed == null) throw new IllegalStateException("Modelo no entrenado");

        Integer chosenClass = -1;

        Optional<Row> row = dataAnalysed.getRows().stream()
                .min(Comparator.comparing(v -> euclideanDistance(v.getData(),data)));


        if (row.isPresent()) {
            //Aquí hacemos un cast porque sabemos que en TableWithLabels solo trabajamos con RowWithLabel
            RowWithLabel chosenRow = (RowWithLabel) row.get();
            chosenClass = dataAnalysed.getLabelAsInteger(chosenRow.getLabel());
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