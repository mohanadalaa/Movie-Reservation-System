package system.movie_reservation_system.Services.TheaterServices;


import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.movie_reservation_system.Entities.AppUserEntity.AppUser;

import system.movie_reservation_system.Entities.Reservations.Reservation;
import system.movie_reservation_system.Entities.Reservations.ReservationStatus;
import system.movie_reservation_system.Entities.ShowTimes.Seat;
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
    private final SeatService seatService;


    public Reservation saveReservation(UUID userId, long showtimeId , List<String> seats){
        AppUser user = userService.getAppUserById(userId);
        Showtime showtime = showtimeService.getShowTimeById(showtimeId);

        if (reservationRepository.existsByUserAndShowtime(user,showtime)){
            throw new ResourceNotFoundException("Reservation Already Exists If You Wish To Continue Please Edit It");
        }
        int ticketCount = seats.size();
        int newOccupiedCapacity = showtime.getOccupiedCapacity()+ticketCount;
        if ( newOccupiedCapacity > showtime.getCapacity()) {
            throw new ResourceNotFoundException("NO VALID SEATS FOR THIS TICKET COUNT");
        }
        seatService.validateSeat(showtime,seats);

        Reservation reservation = new Reservation();
        seatService.reserveSeats(user, reservation,showtime,seats);
        showtime.setOccupiedCapacity(newOccupiedCapacity);
        reservation.setShowtime(showtime);
        reservation.setUser(user);
        reservation.setStatus(ReservationStatus.ACTIVE);
        reservation.setCreatedAt(LocalDateTime.now());
        reservation.setNumberOfTickets(ticketCount);
        reservation.setTotalPrice(ticketCount*showtime.getTicketPrice());
        return reservationRepository.save(reservation);
    }

    @Transactional
    public Reservation editReservation(AppUser user, Showtime showtime, int ticketCount) {
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
    public Reservation deleteReservationForUser(UUID userId,long reservationId) {
        System.out.println("First");
        AppUser user = userService.getAppUserById(userId);
        Reservation reservation = reservationRepository.findById(reservationId).
                orElseThrow(() -> new ResourceNotFoundException("Reservation Not Found"));
        if (!(user.getReservations().contains(reservation))){
            throw new ResourceNotFoundException("Invalid Deletion");
        }

        Showtime showtime = reservation.getShowtime();
        seatService.freeTheSeats(reservation,showtime);
        showtime.getReservations().remove(reservation);
        reservation.setShowtime(null);
        reservation.setTotalPrice(0);
        reservation.setStatus(ReservationStatus.CANCELLED);
        showtime.setOccupiedCapacity( showtime.getOccupiedCapacity() - reservation.getNumberOfTickets());
        showtimeService.saveShowtime(showtime);
        reservationRepository.delete(reservation);
        return reservation;
    }

    public Reservation getReservationByIdAndUser(long reservationId, UUID userId) {
        return  reservationRepository.findByUser_PublicIdAndId(userId,reservationId).
                orElseThrow(() -> new ResourceNotFoundException("Reservation Not Found"));
    }
}
