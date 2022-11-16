package autochecker;

import autochecker.test.TestBatch;
import autochecker.test.TestResult;
import src_reader.SrcReader;

public class Program {

    private final SrcReader srcReader;
    private boolean _hasCompiled;
    private TestResult _testResult;

    public Program(SrcReader srcReader) {
        this.srcReader = srcReader;
        this._hasCompiled = false;
    }

    public void compile() {
        if (this._hasCompiled) {
            return;
        }

    }

    public String getPath() {
        return this.srcReader.getPath();
    }

    public void runTests(TestBatch testBatch) {
        if (!this._hasCompiled) {
            this.compile();
        }
    }

    public ComparisonResult compare(Program other) {
        return ComparisonResult.UNKNOWN;
    }
}
