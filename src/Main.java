import controller.Controller;
import controller.OptionParser;

public class Main {
    public static void main(String[] args) {
        OptionParser parser = new OptionParser();
        parser.parse(args);
        Controller controller = new Controller(parser);
        controller.run();
    }
}