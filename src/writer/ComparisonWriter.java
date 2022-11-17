package writer;

import autochecker.Comparison;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class ComparisonWriter {
    public abstract void write(List<Comparison> comparisons);
    public abstract List<String> getPath();

    protected ComparisonWriterType _type;
    public ComparisonWriterType getType() {
        return _type;
    }
    @NotNull
    public static ComparisonWriter fromOutDir(String outDir, ComparisonWriterType type) {
        return switch (type) {
            case CSV_PAIR -> new CSVPairWriter(outDir) {{_type = type;}};
        };
    }
}
