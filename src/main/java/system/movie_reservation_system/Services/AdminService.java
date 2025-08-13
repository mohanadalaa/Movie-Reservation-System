package system.movie_reservation_system.Services;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import system.movie_reservation_system.Entity.AppUser;
import system.movie_reservation_system.Entity.AppUserRole;
import system.movie_reservation_system.Repositories.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<AppUser> getUsers(){
        return userRepository.findByRole(AppUserRole.ROLE_USER);

    }

    public void createUser(String username, String email, String password) {
       //TODO implement a force user to the database with minimum validations
    }
}
