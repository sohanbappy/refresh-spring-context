package com.bappy.manualrestart;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/restart")
    public String restart() {
        ManualrestartApplication.restart();
        return "Restarted!!";
    }

    @GetMapping("/")
    public String hello() {
        return "Hello!!! I'm alive......";
    }

}
