package system.movie_reservation_system.Services.UserServices;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.movie_reservation_system.Entities.AppUserEntity.AppUser;
import system.movie_reservation_system.Entities.AppUserEntity.AppUserRole;
import system.movie_reservation_system.Repositories.UserRepository;
import system.movie_reservation_system.Exception.ResourceNotFoundException;


@Service
@RequiredArgsConstructor
public class UserService   {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final DatabaseService databaseService;

    public AppUser getAppUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User With Email "+email+" Not Found"));
    }
    @Transactional
    public void deleteAppUserByEmailWithPasswordVerification(String email,String password){
        AppUser user = getAppUserByEmail(email);
        if (user.getRole().equals(AppUserRole.ROLE_DEV)) {
            throw new ResourceNotFoundException("It Cant Happen Lil DEV");
        }
        // Verify password matches
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ResourceNotFoundException("Invalid Credentials - Deletion Failed");
        }
        userRepository.delete(user);
    }
}