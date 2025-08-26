package system.movie_reservation_system.Controllers.Theater;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import system.movie_reservation_system.Entities.ShowTimes.Showtime;
import system.movie_reservation_system.Services.TheaterServices.ShowtimeService;

import java.util.List;
@RestController
@RequestMapping("/api/user/showimes")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN', 'ROLE_DEV')")
public class UserShowtimeController {
    private final ShowtimeService showtimeService;

    @GetMapping("/{date}")
    public List<Showtime> getShowTimesByDate(@PathVariable String date){
        return showtimeService.getShowTimesByDate(date);
    }
    @GetMapping("/{movie_title}")
    public List<Showtime> getShowtimeByMovieTitle(@PathVariable String movie_title) {
        return showtimeService.getShowTimesByMovieTitle(movie_title);
    }
    @GetMapping("/{movie_title}/{date}")
    public List<Showtime> getShowtimeByMovieTitleAtDate(
            @PathVariable String movie_title,
            @PathVariable String date
    ) {
        return showtimeService.getShowTimesByMovieTitleAtSpecificDate(movie_title,date);
    }

}
