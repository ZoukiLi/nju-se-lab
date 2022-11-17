package writer;

import autochecker.Comparison;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
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
        var eq_path = Path.of(_paths.get(0));
        var ineq_path = Path.of(_paths.get(1));

        var equal = "file1,file2\n" + comparisons.stream().filter(Comparison::isSame)
                .map(cmp -> cmp + "\n").reduce("", String::concat);
        equal = equal.substring(0, equal.length() - 1);
        var inequal = "file1,file2\n" + comparisons.stream().filter(cmp -> !cmp.isSame())
                .map(cmp -> cmp + "\n").reduce("", String::concat);
        inequal = inequal.substring(0, inequal.length() - 1);
        try {
            Files.createDirectories(_out_dir);
            Files.deleteIfExists(eq_path);
            Files.deleteIfExists(ineq_path);
            Files.writeString(eq_path, equal, StandardOpenOption.CREATE);
            Files.writeString(ineq_path, inequal, StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new RuntimeException("Error when writing to csv file");
        }
    }
    @Override
    public List<String> getPath() {
        return _paths;
    }
}
