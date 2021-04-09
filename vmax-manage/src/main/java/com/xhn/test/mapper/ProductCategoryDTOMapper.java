package com.xhn.test.mapper;

import com.xhn.test.entity.ProductCategoryDTO;

public interface ProductCategoryDTOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductCategoryDTO record);

    int insertSelective(ProductCategoryDTO record);

    ProductCategoryDTO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductCategoryDTO record);

    int updateByPrimaryKeyWithBLOBs(ProductCategoryDTO record);

    int updateByPrimaryKey(ProductCategoryDTO record);
}