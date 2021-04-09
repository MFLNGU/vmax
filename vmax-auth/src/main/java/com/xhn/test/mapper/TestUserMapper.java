package com.xhn.test.mapper;

import com.xhn.test.model.TestUser;

public interface TestUserMapper {
    int insert(TestUser record);

    int insertSelective(TestUser record);
}