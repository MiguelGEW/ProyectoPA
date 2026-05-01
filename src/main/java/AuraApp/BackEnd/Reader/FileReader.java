package AuraApp.BackEnd.Reader;

import AuraApp.BackEnd.Matrix.Table;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.net.URISyntaxException;
import java.util.Scanner;

public abstract class FileReader<T extends Table> extends ReaderTemplate<T> {

    private Scanner scanner;

    public FileReader(String source) {
        super(source);
    }

    @Override
    void openSource(String source) {
        try {
            URL resource = getClass().getClassLoader().getResource(source);
            if (resource == null) {
                throw new IllegalArgumentException("File not found: " + source);
            }

            this.scanner = new Scanner(new File(resource.toURI()));

        } catch (URISyntaxException | FileNotFoundException e) {
            throw new RuntimeException("Error opening file: " + source, e);
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
        return this.scanner.nextLine();
    }
}
