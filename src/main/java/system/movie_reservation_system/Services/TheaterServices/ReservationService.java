package system.movie_reservation_system.Services.TheaterServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import system.movie_reservation_system.Entities.AppUserEntity.AppUser;
import system.movie_reservation_system.Entities.MovieEntity.Movie;
import system.movie_reservation_system.Entities.Reservations.Reservation;
import system.movie_reservation_system.Entities.Reservations.ReservationStatus;
import system.movie_reservation_system.Entities.ShowTimes.Showtime;
import system.movie_reservation_system.Exception.ResourceNotFoundException;
import system.movie_reservation_system.Repositories.ReservationRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ShowtimeService showtimeService;
    public Reservation saveReservation(AppUser user, long showtimeId , int ticketCount){
        Showtime showtime = showtimeService.getShowTimeById(showtimeId);
        if (showtime.getOccupiedCapacity()+ticketCount > showtime.getCapacity()) {
            throw new ResourceNotFoundException("NO VALID SEATS FOR THIS TICKET COUNT");
        }
        showtime.setOccupiedCapacity(showtime.getOccupiedCapacity()+ticketCount);
        Reservation reservation = new Reservation();
        reservation.setShowtime(showtime);
        reservation.setUser(user);
        reservation.setStatus(ReservationStatus.ACTIVE);
        reservation.setCreatedAt(LocalDateTime.now());
        return reservationRepository.save(reservation);
    }
}
