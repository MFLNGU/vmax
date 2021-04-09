package com.xhn.login.api.impl;

import com.xhn.common.pojo.CommonResult;
import com.xhn.login.api.UmsAdminApi;
import com.xhn.login.entity.dto.UmsAdminLoginParam;
import com.xhn.login.entity.po.UmsPermission;
import com.xhn.login.entity.vo.UmsAdmin;
import com.xhn.login.service.UmsAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @program: vmax
 * @description:
 * @author: mfl
 * @create: 2021-03-28 16:22
 **/
@Controller
public class UmsAdminApiController implements UmsAdminApi {
    @Autowired
    private UmsAdminService adminService;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Override
    public CommonResult<UmsAdmin> register(UmsAdmin umsAdminParam, BindingResult result) {
        UmsAdmin umsAdmin = adminService.register(umsAdminParam);
        if (umsAdmin == null) {
            CommonResult.failed();
        }
        return CommonResult.success(umsAdmin);
    }

    @Override
    public CommonResult login(UmsAdminLoginParam umsAdminLoginParam, BindingResult result) {
        String token = adminService.login(umsAdminLoginParam.getUserName(), umsAdminLoginParam.getPassword());
        if (token == null) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    @Override
    public CommonResult<List<UmsPermission>> getPermissionList(Long adminId) {
        List<UmsPermission> permissionList = adminService.getPermissionList(adminId);
        return CommonResult.success(permissionList);
    }
}
