package system.movie_reservation_system.Services.TheaterServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import system.movie_reservation_system.Entities.MovieEntity.Movie;
import system.movie_reservation_system.Entities.ShowTimes.Showtime;
import system.movie_reservation_system.Exception.ResourceNotFoundException;
import system.movie_reservation_system.Repositories.ShowtimeRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ShowtimeService {
    private final ShowtimeRepository repository;
    private final MovieService movieService;


    public boolean hallNotEmpty(String dateTime,int hallNumber, String date){
        return repository.existsByStartTimeAndHallNumberAndDate(dateTime,hallNumber,date);
    }

    public Showtime createShowtime(String movieTitle,
                                   String date,
                                   String startTime,
                                   String ticketPrice,
                                   String hallCapacity,
                                   String hallNumber){

        if (movieTitle==null || startTime==null || ticketPrice==null || hallCapacity==null || hallNumber==null || date ==null){
            throw new ResourceNotFoundException("Invalid Inputs For The Showtime");
        }

        Movie movie = movieService.findMovieByTitle(movieTitle);
        int capacity =Integer.parseInt(hallCapacity);
        long ticket = Long.parseLong(ticketPrice);
        int hallId= Integer.parseInt(hallNumber);
        if (capacity<=0 ||  ticket<=0){
            throw new ResourceNotFoundException("Invalid Inputs For The Showtime");
        }
        if (hallNotEmpty(startTime,hallId,date)){
            throw new ResourceNotFoundException("Hall Is Already Occupied At "+date +" "+ startTime);
        }
        Showtime showtime = new Showtime();
        showtime.setMovie(movie);
        showtime.setCapacity(capacity);
        showtime.setHallNumber(hallId);
        showtime.setTicketPrice(ticket);
        showtime.setDate(date);
        showtime.setStartTime(startTime);
        showtime.setEndTime(null);
        showtime.setOccupiedCapacity(0);
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
}
