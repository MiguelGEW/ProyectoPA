package Reader;

import Matrix.Table;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public abstract class FileReader<T extends Table> extends ReaderTemplate<T> {

    private Scanner scanner;

    public FileReader(String source) {
        super(source);
    }

    @Override
    void openSource(String source) {
        try {
            this.scanner = new Scanner(new File(source));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("No se ha podido abrir el archivo: " + source, e);
        }
    }

    @Override
    void closeSource() {
        if (this.scanner != null) {
            this.scanner.close();
        }
    }

    @Override
    boolean hasMoreData() {
        return this.scanner != null && this.scanner.hasNextLine();
    }

    @Override
    String getNextData() {
        if (this.scanner != null && this.scanner.hasNextLine()) {
            return this.scanner.nextLine();
        }
        return null;
    }
}
