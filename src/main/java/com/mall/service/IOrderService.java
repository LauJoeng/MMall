package com.mall.service;

import com.github.pagehelper.PageInfo;
import com.mall.common.ServerResponse;
import com.mall.vo.OrderVo;

import java.util.Map;

public interface IOrderService {
    ServerResponse  pay (Long orderNo,Integer userId,String path);

    ServerResponse aliCallback(Map<String,String>params);

    ServerResponse<Boolean> queryOrderPayStatus(Integer userId,Long orderNo);

    ServerResponse createOrder(Integer userId,Integer shippingId);

    ServerResponse<String> cancel(Integer userId,Long orderNo);

    ServerResponse getOrderCountProduct(Integer userId);

    ServerResponse<OrderVo>getOrderDetail(Integer userId,Long orderNo);

    ServerResponse<PageInfo<OrderVo>> getOrderList(Integer userId, Integer pageNum, Integer pageSize);


    //backend
    ServerResponse<PageInfo<OrderVo>>manageList(int pageNum,int pageSize);

    ServerResponse<OrderVo>manageOrderVo(Long orderNo);

    ServerResponse<PageInfo>search(Long orderNo,int pageNum,int pageSize);

    ServerResponse<String> manageSendGoods(Long orderNo);
}
