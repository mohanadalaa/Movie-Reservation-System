package system.movie_reservation_system.Services.TheaterServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import system.movie_reservation_system.Entities.AppUserEntity.AppUser;
import system.movie_reservation_system.Entities.MovieEntity.Movie;
import system.movie_reservation_system.Entities.Reservations.Reservation;
import system.movie_reservation_system.Entities.Reservations.ReservationStatus;
import system.movie_reservation_system.Repositories.ReservationRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final MovieService movieService;
    public Reservation saveReservation(AppUser user,String movieTitle,String party , String seat){
        Movie movie = movieService.findMovieByTitle(movieTitle);

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setStatus(ReservationStatus.ACTIVE);
        reservation.setCreatedAt(LocalDateTime.now());
        return reservationRepository.save(reservation);
    }
}
