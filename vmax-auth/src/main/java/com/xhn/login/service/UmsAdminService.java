package com.xhn.login.service;

import com.xhn.login.entity.po.UmsPermission;
import com.xhn.login.entity.vo.UmsAdmin;

import java.util.List;

/**
 * @program: vmax
 * @description:
 * @author: mfl
 * @create: 2021-03-28 16:29
 **/
public interface UmsAdminService {
    /**
     * 根据用户名获取后台管理员
     */
    UmsAdmin getAdminByUsername(String username);

    /**
     * 注册功能
     */
    UmsAdmin register(UmsAdmin umsAdminParam);

    /**
     * 登录功能
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
    String login(String username, String password);

    /**
     * 获取用户所有权限（包括角色权限和+-权限）
     */
    List<UmsPermission> getPermissionList(Long adminId);
}
