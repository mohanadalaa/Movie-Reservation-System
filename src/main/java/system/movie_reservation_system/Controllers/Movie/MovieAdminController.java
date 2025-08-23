package system.movie_reservation_system.Controllers.Movie;


import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import system.movie_reservation_system.Entities.MovieEntity.Genre;
import system.movie_reservation_system.Entities.MovieEntity.Movie;
import system.movie_reservation_system.Exception.ResponseMap;
import system.movie_reservation_system.Services.TheaterServices.MovieService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DEV')")
@RequiredArgsConstructor
public class MovieAdminController {
    private final MovieService movieService;

    // POST http://localhost:8080/api/admin/movie/add
    @PostMapping("/movie/add")
    public Map<String, Object> addMovie(@RequestParam String title,
                          @RequestParam String description,
                          @RequestParam String genre,
                          @RequestParam String duration,
                          @RequestParam String posterUrl){
        Movie movie=movieService.createMovie(title,description,genre,duration,posterUrl);
        return new ResponseMap.Builder()
                .status("Movie Added Successfully")
                .timestamp()
                .add("title", title)
                .add("description",description)
                .add("genre",genre)
                .add("duration",duration)
                .add("posterUrl",movie.getPosterUrl())
                .build().getResponse();
    }

    // POST http://localhost:8080/api/admin/movie/add
    @PostMapping("/movie/showtime/add")
    public Map<String, Object> addShowtimeToMovie(@RequestParam String title,
                                        @RequestParam String description,
                                        @RequestParam String genre,
                                        @RequestParam String duration,
                                        @RequestParam String posterUrl){
        Movie movie=movieService.createMovie(title,description,genre,duration,posterUrl);
        return new ResponseMap.Builder()
                .status("Movie Added Successfully")
                .timestamp()
                .add("title", title)
                .add("description",description)
                .add("genre",genre)
                .add("duration",duration)
                .add("posterUrl",movie.getPosterUrl())
                .build().getResponse();
    }


    // GET http://localhost:8080/api/admin/movie/{title}
    @GetMapping("/movie/{title}")
    public Movie getMovie(@PathVariable String title) {
        return movieService.findMovieByTitle(title);
    }

    // DELETE http://localhost:8080/api/admin/movie/{title}
    @DeleteMapping("/movie/{title}")
    public Map<String, Object> deleteMovie(@PathVariable String title) {
        movieService.deleteMovieByTitle(title);
        return new ResponseMap.Builder()
                .status("Movie Deleted Successfully")
                .message("All Shows Deleted")
                .timestamp()
                .add("title", title)
                .build().getResponse();
    }
    // GET http://localhost:8080/api/admin/movie/genre/{genre}
    @GetMapping("/movie/genre/{genre}")
    public List<Movie> getMoviesByGenre(@PathVariable String genre) {
        Genre genreEnum = Genre.valueOf(genre.toUpperCase());
        return movieService.getMoviesByGenre(genreEnum);
    }

    // PATCH http://localhost:8080/api/admin/movie/{title}
    @PatchMapping("/movie/{title}")
    public Map<String, Object> updateMoviePartial(@PathVariable String title, @RequestBody Map<String, Object> updates) {
        Movie updatedMovie = movieService.partialUpdate(title, updates);
        return new ResponseMap.Builder()
                .status("Movie Updated Successfully")
                .timestamp()
                .add("title", updatedMovie.getTitle())
                .add("description",updatedMovie.getDescription())
                .add("genre",updatedMovie.getGenre())
                .add("duration",updatedMovie.getDurationMinutes())
                .add("posterUrl",updatedMovie.getPosterUrl())
                .build().getResponse();

    }
}
