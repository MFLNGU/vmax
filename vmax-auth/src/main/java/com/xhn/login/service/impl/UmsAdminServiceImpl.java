package com.xhn.login.service.impl;

import com.xhn.common.util.JwtTokenUtil;
import com.xhn.login.entity.po.UmsPermission;
import com.xhn.login.entity.vo.UmsAdmin;
import com.xhn.login.mapper.UmsAdminMapper;
import com.xhn.login.mapper.UmsAdminRoleRelationMapper;
import com.xhn.login.service.UmsAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.JedisCluster;

import java.util.List;

/**
 * @program: vmax
 * @description:
 * @author: mfl
 * @create: 2021-03-28 16:29
 **/
@Service
public class UmsAdminServiceImpl implements UmsAdminService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UmsAdminServiceImpl.class);

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JedisCluster jedisCluster;

    @Autowired
    private UmsAdminMapper adminMapper;

    @Autowired
    private UmsAdminRoleRelationMapper umsAdminRoleRelationMapper;


    @Override
    public UmsAdmin getAdminByUsername(String username) {

        List<UmsAdmin> result = adminMapper.getAdminInfoByUserName(username);
        if (result != null && result.size() > 0) {
            return result.get(0);
        }
        return null;
    }

    @Override
    public UmsAdmin register(UmsAdmin umsAdminParam) {
        //校验用户名的唯一性 方式一：数据库做唯一性限制（需要考虑是否友好等）；方式二：缓存
        //1.查询数据库是否有重复
        String username = umsAdminParam.getUsername();
        List<UmsAdmin> nameList = adminMapper.getAdminInfoByUserName(username);
        if (!CollectionUtils.isEmpty(nameList)) {//存在同名用户，校验不通过
            return null;
        }
        //2.将当前注册用户名做分布式锁，如果加锁成功则入库，加锁失败返回给前端对于的返回码
        jedisCluster.setex("username",1,username);

        //用户名唯一，入库且状态设置为有效
       // RediscLI
        //对密码加密
        return null;
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("密码不正确");
            }
            //登录成功的用户信息和权限
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //生成token
            token = jwtTokenUtil.generateToken(userDetails);
        } catch (AuthenticationException e) {
            //使用日志占位符，只有在用到的时候才会动态的创建,避免+创建新的String对象，占用不必要的内存
            LOGGER.warn(":登录异常{}", e.getMessage());
        }
        return token;
    }

    @Override
    public List<UmsPermission> getPermissionList(Long adminId) {
        List<UmsPermission> result = umsAdminRoleRelationMapper.getPermissionList(adminId);
        return result;
    }
}
