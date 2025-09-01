package system.movie_reservation_system.Services.TheaterServices;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.movie_reservation_system.Entities.MovieEntity.Genre;
import system.movie_reservation_system.Entities.MovieEntity.Movie;
import system.movie_reservation_system.Entities.ShowTimes.Seat;
import system.movie_reservation_system.Entities.ShowTimes.Showtime;
import system.movie_reservation_system.Entities.ShowTimes.ShowtimeDTO;
import system.movie_reservation_system.Entities.ShowTimes.ShowtimeStatus;
import system.movie_reservation_system.Exception.ResourceNotFoundException;
import system.movie_reservation_system.Repositories.ShowtimeRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;



@Service
@RequiredArgsConstructor
public class ShowtimeService {
    private final ShowtimeRepository repository;
    private final MovieService movieService;
    private final SeatService seatService;


    private void nullValidations(String movieTitle, String date, String startTime, String ticketPrice, String hallCapacity, String hallNumber)
    {
        if (movieTitle==null || startTime==null || ticketPrice==null || hallCapacity==null || hallNumber==null || date ==null){
            throw new ResourceNotFoundException("Invalid Inputs For The Showtime");
        }
    }
    private void beforeCurrentTimeValidations(String date , String startTime){
        LocalDate showDate = LocalDate.parse(date);
        LocalTime showStartTime = LocalTime.parse(startTime);
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        if (showDate.isBefore(today)) {
            throw new ResourceNotFoundException("Showtime date cannot be before today.");
        }
        if (showDate.isEqual(today) && showStartTime.isBefore(now)) {
            throw new ResourceNotFoundException("Showtime start time cannot be before current time.");
        }
    }
    private void movieValidations(int capacity , long ticketPrice){
        if (capacity<=0 ||  ticketPrice<=0){
            throw new ResourceNotFoundException("Invalid Inputs For The Showtime");
        }
    }
    private void hallTimeValidations(int hallId,String date , String showEndTime,String startTime){
        boolean conflict = repository
                .existsByHallNumberAndDateAndStartTimeLessThanAndEndTimeGreaterThan(
                        hallId,
                        date,
                        showEndTime,
                        startTime
                );

        if (conflict) {
            throw new ResourceNotFoundException("Hall is already booked for this time slot");
        }
    }
    @Transactional
    public Showtime createShowtime(String movieTitle,
                                   String date,
                                   String startTime,
                                   String ticketPrice,
                                   String hallCapacity,
                                   String hallNumber){

        nullValidations(movieTitle,date,startTime,ticketPrice,hallCapacity,hallNumber);
        beforeCurrentTimeValidations(date,startTime);

        int capacity =Integer.parseInt(hallCapacity);
        long ticket = Long.parseLong(ticketPrice);
        int hallId= Integer.parseInt(hallNumber);
        Movie movie = movieService.findMovieByTitle(movieTitle);
        movieValidations(capacity,ticket);

        LocalTime showStartTime = LocalTime.parse(startTime);
        LocalTime showEndTime = showStartTime.plusMinutes(movie.getDurationMinutes());

        hallTimeValidations(hallId,date,showEndTime.toString(),startTime);

        Showtime showtime = new Showtime();
        showtime.setMovie(movie);
        showtime.setCapacity(capacity);
        showtime.setHallNumber(hallId);
        showtime.setTicketPrice(ticket);
        showtime.setDate(date);
        showtime.setStartTime(startTime);
        showtime.setShowtimeStatus(ShowtimeStatus.UPCOMING);
        showtime.setEndTime(showEndTime.toString());
        showtime.setOccupiedCapacity(0);

        repository.save(showtime);

        List<Seat> seats = seatService.generateSeatsForShowtime(showtime, showtime.getCapacity());
        showtime.setSeats(seats);

        return repository.save(showtime);
    }
    @Scheduled(fixedRate = 3600000, initialDelay = 1000)
    @Transactional
    public void updateExpiredShowTimes() {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        String todayStr = today.toString();
        String nowStr = now.toString();

        // Update to ONGOING
        List<Showtime> shouldBeOngoing = repository.findByDateAndStartTimeLessThanEqualAndEndTimeGreaterThan(
                todayStr, nowStr, nowStr);
        for (Showtime showtime : shouldBeOngoing) {
            if (showtime.getShowtimeStatus() != ShowtimeStatus.ONGOING) {
                showtime.setShowtimeStatus(ShowtimeStatus.ONGOING);
                repository.save(showtime);
            }
        }

        // Update expired showtimes to COMPLETED
        List<Showtime> expiredByDate = repository.findByDateLessThan(todayStr);
        List<Showtime> expiredToday = repository.findByDateAndEndTimeLessThan(todayStr, nowStr);

        List<Showtime> allExpired = new ArrayList<>();
        allExpired.addAll(expiredByDate);
        allExpired.addAll(expiredToday);

        for (Showtime showtime : allExpired) {
            if (showtime.getShowtimeStatus() != ShowtimeStatus.COMPLETED) {
                showtime.setShowtimeStatus(ShowtimeStatus.COMPLETED);
                repository.save(showtime);
            }
        }
    }

    @Transactional
    public Showtime saveShowtime(Showtime showtime)
    {
        return repository.save(showtime);
    }
    public Showtime deleteShowTime(Long showTimeId){
        Showtime showtime = getShowTimeById(showTimeId);
        repository.delete(showtime);
        return showtime;
    }
    public Showtime getShowTimeById(Long id){
        return repository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("ShowTime Does Not Exists By Id: "+id));
    }
    public List<Showtime> getShowTimes() {
        return repository.findAll();
    }
    public List<Showtime> getShowTimesByDate(String date) {
        return repository.getShowtimesByDate(date);
    }
    public List<Showtime> getShowTimesByDateAndHallId(String date, int hallId) {
        return repository.getShowtimesByDateAndHallNumber(date,hallId);
    }
    public List<Showtime> getShowTimesByMovieTitle(String movieTitle) {
        Movie movie = movieService.findMovieByTitle(movieTitle);
        LocalDate today = LocalDate.now();
        String formattedDate = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return repository.findByMovieAndDateGreaterThanEqual(movie,formattedDate);
    }
    public List<Showtime> getShowTimesByMovieTitleAtSpecificDate(String movieTitle, String date) {
        Movie movie = movieService.findMovieByTitle(movieTitle);
        return repository.getShowtimesByMovieAndDate(movie,date);
    }

}
