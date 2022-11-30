package edu.nju.selab.autochecker.test;

import java.util.List;

/**
 * Structure to hold the results of a test.
 * Both test and results are used for comparison.
 */
public record TestResult (TestBatch test, List<TestRunningRecord> results) {}
