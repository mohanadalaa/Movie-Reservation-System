package system.movie_reservation_system.Services.MovieServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.movie_reservation_system.Entities.MovieEntity.Genre;
import system.movie_reservation_system.Entities.MovieEntity.Movie;
import system.movie_reservation_system.Exception.ResourceNotFoundException;
import system.movie_reservation_system.Repositories.MovieRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final ShowtimeService showtimeService;


    public Movie findMovieByTitle(String title){
        return movieRepository.findByTitle(title).
             orElseThrow(() -> new ResourceNotFoundException("Movie: "+title+" Is Not Found"));
    }

    @Transactional
    public Movie createMovie(String title, String description, String genre, String duration, String posterUrl) {
        Genre genreEnum = Genre.valueOf(genre.toUpperCase());
        int durationMinutes = Integer.parseInt(duration);

        if (findMovieByTitle(title)!=null) {
           throw new ResourceNotFoundException("Movie With Title: "+title+" Is Already Made");
        }
        if (durationMinutes<0){
            throw new ResourceNotFoundException("Invalid Movie Duration");
        }

        //TODO validate posterUrl ,genre ,edge cases all to be handled in here

        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setDescription(description);
        movie.setGenre(genreEnum);
        movie.setDurationMinutes(durationMinutes);
        movie.setPosterUrl(posterUrl);
        movieRepository.save(movie);
        return movie;
    }

    @Transactional(readOnly = true)
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Movie getMovieById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
    }

    @Transactional
    public Movie updateMovie(Long id) {
        return null;
    }

    @Transactional
    public void deleteMovieByTitle(String title) {
        Movie movie = findMovieByTitle(title);
        movieRepository.delete(movie);
    }
}