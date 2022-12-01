package edu.nju.selab.autochecker;

import org.jetbrains.annotations.NotNull;

/**
 * A comparison between two programs.
 * Includes the two programs and the result of the comparison.
 */
public record Comparison (Program program1, Program program2, ComparisonResult result) {

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
}
