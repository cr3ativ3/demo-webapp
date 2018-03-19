package com.demo.seb.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class Response {

    private Object data;
    private String error;

    public Response(Object data) {
        this.data = data;
    }
    public Response(String error) {
        this.error = error;
    }
    public Object getData() {
        return data;
    }
    public String getError() {
        return error;
    }
}