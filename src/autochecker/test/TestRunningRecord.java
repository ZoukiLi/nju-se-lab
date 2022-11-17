package autochecker.test;

public record TestRunningRecord (String stdout, String stderr, boolean timeout) {
    /**
     * check if two TestRunningRecord are equal.
     * @param other the other TestRunningRecord.
     * @return records are considered equal only when stdout, stderr and timeout are equal
     */
    public boolean CompareTo(TestRunningRecord other) {
        return stdout.equals(other.stdout) && stderr.equals(other.stderr) && timeout == other.timeout;
    }
}
