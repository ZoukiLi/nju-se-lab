package edu.nju.selab.handler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexHandler {
    @RequestMapping("/")
    public String index() {
        return "index";
    }
}
