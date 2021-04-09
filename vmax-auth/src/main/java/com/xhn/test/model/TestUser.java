package com.xhn.test.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TestUser {
    @ApiModelProperty(value = "名字")
    private String name;

}