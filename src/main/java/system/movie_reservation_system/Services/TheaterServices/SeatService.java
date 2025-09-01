package system.movie_reservation_system.Services.TheaterServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    // Method that takes Long ID (for your controller)
    public List<Seat> getAvailableSeatsForShowTime(Long showtimeId) {
        return seatRepository.findByShowtime_ShowtimeIdAndReservedFalse(showtimeId);
    }

    // Method that takes Showtime object (for your reservation service)
    public List<Seat> getAvailableSeatsForShowTime(Showtime showtime) {
        return seatRepository.findAvailableSeatsByShowtime(showtime);
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
            //TODO : Save the reservation before using it
          //  seat.setReservation(reservation);
            reservedSeats.add(seat);
        }
        reservation.setSeats(reservedSeats);
    }
}
