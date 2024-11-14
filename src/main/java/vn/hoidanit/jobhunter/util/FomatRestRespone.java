package vn.hoidanit.jobhunter.util;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.core.io.Resource;


import jakarta.servlet.http.HttpServletResponse;
import vn.hoidanit.jobhunter.DTO.response.RestRespone;

@ControllerAdvice
public class FomatRestRespone implements ResponseBodyAdvice<Object> {

    @Override
    @SuppressWarnings("null")
    public boolean supports(MethodParameter returnType, @SuppressWarnings("rawtypes") Class converterType) {
        return true;
    }

    
    @Override
    @Nullable
    @SuppressWarnings("null")
    public Object beforeBodyWrite(
            Object body,
            MethodParameter returnType,
            MediaType selectedContentType,
            @SuppressWarnings("rawtypes") Class selectedConverterType,
            ServerHttpRequest request,
            ServerHttpResponse response) {
        HttpServletResponse ServletResponse = ((ServletServerHttpResponse) response).getServletResponse();
        int status = ServletResponse.getStatus();
        if (body instanceof String || body instanceof Resource) {
            return body;
        }
        RestRespone<Object> res = new RestRespone<>();
        res.setStatuscode(status);
        if (status >= 400) {
          return body;
        }
        else {
           
            res.setMessage("Success");
            res.setError("No Error");
            res.setData(body);
        }

        return res;
    }

}
