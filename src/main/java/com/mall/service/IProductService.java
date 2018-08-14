package com.mall.service;

import com.mall.common.ServerResponse;
import com.mall.pojo.Product;

public interface IProductService {
    ServerResponse saveOrUpdateProduct(Product product);
    ServerResponse setSaleStatus(Integer productId,Integer status);
    ServerResponse<Object>manageProductDetail(Integer productId);
}
