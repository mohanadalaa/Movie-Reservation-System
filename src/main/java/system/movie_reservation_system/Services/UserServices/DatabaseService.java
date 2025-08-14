package system.movie_reservation_system.Services.UserServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.movie_reservation_system.Entities.AppUserEntity.AppUser;
import system.movie_reservation_system.Entities.AppUserEntity.AppUserRole;
import system.movie_reservation_system.Exception.ResourceNotFoundException;
import system.movie_reservation_system.Repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class DatabaseService {
    private final UserRepository userRepository;

    public AppUser getAppUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User:"+username+" not found"));
    }

    @Transactional
    public void deleteAppUserByUsername(String username){
        AppUser user =userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User:"+username+" not found"));
        if (user.getRole().equals(AppUserRole.ROLE_ADMIN) || user.getRole().equals(AppUserRole.ROLE_DEV)) {
           throw  new ResourceNotFoundException("User Deletion Denied, user is an Admin!");
        }
         userRepository.delete(user);
    }

}
