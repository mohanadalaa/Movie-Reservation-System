package system.movie_reservation_system.Services.AdminServices;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import system.movie_reservation_system.Entities.AppUserEntity.AppUser;
import system.movie_reservation_system.Entities.AppUserEntity.AppUserRole;
import system.movie_reservation_system.Repositories.UserRepository;
import system.movie_reservation_system.Services.UserServices.DatabaseService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final DatabaseService databaseService;

    public List<AppUser> getUsers(){
        return userRepository.findByRole(AppUserRole.ROLE_USER);

    }

    public void createUser(String username, String email, String password) {
       //TODO implement a force user to the database with minimum validations
    }
    public void deleteUser(String username){
        databaseService.deleteAppUserByUsername(username);
    }

    public AppUser getUserByUsername(String username){
       return databaseService.getAppUserByUsername(username);
    }
}
