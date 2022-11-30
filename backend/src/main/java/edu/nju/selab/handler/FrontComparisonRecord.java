package edu.nju.selab.handler;

import edu.nju.selab.autochecker.Comparison;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public record FrontComparisonRecord(
        String leftTitle,
        String leftContent,
        String rightTitle,
        String rightContent
) {
    public FrontComparisonRecord(@NotNull Comparison comparison) throws IOException {
        this(
                comparison.program1().getPath(),
                comparison.program1().read(),
                comparison.program2().getPath(),
                comparison.program2().read()
        );
    }
}
