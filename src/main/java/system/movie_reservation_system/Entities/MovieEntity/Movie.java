package system.movie_reservation_system.Entities.MovieEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonMerge;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.Name;
import lombok.Data;
import system.movie_reservation_system.Entities.ShowTimes.Showtime;

import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Long movieId;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Title is required")
    private String title;

    @Column(nullable = false)
    @NotBlank(message = "Description is required")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Genre is required")
    private Genre genre;

    @Column(name = "duration_minutes", nullable = false)
    @Min(value = 1, message = "Duration must be at least 1 minute")
    private int durationMinutes;

    @Column(name = "poster_url", nullable = true)
    @JsonIgnore
    private String posterUrl;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Showtime> showtimes = new ArrayList<>();
}
