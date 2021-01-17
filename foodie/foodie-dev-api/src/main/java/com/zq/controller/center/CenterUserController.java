package com.zq.controller.center;

import com.zq.pojo.Users;
import com.zq.pojo.bo.center.CenterUserBO;
import com.zq.service.center.CenterUserService;
import com.zq.utils.CookieUtils;
import com.zq.utils.JsonUtils;
import com.zq.utils.ZQJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Api(value = "用户信息",tags = {"用户信息相关接口"})
@RequestMapping("userInfo")
@RestController
public class CenterUserController {
    @Autowired
    private CenterUserService centerUserService;

    @ApiOperation(value = "获取用户信息",notes = "获取用户信息",httpMethod = "POST")
    @PostMapping("/update")
    public ZQJSONResult update(
            @ApiParam(name = "userId",value = "用户ID",required = true)
            @RequestParam String userId,
            @RequestBody @Valid CenterUserBO userBO,
            HttpServletRequest request,
            HttpServletResponse response
            ){

        Users users = centerUserService.updateUserInfo(userId,userBO);

        users = setNullProperty(users);

        CookieUtils.setCookie(request,response,"user", JsonUtils.objectToJson("users"),true);

        //TODO 后续修改整合redis 添加token

        return ZQJSONResult.ok();

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
}
