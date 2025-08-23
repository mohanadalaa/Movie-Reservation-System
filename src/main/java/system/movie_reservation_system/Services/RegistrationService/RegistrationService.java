package system.movie_reservation_system.Services.RegistrationService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.movie_reservation_system.Entities.AppUserEntity.AppUser;
import system.movie_reservation_system.Entities.AppUserEntity.AppUserRole;
import system.movie_reservation_system.Exception.ResourceNotFoundException;
import system.movie_reservation_system.Repositories.UserRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor

public class RegistrationService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // POST  http://localhost:8080/api/login
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    // POST http://localhost:8080/api/register
    @Transactional
    public AppUser createUser(String username, String email, String rawPassword) {
        if (userRepository.existsByUsername(username)) {
            throw new ResourceNotFoundException("Username Is Already Taken");
        }
        if (userRepository.existsByEmail(email)) {
            throw new ResourceNotFoundException("Email IS Already Registered");
        }
        if (!email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new ResourceNotFoundException("Invalid Email Format");
        }
        if (rawPassword.length() < 3) {
            throw new ResourceNotFoundException("Password Must Be At Least 8 Characters");
        }
        AppUser user = new AppUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole(AppUserRole.ROLE_USER);
        userRepository.save(user);
        return user;
    }
}
