package autochecker;

/**
 * A comparison between two programs.
 * Includes the two programs and the result of the comparison.
 */
public record Comparison (Program _program1, Program _program2, ComparisonResult _result) {

    /**
     * @return csv representation of this comparison.
     */
    public String toString() {
        return _program1.getPath() + "," + _program2.getPath();
    }
    public ComparisonResult getResult() {
        return _result;
    }
}
