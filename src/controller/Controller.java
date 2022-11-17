package controller;

import autochecker.Checker;
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
    private final OptionParser _options;

    /**
     * Create a new Controller.
     * @param optionParser the options for the program.
     */
    public Controller(OptionParser optionParser) {
        _options = optionParser;
    }

    /**
     * Run the program.
     * uses the options passed in the constructor.
     * after options are parsed, the program will run.
     */
    public void run() {
        String inputDir = _options.getInputDir();
        String outputDir = _options.getOutputDir();
        Path inputPath = Path.of(inputDir);
        // get all src files in input directory
        List<String> srcPaths;
        try {
            srcPaths = Files.walk(inputPath)
                    .filter(Files::isRegularFile)
                    .filter(path -> path.endsWith(".c") || path.endsWith(".cpp"))
                    .map(Path::toString)
                    .toList();
        } catch (IOException e) {
            System.err.println("Error when reading input directory");
            return;
        }
        // read stdin_fmt.txt
        String stdinFmt;
        try {
            stdinFmt = Files.readString(inputPath.resolve("stdin_format.txt"));
        } catch (IOException e) {
            System.err.println("Error when reading stdin_fmt.txt");
            return;
        }
        // create checker
        Checker checker = new Checker(srcPaths, stdinFmt, _options.getNumTests(), _options.getSeed());
        try {
            checker.check(_options.getTimeout());
        } catch (RuntimeException e) {
            System.err.println("Error when checking src files");
            System.err.println(e.getMessage());
            return;
        }
        // write results to output directory
        ComparisonWriter writer = ComparisonWriter.fromOutDir(outputDir, ComparisonWriterType.CSV_PAIR);
        writer.write(checker.getComparisons());
    }
}
