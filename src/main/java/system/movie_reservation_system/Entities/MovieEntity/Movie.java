package system.movie_reservation_system.Entities.MovieEntity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Entity
@Data
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Title is required")
    private String title;

    @Column(nullable = false)
    @NotBlank(message = "Description is required")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Genre is required")  // Changed from @NotBlank
    private Genre genre;

    @Column(name = "duration_minutes", nullable = false)
    @Min(value = 1, message = "Duration must be at least 1 minute")
    private int durationMinutes;  // Can stay as primitive since we validate via @Min

    @Column(name = "poster_url", nullable = true)
    private String posterUrl;  // No validation annotations = nullable
 //   @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
  //  private List<Showtime> showtimes = new ArrayList<>();
}
