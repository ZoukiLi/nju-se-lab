package edu.nju.selab.handler;

import edu.nju.selab.autochecker.Comparison;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class FrontHandler {
    private final List<Comparison> _comparisons;
    private int _index = 0;

    @PostMapping("/api/nextComparison")
    public FrontComparisonRecord nextComparison(@RequestParam("manualResult") String manualResult) {
        try {
            return new FrontComparisonRecord(_comparisons.get(_index++));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public FrontHandler(List<Comparison> comparisons) {
        _comparisons = comparisons;
    }
}
