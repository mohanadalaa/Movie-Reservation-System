package system.movie_reservation_system.Services.TheaterServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.movie_reservation_system.Entities.AppUserEntity.AppUser;

import system.movie_reservation_system.Entities.Reservations.Reservation;
import system.movie_reservation_system.Entities.Reservations.ReservationStatus;
import system.movie_reservation_system.Entities.ShowTimes.Showtime;
import system.movie_reservation_system.Exception.ResourceNotFoundException;
import system.movie_reservation_system.Repositories.ReservationRepository;
import system.movie_reservation_system.Services.UserServices.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ShowtimeService showtimeService;
    private final UserService userService;

    @Transactional
    public Reservation saveReservation(UUID userId, long showtimeId , int ticketCount){
        AppUser user = userService.getAppUserById(userId);
        Showtime showtime = showtimeService.getShowTimeById(showtimeId);

        int newOccupiedCapacity = showtime.getOccupiedCapacity()+ticketCount;
        if ( newOccupiedCapacity > showtime.getCapacity()) {
            throw new ResourceNotFoundException("NO VALID SEATS FOR THIS TICKET COUNT");
        }
        if (reservationRepository.existsByUserAndShowtime(user,showtime)){ //reservation already exists just edit it lil bro
            return editReservation(user,showtime,ticketCount);
        }
        showtime.setOccupiedCapacity(newOccupiedCapacity);
        Reservation reservation = new Reservation();
        reservation.setShowtime(showtime);
        reservation.setUser(user);
        reservation.setStatus(ReservationStatus.ACTIVE);
        reservation.setCreatedAt(LocalDateTime.now());
        reservation.setNumberOfTickets(ticketCount);
        reservation.setTotalPrice(ticketCount*showtime.getTicketPrice());
        return reservationRepository.save(reservation);
    }

    @Transactional
    private Reservation editReservation(AppUser user, Showtime showtime, int ticketCount) {
        Reservation reservation = reservationRepository.findByUserAndShowtime(user,showtime);
        int newOccupiedCapacity = showtime.getOccupiedCapacity()+ticketCount;
        showtime.setOccupiedCapacity(newOccupiedCapacity);
        int totalTicketCount = reservation.getNumberOfTickets()+ticketCount;
        reservation.setNumberOfTickets(totalTicketCount);
        reservation.setTotalPrice(totalTicketCount*showtime.getTicketPrice());

        return reservation;
    }

    @Transactional
    public List<Reservation> getReservationsByUser(UUID userId) {
        return reservationRepository.findByUser_PublicId(userId);
    }

    @Transactional
    public String deleteReservationForUser(long id) {
        Reservation reservation = reservationRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Reservation Not Found"));
        Showtime showtime = reservation.getShowtime();
        showtime.setOccupiedCapacity( showtime.getOccupiedCapacity() - reservation.getNumberOfTickets());
        showtimeService.saveShowtime(showtime);
        reservationRepository.deleteById(id);
        return "DELETED SUCCESSFULLY!!";
    }

    public Reservation getReservationByIdAndUser(long reservationId, UUID userId) {
        return  reservationRepository.findByUser_PublicIdAndId(userId,reservationId).
                orElseThrow(() -> new ResourceNotFoundException("Reservation Not Found"));
    }
}
