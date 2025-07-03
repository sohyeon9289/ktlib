// ===== AuthorAuthController.java (Author 서비스 전용, AuthorRepository 수정 반영) =====
package ktlib.api;

import ktlib.domain.Author;
import ktlib.domain.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthorAuthController {

    private final AuthorRepository authorRepository;
    private final PasswordEncoder passwordEncoder;
    private final ktlib.config.JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String> registerAuthor(@RequestBody Author author) {
        author.setAuthorPassword(passwordEncoder.encode(author.getAuthorPassword()));
        author.setStatus("Pending");
        authorRepository.save(author);
        return ResponseEntity.ok("Author registered successfully (awaiting approval)");
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> loginAuthor(@RequestBody LoginRequest request) {
        Author author = authorRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Author not found"));
        if (!passwordEncoder.matches(request.getPassword(), author.getAuthorPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        if (!"Approved".equals(author.getStatus())) {
            throw new RuntimeException("Author not approved yet");
        }
        String token = jwtUtil.generateToken(author.getEmail(), "AUTHOR");
        return ResponseEntity.ok(new TokenResponse(token));
    }

@RestController
public class AdminTestController {

    @GetMapping("/admin/test")
    public String adminTest() {
        return "Admin auth test successful ✅";
            }
}

@RestController
public class UserAuthTestController {

    @GetMapping("/user/test")
    public String userTest() {
        return "User auth test successful ✅";
    }
}



}
