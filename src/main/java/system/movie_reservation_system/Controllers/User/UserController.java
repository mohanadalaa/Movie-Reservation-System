package system.movie_reservation_system.Controllers.User;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;
import system.movie_reservation_system.Entities.AppUserEntity.AppUser;
import system.movie_reservation_system.Exception.ResponseMap;
import system.movie_reservation_system.Services.UserServices.UserService;
import system.movie_reservation_system.Security.JwtUtil;

import java.util.Map;


@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN', 'ROLE_DEV')")
public class UserController {

    private final JwtUtil jwtUtil;
    private final UserService userService;


    @GetMapping("/profile")
    public AppUser getProfile(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String email = jwtUtil.extractEmail(token);
        return  userService.getAppUserByEmail(email);
    }

    // POST  http://localhost:8080/api/user/profile/delete/account
    @PostMapping("/profile/delete/account")
    public Map<String, Object> deleteProfile(@RequestHeader("Authorization") String authHeader,@RequestParam String password){
        String token = authHeader.replace("Bearer ", "");
        String email = jwtUtil.extractEmail(token);
        String username = jwtUtil.extractUsername(token);
        userService.deleteAppUserByEmailWithPasswordVerification(email,password);

        return new ResponseMap.Builder()
                .status("Account Has Been Deleted Successfully")
                .timestamp()
                .message("Goodbye "+username+" ......")
                .build().getResponse();
    }
}