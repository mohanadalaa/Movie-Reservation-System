package system.movie_reservation_system.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import system.movie_reservation_system.Entities.MovieEntity.Movie;
import system.movie_reservation_system.Entities.ShowTimes.Showtime;


import java.util.List;
import java.util.Optional;


public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {
    boolean existsByHallNumberAndDateAndStartTimeLessThanAndEndTimeGreaterThan(
            int hallNumber,
            String date,
            String newEndTime,
            String newStartTime
    );

    List<Showtime> getShowtimesByDate(String date);
    List<Showtime> getShowtimesByDateAndHallNumber(String date,int hallId);
    List<Showtime> findByMovieAndDateGreaterThanEqual(Movie movie, String date);
    List<Showtime> getShowtimesByMovieAndDate(Movie movie, String date);

    List<Showtime> findByDateLessThan(String date);
    List<Showtime> findByDateAndEndTimeLessThan(String date, String endTime);

    List<Showtime> findByDateAndStartTimeLessThanEqualAndEndTimeGreaterThan(
            String date,
            String currentTime,
            String currentTime2
    );

}
