package edu.nju.selab.handler;

import edu.nju.selab.autochecker.Comparison;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public record FrontComparisonRecord(
        String leftCaption,
        String leftContent,
        String rightCaption,
        String rightContent,
        boolean end
) {
    public FrontComparisonRecord(@NotNull Optional<Comparison> comparison) {
        this(
                comparison.map(c -> c.program1().getPath()).orElse(""),
                comparison.map(c -> c.program1().read()).orElse(""),
                comparison.map(c -> c.program2().getPath()).orElse(""),
                comparison.map(c -> c.program2().read()).orElse(""),
                comparison.isEmpty()
        );
    }
}
