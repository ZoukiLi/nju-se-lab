package autochecker.test;

public class TestRunningRecord {
    private final String _stdout;
    private final String _stderr;
    private final boolean _timeout;

    /**
     * Create a new TestRunningRecord.
     * @param stdout the stdout of the test.
     * @param stderr the stderr of the test.
     * @param timeout whether the test timed out.
     */
    public TestRunningRecord(String stdout, String stderr, boolean timeout) {
        _stdout = stdout;
        _stderr = stderr;
        _timeout = timeout;
    }

    /**
     * check if two TestRunningRecord are equal.
     * @param other the other TestRunningRecord.
     * @return records are considered equal only when stdout, stderr and timeout are equal
     */
    public boolean CompareTo(TestRunningRecord other) {
        return _stdout.equals(other._stdout) && _stderr.equals(other._stderr) && _timeout == other._timeout;
    }
}
