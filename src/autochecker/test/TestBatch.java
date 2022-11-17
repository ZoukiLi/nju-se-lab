package autochecker.test;

import java.util.List;

/**
 * A batch of tests to be run.
 */
public record TestBatch (List<String> tests, int batchSize) {
    public TestBatch(List<String> tests) {
        this(tests, tests.size());
    }
}
