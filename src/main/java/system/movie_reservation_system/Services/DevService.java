package system.movie_reservation_system.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import system.movie_reservation_system.Entity.AppUser;
import system.movie_reservation_system.Entity.AppUserRole;
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

    public void promoteToAdmin(String username) {
        AppUser appUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));

        if (appUser.getRole() == AppUserRole.ROLE_ADMIN) {
            throw new ResourceNotFoundException(username + " is already an admin");
        }

        appUser.setRole(AppUserRole.ROLE_ADMIN);
        userRepository.save(appUser);
    }

    public List<AppUser> getAllDb() {
        return userRepository.findAll();
    }
}
