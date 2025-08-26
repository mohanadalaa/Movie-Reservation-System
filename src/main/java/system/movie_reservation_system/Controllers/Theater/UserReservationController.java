package system.movie_reservation_system.Controllers.Theater;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import system.movie_reservation_system.Entities.Reservations.Reservation;
import system.movie_reservation_system.Security.JwtUtil;
import system.movie_reservation_system.Services.TheaterServices.ReservationService;


import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user/reservation")
@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN', 'ROLE_DEV')")
@RequiredArgsConstructor
public class UserReservationController {
    private final ReservationService service;
    private final JwtUtil jwtUtil;


    //TODO edit this function make it via seats
    @PostMapping("/confirm")
    public Reservation confirmReservation(@RequestHeader("Authorization") String authHeader,
                            @RequestParam long showtimeId,
                            @RequestParam int ticketCount
    ) {
        String token = authHeader.replace("Bearer ", "");
        UUID userId = jwtUtil.extractPublicUserId(token);
        return service.saveReservation(userId,showtimeId,ticketCount);
    }

    @GetMapping("/{reservationId}")
    public Reservation getReservationDetails(
            @PathVariable long reservationId,
            @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.replace("Bearer ", "");
        UUID userId = jwtUtil.extractPublicUserId(token);
        return service.getReservationByIdAndUser(reservationId, userId);
    }

    @GetMapping("/reservations")
    public List<Reservation> getUserReservations(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        UUID userId = jwtUtil.extractPublicUserId(token);
        return service.getReservationsByUser(userId);
    }

    @DeleteMapping("/{id}")
    public String deleteUserReservation(@PathVariable long id) {
        return service.deleteReservationForUser(id);
    }
}
