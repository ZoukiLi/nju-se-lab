package autochecker.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Generator that read format and generate test cases.
 */
public class TestGenerator {
    private final Random _random;
    private final int _batch_size;
    private final String _fmt;

    /**
     * Create a new TestGenerator.
     * @param fmt the format of the test cases.
     * @param batch_size the size of the batch.
     * @param seed the seed of the random number generator.
     */
    public TestGenerator(String fmt, int batch_size, long seed) {
        _fmt = fmt;
        _batch_size = batch_size;
        _random = new Random(seed);
    }

    private char randomAlpha() {
        if (_random.nextBoolean()) {
            return (char) ('a' + _random.nextInt(26));
        } else {
            return (char) ('A' + _random.nextInt(26));
        }
    }

    private int randomIntRange(int min, int max) {
        return min + _random.nextInt(max - min + 1);
    }
    private String randomString(int a, int b) {
        StringBuilder sb = new StringBuilder();
        int length = randomIntRange(a, b);
        for (int i = 0; i < length; i++) {
            sb.append(randomAlpha());
        }
        return sb.toString();
    }

    private String generateOneTest() {
        // _fmt is multiple lines, each line has multiple types.
        // Each type is one of:
        // int(a, b) - an integer between a and b (inclusive)
        // string(a, b) - a string of length between a and b (inclusive)
        // char - a single a-zA-Z character
        String[] lines = _fmt.split("\\r?\\n");
        StringBuilder sb = new StringBuilder();

        for (String line : lines) {
            String[] types = line.split("\\s+");
            for (String type : types) {
                if (type.equals("char")) {
                    sb.append(randomAlpha());
                } else {
                    int start = type.indexOf('(');
                    int end = type.indexOf(')');
                    String[] args = type.substring(start + 1, end).split(",");
                    int a = Integer.parseInt(args[0]);
                    int b = Integer.parseInt(args[1]);
                    if (type.startsWith("int")) {
                        sb.append(randomIntRange(a, b));
                    } else if (type.startsWith("string")) {
                        sb.append(randomString(a, b));
                    } else {
                        throw new RuntimeException("Unknown type: " + type);
                    }
                }
                sb.append(' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    /**
     * Generate a batch of test cases.
     * @return the batch of test cases.
     */
    public TestBatch generateTests() {
        List<String> tests = new ArrayList();
        for (int i = 0; i < _batch_size; i++) {
            tests.add(generateOneTest());
        }
        return new TestBatch(tests);
    }

}
