package system.movie_reservation_system.Controllers.Theater;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import system.movie_reservation_system.Entities.ShowTimes.Seat;
import system.movie_reservation_system.Services.TheaterServices.SeatService;
import system.movie_reservation_system.Services.TheaterServices.ShowtimeService;

import java.util.List;

@RestController
@RequestMapping("/api/user/showtimes/{showtime_id}/seats")
@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN', 'ROLE_DEV')")
@RequiredArgsConstructor
public class UserSeatController {
    private final ShowtimeService showtimeService;
    private final SeatService seatService;

    @GetMapping
    public List<Seat> getAvailableSeatsForShowtime(@PathVariable long showtime_id){
        System.out.println("=== CONTROLLER DEBUG ===");
        System.out.println("Controller called with ID: " + showtime_id);

        List<Seat> result = seatService.getAvailableSeatsForShowTime(showtime_id);
        System.out.println("Controller got: " + result.size() + " seats");

        return result;
    }

}
