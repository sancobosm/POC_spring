package cobos.santiago.poc.api;


import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;

@RestController
@RequestMapping("/api")
public class HelloWorldController {
    @GetMapping
    public String helloWorld(){
        return "Hello world";
    }



}
