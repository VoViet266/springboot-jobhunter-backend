package vn.hoidanit.jobhunter.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RestRespone<T> {
    private int statuscode;
    private String error;
    private Object message;
    private T data;
}

