package edu.nju.selab;
import edu.nju.selab.controller.Controller;
import edu.nju.selab.controller.OptionParser;

public class Main {
    public static void main(String[] args) {
        var parser = new OptionParser();
        new Controller(parser.parse(args)).run();
    }
}