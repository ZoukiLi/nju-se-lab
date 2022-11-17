package src_reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class LocalFileReader extends SrcReader {
    private final String _srcPath;
    @Override
    public String read() throws IOException {
        return Files.readString(Path.of(_srcPath));
    }

    @Override
    public String getPath() {
        return _srcPath;
    }

    public LocalFileReader(String path) {
        _srcPath = path;
    }
}
