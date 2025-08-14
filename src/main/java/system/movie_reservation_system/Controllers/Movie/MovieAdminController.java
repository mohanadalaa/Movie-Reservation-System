package system.movie_reservation_system.Controllers.Movie;


import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import system.movie_reservation_system.Entities.MovieEntity.Genre;
import system.movie_reservation_system.Entities.MovieEntity.Movie;
import system.movie_reservation_system.Exception.ResponseMap;
import system.movie_reservation_system.Repositories.MovieRepository;
import system.movie_reservation_system.Services.MovieServices.MovieService;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DEV')")
@RequiredArgsConstructor
public class MovieAdminController {
    private final MovieService movieService;

    // POST http://localhost:8080/api/admin/movie/add
    @PostMapping("/movie/add")
    public Movie addMovie(@RequestParam String title,
                          @RequestParam String description,
                          @RequestParam String genre,
                          @RequestParam String duration,
                          @RequestParam String posterUrl){

        return movieService.createMovie(title,description,genre,duration,posterUrl);
    }
    // GET http://localhost:8080/api/admin/movie
    @PostMapping("/movie")
    public Movie getMovie(@RequestParam String title){
        return movieService.findMovieByTitle(title);
    }

    // DELETE http://localhost:8080/api/admin/movie/delete
    @DeleteMapping("/movie/delete")
    public Map<String,Object> deleteMovie(@RequestParam String title){
        movieService.deleteMovieByTitle(title);
        return new ResponseMap.Builder()
                .status("Movie Deleted Successfully")
                .message("All Shows Deleted")
                .timestamp()
                .add("title",title)
                .build().getResponse();
    }
}
