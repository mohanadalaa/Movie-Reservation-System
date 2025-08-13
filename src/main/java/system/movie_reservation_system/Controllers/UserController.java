package system.movie_reservation_system.Controllers;


import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import system.movie_reservation_system.Entity.AppUser;
import system.movie_reservation_system.Services.UserService;
import system.movie_reservation_system.Security.JwtUtil;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;


    @PostMapping("/login")
    public Map<String, String> login(@RequestParam String email,
                                     @RequestParam String password,
                                     HttpServletResponse response) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );


        AppUser user = userService.getAppUserByEmail(email);
        String token = jwtUtil.generateToken(user);
        return Map.of("token", token);
    }

    // optional helper endpoint to register a user (for testing)
    @PostMapping("/register")
    public Map<String, String> register(@RequestParam String username,@RequestParam String email, @RequestParam String password) {
        userService.createUser(username,email, password);
        return Map.of("status", "created");
    }

    @GetMapping("/profile")
    public AppUser getProfile(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String username = jwtUtil.extractUsername(token);
        return  userService.getAppUserByUsername(username);
    }
}