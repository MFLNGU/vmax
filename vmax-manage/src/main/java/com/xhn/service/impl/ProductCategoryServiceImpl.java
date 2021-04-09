package com.xhn.service.impl;

import com.xhn.entity.dto.ProductCategoryDTO;
import com.xhn.entity.po.ProductCategoryDO;
import com.xhn.mapper.ProductCategoryMapper;
import com.xhn.service.ProductCategoryService;
import com.xhn.util.IdUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @program: vmax
 * @description:
 * @author: mfl
 * @create: 2021-03-28 19:58
 **/
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    ProductCategoryMapper productCategoryMapper;

    @Override
    public void createProductCategory(ProductCategoryDTO productCategoryDTO) {
        ProductCategoryDO productCategoryDO = new ProductCategoryDO();
        BeanUtils.copyProperties(productCategoryDTO,productCategoryDO);
        productCategoryDO.setId(IdUtils.getId());
        Long userId = 1L;
        productCategoryDO.setCreatedBy(userId);
        productCategoryDO.setCreatedTime(new Date());
       productCategoryMapper.insert(productCategoryDO);
    }
    public static void main(String[] args) {


    }

}


=