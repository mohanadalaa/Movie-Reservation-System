//package system.movie_reservation_system.Entities.Reservations;
//
//import jakarta.persistence.*;
//import lombok.Data;
//import system.movie_reservation_system.Entities.ShowTimes.Showtime;
//
//@Entity
//@Table(name = "seats")
//@Data
//public class Seat {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "seat_id")
//    private Long seatId;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "showtime_id", nullable = false)
//    private Showtime showtime;
//
//    @Column(nullable = false)
//    private String seatNumber;  // Example: "A0", "B5"
//
//    @Column(nullable = false)
//    private boolean available = true;  // default: free
//}