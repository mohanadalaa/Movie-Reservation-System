package system.movie_reservation_system.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import system.movie_reservation_system.Entity.AppUser;
import system.movie_reservation_system.Entity.AppUserRole;
import system.movie_reservation_system.Repositories.UserRepository;
import system.movie_reservation_system.Exception.ResourceNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // used by spring security when authenticating username/password
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public AppUser getAppUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public AppUser getAppUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    //For The SignIN
    public void createUser(String username, String email, String rawPassword) {
        if (userRepository.existsByUsername(username)) {
            throw new ResourceNotFoundException("Username already taken");
        }
        if (userRepository.existsByEmail(email)) {
            throw new ResourceNotFoundException("Email already registered");
        }
        if (!email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new ResourceNotFoundException("Invalid email format");
        }
        if (rawPassword.length() < 3) {
            throw new ResourceNotFoundException("Password must be at least 8 characters");
        }
        AppUser user = new AppUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole(AppUserRole.ROLE_USER);
        userRepository.save(user);
    }

}