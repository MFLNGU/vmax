package com.xhn.login.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: vmax
 * @description:
 * @author: mfl
 * @create: 2021-03-28 16:55
 **/
@Data
public class UmsAdmin {

    private Long id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    private Integer status;
}
