package system.movie_reservation_system.Entities.AppUserEntity;

import system.movie_reservation_system.Entities.Reservations.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface AppUserDisplay {
    UUID getPublicId();
    String getUsername();
    String getEmail();
    AppUserRole getRole();
    List<Reservation> getReservations();
}
