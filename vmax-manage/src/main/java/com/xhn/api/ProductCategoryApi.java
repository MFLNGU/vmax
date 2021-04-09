package com.xhn.api;

import com.xhn.entity.dto.ProductCategoryDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @program: vmax
 * @description: 商品分类API
 * @author: mfl
 * @create: 2021-03-28 19:41
 **/
@Api(tags = "商品分类API")
@RequestMapping(value = "/manage/productCategory")
public interface ProductCategoryApi {

    @ApiOperation(value = "新建商品分类")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "fail")})
    @RequestMapping(value = "/createProductCategory", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<Void> createProductCategory(@RequestBody ProductCategoryDTO productCategoryDTO);
}
