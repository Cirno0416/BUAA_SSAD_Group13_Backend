package com.innoshare.common;
import lombok.Data;

@Data
public class Response {

    private int code;

    private String message;

    private String type;

    private Boolean success;

    private Object data;

    public static Response success(String message) {
        Response r = new Response();
        r.setCode(200);
        r.setMessage(message);
        r.setSuccess(true);
        r.setType("success");
        r.setData(null);
        return r;
    }

    public static Response success(String message, Object data) {
        Response r = success(message);
        r.setData(data);
        return r;
    }

    public static Response warning(String message) {
        Response r = error(message);
        r.setType("warning");
        return r;
    }

    public static Response error(String message) {
        Response r = success(message);
        r.setSuccess(false);
        r.setType("error");
        return r;
    }

    public static Response fatal(String message) {
        Response r = error(message);
        r.setCode(500);
        return r;
    }
}
