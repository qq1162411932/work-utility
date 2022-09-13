package core.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/boot")
public class BootStartTestController {

    @GetMapping("/start")
    public String bootStart(){
        return "ok";
    }
}
