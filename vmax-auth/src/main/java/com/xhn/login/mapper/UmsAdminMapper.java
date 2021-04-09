package com.xhn.login.mapper;

import com.xhn.login.entity.vo.UmsAdmin;

import java.util.List;

/**
 * @program: vmax
 * @description:
 * @author: mfl
 * @create: 2021-03-28 17:09
 **/
public interface UmsAdminMapper {

    public List<UmsAdmin> getAdminInfoByUserName(String username);
}
