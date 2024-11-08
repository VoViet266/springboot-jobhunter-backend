package vn.hoidanit.jobhunter.Entity;


public class RestRespone<T> {
    private int statuscode;
    private String error;
    private String message;
    private  T data;
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
  
    public int getStatuscode() {
        return statuscode;
    }
    public void setStatuscode(int statuscode) {
        this.statuscode = statuscode;
    }
    public String getError() {
        return error;
    }
    public void setError(String error) {
        this.error = error;
    }

    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }

}
