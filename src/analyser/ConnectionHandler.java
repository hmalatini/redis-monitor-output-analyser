package analyser;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

class ConnectionHandler {

    private final BufferedReader reader;

    ConnectionHandler(String filePath) throws IOException {
        this.reader = Files.newBufferedReader(Paths.get(filePath));
    }

    Optional<String> read() throws IOException {
        return Optional.ofNullable(reader.readLine());
    }

    void close() throws IOException {
        reader.close();
    }
}
