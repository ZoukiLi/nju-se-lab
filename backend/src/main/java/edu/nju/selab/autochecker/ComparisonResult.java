package edu.nju.selab.autochecker;

/**
 * One comparison result.
 * Value is same, different, or unknown.
 */
public enum ComparisonResult {
    /**
     * same must be assured by the user.
     */
    SAME,
    /**
     * different can be assured by the autochecker.
     */
    DIFFERENT,
    /**
     * unknown means the autochecker cannot decide.
     */
    UNKNOWN,
    /**
     * skip means the user does not want to compare this pair.
     */
    SKIP,
}
