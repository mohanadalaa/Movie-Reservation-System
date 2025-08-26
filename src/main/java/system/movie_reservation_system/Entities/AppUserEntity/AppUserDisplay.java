package system.movie_reservation_system.Entities.AppUserEntity;

import java.util.UUID;

public interface AppUserDisplay {
    UUID getPublicId();
    String getUsername();
    String getEmail();
    AppUserRole getRole();
}
