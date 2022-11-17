package writer;

import autochecker.Comparison;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CSVPairWriter extends ComparisonWriter {
    private final Path _out_dir;
    private final List<String> _paths;

    public CSVPairWriter(String outDir) {
        _out_dir = Path.of(outDir);
        _paths = new ArrayList<>() { {
            add(_out_dir.resolve("equal.csv").toString());
            add(_out_dir.resolve("inequal.csv").toString());
        } };
    }
    @Override
    public void write(@NotNull List<Comparison> comparisons) {
        var equal = comparisons.stream().filter(Comparison::isSame)
                .map(cmp -> cmp + "\n").reduce("", String::concat);
        var inequal = comparisons.stream().filter(cmp -> !cmp.isSame())
                .map(cmp -> cmp + "\n").reduce("", String::concat);
        try {
            Files.writeString(Path.of(_paths.get(0)), equal);
            Files.writeString(Path.of(_paths.get(1)), inequal);
        } catch (IOException e) {
            throw new RuntimeException("Error when writing to csv file");
        }
    }
    @Override
    public List<String> getPath() {
        return _paths;
    }
}
