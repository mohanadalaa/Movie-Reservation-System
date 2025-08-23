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
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN', 'ROLE_DEV')")
public class AdminReservationController {
    private final ReservationService service;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @PostMapping("/confrim")
    public Reservation test(@RequestHeader("Authorization") String authHeader,
                            @RequestParam String title,
                            @RequestParam String party,
                            @RequestParam String seat
                            ) {
        String token = authHeader.replace("Bearer ", "");
        UUID id = jwtUtil.extractPublicUserId(token);
        AppUser user = userService.getAppUserById(id);

        return service.saveReservation(user,title,party,seat);
    }

    @GetMapping("/res")
    public List<Reservation> getUserReservations(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        UUID id = jwtUtil.extractPublicUserId(token);
        AppUser user = userService.getAppUserById(id);
        return user.getReservations();
    }

}
