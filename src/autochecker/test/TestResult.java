package autochecker.test;

import java.util.List;

/**
 * Structure to hold the results of a test.
 * Both test and results are used for comparison.
 */
public class TestResult {
    private final TestBatch _test;
    private final List<String> _results;
    private final List<Boolean> _overTime;

    /**
     * Create a new TestResult.
     * @param test the test that was run.
     * @param results the results of the test.
     * @param overTime whether the test took too long.
     */
    public TestResult(TestBatch test, List<String> results, List<Boolean> overTime) {
        _test = test;
        _results = results;
        _overTime = overTime;
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
    public List<String> getResults() {
        return _results;
    }

    /**
     * Get whether the test took too long.
     * @return whether the test took too long.
     */
    public List<Boolean> getOverTime() {
        return _overTime;
    }
}
