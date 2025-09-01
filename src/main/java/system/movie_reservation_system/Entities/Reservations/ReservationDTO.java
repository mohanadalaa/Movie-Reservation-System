package system.movie_reservation_system.Entities.Reservations;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import java.util.List;


public record ReservationDTO(
        @Positive(message = "Showtime ID must be positive")
        long showtimeId,
        @NotEmpty(message = "At least one seat must be selected")
        List<String> seats
) {}