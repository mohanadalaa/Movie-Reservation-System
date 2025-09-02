package system.movie_reservation_system.Services.TheaterServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import system.movie_reservation_system.Entities.AppUserEntity.AppUser;
import system.movie_reservation_system.Entities.Reservations.Reservation;
import system.movie_reservation_system.Entities.ShowTimes.Seat;
import system.movie_reservation_system.Entities.ShowTimes.Showtime;
import system.movie_reservation_system.Exception.ResourceNotFoundException;
import system.movie_reservation_system.Repositories.SeatRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeatService {
    private final SeatRepository seatRepository;

    public List<Seat> generateSeatsForShowtime(Showtime showtime, int totalSeats) {

        List<Seat> seats = new ArrayList<>();

        int cols = totalSeats / 2; // split equally into A and B
        if (totalSeats % 2 != 0) {
            cols++; // if odd, row B will get one less
        }

        for (char row = 'A'; row <= 'B'; row++) {
            for (int col = 0; col < cols && seats.size() < totalSeats; col++) {
                Seat seat = new Seat();
                seat.setShowtime(showtime);
                seat.setSeatNumber(row + String.valueOf(col)); // e.g., A0, A1, B0...
                seat.setReserved(false);
                seats.add(seat);
            }
        }
        return seatRepository.saveAll(seats);
    }

    public List<Seat> getAvailableSeatsForShowTime(Long showtimeId) {
        return seatRepository.findByShowtime_ShowtimeIdAndReservedFalse(showtimeId);
    }

    public void validateSeat(Showtime showtime, List<String> seats) {
        List<Seat> showtimeSeats = showtime.getSeats();
        Map<String, Seat> seatMap = showtimeSeats.stream()
                .collect(Collectors.toMap(Seat::getSeatNumber, seat -> seat));
        for (String requestedSeatNumber : seats) {
            Seat seat = seatMap.get(requestedSeatNumber);
            if (seat == null) {
                throw new ResourceNotFoundException("Seat " + requestedSeatNumber + " does not exist in this showtime");
            }
            if (seat.isReserved()) {
                throw new ResourceNotFoundException("Seat " + requestedSeatNumber + " is taken in this showtime");
            }
        }
    }

    public void reserveSeats(AppUser user ,Reservation reservation, Showtime showtime, List<String> seats) {
        List<Seat> showtimeSeats = showtime.getSeats();
        Map<String, Seat> seatMap = showtimeSeats.stream()
                .collect(Collectors.toMap(Seat::getSeatNumber, seat -> seat));
        List<Seat> reservedSeats = new ArrayList<>();
        for (String requestedSeatNumber : seats) {
            Seat seat = seatMap.get(requestedSeatNumber);
            seat.setReserved(true);
            seat.setReservedByUserId(user.getPublicId());
            seat.setReservation(reservation);
            reservedSeats.add(seat);
        }
        reservation.setSeats(reservedSeats);
    }

    @Transactional
    public void freeTheSeats(Reservation reservation, Showtime showtime) {
        List<Seat> showtimeSeats = showtime.getSeats();

        Map<String, Seat> seatMap = showtimeSeats.stream()
                .collect(Collectors.toMap(Seat::getSeatNumber, seat -> seat));

        for (Seat reservedSeat : reservation.getSeats()) {
            Seat seat = seatMap.get(reservedSeat.getSeatNumber());
            if (seat != null) { // safety check
                seat.setReserved(false);
                seat.setReservedByUserId(null);
                seat.setReservation(null);
            }
        }
        reservation.setSeats(null);
    }

}
