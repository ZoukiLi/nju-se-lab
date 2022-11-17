package controller;


import java.util.*;

/** This class is responsible for parsing the command line options
 *  and storing them in a data structure for later use.
 */
public class OptionParser {

    private String inputDir;
    private String outputDir;
    private long timeout;
    private long seed;
    private int numTests;

    /** initialize the parser.
     *  all options are set to default values.
     */
    public OptionParser() {
        inputDir = ".";
        outputDir = ".";
        timeout = 1000;
        seed = System.currentTimeMillis();
        numTests = 10;
    }

    /** get the input directory
     * @return the input directory
     */
    public String getInputDir() {
        return inputDir;
    }
    /** get the output directory
     * @return the output directory
     */
    public String getOutputDir() {
        return outputDir;
    }
    /** get the timeout
     * @return the timeout
     */
    public long getTimeout() {
        return timeout;
    }
    /** get the seed
     * @return the seed
     */
    public long getSeed() {
        return seed;
    }
    /** get the number of tests
     * @return the number of tests
     */
    public int getNumTests() {
        return numTests;
    }

    // format: [-i, --input] inputDir [-o, --output] outputDir
    //         [-t, --timeout] timeout [-s, --seed] seed
    //         [-n, --numTests] numTests
    private static final Option[] options = {
            new Option('i', "input", OptionType.STRING),
            new Option('o', "output", OptionType.STRING),
            new Option('t', "timeout", OptionType.LONG),
            new Option('s', "seed", OptionType.LONG),
            new Option('n', "numTests", OptionType.INT),
    };

    /** Parse the command line options and store them in a data structure.
     *  @param args the command line options
     *  @return error message if there is an error, otherwise empty
     */
    public Optional<String> parse(String[] args) {
        Map<Option, Object> parsed = new HashMap<>();
        List<String> argsList = Arrays.asList(args);
        try {
            argsList.stream().skip(1).filter(arg -> arg.startsWith("-")).forEach(arg -> {
                Arrays.stream(options).filter(opt -> opt.match(arg)).forEach(opt -> {
                            int i = argsList.indexOf(arg);
                            if (i == argsList.size() - 1) {
                                throw new IllegalArgumentException("Option " + arg + " must have a value");
                            }
                            parsed.put(opt, opt.parse(argsList.get(i + 1)));
                        });
            });
        } catch (IllegalArgumentException e) {
            return Optional.of(e.getMessage());
        } catch (Exception e) {
            return Optional.of("Error when parsing options");
        }
        inputDir = (String) parsed.getOrDefault(options[0], inputDir);
        outputDir = (String) parsed.getOrDefault(options[1], outputDir);
        timeout = (long) parsed.getOrDefault(options[2], timeout);
        seed = (long) parsed.getOrDefault(options[3], seed);
        numTests = (int) parsed.getOrDefault(options[4], numTests);
        return Optional.empty();
    }

}
