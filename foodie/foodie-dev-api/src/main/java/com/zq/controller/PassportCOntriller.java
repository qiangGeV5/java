package com.zq.controller;

import com.zq.pojo.bo.UserBO;
import com.zq.service.UserService;
import com.zq.utils.ZQJSONResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("passport")
public class PassportCOntriller {

    @Autowired
    private UserService userService;

    @GetMapping("/usernameIsExist")
    public ZQJSONResult usernameIsExist(@RequestParam String username){

        //判断用户名是否为空
        if (StringUtils.isBlank(username)){
            return ZQJSONResult.errorMsg("用户名不能为空");
        }

        //2、查找注册的用户是否存在
        boolean isExist = userService.queryUserNameIsExist(username);
        if (isExist){
            return ZQJSONResult.errorMsg("用户名已经存在");
        }

        //请求成功用户名没问题
        return ZQJSONResult.ok();
    }

    @PostMapping("/regist")
    public ZQJSONResult regist(@RequestBody UserBO userBO){

        String username = userBO.getUsername();
        String password = userBO.getPassword();
        String confirmPassword = userBO.getConfirmPassword();
        //1、判断用户名和密码不为空
        if (StringUtils.isBlank(username)){return ZQJSONResult.errorMsg("用户名不能为空");}
        if (StringUtils.isBlank(password)){return ZQJSONResult.errorMsg("密码不能为空");}
        if (StringUtils.isBlank(confirmPassword)){return ZQJSONResult.errorMsg("验证密码不能为空");}

        //2、查询用户名是否存在
        boolean isExist = userService.queryUserNameIsExist(username);
        if (isExist){
            return ZQJSONResult.errorMsg("用户名已经存在");
        }
        //3、密码长度不少于6位
        if (password.length()<6){
            return ZQJSONResult.errorMsg("密码太短了");
        }
        //4、判断两次密码是否一致
        if (!password.equals(confirmPassword)){
            return ZQJSONResult.errorMsg("两次密码不一致");
        }
        //5、实现注册
        userService.createUser(userBO);

        return ZQJSONResult.ok();

    }
}
