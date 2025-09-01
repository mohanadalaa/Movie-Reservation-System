package system.movie_reservation_system.Controllers.Theater;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import system.movie_reservation_system.Entities.Reservations.Reservation;
import system.movie_reservation_system.Entities.Reservations.ReservationDTO;
import system.movie_reservation_system.Entities.ShowTimes.Seat;
import system.movie_reservation_system.Security.JwtUtil;
import system.movie_reservation_system.Services.TheaterServices.ReservationService;


import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user/reservations")
@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN', 'ROLE_DEV')")
@RequiredArgsConstructor
public class UserReservationController {
    private final ReservationService service;
    private final JwtUtil jwtUtil;


    //Create
    //
    //TODO edit this function make it via seats
    @PostMapping("/confirm")
    public Reservation confirmReservation(@RequestHeader("Authorization") String authHeader,
                                          @RequestBody ReservationDTO reservationDTO
                                          ) {
        String token = authHeader.replace("Bearer ", "");
        UUID userId = jwtUtil.extractPublicUserId(token);
        return service.saveReservation(userId, reservationDTO.showtimeId(), reservationDTO.seats());
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

    @GetMapping
    public List<Reservation> getUserReservations(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        UUID userId = jwtUtil.extractPublicUserId(token);
        return service.getReservationsByUser(userId);
    }

    @DeleteMapping("/{reservationId}")
    public String deleteUserReservation(@PathVariable long reservationId) {
        return service.deleteReservationForUser(reservationId);
    }
}
