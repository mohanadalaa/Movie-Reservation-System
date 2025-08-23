package system.movie_reservation_system.Entities.ShowTimes;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import system.movie_reservation_system.Entities.MovieEntity.Movie;
import system.movie_reservation_system.Entities.Reservations.Reservation;


import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "showtimes")
@Data
public class Showtime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "showtime_id")
    private Long showtimeId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "movie_id", nullable = false)
    @JsonIgnoreProperties("showtimes")
    private Movie movie;

    @Column(name="day_date",nullable = true)
    private String date;

    @Column(name="start_time",nullable = true)
    private String startTime;

    @Column(name="end_time",nullable = true)
    @JsonIgnore
    private String endTime;

    @Column(nullable = false)
    private long ticketPrice;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "occupied_capacity")
    private int occupiedCapacity;

    @Column(name = "hall_id")
    private int hallNumber;

    @OneToMany(mappedBy = "showtime", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Reservation> reservations = new ArrayList<>();

}
