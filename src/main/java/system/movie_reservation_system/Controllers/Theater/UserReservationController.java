package system.movie_reservation_system.Controllers.Theater;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import system.movie_reservation_system.Entities.AppUserEntity.AppUser;
import system.movie_reservation_system.Entities.Reservations.Reservation;
import system.movie_reservation_system.Security.JwtUtil;
import system.movie_reservation_system.Services.TheaterServices.ReservationService;
import system.movie_reservation_system.Services.UserServices.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user/reservation")
@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN', 'ROLE_DEV')")
@RequiredArgsConstructor
public class UserReservationController {
    private final ReservationService service;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @PostMapping("/confirm")
    public Reservation confirmReservation(@RequestHeader("Authorization") String authHeader,
                            @RequestParam long showtimeId,
                            @RequestParam int ticketCount
    ) {
        String token = authHeader.replace("Bearer ", "");
        UUID id = jwtUtil.extractPublicUserId(token);
        AppUser user = userService.getAppUserById(id);
        return service.saveReservation(user,showtimeId,ticketCount);
    }

    @GetMapping("/res")
    public List<Reservation> getUserReservations(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        UUID id = jwtUtil.extractPublicUserId(token);
        AppUser user = userService.getAppUserById(id);
        return user.getReservations();
    }
}
