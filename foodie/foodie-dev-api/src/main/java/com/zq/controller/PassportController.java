package com.zq.controller;

import com.zq.pojo.Users;
import com.zq.pojo.bo.UserBO;
import com.zq.pojo.vo.UsersVO;
import com.zq.service.UserService;
import com.zq.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Api(value = "注册登录",tags = "用于注册登录的相关接口")
@RestController
@RequestMapping("passport")
public class PassportController extends BaseController{

    @Autowired
    private UserService userService;

    @Autowired
    private RedisOperator redisOperator;


    @ApiOperation(value = "用户名是否存在", notes = "用户名是否存在",httpMethod = "GET")
    @GetMapping("/usernameIsExist")
    public ZQJSONResult usernameIsExist(@RequestParam String username){


        Logger logger = LoggerFactory.getLogger(PassportController.class);
        logger.error("============sssss============");
        logger.debug("============info============");


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

    @ApiOperation(value = "用户注册", notes = "用户注册",httpMethod = "POST")
    @PostMapping("/regist")
    public ZQJSONResult regist(@RequestBody UserBO userBO, HttpServletRequest request,
                               HttpServletResponse response){

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
        Users users = userService.createUser(userBO);


        UsersVO usersVO = conventUserVO(users);

        CookieUtils.setCookie(request, response, "user",
                JsonUtils.objectToJson(usersVO), true);



        return ZQJSONResult.ok();

    }



    @ApiOperation(value = "用户登录", notes = "用户登录",httpMethod = "POST")
    @PostMapping("/login")
    public ZQJSONResult login(@RequestBody UserBO userBO,
                              HttpServletRequest request,
                              HttpServletResponse response) throws Exception{

        String username = userBO.getUsername();
        String password = userBO.getPassword();
        //1、判断用户名和密码不为空
        if (StringUtils.isBlank(username)){return ZQJSONResult.errorMsg("用户名不能为空");}
        if (StringUtils.isBlank(password)){return ZQJSONResult.errorMsg("密码不能为空");}

        Users users = userService.queryUserForLogin(userBO.getUsername(), MD5Utils.getMD5Str(password));

        if (users == null){
            return ZQJSONResult.errorMsg("用户名密码不能为空");
        }


//        users = setNullProperty(users);

        UsersVO usersVO = conventUserVO(users);
        CookieUtils.setCookie(request, response, "user",
                JsonUtils.objectToJson(usersVO), true);

        // todo 生成用户token，存入redis会话
        // todo 同步购物车数据
        return ZQJSONResult.ok(users);
    }

    private Users setNullProperty(Users userResult) {
        userResult.setPassword(null);
        userResult.setMobile(null);
        userResult.setEmail(null);
        userResult.setCreatedTime(null);
        userResult.setUpdatedTime(null);
        userResult.setBirthday(null);
        return userResult;
    }

    @ApiOperation(value = "用户退出登录", notes = "用户退出登录",httpMethod = "POST")
    @PostMapping("/logout")
    public ZQJSONResult logout(@RequestParam String userId,
                               HttpServletRequest request,
                               HttpServletResponse response) {

        //清除用户相关cookie信息
        CookieUtils.deleteCookie(request,response,"user");

        //todo 用户退出登录，需要清空购物车
        //分布式会话中需要清楚用户数据
        redisOperator.del(REDIS_USER_TOKEN+":"+userId);

        return ZQJSONResult.ok();
    }


}
