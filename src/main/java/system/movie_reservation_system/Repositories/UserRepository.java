package system.movie_reservation_system.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import system.movie_reservation_system.Entities.AppUserEntity.AppUser;
import system.movie_reservation_system.Entities.AppUserEntity.AppUserDisplay;
import system.movie_reservation_system.Entities.AppUserEntity.AppUserRole;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsernameAndRole(String username,AppUserRole role);
    Optional<AppUser> findByEmail(String email);
    List<AppUser> findByRole(AppUserRole role);
    Optional<AppUser> findByPublicIdAndRole(UUID publicId,AppUserRole role);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<AppUser> findByPublicId(UUID id);
    Optional<AppUserDisplay> findProjectedByPublicId(UUID id);
    void deleteByPublicId(UUID id);
}