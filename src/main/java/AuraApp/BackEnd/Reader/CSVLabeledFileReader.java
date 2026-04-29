package AuraApp.BackEnd.Reader;

import AuraApp.BackEnd.Matrix.RowWithLabel;
import AuraApp.BackEnd.Matrix.TableWithLabels;
import java.util.ArrayList;
import java.util.List;

public class CSVLabeledFileReader extends FileReader<TableWithLabels> {

    public CSVLabeledFileReader(String source) {
        super(source);
    }
    // TODO: Hay lógica repetida de troceado y limpieza de tokens
    // TODO: Se podrían extraer utilidades comunes para mejorar la reutilización y reducir el duplicado
    @Override
    void processHeaders(String headers) {
        String[] tokens = headers.split(",");
        List<String> headerList = new ArrayList<>();

        for (int i = 0; i < tokens.length - 1; i++) {
            headerList.add(tokens[i].strip());
        }
        this.table = new TableWithLabels(headerList);
    }

    @Override
    void processData(String data) {
        String[] tokens = data.split(",");
        List<Double> values = new ArrayList<>();
        for (int i = 0; i < tokens.length - 1; i++) {
            values.add(Double.parseDouble(tokens[i].strip()));
        }
        String label = tokens[tokens.length - 1].strip();
        this.table.addRow(new RowWithLabel(values, label));
    }
}
