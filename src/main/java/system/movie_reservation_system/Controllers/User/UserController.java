package system.movie_reservation_system.Controllers.User;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;
import system.movie_reservation_system.Entities.AppUserEntity.AppUser;
import system.movie_reservation_system.Entities.AppUserEntity.AppUserDisplay;
import system.movie_reservation_system.Exception.ResourceNotFoundException;
import system.movie_reservation_system.Exception.ResponseMap;
import system.movie_reservation_system.Services.UserServices.UserService;
import system.movie_reservation_system.Security.JwtUtil;

import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/api/user/account")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN', 'ROLE_DEV')")
public class UserController {

    private final JwtUtil jwtUtil;
    private final UserService userService;

    //Read
    //  http://localhost:8080/api/user/account
    @GetMapping
    public AppUserDisplay getProfile(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        UUID id = jwtUtil.extractPublicUserId(token);
        return userService.getUserDisplayById(id);
    }

    //Update
    //  http://localhost:8080/api/user/account
    @PutMapping
    public Map<String, Object> updateProfile(@RequestHeader("Authorization") String authHeader,
                                             @RequestParam String username,
                                             @RequestParam String email,
                                             @RequestParam String password) {
        String token = authHeader.replace("Bearer ", "");
        UUID id = jwtUtil.extractPublicUserId(token);
        AppUser user = userService.updateUser(id,username,email,password);
        return new ResponseMap.Builder()
                .status("Account Updated Successfully")
                .timestamp()
                .message("Profile updated")
                .add("User",user)
                .build().getResponse();
    }
    //Delete
    //  http://localhost:8080/api/user/account
    @DeleteMapping
    public Map<String, Object> deleteProfile(@RequestHeader("Authorization") String authHeader,
                                             @RequestParam String email,
                                             @RequestParam String password){
        String token = authHeader.replace("Bearer ", "");
        UUID id = jwtUtil.extractPublicUserId(token);
        String username = userService.deleteAppUserByEmailWithPasswordVerification(id,email,password);
        return new ResponseMap.Builder()
                .status("Account Has Been Deleted Successfully")
                .timestamp()
                .message("Goodbye "+username+" ......")
                .build().getResponse();
    }
}