package com.zq.controller;

import com.zq.service.UserService;
import com.zq.utils.ZQJSONResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
