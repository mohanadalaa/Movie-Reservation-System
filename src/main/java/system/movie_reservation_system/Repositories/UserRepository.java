package system.movie_reservation_system.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import system.movie_reservation_system.Entity.AppUser;
import system.movie_reservation_system.Entity.AppUserRole;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);
    Optional<AppUser> findByEmail(String email);
    List<AppUser> findByRole(AppUserRole role);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    AppUser getAppUserByUsername(String username);
}