package system.movie_reservation_system.Controllers.Admins;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import system.movie_reservation_system.Entities.AppUserEntity.AppUser;
import system.movie_reservation_system.Exception.ResourceNotFoundException;
import system.movie_reservation_system.Security.JwtUtil;
import system.movie_reservation_system.Services.AdminServices.AdminService;

import java.util.List;
//Admins Only Controller
@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DEV')")
@RequiredArgsConstructor
public class AdminController {
    private final JwtUtil jwtUtil;
    private final AdminService adminService;

    // GET http://localhost:8080/api/admin/users
    @GetMapping("/users")
    public List<AppUser> getAllUsers() {
        return adminService.getUsers();
    }

    // POST http://localhost:8080/api/admin/user/create
    @PostMapping("/user/create")
    public void createUser(@RequestParam String username,@RequestParam String email, @RequestParam String password ) {
        adminService.createUser(username,email,password);
    }

    // POST http://localhost:8080/api/admin/user/delete
    @PostMapping("/user/delete")
    public void deleteUser(@RequestParam String username ,@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String checkUsername = jwtUtil.extractUsername(token);
        if (username.equals(checkUsername)){
            throw new ResourceNotFoundException("Admin Can Only Delete Himself From Profile");
        }
        adminService.deleteUser(username);
    }

}
