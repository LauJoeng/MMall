package com.mall.service;

import com.mall.common.ServerResponse;
import com.mall.pojo.Category;
import com.mall.pojo.User;

import java.util.List;

public interface IUserService {
    ServerResponse<User> login(String username, String password);
    ServerResponse<String> register(User user);
    ServerResponse<String>checkValid(String str,String type);
    ServerResponse<String> selectQuestion(String username);
    ServerResponse<String>checkAnswer(String username,String question,String answer);
    ServerResponse<String>forgetResetPassword(String username,String newPassword,String forgetToken);
    ServerResponse<String>resetPassword(String oldPassword,String newPassword,User user);
    ServerResponse<User>updateInformation(User user);
    ServerResponse<User>getInformation(Integer userId);


    ServerResponse checkAdminRole(User user);

}
