package system.movie_reservation_system.Requests;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
