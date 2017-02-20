package com.steveperkins.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@RefreshScope
@Controller
@RequestMapping("/")
public class MainController {

    /**
     * For variables annotated with <code>@Value</code>, the Spring Cloud Config Client will auto-populate them
     * with values obtained from the config server.  The key value on the left-hand side of the expression will
     * be used (e.g. "message"), and the value on the right-hand side will be the default applied if no value is
     * found (e.g. "Not found").
     */
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
