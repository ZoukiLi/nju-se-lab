package controller;


import java.util.Optional;

/** This class is responsible for parsing the command line options
 *  and storing them in a data structure for later use.
 */
public class OptionParser {

    // input directory
    private String inputDir;

    // output directory
    private String outputDir;

    /** initialize the parser.
     * inputDir and outputDir are set to empty strings.
     */
    public OptionParser() {
        inputDir = "";
        outputDir = "";
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

    /** Parse the command line options and store them in a data structure.
     *  @param args the command line options
     *  @return error message if there is an error, otherwise empty
     */
    public Optional<String> parse(String[] args) {
        // format: [-i, --input] inputDir [-o, --output] outputDir
        int numNoParam = 0;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-i") || args[i].equals("--input")) {
                if (i + 1 < args.length) {
                    inputDir = args[i + 1];
                    i++;
                } else {
                    return Optional.of("Missing input directory");
                }
            } else if (args[i].equals("-o") || args[i].equals("--output")) {
                if (i + 1 < args.length) {
                    outputDir = args[i + 1];
                    i++;
                } else {
                    return Optional.of("Missing output directory");
                }
            } else {
                if (numNoParam == 0) {
                    inputDir = args[i];
                    numNoParam++;
                } else if (numNoParam == 1) {
                    outputDir = args[i];
                } else {
                    return Optional.of("Unknown option: " + args[i]);
                }
            }
        }
        return Optional.empty();
    }

}
