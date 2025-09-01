package system.movie_reservation_system.Entities.Reservations;

import com.fasterxml.jackson.annotation.JsonBackReference;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.Data;
import system.movie_reservation_system.Entities.AppUserEntity.AppUser;
import system.movie_reservation_system.Entities.ShowTimes.Seat;
import system.movie_reservation_system.Entities.ShowTimes.Showtime;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonBackReference
    private AppUser user;

    @Column(name = "created_at")
    private LocalDateTime createdAt;


    @Column(name = "price")
    private long totalPrice;

    @Column(name = "number_of_tickets")
    private int numberOfTickets;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @ManyToOne(fetch = FetchType.EAGER) // EAGER so showtime is fetched immediately
    @JoinColumn(name = "showtime_id", nullable = false)
    @JsonIgnoreProperties("reservations") // don’t serialize reservations inside showtime
    private Showtime showtime;

    // One reservation can have many seats
    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL)
    private List<Seat> seats = new ArrayList<>();
}
