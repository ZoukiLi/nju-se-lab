package src_reader;


import org.jetbrains.annotations.NotNull;

public abstract class SrcReader {
    public abstract String read();
    public abstract String getPath();

    private SrcReaderType type;
    public SrcReaderType getType() {
        return type;
    }

    @NotNull
    public static SrcReader fromPath(String path, SrcReaderType type) {
        return null;
    }
}
