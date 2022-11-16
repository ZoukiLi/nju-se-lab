package autochecker.test;

import java.util.List;

/**
 * Structure to hold the results of a test.
 * Both test and results are used for comparison.
 */
public class TestResult {
    private final TestBatch _test;
    private final List<TestRunningRecord> _results;

    /**
     * Create a new TestResult.
     * @param test the test.
     * @param results the results of the test.
     */
    public TestResult(TestBatch test, List<TestRunningRecord> results) {
        _test = test;
        _results = results;
    }

    /**
     * Get the test that was run.
     * @return the test that was run.
     */
    public TestBatch getTest() {
        return _test;
    }

    /**
     * Get the results of the test.
     * @return the results of the test.
     */
    public List<TestRunningRecord> getResults() {
        return _results;
    }
}
