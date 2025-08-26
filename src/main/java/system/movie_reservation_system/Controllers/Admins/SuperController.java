package system.movie_reservation_system.Controllers.Admins;


import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import system.movie_reservation_system.Entities.AppUserEntity.AppUser;
import system.movie_reservation_system.Exception.ResponseMap;
import system.movie_reservation_system.Services.AdminServices.DevService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dev/admin")
@PreAuthorize("hasRole('ROLE_DEV')")
@RequiredArgsConstructor
public class SuperController {

    private final DevService devService;

    //  http://localhost:8080/api/dev/admin/promote/{user_id}
    @PostMapping("/promote/{user_id}")
    public AppUser promoteUserToAdmin(@PathVariable long user_id){
        return devService.promoteToAdmin(user_id);
    }
    //  http://localhost:8080/api/dev/admin/demote/{user_id}
    @PostMapping("/demote/{user_id}")
    public AppUser demoteUserToAdmin(@PathVariable long user_id){
        return devService.demoteToUser(user_id);
    }
    //  http://localhost:8080/api/dev/admin/{user_id}
    @DeleteMapping("/{user_id}")
    public Map<String, Object> deleteUserOrAdmin(@PathVariable long user_id){
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


}
