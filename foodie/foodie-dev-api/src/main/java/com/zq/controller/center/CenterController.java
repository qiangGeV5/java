package com.zq.controller.center;

import com.zq.pojo.Users;
import com.zq.service.center.CenterUserService;
import com.zq.utils.ZQJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "用户中心",tags = {"用户中心相关接口"})
@RequestMapping("center")
@RestController
public class CenterController {
    @Autowired
    private CenterUserService centerUserService;

    @ApiOperation(value = "获取用户信息",notes = "获取用户信息",httpMethod = "GET")
    @GetMapping("/userInfo")
    public ZQJSONResult userInfo(
            @ApiParam(name = "userId",value = "用户ID",required = true)
            @RequestParam String userId){

        Users users = centerUserService.queryUserInfo(userId);
        return ZQJSONResult.ok(users);

    }
}
