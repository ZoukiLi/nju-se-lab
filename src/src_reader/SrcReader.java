package src_reader;


import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class SrcReader {
    public abstract String read() throws IOException;
    public abstract String getPath();

    protected SrcReaderType _type;
    public SrcReaderType getType() {
        return _type;
    }
    public String getTempCopyPath(Path path, String prefix) throws IOException {
        Path tempDir = Files.createTempDirectory(path, prefix +"-src-");
        Path filePath = Path.of(getPath());
        Path tempFilePath = tempDir.resolve(filePath.getFileName());
        //copy the file to the temp dir
        Files.copy(filePath, tempFilePath);
        return tempFilePath.toString();
    }

    @NotNull
    public static SrcReader fromPath(String path, @NotNull SrcReaderType type) {
        return switch (type) {
            case LOCAL_FILE -> new LocalFileReader(path){ {_type = type;} };
            default -> throw new IllegalArgumentException("Unknown SrcReaderType");
        };
    }
}
