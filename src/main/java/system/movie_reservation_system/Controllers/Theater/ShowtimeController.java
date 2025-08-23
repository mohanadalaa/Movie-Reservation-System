package system.movie_reservation_system.Controllers.Theater;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import system.movie_reservation_system.Entities.ShowTimes.Showtime;
import system.movie_reservation_system.Exception.ResponseMap;
import system.movie_reservation_system.Services.TheaterServices.ShowtimeService;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DEV')")
public class ShowtimeController {
    private final ShowtimeService showtimeService;

    @PostMapping("/showtime")
    public Showtime createShowtime(@RequestParam String title,
                                   @RequestParam String date, //2025-5-1
                                   @RequestParam String startTime,
                                   @RequestParam String ticketPrice,
                                   @RequestParam String capacity,   //hall capacity
                                   @RequestParam String hall
    ) {
        return showtimeService.createShowtime(title, date,startTime, ticketPrice,capacity,hall);
    }

    @GetMapping("/showtime/{id}")
    public Showtime getShowtime(@PathVariable long id){
        return showtimeService.getShowTimeById(id);
    }
    @GetMapping("/showtimes")
    public List<Showtime> getAllShowTimes(){
        return showtimeService.getShowTimes();
    }
    @GetMapping("/showtimes/{date}")
    public List<Showtime> getShowTimesByDate(@PathVariable String date){
        return showtimeService.getShowTimesByDate(date);
    }
    @GetMapping("/showtimes/{date}/{hallId}")
    public List<Showtime> getShowTimesByDateAndHall(@PathVariable String date,@PathVariable int hallId){
        return showtimeService.getShowTimesByDateAndHallId(date,hallId);
    }

    @DeleteMapping("/showtimes/{id}")
    public Map<String, Object> deleteShowtime(@PathVariable long id){
        Showtime showtime = showtimeService.deleteShowTime(id);
        return new ResponseMap.Builder()
                .status("ShowTime Deleted Successfully")
                .add("ShowTime",showtime)
                .timestamp()
                .build().getResponse();
    }
}
