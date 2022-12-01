package edu.nju.selab.writer;

import edu.nju.selab.autochecker.Comparison;
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
        _paths = new ArrayList<>();

        _paths.add(_out_dir.resolve("equal.csv").toString());
        _paths.add(_out_dir.resolve("inequal.csv").toString());
        _paths.add(_out_dir.resolve("unknown.csv").toString());
    }
    @Override
    public void write(@NotNull List<Comparison> comparisons) {
        var eq_path = Path.of(_paths.get(0));
        var ineq_path = Path.of(_paths.get(1));
        var unk_path = Path.of(_paths.get(2));
        final var header = "file1,file2\n";

        var equal = new StringBuilder(header);
        var inequal = new StringBuilder(header);
        var unknown = new StringBuilder(header);

        for (var comp : comparisons) {
            if (comp == null) {
                continue;
            }
            if (comp.isSame()) {
                equal.append(comp.toString()).append("\n");
            } else if (comp.isDifferent()) {
                inequal.append(comp.toString()).append("\n");
            } else {
                unknown.append(comp.toString()).append("\n");
            }
        }
        equal.deleteCharAt(equal.length() - 1);
        inequal.deleteCharAt(inequal.length() - 1);
        unknown.deleteCharAt(unknown.length() - 1);


        try {
            Files.createDirectories(_out_dir);
            Files.deleteIfExists(eq_path);
            Files.deleteIfExists(ineq_path);
            Files.deleteIfExists(unk_path);
            Files.writeString(eq_path, equal, StandardOpenOption.CREATE);
            Files.writeString(ineq_path, inequal, StandardOpenOption.CREATE);
            Files.writeString(unk_path, unknown, StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new RuntimeException("Error when writing to csv file");
        }
    }
    @Override
    public List<String> getPath() {
        return _paths;
    }
}
