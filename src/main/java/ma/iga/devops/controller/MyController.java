package ma.iga.devops.controller;



import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/")
public class MyController {

    @GetMapping(value = "add")
    public Integer add(@RequestParam("n1") Integer n1,@RequestParam("n2") Integer n2) {
        return n1-n2;
    }
}
