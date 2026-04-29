package AuraApp.BackEnd.Reader;

import AuraApp.BackEnd.Matrix.Row;
import AuraApp.BackEnd.Matrix.Table;
import java.util.ArrayList;
import java.util.List;

public class CSVUnlabeledFileReader extends FileReader<Table> {

    public CSVUnlabeledFileReader(String source) {
        super(source);
    }
    // TODO: Hay lógica repetida de troceado y limpieza de tokens
    // TODO: Se podrían extraer utilidades comunes para mejorar la reutilización y reducir el duplicado
    @Override
    void processHeaders(String headers) {
        String[] tokens = headers.split(",");
        List<String> headerList = new ArrayList<>();
        for (String t : tokens) headerList.add(t.strip());
        this.table = new Table(headerList);
    }

    @Override
    void processData(String data) {
        String[] tokens = data.split(",");
        List<Double> values = new ArrayList<>();
        for (String t : tokens) values.add(Double.parseDouble(t.strip()));
        this.table.addRow(new Row(values));
    }
}
