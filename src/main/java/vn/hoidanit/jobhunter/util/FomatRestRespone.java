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
import vn.hoidanit.jobhunter.DTO.response.RestRespone;

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

        RestRespone<Object> res = new RestRespone<>();
        res.setStatuscode(status);
        if (status > 400) {
            if (status == 401) {
                res.setError("Unauthorized");
                res.setMessage(body.toString());
            } else if (status == 403) {
                res.setError("Forbidden");
                res.setMessage(body.toString());
            } else if (status == 400) {
                res.setError("Bad Request");
                res.setMessage(body.toString());
            } else if (status == 405) {
                res.setError("Method Not Allowed");
                res.setMessage(body.toString());
            } else if (status == 409) {
                res.setError("Conflict");
                res.setMessage("Conflict");
            } else if (status == 415) {
                res.setError("Unsupported Media Type");
                res.setMessage(body.toString());
            } else if (status == 404) {
                res.setError("Not Found");
                res.setMessage(body.toString());
            } else if (status == 500) {
                res.setError("Internal Server Error");
                res.setMessage(body.toString());
            }
        } else {
            res.setStatuscode(status);
            res.setMessage("Get API Success");
            res.setError("Don't have error");
            res.setData(body);
        }
        return res;
    }

}
