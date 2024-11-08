package vn.hoidanit.jobhunter.util;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import jakarta.servlet.http.HttpServletResponse;
import vn.hoidanit.jobhunter.Entity.RestRespone;

@ControllerAdvice
public class FomatRestRespone implements ResponseBodyAdvice<Object> {

    @SuppressWarnings({ "null", "rawtypes" })
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @SuppressWarnings({ "null", "rawtypes" })
    @Override
    @Nullable
    public Object beforeBodyWrite(
        Object body,
        MethodParameter returnType,
        MediaType selectedContentType,
        Class selectedConverterType,
        ServerHttpRequest request,
        ServerHttpResponse response) {
        HttpServletResponse ServletResponse = ((ServletServerHttpResponse) response).getServletResponse();
        int status = ServletResponse.getStatus();

            
        RestRespone<Object> res  = new RestRespone<>();
        res.setStatuscode(status);
     
        switch (status) {
            case 400 -> {
                res.setError(body.toString());
                res.setMessage("Bad Request");
            }
            case 401 -> {
                res.setError(body.toString());
                res.setMessage("Unauthorized");
            }
            case 403 -> {
                res.setError(body.toString());
                res.setMessage("Forbidden");
            }
            case 404 -> {
                res.setError(body.toString());
                res.setMessage("Not Found");
            }
            case 500 -> {
                res.setError(body.toString());
                res.setMessage("Internal Server Error");
            }
            case 503 -> {
                res.setError(body.toString());
                res.setMessage("Service Unavailable");
            }
            default -> {
                res.setData(body);
                res.setMessage("Success!");
            }
        }
        return res; 
    }

}
