//package system.movie_reservation_system;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//import system.movie_reservation_system.Entity.AppUser;
//import system.movie_reservation_system.Entity.AppUserRole;
//import system.movie_reservation_system.Repositories.UserRepository;
//
//@Component
//public class AdminSeeder implements CommandLineRunner {
//
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    public AdminSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Override
//    public void run(String... args) {
//        if (userRepository.count() == 0) {
//            AppUser admin = new AppUser();
//            admin.setUsername("mohanad");
//            admin.setPassword(passwordEncoder.encode("123"));
//            admin.setRole(AppUserRole.ROLE_DEV);
//            admin.setEmail("mohanadalaa2004@gmail.com");
//            userRepository.save(admin);
//        }
//
//    }
//}