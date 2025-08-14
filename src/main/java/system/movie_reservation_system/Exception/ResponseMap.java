package system.movie_reservation_system.Exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.LinkedHashMap;
import java.util.Map;


public class ResponseMap {

    private final Map<String, Object> response;

    private ResponseMap(Builder builder) {
        this.response = builder.response;
    }

    public Map<String, Object> getResponse() {
        return response;
    }

    public static class Builder {
        private final Map<String, Object> response = new LinkedHashMap<>();

        public Builder add(String key, Object value) {
            response.put(key, value);
            return this;
        }

        public Builder status(String status) {
            response.put("status", status);
            return this;
        }

        public Builder message(String message) {
            response.put("message", message);
            return this;
        }
        public Builder timestamp(){
            response.put("timestamp", LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            return this;
        }

        public ResponseMap build() {
            return new ResponseMap(this);
        }
    }
}
