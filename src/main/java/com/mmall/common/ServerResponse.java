package com.mmall.common;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ServerResponse<T> implements Serializable {
    //保证Json序列化的时候，如果是null对象，key也会消失
    private int status;
    private String msg;
    private T data;

    private ServerResponse(int status) {
        this.status = status;
    }

    private ServerResponse(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    private ServerResponse(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    private ServerResponse(int status, T data) {
        this.status = status;
        this.data = data;
    }

    @JsonIgnore
    //使之不在序列化结果当中
    public boolean isSuccess() {
        return this.status == ResponserCode.SUCCESS.getCode();
    }

    public int getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public static <T> ServerResponse<T> crateBySuccess() {
        return new ServerResponse<T>(ResponserCode.SUCCESS.getCode());
    }

    public static <T> ServerResponse<T> crateBySuccessMessage(String msg) {
        return new ServerResponse<T>(ResponserCode.SUCCESS.getCode(), msg);
    }

    public static <T> ServerResponse<T> crateBySuccess(T data) {
        return new ServerResponse<T>(ResponserCode.SUCCESS.getCode(), data);
    }

    public static <T> ServerResponse<T> crateBySuccess(String msg, T data) {
        return new ServerResponse<T>(ResponserCode.SUCCESS.getCode(), msg, data);
    }

    public static <T> ServerResponse<T> createByError() {
        return new ServerResponse<T>(ResponserCode.ERROR.getCode(), ResponserCode.ERROR.getDesc());
    }

    public static <T> ServerResponse<T> createByError(String errorMessage) {
        return new ServerResponse<T>(ResponserCode.ERROR.getCode(), errorMessage);
    }

    public static <T> ServerResponse<T> createByError(int errorCode, String errorMessage) {
        return new ServerResponse<T>(errorCode, errorMessage);
    }

    public static <T> ServerResponse<T> createByMessage(String msg) {
        return new ServerResponse<T>(ResponserCode.ERROR.getCode(),msg);
    }


}
