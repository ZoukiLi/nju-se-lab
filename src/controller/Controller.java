package controller;

import autochecker.Checker;
import autochecker.Comparison;
import org.jetbrains.annotations.Unmodifiable;
import writer.ComparisonWriter;
import writer.ComparisonWriterType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Main controller class for overall program.
 */
public class Controller {
    private final OptionRecords _options;

    /**
     * Create a new Controller.
     * @param options the options for the program.
     */
    public Controller(OptionRecords options) {
        _options = options;
    }

    private @Unmodifiable List<Comparison> runSubdir(Path inputPath) {
        // get all src files in input directory
        List<String> srcPaths;
        try (var stream = Files.walk(inputPath)) {
            srcPaths = stream
                    .filter(Files::isRegularFile)
                    .map(Path::toString)
                    .filter(path -> path.endsWith(".c") || path.endsWith(".cpp"))
                    .toList();
        } catch (IOException e) {
            System.err.println(inputPath + ": Error when reading input directory");
            return List.of();
        }
        // read stdin_fmt.txt
        String stdinFmt;
        try {
            stdinFmt = Files.readString(inputPath.resolve("stdin_format.txt"));
        } catch (IOException e) {
            System.err.println(inputPath + ": Error when reading stdin_format.txt");
            return List.of();
        }
        // create checker
        var checker = new Checker(srcPaths, stdinFmt, _options.numTests(), _options.seed());
        try {
            checker.check(_options.timeout());
        } catch (RuntimeException e) {
            System.err.println(inputPath + ": Error when checking src files");
            System.err.println(e.getMessage());
            return List.of();
        }
        return checker.getComparisons();
    }

    /**
     * Run the program.
     * uses the options passed in the constructor.
     * after options are parsed, the program will run.
     */
    public void run() {
        var inputDir = _options.inputDir();
        var outputDir = _options.outputDir();
        var inputPath = Path.of(inputDir);
        List<Comparison> cmpResults;
        try (var stream = Files.walk(inputPath)) {
            if (_options.isRecursive()) {
                cmpResults = stream.filter(Files::isDirectory).filter(p -> 0 != p.compareTo(inputPath))
                        .map(this::runSubdir).flatMap(List::stream).toList();
            } else {
                cmpResults = runSubdir(inputPath);
            }
        } catch (IOException e) {
            System.err.println(inputDir + ": Error when reading input directory");
            return;
        }
        // write results to output directory
        var writer = ComparisonWriter.fromOutDir(outputDir, ComparisonWriterType.CSV_PAIR);
        writer.write(cmpResults);
    }
}
