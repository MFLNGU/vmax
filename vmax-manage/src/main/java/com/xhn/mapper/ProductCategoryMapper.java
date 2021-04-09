package com.xhn.mapper;

import com.xhn.entity.po.ProductCategoryDO;

public interface ProductCategoryMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ProductCategoryDO record);

    int insertSelective(ProductCategoryDO record);

    ProductCategoryDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductCategoryDO record);

    int updateByPrimaryKey(ProductCategoryDO record);
}