package cobos.santiago.poc.api;

/*import cobos.santiago.poc.domain.Person;
import cobos.santiago.poc.domain.PersonDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;*/
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;

@RestController
@RequestMapping("/api")
//@Slf4j
public class HelloWorldController {
    @GetMapping
    public String helloWorld(){
        return "Hello world";
    }



}
