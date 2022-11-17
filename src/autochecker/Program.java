package autochecker;

import autochecker.test.TestBatch;
import autochecker.test.TestResult;
import autochecker.test.TestRunningRecord;
import src_reader.SrcReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.IntStream;

/**
 * Program structure that stores src file and test results
 */
public class Program {

    private final SrcReader srcReader;
    private boolean _hasCompiled;
    private TestResult _testResult;
    private String _executablePath;

    /**
     * Create a new Program.
     * @param srcReader the srcReader of the program.
     */
    public Program(SrcReader srcReader) {
        this.srcReader = srcReader;
        this._hasCompiled = false;
    }

    /**
     * Compile the program.
     * use temp folder to store the executable file.
     */
    public void compile() {
        if (this._hasCompiled) {
            return;
        }
        String srcTempPath;
        try {
            Path tmpDir = Path.of(System.getProperty("java.io.tmpdir"), "autochecker");
            Files.createDirectories(tmpDir);
            srcTempPath = this.srcReader.getTempCopyPath(tmpDir, "auto-checker");
        } catch (IOException e) {
            throw new RuntimeException("Error when creating temp copy of src file");
        }
        // use outside compiler to compile the src file
        _executablePath = srcTempPath.replaceAll("\\.c(pp)?$", "");
        try {
            var pb = new ProcessBuilder("g++", "-o", _executablePath, srcTempPath);
            var compileProcess = pb.start();
            compileProcess.waitFor();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error when compiling src file");
        }
        this._hasCompiled = true;
    }

    /**
     * get the original src file path.
     * @return the original src file path.
     */
    public String getPath() {
        return this.srcReader.getPath();
    }

    /**
     * Run the program with the given test.
     * @param testBatch the test to run.
     * @param timeout the timeout for the test, in milliseconds.
     */
    public void runTests(TestBatch testBatch, long timeout) {
        if (!this._hasCompiled) {
            this.compile();
        }
        // run the executable with the test batch
        var records = new ArrayList<TestRunningRecord>();
        testBatch.tests().forEach(t -> {
            try {
                var pb = new ProcessBuilder(_executablePath);
                var runProcess = pb.start();
                runProcess.getOutputStream().write(t.getBytes());
                runProcess.getOutputStream().flush();
                boolean inTime = runProcess.waitFor(timeout, java.util.concurrent.TimeUnit.MILLISECONDS);
                if (!inTime) {
                    runProcess.destroy();
                    records.add(new TestRunningRecord("", "", true));
                    return;
                }
                byte[] stdout = runProcess.getInputStream().readAllBytes();
                byte[] stderr = runProcess.getErrorStream().readAllBytes();
                records.add(new TestRunningRecord(new String(stdout), new String(stderr), false));
            } catch (InterruptedException e) {
                records.add(new TestRunningRecord("", "", true));
            } catch (IOException e) {
                throw new RuntimeException("Error when reading test output");
            }
        });
        _testResult = new TestResult(testBatch, records);
    }

    /**
     * Compare the test result with the given test result.
     * @param other the test result to compare with.
     * @return CompareResult of the comparison.
     */
    public ComparisonResult compare(Program other) {
        if (!this._hasCompiled || !other._hasCompiled) {
            return ComparisonResult.UNKNOWN;
        }
        if (this._testResult == null || other._testResult == null) {
            return ComparisonResult.UNKNOWN;
        }
        if (this._testResult.test() != other._testResult.test()) {
            return ComparisonResult.UNKNOWN;
        }

        var r1 = this._testResult.results();
        var r2 = other._testResult.results();
        return IntStream.range(0, r1.size())
                .mapToObj(i -> r1.get(i).CompareTo(r2.get(i)))
                .reduce(true, (a, b) -> a && b) ? ComparisonResult.SAME : ComparisonResult.DIFFERENT;
    }
}