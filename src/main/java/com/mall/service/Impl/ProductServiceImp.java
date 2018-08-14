package com.mall.service.Impl;

import com.mall.common.ResponseCode;
import com.mall.common.ServerResponse;
import com.mall.dao.CategoryMapper;
import com.mall.dao.ProductMapper;
import com.mall.pojo.Category;
import com.mall.pojo.Product;
import com.mall.service.IProductService;
import com.mall.util.PropertiesUtil;
import com.mall.vo.ProductDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("iProductService")
public class ProductServiceImp implements IProductService {

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ServerResponse saveOrUpdateProduct(Product product) {
        if(product != null){
            String[] subImageArray = product.getSubImages().split(",");
            if(subImageArray.length > 0){
                product.setMainImage(subImageArray[0]);
            }
            if (product.getId() != null){
                 int rowCount = productMapper.updateByPrimaryKey(product);
                 if(rowCount > 0){
                     return ServerResponse.createBySuccessMessage("更新产品成功");
                 }
                return ServerResponse.createByErrorMessage("更新产品失败");
            }else{
                int rowCount = productMapper.insert(product);
                if(rowCount > 0){
                    return ServerResponse.createBySuccessMessage("增加产品成功");
                }
                return ServerResponse.createByErrorMessage("增加产品失败");
            }
        }
        return ServerResponse.createByErrorMessage("新增或更新产品参数不正确");
    }

    @Override
    public ServerResponse setSaleStatus(Integer productId, Integer status) {
        if(productId == null || status == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = new Product();
        product.setId(product.getId());
        product.setStatus(product.getStatus());
        int rowCount = productMapper.updateByPrimaryKeySelective(product);
        if(rowCount > 0){
            return ServerResponse.createBySuccessMessage("修改产品销售状态成功");
        }
        return ServerResponse.createByErrorMessage("修改产品销售状态失败");
    }

    @Override
    public ServerResponse<Object> manageProductDetail(Integer productId) {
        if(productId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if(product == null){
            return ServerResponse.createByErrorMessage("产品已下架或者删除");
        }
        //VO对象--value  object
        //pojo->bo(business object)->vo(view object)
        ProductDetailVo productDetailVo =
        return null;
    }

    private void assembleProductDetailVo(Product product){
        ProductDetailVo productDetailVo = new ProductDetailVo();
        productDetailVo.setId(product.getId());
        productDetailVo.setSubtitle(product.getSubtitle());
        productDetailVo.setPrice(product.getPrice());
        product.setSubImages(product.getSubImages());
        product.setMainImage(product.getMainImage());
        productDetailVo.setCategoryId(product.getCategoryId());
        productDetailVo.setDetail(product.getDetail());
        productDetailVo.setName(product.getName());
        productDetailVo.setStock(product.getStock());
        product.setStatus(product.getStatus());

        productDetailVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.happymmall.com/"));
        Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
        if(category == null){
            productDetailVo.setParentCategoryId(0);//默认是根节点
        }else{
            productDetailVo.setParentCategoryId(category.getParentId());
        }
        //imageHost
        //parentCategoryId
        //createTime
        //updateTime
    }


}
