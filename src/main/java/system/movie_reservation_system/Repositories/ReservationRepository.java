package system.movie_reservation_system.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import system.movie_reservation_system.Entities.Reservations.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
