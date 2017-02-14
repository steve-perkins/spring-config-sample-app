package com.steveperkins.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
public class MainController {

    @Value("${message:Not found}")
    private String message;

    @Value("${jdbcUrl:Not found}")
    private String jdbcUrl;

    @Value("${jdbcUsername:Not found}")
    private String jdbcUsername;

    @Value("${jdbcPassword:Not found}")
    private String jdbcPassword;

    @GetMapping
    public ModelAndView view() {
        final Map<String, Object> model = new HashMap<>();
        model.put("message", message);
        model.put("jdbcUrl", jdbcUrl);
        model.put("jdbcUsername", jdbcUsername);
        model.put("jdbcPassword", jdbcPassword);
        return new ModelAndView("index", model);
    }

}
