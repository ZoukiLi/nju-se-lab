package src_reader;

public abstract class SrcReader {
    public abstract String read();
    public abstract String getPath();

    private SrcReaderType type;
    public SrcReaderType getType() {
        return type;
    }

    public static SrcReader fromPath(String path, SrcReaderType type) {
        return null;
    }
}
