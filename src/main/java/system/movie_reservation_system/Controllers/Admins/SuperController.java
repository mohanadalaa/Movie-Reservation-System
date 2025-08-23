package system.movie_reservation_system.Controllers.Admins;


import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import system.movie_reservation_system.Entities.AppUserEntity.AppUser;
import system.movie_reservation_system.Security.JwtUtil;
import system.movie_reservation_system.Services.AdminServices.DevService;

import java.util.List;

//Admins Only Controller
@RestController
@RequestMapping("/api/dev")
@PreAuthorize("hasRole('ROLE_DEV')")
@RequiredArgsConstructor
public class SuperController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final DevService devService;

    // POST  http://localhost:8080/api/dev/user/promote
    @PostMapping("/user/promote")
    public void promoteUserToAdmin(@RequestParam String username){
        devService.promoteToAdmin(username);
    }
    // POST  http://localhost:8080/api/dev/user/delete
    @PostMapping("/user/delete")
    public void deleteUserOrAdmin(@RequestParam String username){
        devService.deleteUserOrAdmin(username);
    }

    // GET  http://localhost:8080/api/dev/admins
    @GetMapping("/admins")
    public List<AppUser> getAdmins(){
        return devService.getAdmins();
    }

    // GET  http://localhost:8080/api/dev/db
    @GetMapping("/db")
    public List<AppUser> getAll(){
        return devService.getAllDb();
    }


}
