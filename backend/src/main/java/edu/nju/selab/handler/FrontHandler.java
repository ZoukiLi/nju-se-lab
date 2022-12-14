package edu.nju.selab.handler;


import edu.nju.selab.autochecker.ComparisonResult;
import edu.nju.selab.autochecker.Program;
import edu.nju.selab.controller.Controller;
import edu.nju.selab.controller.OptionParser;
import jakarta.annotation.PostConstruct;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.Map;
import java.util.Optional;

@RestController
public class FrontHandler {
    private final ApplicationContext context;
    private ComparisonMatrix matrix;
    private Controller controller;
    private Program program1;
    private Program program2;

    @PostMapping(value = "/api/nextComparison", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public FrontComparisonRecord nextComparison(@RequestBody @NotNull Map<String, String> body) {
        body.forEach((k, v) -> {
            System.out.println(k + " " + v);
        });
        if (!body.containsKey("manualResult"))
            return new FrontComparisonRecord(matrix.unknownComparisons());
        var result = body.get("manualResult");
        switch (result) {
            case "same" -> matrix.mark(program1, program2, ComparisonResult.SAME);
            case "different" -> matrix.mark(program1, program2, ComparisonResult.DIFFERENT);
            case "notSure" -> matrix.mark(program1, program2, ComparisonResult.SKIP);
        }

        // if matrix empty, return null.
        if (matrix.unknownComparisons().isEmpty()) {
            controller.writeResults(matrix.comparisons());
            return new FrontComparisonRecord(Optional.empty());
        }
        program1 = matrix.unknownComparisons().get().program1();
        program2 = matrix.unknownComparisons().get().program2();
        return new FrontComparisonRecord(matrix.unknownComparisons());
    }

    public FrontHandler(ApplicationContext context) {
        this.context = context;
    }

    @PostConstruct
    public void init() {
        var commandLineArgs = context.getBean(ApplicationArguments.class);
        var records = new OptionParser().parse(commandLineArgs.getSourceArgs());
        controller = new Controller(records);
        matrix = new ComparisonMatrix(controller.run());
    }
}
