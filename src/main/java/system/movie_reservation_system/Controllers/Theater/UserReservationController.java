package system.movie_reservation_system.Controllers.Theater;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import system.movie_reservation_system.Entities.Reservations.Reservation;
import system.movie_reservation_system.Entities.Reservations.ReservationDTO;
import system.movie_reservation_system.Entities.ShowTimes.Seat;

import system.movie_reservation_system.Exception.ResponseMap;
import system.movie_reservation_system.Security.JwtUtil;
import system.movie_reservation_system.Services.TheaterServices.ReservationService;


import java.util.*;

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
    public ReservationResponse getReservationDetails(
            @PathVariable long reservationId,
            @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.replace("Bearer ", "");
        UUID userId = jwtUtil.extractPublicUserId(token);
        Reservation reservation = service.getReservationByIdAndUser(reservationId, userId);
        return new ReservationResponse(reservation);
    }
    @GetMapping
    public List<ReservationResponse> getUserReservations(
            @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.replace("Bearer ", "");
        UUID userId = jwtUtil.extractPublicUserId(token);

        return service.getReservationsByUser(userId).stream()
                .map(ReservationResponse::new)
                .toList();
    }

    @DeleteMapping("/{reservationId}")
    public  Map<String,Object> deleteUserReservation(@RequestHeader("Authorization") String authHeader ,
                                        @PathVariable long reservationId) {
        String token = authHeader.replace("Bearer ", "");
        UUID userId = jwtUtil.extractPublicUserId(token);
        Reservation reservation =  service.deleteReservationForUser(userId,reservationId);
        return new ResponseMap.Builder()
                .status("Reservation Has Been Deleted Successfully")
                .timestamp()
                .add("Reservation: ",reservation)
                .build().getResponse();
    }
    //  Inner DTO class
    @Getter
    static class ReservationResponse {
        private final long reservationId;
        private final String createdAt;
        private final int numberOfTickets;
        private final double totalPrice;
        private final String status;
        private final String showTimeMovie;
        private final String showTimeDate;
        private final String startsAt;
        private final String showTimeStatus;
        private final List<Seat> seats;

        ReservationResponse(Reservation reservation) {
            this.reservationId = reservation.getId();
            this.createdAt = reservation.getCreatedAt().toString();
            this.numberOfTickets = reservation.getNumberOfTickets();
            this.totalPrice = reservation.getTotalPrice();
            this.status = reservation.getStatus().toString();
            this.showTimeMovie = reservation.getShowtime().getMovie().getTitle(); // adjust if object
            this.showTimeDate = reservation.getShowtime().getDate();
            this.startsAt = reservation.getShowtime().getStartTime();
            this.showTimeStatus = reservation.getShowtime().getShowtimeStatus().toString();
            this.seats = reservation.getSeats();
        }

    }
}
