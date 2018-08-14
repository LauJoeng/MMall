package com.mall.service;

import com.mall.common.ServerResponse;

public interface ICategoryService {
    ServerResponse addCategory(String categoryName,Integer parentId);
    ServerResponse updateCategoryName(Integer categoryId,String categoryName);
}
