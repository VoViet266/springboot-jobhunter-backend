package vn.hoidanit.jobhunter.service.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import vn.hoidanit.jobhunter.dto.response.RestRespone;

@RestControllerAdvice
public class GlobalExeption {
    @ExceptionHandler(value = IdInvalidException.class)
    public ResponseEntity<RestRespone<Object>> handleIdInvalidException(Exception exception) {
        RestRespone<Object> res = new RestRespone<>();
        res.setStatuscode(HttpStatus.NOT_FOUND.value());
        res.setError(exception.getMessage());
        res.setMessage("Id Invalid Exception");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<RestRespone<Object>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        RestRespone<Object> res = new RestRespone<>();
        res.setStatuscode(HttpStatus.NOT_FOUND.value());
        res.setError(ex.getMessage());
        res.setMessage("Resource Not Found Exception");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<RestRespone<Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        RestRespone<Object> res = new RestRespone<>();
        res.setStatuscode(HttpStatus.BAD_REQUEST.value());
        res.setError(ex.getMessage());
        res.setMessage("IllegalArgument Exception");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<RestRespone<Object>> handleAuthicationException(AuthenticationException ex) {
        RestRespone<Object> res = new RestRespone<>();
        res.setStatuscode(HttpStatus.UNAUTHORIZED.value());
        res.setError(ex.getMessage());
        res.setMessage("Authication Exception");

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
    }
}
