package controller;

import java.util.Optional;

public record OptionRecords(String inputDir, String outputDir, long timeout,
                            long seed, int numTests, boolean isRecursive, Optional<String> error) {
}
