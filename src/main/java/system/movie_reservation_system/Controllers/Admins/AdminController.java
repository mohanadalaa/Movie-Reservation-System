package system.movie_reservation_system.Controllers.Admins;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import system.movie_reservation_system.Entities.AppUserEntity.AppUser;

import system.movie_reservation_system.Exception.ResponseMap;
import system.movie_reservation_system.Services.AdminServices.AdminService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/users")
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DEV')")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    //  http://localhost:8080/api/admin/users
    @GetMapping
    public List<AppUser> getAllUsers() {
        return adminService.getUsers();
    }
    //Read
    //  http://localhost:8080/api/admin/users/{user_id}
    @GetMapping("/{user_id}")
    public AppUser getUser(@PathVariable UUID user_id) {
        return adminService.getUser(user_id);
    }

    //  http://localhost:8080/api/users/{username}
    @GetMapping("username/{username}")
    public AppUser getUserByUsername(@PathVariable String username){
        return adminService.getUserByUsername(username);
    }


    //Delete
    //  http://localhost:8080/api/admin/users/{user_id}
    @DeleteMapping("/{user_id}")
    public Map<String, Object> deleteUser(@PathVariable UUID user_id){
        adminService.deleteUser(user_id);
        return new ResponseMap.Builder()
                .status("Account Has Been Deleted Successfully")
                .timestamp()
                .build().getResponse();
    }

}
