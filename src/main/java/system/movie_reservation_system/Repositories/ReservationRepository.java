package system.movie_reservation_system.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import system.movie_reservation_system.Entities.AppUserEntity.AppUser;
import system.movie_reservation_system.Entities.Reservations.Reservation;
import system.movie_reservation_system.Entities.ShowTimes.Showtime;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUser_PublicId(UUID userId);
    boolean existsByUserAndShowtime(AppUser user, Showtime showtime);
    Reservation findByUserAndShowtime(AppUser user , Showtime showtime);
    Optional<Reservation> findByUser_PublicIdAndId(UUID userId , long reservationId);
}
