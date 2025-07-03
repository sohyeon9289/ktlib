package ktlib.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminTestController {

    @GetMapping("/admin/test")
    public String adminTest() {
        return "Admin auth test successful ✅";
    }
}
