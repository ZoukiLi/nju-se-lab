package writer;

import autochecker.Comparison;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class ComparisonWriter {
    public abstract void write(List<Comparison> comparisons);
    public abstract List<String> getPath();

    private ComparisonWriterType type;
    public ComparisonWriterType getType() {
        return type;
    }
    @NotNull
    public static ComparisonWriter fromOutDir(String outDir, ComparisonWriterType type) {
        return null;
    }
}
