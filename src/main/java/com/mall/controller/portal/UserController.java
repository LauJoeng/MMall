package com.mall.controller.portal;

import com.mall.common.Const;
import com.mall.common.ResponseCode;
import com.mall.common.ServerResponse;
import com.mall.pojo.User;
import com.mall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private IUserService iUserService;

    @ResponseBody
    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    public ServerResponse<User> login(String username, String password, HttpSession session){
        ServerResponse<User> response = iUserService.login(username,password);
        if(response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        System.out.println(session.getAttribute(Const.CURRENT_USER));
        return response;
    }

    @ResponseBody
    @RequestMapping(value = "logout.do",method = RequestMethod.POST)
    public ServerResponse<String>logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }

    @ResponseBody
    @RequestMapping(value = "register.do",method = RequestMethod.POST)
    public ServerResponse<String>register(User user){
        return iUserService.register(user);
    }
    @ResponseBody
    @RequestMapping(value = "check_valid.do",method = RequestMethod.POST)
    public ServerResponse<String> chekValid(String str,String type){
        return iUserService.checkValid(str,type);
    }

    @ResponseBody
    @RequestMapping(value = "get_user_info.do",method = RequestMethod.POST)
    public ServerResponse<User>getUserInfo(HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        System.out.println(user);
        if(user != null){
            return ServerResponse.createBySuccess(user);
        }
        return ServerResponse.createByErrorMessage("用户未登录，无法获取当前用户的信息");
    }

    @ResponseBody
    @RequestMapping(value = "forget_get_question.do",method = RequestMethod.POST)
    public ServerResponse<String> forgetGetQuestion(String username){
        return iUserService.selectQuestion(username);
    }
    @ResponseBody
    @RequestMapping(value = "forget_check_answer.do",method = RequestMethod.POST)
    public  ServerResponse<String>forgetCheckAnswer(String username,String question,String answer){
        return iUserService.checkAnswer(username,question,answer);
    }
    @ResponseBody
    @RequestMapping(value = "forget_reset_password.do",method = RequestMethod.POST)
    public ServerResponse<String>forgetResetPassword(String username,String newPassword,String forgetToken){
        return iUserService.forgetResetPassword(username,newPassword,forgetToken);
    }
    @ResponseBody
    @RequestMapping(value = "reset_password.do",method = RequestMethod.POST)
    public ServerResponse<String>resetPassword(HttpSession session,String oldPassword,String newPassword){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return iUserService.resetPassword(oldPassword,newPassword,user);
    }
    @ResponseBody
    @RequestMapping(value = "update_information.do",method = RequestMethod.POST)
    public ServerResponse<User>updateInformation(HttpSession session,User user){
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if(currentUser == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        user.setId(currentUser.getId());
        user.setUsername(currentUser.getUsername());
        ServerResponse<User>response = iUserService.updateInformation(user);
        if(response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }
    @ResponseBody
    @RequestMapping(value = "get_information.do",method = RequestMethod.POST)
    public ServerResponse<User>getInformation(HttpSession session){
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if(currentUser == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"未登录，需要强制登录states=10");
        }
        return iUserService.getInformation(currentUser.getId());
    }

}
