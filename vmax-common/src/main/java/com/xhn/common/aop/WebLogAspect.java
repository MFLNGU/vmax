package com.xhn.common.aop;

import com.xhn.common.annotation.ApiOperation;
import com.xhn.common.pojo.entity.WebLogDO;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: vmax
 * @description: 统一日志处理切面
 * @author: mfl
 * @create: 2021-03-28 22:24
 **/
@Aspect
public class WebLogAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebLogAspect.class);

    @Around(value = "@annotation(com.xhn.common.annotation.WebLogAnnotation)")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();

        //获取请求信息
        WebLogDO webLogDO = new WebLogDO();
        String url = request.getRequestURL().toString();

       // webLogDO.setBasePath(StrUtil);todo
        webLogDO.setUri(request.getRequestURI());
        webLogDO.setIp(request.getRemoteUser());
        webLogDO.setMethod(request.getMethod());
        webLogDO.setResult(result);
        webLogDO.setSpendTime((int) (endTime - startTime));
        webLogDO.setStartTime(startTime);
        //设置方法参数
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        if (method.isAnnotationPresent(ApiOperation.class)) {
            ApiOperation annotation = method.getAnnotation(ApiOperation.class);
            webLogDO.setDescription(annotation.value());
        }

        webLogDO.setParameter(getParameters(method, joinPoint.getArgs()));
        LOGGER.debug("{}", webLogDO);
        return result;
    }

    private Object getParameters(Method method, Object[] args) {
        List<Object> argList = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            //参数由@RequestBody修饰
            if (parameters[i].isAnnotationPresent(RequestBody.class)) {
                argList.add(args[i]);
            }
            RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
            if (requestParam != null) {
                Map<String, Object> map = new HashMap<>();
                String key = parameters[i].getName();
                if (requestParam.value() != null) {
                    key = requestParam.value();
                }
                map.put(key, args[i]);
            }
        }
        if (CollectionUtils.isEmpty(argList)) {
            return null;
        } else if (argList.size() == 1) {
            return argList.get(0);
        } else {
            return argList;
        }
    }
}
