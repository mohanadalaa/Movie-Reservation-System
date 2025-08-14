package system.movie_reservation_system.Services.AdminServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.movie_reservation_system.Entities.AppUserEntity.AppUser;
import system.movie_reservation_system.Entities.AppUserEntity.AppUserRole;
import system.movie_reservation_system.Exception.ResourceNotFoundException;
import system.movie_reservation_system.Repositories.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DevService {

    private final UserRepository userRepository;

    public List<AppUser> getAdmins(){
        return userRepository.findByRole(AppUserRole.ROLE_ADMIN);
    }

    @Transactional
    public void promoteToAdmin(String username) {
        AppUser appUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found With Username: " + username));

        if (appUser.getRole() == AppUserRole.ROLE_ADMIN) {
            throw new ResourceNotFoundException(username + " Is Already An Admin");
        }
        appUser.setRole(AppUserRole.ROLE_ADMIN);
        userRepository.save(appUser);
    }

    public List<AppUser> getAllDb() {
        return userRepository.findAll();
    }
}
