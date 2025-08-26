package system.movie_reservation_system.Services.AdminServices;


import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import system.movie_reservation_system.Entities.AppUserEntity.AppUser;
import system.movie_reservation_system.Entities.AppUserEntity.AppUserRole;
import system.movie_reservation_system.Exception.ResourceNotFoundException;
import system.movie_reservation_system.Repositories.UserRepository;
import system.movie_reservation_system.Services.UserServices.DatabaseService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;


    public List<AppUser> getUsers(){
        return userRepository.findByRole(AppUserRole.ROLE_USER);

    }
    public void deleteUser(long userId){
        userRepository.deleteById(userId);
    }

    public AppUser getUser(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User With Id :"+userId+" IS Not Found"));
    }
}
