package system.movie_reservation_system.Services.UserServices;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.movie_reservation_system.Entities.AppUserEntity.AppUser;
import system.movie_reservation_system.Entities.AppUserEntity.AppUserDisplay;
import system.movie_reservation_system.Entities.AppUserEntity.AppUserRole;
import system.movie_reservation_system.Repositories.UserRepository;
import system.movie_reservation_system.Exception.ResourceNotFoundException;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserService   {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public AppUser getAppUserByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User With Email "+email+" Not Found"));
    }
    @Transactional
    public AppUser getAppUserById(UUID id){
        return userRepository.findByPublicId(id)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
    }

    public AppUserDisplay getUserDisplayById(UUID id ){
        return userRepository.findProjectedByPublicId(id)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
    }

    @Transactional
    public String deleteAppUserByEmailWithPasswordVerification(UUID id, String email, String password){
        AppUser user = getAppUserById(id);
        if (!user.getEmail().equals(email)){
            throw new ResourceNotFoundException("Invalid Credentials - Deletion Failed");
        }
        if (user.getRole().equals(AppUserRole.ROLE_DEV) ||
                user.getRole().equals(AppUserRole.ROLE_ADMIN)) {
            throw new ResourceNotFoundException("Invalid Deletion Admin Can Only Be Deleted By Dev");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ResourceNotFoundException("Invalid Credentials - Deletion Failed");
        }
        userRepository.delete(user);
        return user.getUsername();
    }

    @Transactional
    public AppUser updateUser(UUID id, String username, String email, String password) {
            AppUser user = getAppUserById(id);

        if (userRepository.existsByUsername(username)) {
            throw new ResourceNotFoundException("Username Is Already Taken");
        }
        if (userRepository.existsByEmail(email)) {
            throw new ResourceNotFoundException("Email IS Already Registered");
        }

        if (!username.isBlank()){
            user.setUsername(username);
        }
        if (!email.isBlank()){
            if (!email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                throw new ResourceNotFoundException("Invalid Email Format");
            }
            user.setEmail(email);
        }
        if (!password.isBlank()){
            if (password.length() < 3) {
                throw new ResourceNotFoundException("Password Must Be At Least 8 Characters");
            }
            user.setPassword(passwordEncoder.encode(password));
        }
        userRepository.save(user);
        return user;
    }
}