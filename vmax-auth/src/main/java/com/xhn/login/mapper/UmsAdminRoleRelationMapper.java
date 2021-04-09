package com.xhn.login.mapper;

import com.xhn.login.entity.po.UmsPermission;

import java.util.List;

/**
 * @program: vmax
 * @description:
 * @author: mfl
 * @create: 2021-03-28 17:12
 **/
public interface UmsAdminRoleRelationMapper {
    List<UmsPermission> getPermissionList(Long adminId);
}
