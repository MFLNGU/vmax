package com.xhn.common.pojo;

/**
 * @program: vmax
 * @description:
 * @author: mfl
 * @create: 2021-03-28 16:39
 **/

import io.swagger.annotations.ApiModelProperty;

/**
 *
 */
public enum ResultCode implements IErrorCode {
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    VALIDATE_FAILED(404, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限");

    @ApiModelProperty(value = "响应码")
    private long code;

    @ApiModelProperty("提示信息")
    private String message;

    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
