package system.movie_reservation_system.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import system.movie_reservation_system.Entity.AppUser;
import system.movie_reservation_system.Services.AdminService;

import system.movie_reservation_system.Security.JwtUtil;

import java.util.List;
//Admins Only Controller
@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DEV')")
@RequiredArgsConstructor
public class AdminController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final AdminService adminService;

    // GET http://localhost:8080/api/admin/users
    @GetMapping("/users")
    public List<AppUser> getAllUsers() {
        return adminService.getUsers();
    }

    // POST http://localhost:8080/api/admin/users
    @PostMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public void createUser(@RequestParam String username,@RequestParam String email, @RequestParam String password ) {
        adminService.createUser(username,email,password);
    }

}
