package edu.nju.selab.controller;


import java.util.*;

public class OptionParser {

    /** initialize the parser.
     *  all options are set to default values.
     */
    public OptionParser() {
    }

    // format: [-i, --input] inputDir [-o, --output] outputDir
    //         [-t, --timeout] timeout [-s, --seed] seed
    //         [-n, --numTests] numTests [-r, --recursive]
    private static final Option[] options = {
            new Option('i', "input", OptionType.STRING),
            new Option('o', "output", OptionType.STRING),
            new Option('t', "timeout", OptionType.LONG),
            new Option('s', "seed", OptionType.LONG),
            new Option('n', "numTests", OptionType.INT),
            new Option('r', "recursive", OptionType.NONE),
    };

    /** Parse the command line options and store them in a data structure.
     *  @param args the command line options
     *  @return error message if there is an error, otherwise empty
     */
    public OptionRecords parse(String[] args) {
        var parsed = new HashMap<Option, Object>();
        var argsList = Arrays.asList(args);
        var err = Optional.<String>empty();
        try {
            argsList.stream().filter(arg -> arg.startsWith("-")).forEach(arg ->
                    Arrays.stream(options).filter(opt -> opt.match(arg)).forEach(opt -> {
                        int i = argsList.indexOf(arg);
                        if (opt.type() == OptionType.NONE) {
                            parsed.put(opt, true);
                        } else if(i + 1 < argsList.size()) {
                            parsed.put(opt, opt.parse(argsList.get(i + 1)));
                        } else {
                            throw new IllegalArgumentException("Missing argument for option " + arg);
                        }
                    }));
        } catch (IllegalArgumentException e) {
            err = Optional.of(e.getMessage());
        } catch (Exception e) {
            err = Optional.of("Error when parsing options");
        }
        return new OptionRecords(
                parsed.getOrDefault(options[0], ".").toString(),
                parsed.getOrDefault(options[1], ".").toString(),
                (long) parsed.getOrDefault(options[2], 1000L),
                (long) parsed.getOrDefault(options[3], 0L),
                (int) parsed.getOrDefault(options[4], 10),
                parsed.containsKey(options[5]),
                err
        );
    }

}
