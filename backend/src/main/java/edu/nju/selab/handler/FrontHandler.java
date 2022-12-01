package edu.nju.selab.handler;

import edu.nju.selab.autochecker.Comparison;
import edu.nju.selab.controller.Controller;
import edu.nju.selab.controller.OptionParser;
import jakarta.annotation.PostConstruct;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class FrontHandler {
    private final ApplicationContext context;
    private List<Comparison> _comparisons;
    private int _index = 0;

    @PostMapping(value = "/api/nextComparison", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public FrontComparisonRecord nextComparison(@RequestBody Map<String, String> body) {
        body.forEach((k, v) -> {
            System.out.println(k + " " + v);
        });
        if (_index == _comparisons.size()) {
            return new FrontComparisonRecord(Optional.empty());
        }
        var comparison = _comparisons.get(_index++);
        return new FrontComparisonRecord(Optional.of(comparison));
    }

    public FrontHandler(ApplicationContext context) {
        this.context = context;
    }

    @PostConstruct
    public void init() {
        var parser = new OptionParser();
        var commandLineArgs = context.getBean(ApplicationArguments.class);
        var records = parser.parse(commandLineArgs.getSourceArgs());
        var controller = new Controller(records);
        _comparisons = controller.run();
    }
}
