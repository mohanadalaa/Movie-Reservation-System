package system.movie_reservation_system.Entities.ShowTimes;

public record ShowtimeDTO(
        String title,
        String date,
        String startTime,
        String ticketPrice,
        String capacity,
        String hall
) {}