//package system.movie_reservation_system;
//
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//import system.movie_reservation_system.Entity.AppUser;
//import system.movie_reservation_system.Entity.AppUserRole;
//import system.movie_reservation_system.Repositories.UserRepository;
//
//@Component
//@RequiredArgsConstructor
//public class TestDataSeeder implements CommandLineRunner {
//
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    @Override
//    public void run(String... args) {
//
//            // Create Admins
//            createUser("admin1@cinema.com", "admin1", "Admin123!", AppUserRole.ROLE_ADMIN);
//            createUser("admin2@cinema.com", "admin2", "Admin456!", AppUserRole.ROLE_ADMIN);
//
//            // Create Regular Users
//            createUser("user1@cinema.com", "user1", "User123!", AppUserRole.ROLE_USER);
//            createUser("user2@cinema.com", "user2", "User456!", AppUserRole.ROLE_USER);
//
//    }
//
//    private void createUser(String email, String username, String password, AppUserRole role) {
//            AppUser user = new AppUser();
//            user.setEmail(email);
//            user.setUsername(username);
//            user.setPassword(passwordEncoder.encode(password));
//            user.setRole(role);
//            userRepository.save(user);
//    }
//}