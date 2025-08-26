package system.movie_reservation_system.Services.TheaterServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.movie_reservation_system.Entities.ShowTimes.Seat;
import system.movie_reservation_system.Entities.ShowTimes.Showtime;
import system.movie_reservation_system.Repositories.SeatRepository;

import java.util.ArrayList;
import java.util.List;

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

    public List<Seat> getAvailableSeatsForShowTime(long showtimeId){
        return seatRepository.getSeatsByShowtime_ShowtimeIdAndIsReservedFalse(showtimeId);
    }

}
