package edu.nju.selab.handler;

import edu.nju.selab.autochecker.Comparison;
import edu.nju.selab.autochecker.test.TestRunningRecord;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public record FrontComparisonRecord(
        String leftCaption,
        String leftContent,
        String rightCaption,
        String rightContent,
        String[] testCases,
        TestRunningRecord[] leftResults,
        TestRunningRecord[] rightResults,
        boolean end
) {
    public FrontComparisonRecord(@NotNull Optional<Comparison> comparison) {
        this(
                comparison.map(c -> c.program1().getPath()).orElse(""),
                comparison.map(c -> c.program1().read()).orElse(""),
                comparison.map(c -> c.program2().getPath()).orElse(""),
                comparison.map(c -> c.program2().read()).orElse(""),
                comparison.map(c -> c.program1().testResult().test()
                        .tests().toArray(String[]::new)).orElse(new String[0]),
                comparison.map(c -> c.program1().testResult().results()
                        .toArray(TestRunningRecord[]::new)).orElse(new TestRunningRecord[0]),
                comparison.map(c -> c.program2().testResult().results()
                        .toArray(TestRunningRecord[]::new)).orElse(new TestRunningRecord[0]),
                comparison.isEmpty()
        );
    }

    @Override
    public int hashCode() {
        return (leftCaption + rightCaption).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FrontComparisonRecord other) {
            return leftCaption.equals(other.leftCaption) && rightCaption.equals(other.rightCaption);
        }
        return false;
    }

    @Contract(pure = true)
    @Override
    public @NotNull String toString() {
        // return json representation of this record.
        return String.format(
"""
{
"leftCaption": "%s",
"leftContent": "%s",
"rightCaption": "%s",
"rightContent": "%s",
"testCases": %s,
"leftResults": %s,
"rightResults": %s,
"end": %s
}
""",
                leftCaption,
                leftContent,
                rightCaption,
                rightContent,
                List.of(testCases),
                List.of(leftResults),
                List.of(rightResults),
                end
        );
    }
}
