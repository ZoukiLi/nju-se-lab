package autochecker;

/**
 * A comparison between two programs.
 * Includes the two programs and the result of the comparison.
 */
public class Comparison {

    private final Program _program1;
    private final Program _program2;
    private final ComparisonResult _result;

    /**
     * Create a new Comparison.
     * @param program1 the first program.
     * @param program2 the second program.
     * @param result the result of the comparison.
     */
    public Comparison(Program program1, Program program2, ComparisonResult result) {
        _program1 = program1;
        _program2 = program2;
        _result = result;
    }

    public Program getProgram1() {
        return _program1;
    }
    public Program getProgram2() {
        return _program2;
    }
    public ComparisonResult getResult() {
        return _result;
    }
}
