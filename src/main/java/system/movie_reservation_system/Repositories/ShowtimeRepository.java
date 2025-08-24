package system.movie_reservation_system.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import system.movie_reservation_system.Entities.MovieEntity.Movie;
import system.movie_reservation_system.Entities.ShowTimes.Showtime;

import java.util.List;

public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {
    boolean existsByStartTimeAndHallNumberAndDate(String startTime,int hallNumber,String date);
    List<Showtime> getShowtimesByDate(String date);
    List<Showtime> getShowtimesByDateAndHallNumber(String date,int hallId);
    List<Showtime> findByMovieAndDateGreaterThanEqual(Movie movie, String date);

}
