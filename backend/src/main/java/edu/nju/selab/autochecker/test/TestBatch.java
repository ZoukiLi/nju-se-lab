package edu.nju.selab.autochecker.test;

import java.util.List;

/**
 * A batch of tests to be run.
 * @param tests The tests to be run.
 * @param batchSize The number of tests to run.
 */
public record TestBatch (List<String> tests, int batchSize) {
    public TestBatch(List<String> tests) {
        this(tests, tests.size());
    }
}
