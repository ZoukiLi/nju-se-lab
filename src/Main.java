import controller.Controller;
import controller.OptionParser;

public class Main {
    public static void main(String[] args) {
        var parser = new OptionParser();
        new Controller(parser.parse(args)).run();
    }
}