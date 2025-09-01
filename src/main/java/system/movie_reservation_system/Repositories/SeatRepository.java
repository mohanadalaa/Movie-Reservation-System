package system.movie_reservation_system.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import system.movie_reservation_system.Entities.ShowTimes.Seat;
import system.movie_reservation_system.Entities.ShowTimes.Showtime;
import org.springframework.data.repository.query.Param;
import java.util.List;


@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    // Method 1: Get all seats by showtime ID
    List<Seat> findByShowtime_ShowtimeId(Long showtimeId);

    // Method 2: Get available seats by showtime ID
    List<Seat> findByShowtime_ShowtimeIdAndReservedFalse(Long showtimeId);

    // Method 3: If you really want to pass Showtime object, use @Query
    @Query("SELECT s FROM Seat s WHERE s.showtime = :showtime")
    List<Seat> findByShowtime(@Param("showtime") Showtime showtime);

    // Method 4: Get available seats using Showtime object
    @Query("SELECT s FROM Seat s WHERE s.showtime = :showtime AND s.reserved = false")
    List<Seat> findAvailableSeatsByShowtime(@Param("showtime") Showtime showtime);
}