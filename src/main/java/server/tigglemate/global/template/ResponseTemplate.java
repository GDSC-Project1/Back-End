package server.tigglemate.global.template;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResponseTemplate <T>{
    int status;
    String message;
    T data;

    public ResponseTemplate(HttpStatus status, String message, T data) {
        this.status = status.value();
        this.message = message;
        this.data = data;
    }
}
