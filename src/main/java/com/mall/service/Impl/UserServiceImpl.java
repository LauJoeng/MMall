package com.mall.service.Impl;

import com.mall.common.Const;
import com.mall.common.ServerResponse;
import com.mall.dao.UserMapper;
import com.mall.pojo.User;
import com.mall.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse<User> login(String username, String password) {
        int resultCount = userMapper.checkUsername(username);
        if(resultCount == 0){
            return ServerResponse.createByErrorMessage("用户名不存在");
        }
        //todo 密码登录MD5
        User user = userMapper.selectLogin(username,password);
        if(user == null){
            return ServerResponse.createByErrorMessage("密码错误");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登录成功",user);
    }

    public ServerResponse<String>register(User user){
        int resultCount = userMapper.checkUsername(user.getUsername());
        if(resultCount > 0){
            return ServerResponse.createByErrorMessage("用户名已存在");
        }
        resultCount = userMapper.checkEmail(user.getEmail());
        if(resultCount > 0){
            return ServerResponse.createByErrorMessage("email已存在");
        }
        user.setRole(Const.Role.ROLE_CUSTOMER);
        //MD5加密

        return null;
    }
}
