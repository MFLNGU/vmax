package com.xhn.login.api;

import com.xhn.common.pojo.CommonResult;
import com.xhn.login.entity.dto.UmsAdminLoginParam;
import com.xhn.login.entity.po.UmsPermission;
import com.xhn.login.entity.vo.UmsAdmin;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author: 86188
 * @date: 2021/3/28
 * @desc
 */
@Api(tags = "用户注册登录")
@RequestMapping(value = "/admin")
public interface UmsAdminApi {

    @ApiOperation(value = "用户注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    CommonResult<UmsAdmin> register(@RequestBody UmsAdmin umsAdminParam, BindingResult result);

    @ApiOperation(value = "用户登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult login(@RequestBody UmsAdminLoginParam umsAdminLoginParam, BindingResult result);

    @ApiOperation("获取用户所有权限（包括+-权限）")
    @RequestMapping(value = "/permission/{adminId}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<UmsPermission>> getPermissionList(@PathVariable Long adminId);

}
