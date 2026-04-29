package AuraApp.BackEnd.Reader;

import AuraApp.BackEnd.Matrix.Table;

public abstract class ReaderTemplate<T extends Table> {

    protected T table;
    protected String source;

    public ReaderTemplate(String source) {
        this.source = source;
    }

    public final T readTableFromSource() {
        openSource(source);

        try {

            if (hasMoreData()) {
                String headers = getNextData();
                processHeaders(headers);
            }

            while (hasMoreData()) {
                String data = getNextData();
                processData(data);
            }
        } finally {

            closeSource();
        }

        return table;
    }


    abstract void openSource(String source);
    abstract void processHeaders(String headers);
    abstract void processData(String data);
    abstract void closeSource();
    abstract boolean hasMoreData();
    abstract String getNextData();
}
