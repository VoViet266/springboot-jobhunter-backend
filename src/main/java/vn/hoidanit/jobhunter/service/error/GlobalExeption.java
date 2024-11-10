package vn.hoidanit.jobhunter.service.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import vn.hoidanit.jobhunter.DTO.response.RestRespone;
public class GlobalExeption {
    @ExceptionHandler(value = IdInValidException.class)
    public ResponseEntity<RestRespone<Object>> handleException(IdInValidException exception) {
        RestRespone<Object> res = new RestRespone<Object>();
        res.setStatuscode(HttpStatus.BAD_REQUEST.value());
        res.setError(exception.getMessage());
        res.setMessage("IdInValidException");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);


    }
}
