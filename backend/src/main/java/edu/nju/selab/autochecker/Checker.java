package edu.nju.selab.autochecker;

import edu.nju.selab.autochecker.test.TestBatch;
import edu.nju.selab.autochecker.test.TestGenerator;
import edu.nju.selab.src_reader.SrcReader;
import edu.nju.selab.src_reader.SrcReaderType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * The main class for the AutoChecker program.
 * Auto checker is to compare programs and to determine if they are the same.
 */
public class Checker {
    private final List<Program> _programs;
    private final TestGenerator _testGenerator;
    private TestBatch _testBatch;
    private final List<Comparison> _comparisons;

    /**
     * Creates a new Checker instance.
     * @param src_paths The paths to the source files to be checked.
     * @param fmt The format of the stdin of tests.
     * @param batch_size The number of tests to be generated at a time.
     * @param seed The seed for the random number generator.
     */
    public Checker(@NotNull List<String> src_paths, String fmt, int batch_size, long seed) {
        // create a Program for each src file
        _programs = src_paths.stream().map(p -> SrcReader.fromPath(p, SrcReaderType.LOCAL_FILE))
                .map(Program::new).toList();
        // create a TestGenerator
        _testGenerator = new TestGenerator(fmt, batch_size, seed);
        _comparisons = new ArrayList<>();
    }

    /**
     * Compiles all the programs and compares them to each other.
     * Saves the results of the comparisons.
     */
    public void check(long timeout) {
        // compile all programs
        _programs.forEach(Program::compile);
        // generate a test batch
        _testBatch = _testGenerator.generateTests();
        // run the test batch on all programs
        _programs.forEach(p -> p.runTests(_testBatch, timeout));
        // compare all programs
        _programs.forEach(p1 ->
                _programs.stream().skip(_programs.indexOf(p1) + 1).forEach(p2 ->
                        _comparisons.add(new Comparison(p1, p2, p1.compare(p2)))));
    }

    /**
     * Get the results of the comparisons.
     */
    public List<Comparison> getComparisons() {
        return _comparisons;
    }
}
