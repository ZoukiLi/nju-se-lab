package edu.nju.selab.autochecker;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * A comparison between two programs.
 * Includes the two programs and the result of the comparison.
 */
public final class Comparison {
    private final Program program1;
    private final Program program2;
    private ComparisonResult result;

    /**
     *
     */
    public Comparison(Program program1, Program program2, ComparisonResult result) {
        this.program1 = program1;
        this.program2 = program2;
        this.result = result;
    }

    /**
     * @return csv representation of this comparison.
     */
    public @NotNull String toString() {
        return program1.getPath() + "," + program2.getPath();
    }

    /**
     * @return is SAME.
     */
    public boolean isSame() {
        return result == ComparisonResult.SAME;
    }

    /**
     * @return is DIFFERENT.
     */
    public boolean isDifferent() {
        return result == ComparisonResult.DIFFERENT;
    }

    /**
     * @return is UNKNOWN.
     */
    public boolean isUnknown() {
        return result == ComparisonResult.UNKNOWN;
    }

    public Program program1() {
        return program1;
    }

    public Program program2() {
        return program2;
    }

    public void mark(ComparisonResult result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Comparison) obj;
        return Objects.equals(this.program1, that.program1) &&
                Objects.equals(this.program2, that.program2) &&
                Objects.equals(this.result, that.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(program1, program2, result);
    }

}
