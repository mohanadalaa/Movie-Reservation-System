package system.movie_reservation_system.Controllers.Admins;


import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import system.movie_reservation_system.Entities.AppUserEntity.AppUser;
import system.movie_reservation_system.Exception.ResponseMap;
import system.movie_reservation_system.Services.AdminServices.DevService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/dev/admin")
@PreAuthorize("hasRole('ROLE_DEV')")
@RequiredArgsConstructor
public class SuperController {

    private final DevService devService;

    //  http://localhost:8080/api/dev/admin/promote/{user_id}
    @PostMapping("/promote/{user_id}")
    public AppUser promoteUserToAdmin(@PathVariable UUID user_id){
        return devService.promoteToAdmin(user_id);
    }
    //  http://localhost:8080/api/dev/admin/demote/{admin_id}
    @PostMapping("/demote/{admin_id}")
    public AppUser demoteAdminToUser(@PathVariable UUID admin_id){
        return devService.demoteToUser(admin_id);
    }
    //  http://localhost:8080/api/dev/admin/{user_id}
    @DeleteMapping("/{user_id}")
    public Map<String, Object> deleteUserOrAdmin(@PathVariable UUID user_id){
        devService.deleteUserOrAdmin(user_id);
        return new ResponseMap.Builder()
                .status("Admin Has Been Deleted Successfully")
                .timestamp()
                .build().getResponse();
    }
    //  http://localhost:8080/api/dev/admin
    @GetMapping
    public List<AppUser> getAdmins(){
        return devService.getAdmins();
    }

    //  http://localhost:8080/api/dev/admin/{admin_id}
    @GetMapping("/{admin_id}")
    public AppUser getAdmin(@PathVariable UUID admin_id){
        return devService.getAdmin(admin_id);
    }

    //  http://localhost:8080/api/dev/admin/{username}
    @GetMapping("username/{username}")
    public AppUser getAdminByUsername(@PathVariable String username){
        return devService.getAdminByUsername(username);
    }

}
