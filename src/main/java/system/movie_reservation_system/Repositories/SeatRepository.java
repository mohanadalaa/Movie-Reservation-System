package system.movie_reservation_system.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import system.movie_reservation_system.Entities.ShowTimes.Seat;

import java.util.List;


@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> getSeatsByShowtime_ShowtimeIdAndIsReservedFalse(Long showtimeId);
}
