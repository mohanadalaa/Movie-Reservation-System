package system.movie_reservation_system.Controllers.AdminTheater;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import system.movie_reservation_system.Entities.MovieEntity.Movie;
import system.movie_reservation_system.Entities.ShowTimes.Showtime;
import system.movie_reservation_system.Entities.ShowTimes.ShowtimeDTO;
import system.movie_reservation_system.Exception.ResponseMap;
import system.movie_reservation_system.Services.TheaterServices.ShowtimeService;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/admin/movie/showtimes")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DEV')")
public class AdminShowtimeController {
    private final ShowtimeService showtimeService;

    //Create
    //  http://localhost:8080/api/admin/movie/showtimes
    @PostMapping
    public Showtime createShowtime(@RequestParam String title,
                                   @RequestParam String date, //2025-5-1
                                   @RequestParam String startTime,
                                   @RequestParam String ticketPrice,
                                   @RequestParam String capacity,   //hall capacity
                                   @RequestParam String hall
    ) {
        return showtimeService.createShowtime(title, date,startTime, ticketPrice,capacity,hall);
    }

    //Read
    //  http://localhost:8080/api/admin/movie/showtimes/{id}
    @GetMapping("/{id}")
    public Showtime getShowtime(@PathVariable long id){
        return showtimeService.getShowTimeById(id);
    }

    @GetMapping
    public List<Showtime> getAllShowTimes(){
        return showtimeService.getShowTimes();
    }

    @GetMapping("date/{date}")
    public List<Showtime> getShowTimesByDate(@PathVariable String date){
        return showtimeService.getShowTimesByDate(date);
    }

    @GetMapping("date/{date}/hall/{hallId}")
    public List<Showtime> getShowTimesByDateAndHall(@PathVariable String date,@PathVariable int hallId){
        return showtimeService.getShowTimesByDateAndHallId(date,hallId);
    }
    //Delete
    //  http://localhost:8080/api/admin/movie/showtimes/2
    @DeleteMapping("/{id}")
    public Map<String, Object> deleteShowtime(@PathVariable long id){
        Showtime showtime = showtimeService.deleteShowTime(id);
        return new ResponseMap.Builder()
                .status("ShowTime Deleted Successfully")
                .add("ShowTime",showtime)
                .timestamp()
                .build().getResponse();
    }
}
