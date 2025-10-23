package com.example.assignment_tracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // NOT @RestController. This one returns HTML pages.
public class PageController {

    /**
     * This method handles requests to the root URL ("/")
     * and returns the "index" template.
     * Spring Boot + Thymeleaf will look for /resources/templates/index.html
     */
    @GetMapping("/")
    public String getIndexPage() {
        return "index"; // This tells Thymeleaf to find "index.html"
    }
}
