package com.junlong.windseeker.web.response;

import com.junlong.windseeker.constants.ErrorConstants.Errors;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * Created by niujunlong on 17/9/6.
 */
public class BaseResponse<T> implements Serializable{
    private static final long serialVersionUID = 1L;

    private T data;
    private Integer httpStatus;
    private Integer errorCode;
    private String errorMsg;
    private BaseResponse() {}

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(Integer httpStatus) {
        this.httpStatus = httpStatus;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    /**
     * 创建响应体，成功返回，设置返回的数据
     *
     * @param data
     * @return 响应体
     */
    public static <E> BaseResponse<E> build(E data) {
        return build(HttpStatus.OK.value(), Errors.SUCCESS, data);
    }

    /**
     * 创建响应体。
     *
     * @param httpStatus    HTTP状态码
     * @param error     错误信息
     * @param data      返回的数据
     * @return 响应体
     */
    public static <E> BaseResponse<E> build(int httpStatus, Errors error, E data) {
        BaseResponse<E> response = new BaseResponse<>();
        response.data = data;
        response.httpStatus = httpStatus;
        response.errorCode = error.getCode();
        response.errorMsg = error.getMessage();
        return response;
    }

}
