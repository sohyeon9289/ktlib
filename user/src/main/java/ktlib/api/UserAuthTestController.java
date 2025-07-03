package ktlib.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAuthTestController {

    @GetMapping("/user/test")
    public String userTest() {
        return "User auth test successful ✅";
    }
}