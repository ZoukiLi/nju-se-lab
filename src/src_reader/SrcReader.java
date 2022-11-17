package src_reader;


import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class SrcReader {
    public abstract String read() throws IOException;
    public abstract String getPath();

    private SrcReaderType type;
    public SrcReaderType getType() {
        return type;
    }
    public String getTempCopyPath(String prefix) throws IOException {
        Path tempDir = Files.createTempDirectory(prefix +"-src");
        Path filePath = Path.of(getPath());
        Path tempFilePath = tempDir.resolve(filePath.getFileName());
        //copy the file to the temp dir
        Files.copy(filePath, tempFilePath);
        return tempFilePath.toString();
    }

    @NotNull
    public static SrcReader fromPath(String path, @NotNull SrcReaderType type) {
        switch (type) {
            case LOCAL_FILE -> {
                SrcReader reader = new LocalFileReader(path);
                reader.type = type;
                return reader;
            }
            default -> throw new IllegalArgumentException("Unknown SrcReaderType");
        }
    }
}
