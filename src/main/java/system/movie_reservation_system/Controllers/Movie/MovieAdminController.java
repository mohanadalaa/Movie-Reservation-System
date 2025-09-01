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
@RequestMapping("/api/admin/movies")
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DEV')")
@RequiredArgsConstructor
public class MovieAdminController {
    private final MovieService movieService;

    //Create
    //  http://localhost:8080/api/admin/movies
    @PostMapping
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

    //Read
    //  http://localhost:8080/api/admin/movies/{title}
    @GetMapping("/{title}")
    public Movie getMovie(@PathVariable String title) {
        return movieService.findMovieByTitle(title);
    }
    //Delete
    //  http://localhost:8080/api/admin/movies/{title}
    @DeleteMapping("/{title}")
    public Map<String, Object> deleteMovie(@PathVariable String title) {
        movieService.deleteMovieByTitle(title);
        return new ResponseMap.Builder()
                .status("Movie Deleted Successfully")
                .message("All Shows Deleted")
                .timestamp()
                .add("title", title)
                .build().getResponse();
    }

    //Read By Genre
    //  http://localhost:8080/api/admin/movies/genre/{genre}
    @GetMapping("/genre/{genre}")
    public List<Movie> getMoviesByGenre(@PathVariable String genre) {
        Genre genreEnum = Genre.valueOf(genre.toUpperCase());
        return movieService.getMoviesByGenre(genreEnum);
    }



    @PatchMapping("/{title}")
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
