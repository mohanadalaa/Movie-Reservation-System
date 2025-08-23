package system.movie_reservation_system.Services.TheaterServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.movie_reservation_system.Entities.MovieEntity.Genre;
import system.movie_reservation_system.Entities.MovieEntity.Movie;
import system.movie_reservation_system.Exception.ResourceNotFoundException;
import system.movie_reservation_system.Repositories.MovieRepository;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;


    public Movie findMovieByTitle(String title){
        return movieRepository.findByTitle(title)
                .orElseThrow(() -> new ResourceNotFoundException("Movie with title: "+ title+" is not found"));
    }

    public Movie createMovie(String title, String description, String genre, String duration, String posterUrl) {

        if (title.isEmpty() || description.isEmpty() || genre.isEmpty()){
            throw new ResourceNotFoundException("Invalid Movie Information");
        }

        int durationMinutes = Integer.parseInt(duration);
        if (movieRepository.existsByTitle(title)){
            throw new ResourceNotFoundException("Movie with title: "+ title+" already exists");
        }
        if (durationMinutes<=0) {
            throw new ResourceNotFoundException("Invalid Movie duration");
        }
        if (posterUrl.isEmpty()){
            posterUrl=null;
        }
        if (movieRepository.existsByPosterUrl(posterUrl) && posterUrl!=null){
             throw new ResourceNotFoundException("Poster Is Invalid");
        }


        //TODO validate posterUrl ,genre ,edge cases all to be handled in here
        Genre genreEnum = Genre.valueOf(genre.toUpperCase());
        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setDescription(description);
        movie.setGenre(genreEnum);
        movie.setPosterUrl(posterUrl);
        movie.setDurationMinutes(durationMinutes);
        movieRepository.save(movie);
        return movie;
    }

    public List<Movie> getMoviesByGenre(Genre genre){
        return movieRepository.findByGenre(genre);
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Movie getMovieById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
    }



    @Transactional
    public void deleteMovieByTitle(String title) {
        Movie movie = movieRepository.findByTitle(title)
                .orElseThrow(() -> new ResourceNotFoundException("Movie with title: "+ title+" is not found"));
        movieRepository.delete(movie);
    }


    public Movie partialUpdate(String title, Map<String, Object> updates) {
        Movie movie = movieRepository.findByTitle(title)
                .orElseThrow(() -> new ResourceNotFoundException("Movie with title: " + title + " is not found"));

        updates.forEach((key, obj) -> {
            String value = obj.toString();
            switch (key) {
                case "title":
                    if (value != null && !value.isBlank()) {
                        if (!movieRepository.existsByTitle(value)) {
                            movie.setTitle(value);
                        } else {
                            throw new ResourceNotFoundException("Movie With Title: " + value + " Already Exists");
                        }
                    }
                    break;
                case "description":
                    if (value != null && !value.isBlank()) {
                        movie.setDescription(value);
                    }
                    break;
                case "genre":
                    if (value != null && !value.isBlank()) {
                        Genre genreEnum = Genre.valueOf(value.toUpperCase());
                        movie.setGenre(genreEnum);
                    }
                    break;
                case "duration":
                    if (value != null && !value.isBlank()) {
                        int duration = Integer.parseInt(value);
                        if (duration > 0) {
                            movie.setDurationMinutes(duration);
                        } else {
                            throw new ResourceNotFoundException("Duration Must Be Positive");
                        }
                    }
                    break;
                case "posterUrl":
                    if (value != null && !value.isBlank()) {
                        if (!movieRepository.existsByPosterUrl(value)) {
                            movie.setPosterUrl(value);
                        } else {
                            throw new ResourceNotFoundException("This Poster Already Exists");
                        }
                    }
                    break;
                default:
                    throw new ResourceNotFoundException("Invalid field for update: " + key);
            }
        });
        return movieRepository.save(movie);
    }

}