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
        res.setStatuscode(HttpStatus.BAD_REQUEST.value());
        res.setError(exception.getMessage());
        res.setMessage("IdInvalidException");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }




}
