package edu.nju.selab.handler;

import edu.nju.selab.autochecker.Comparison;
import edu.nju.selab.autochecker.ComparisonResult;
import edu.nju.selab.autochecker.Program;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.*;

public record ComparisonMatrix(Map<Program, Map<Program, Comparison>> matrix) {
    /**
     * from a list of comparisons, construct a comparison matrix.
     *
     * @param comparisons list of comparisons.
     */
    public ComparisonMatrix(@NotNull List<Comparison> comparisons) {
        this(new HashMap<>());
        comparisons.forEach(c -> {
            if (!matrix.containsKey(c.program1())) {
                matrix.put(c.program1(), new HashMap<>());
            }
            matrix.get(c.program1()).put(c.program2(), c);
            if (!matrix.containsKey(c.program2())) {
                matrix.put(c.program2(), new HashMap<>());
            }
            matrix.get(c.program2()).put(c.program1(), c);
        });
    }

    /**
     * get ordered program list.
     *
     * @return set of programs in this matrix.
     */
    public @NotNull @Unmodifiable List<Program> getPrograms() {
        return List.copyOf(matrix.keySet());
    }

    /**
     * get all the comparisons between each two programs.
     *
     * @return a list of comparisons.
     */
    public @NotNull List<Comparison> comparisons() {
        // for each two programs, there is only one comparison.
        return getPrograms().stream().flatMap(p1 ->
                getPrograms().stream().skip(getPrograms().indexOf(p1) + 1L)
                        .map(p2 -> matrix.get(p1).get(p2))).filter(Objects::nonNull).toList();
    }

    /**
     * get unknown comparisons.
     * @return Optional.empty() if all comparisons are known.
     */
    public @NotNull Optional<Comparison> unknownComparisons() {
        return matrix.values().stream().flatMap(m -> m.values().stream())
                .filter(Comparison::isUnknown).findFirst();
    }

    /**
     * mark two programs as equivalent or not equivalent.
     * @param p1 program 1.
     * @param p2 program 2.
     * @param equivalent true if equivalent, false if not equivalent.
     */
    public void mark(@NotNull Program p1, @NotNull Program p2, ComparisonResult equivalent) {
        matrix.get(p1).get(p2).mark(equivalent);

        // if p1 and p2 are equivalent, then all programs equivalent to p1 are equivalent to p2.
        // if p1 and p2 are equivalent, then all programs equivalent to p2 are equivalent to p1.
        if (equivalent == ComparisonResult.SAME || equivalent == ComparisonResult.DIFFERENT) {
            expandEquivalence(p2, p1, equivalent);
            expandEquivalence(p1, p2, equivalent);
        }
    }

    private void expandEquivalence(@NotNull Program p1, @NotNull Program p2, ComparisonResult equivalent) {
        matrix.get(p2).values().stream().filter(Comparison::isSame).map(Comparison::program2)
                .filter(p -> !p.equals(p1)).forEach(p -> {
                    matrix.get(p).get(p1).mark(equivalent);
                    matrix.get(p1).get(p).mark(equivalent);
                });
    }
}
