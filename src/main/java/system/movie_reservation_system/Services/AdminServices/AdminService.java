package system.movie_reservation_system.Services.AdminServices;


import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import system.movie_reservation_system.Entities.AppUserEntity.AppUser;
import system.movie_reservation_system.Entities.AppUserEntity.AppUserRole;
import system.movie_reservation_system.Exception.ResourceNotFoundException;
import system.movie_reservation_system.Repositories.UserRepository;


import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;


    public List<AppUser> getUsers(){
        return userRepository.findByRole(AppUserRole.ROLE_USER);
    }
    public void deleteUser(UUID userId){
        userRepository.deleteByPublicId(userId);
    }

    public AppUser getUser(UUID publicUserId) {
        return userRepository.findByPublicIdAndRole(publicUserId,AppUserRole.ROLE_USER)
                .orElseThrow(() -> new ResourceNotFoundException("User With Id :"+publicUserId+" IS Not Found"));
    }

    public AppUser getUserByUsername(String username) {
        return userRepository.findByUsernameAndRole(username,AppUserRole.ROLE_USER)
                .orElseThrow(() -> new ResourceNotFoundException("User: "+username+" not found"));    }
}
