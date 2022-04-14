package com.graduation.chat.result;

import lombok.Data;

@Data
public class BaseResult<T> {
    /**
     * 错误码
     */
    private Long code;

    /**
     * 错误信息
     */
    private String msg;

    /**
     * 数据
     */
    private T data;

    public BaseResult(Long code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> BaseResult SUCCESS(T data) {
        return new BaseResult((long) 0, "", data);
    }

    public static <T> BaseResult FAIL(Long code, String msg, T data) {
        return new BaseResult(code, msg, data);
    }

    public static <T> BaseResult ERROR(Long code, String msg, T data) {
        return new BaseResult(code, msg, data);
    }
}
