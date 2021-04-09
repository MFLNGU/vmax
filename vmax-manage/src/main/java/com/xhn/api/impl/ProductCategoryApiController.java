package com.xhn.api.impl;

import com.xhn.api.ProductCategoryApi;
import com.xhn.entity.dto.ProductCategoryDTO;
import com.xhn.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

/**
 * @program: vmax
 * @description:
 * @author: mfl
 * @create: 2021-03-28 19:57
 **/
@Controller
public class ProductCategoryApiController implements ProductCategoryApi {
    @Autowired
    ProductCategoryService productCategoryService;
    @Override
    public ResponseEntity<Void> createProductCategory(ProductCategoryDTO productCategoryDTO) {
        productCategoryService.createProductCategory(productCategoryDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
