package autochecker.test;

import java.util.List;

/**
 * A batch of tests to be run.
 */
public class TestBatch {
    /**
     * final tests in this batch.
     */
    private final List<String> _tests;
    /**
     * get the tests in this batch.
     */
    public List<String> getTests() {
        return _tests;
    }

    /**
     * Create a new TestBatch.
     * @param tests the tests in this batch.
     */
    public TestBatch(List<String> tests) {
        _tests = tests;
    }
}
