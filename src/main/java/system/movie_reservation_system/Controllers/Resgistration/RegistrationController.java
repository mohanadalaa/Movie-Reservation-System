package system.movie_reservation_system.Controllers.Resgistration;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import system.movie_reservation_system.Entities.AppUserEntity.AppUser;
import system.movie_reservation_system.Exception.ResponseMap;
import system.movie_reservation_system.Security.JwtUtil;
import system.movie_reservation_system.Services.RegistrationService.RegistrationService;
import system.movie_reservation_system.Services.UserServices.UserService;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;
    private final RegistrationService registrationService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public Map<String, Object> register(@RequestParam String username,
                                        @RequestParam String email,
                                        @RequestParam String password) {
        AppUser newUser = registrationService.createUser(username, email, password);
        return new ResponseMap.Builder()
                .status("Account created successfully")
                .timestamp()
                .add("email",newUser.getEmail())
                .message("Welcome "+newUser.getUsername())
                .build().getResponse();
    }

    @PostMapping("/login")
    public  Map<String, Object> login(@RequestParam String email,
                                      @RequestParam String password,
                                      HttpServletResponse response) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        AppUser user = userService.getAppUserByEmail(email);
        String token = jwtUtil.generateToken(user);
        return new ResponseMap.Builder()
                .message("Welcome " + user.getUsername())
                .add("token", token)
                .build().getResponse();
    }

}
