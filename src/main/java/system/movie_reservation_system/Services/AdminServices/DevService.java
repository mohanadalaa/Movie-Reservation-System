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
    public AppUser promoteToAdmin(long userId) {
        AppUser appUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found With Id: " + userId));

        if (appUser.getRole() == AppUserRole.ROLE_ADMIN) {
            throw new ResourceNotFoundException(appUser.getUsername() + " Is Already An Admin");
        }
        appUser.setRole(AppUserRole.ROLE_ADMIN);
       return userRepository.save(appUser);
    }
    @Transactional
    public void deleteUserOrAdmin(long userId) {
        AppUser appUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found With ID: " + userId));
        if (appUser.getRole().equals(AppUserRole.ROLE_DEV)){
              throw new ResourceNotFoundException("Invalid Move Smart.....");
        }
        userRepository.delete(appUser);
    }

    public AppUser demoteToUser(long userId) {
        AppUser appUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found With Id: " + userId));

        if (appUser.getRole() == AppUserRole.ROLE_USER) {
            throw new ResourceNotFoundException(appUser.getUsername() + " Is Already A User");
        }
        appUser.setRole(AppUserRole.ROLE_USER);
        return userRepository.save(appUser);
    }
}
