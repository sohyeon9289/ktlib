package ktlib.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorAuthTestController {

    @GetMapping("/author/test")
    public String authorTest() {
        return "Author auth test successful ✅";
    }
}