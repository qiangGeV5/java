package com.zq.controller.center;

import com.zq.controller.BaseController;
import com.zq.pojo.Users;
import com.zq.pojo.bo.center.CenterUserBO;
import com.zq.service.center.CenterUserService;
import com.zq.utils.CookieUtils;
import com.zq.utils.JsonUtils;
import com.zq.utils.ZQJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.Binding;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "用户信息",tags = {"用户信息相关接口"})
@RequestMapping("userInfo")
@RestController
public class CenterUserController extends BaseController {
    @Autowired
    private CenterUserService centerUserService;

    @ApiOperation(value = "修改用户头像",notes = "修改用户头像",httpMethod = "POST")
    @PostMapping("/uploadFace")
    public ZQJSONResult uploadFace(
            @ApiParam(name = "userId",value = "用户ID",required = true)
            @RequestParam String userId,
            @ApiParam(name = "file",value = "用户头像",required = true)
            MultipartFile file,
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        FileOutputStream fileOutputStream = null;
        //用户保存地址
        String fileSpace = IMAGE_USER_FACE;
        String uploadPathPrefix = File.separator + userId;

        if (file != null){
           try {
               String fileName = file.getOriginalFilename();
               if (StringUtils.isNotBlank(fileName)){
                   //文件重命名
                   String fileNameArr[]= fileName.split("\\.");
                   //文件后缀名
                   String suffix = fileNameArr[fileNameArr.length - 1];
                   //文件重命名 （覆盖式上传）
                   String newFileName = "face-" + userId + "." +suffix;
                   //文件头像最终上传位置
                   String finalFacePath = fileSpace + uploadPathPrefix + File.separator + newFileName;
                   File outFile = new File(finalFacePath);
                   if (outFile.getParentFile() != null){
                       //创建文件夹
                       outFile.getParentFile().mkdirs();
                   }
                   //文件输出保存到目录
                   fileOutputStream = new FileOutputStream(outFile);
                   InputStream inputStream = file.getInputStream();
                   IOUtils.copy(inputStream,fileOutputStream);
               }
           }catch (IOException e){
               e.printStackTrace();
           }finally {

               try {
                   if (fileOutputStream != null){
                       fileOutputStream.flush();
                       fileOutputStream.close();
                   }
               }catch (IOException e){
                   e.printStackTrace();
               }

           }
        }
        else {
            return ZQJSONResult.errorMsg("");
        }
        return ZQJSONResult.ok();

    }

    @ApiOperation(value = "获取用户信息",notes = "获取用户信息",httpMethod = "POST")
    @PostMapping("/update")
    public ZQJSONResult update(
            @ApiParam(name = "userId",value = "用户ID",required = true)
            @RequestParam String userId,
            @RequestBody @Valid CenterUserBO userBO,
            BindingResult result,
            HttpServletRequest request,
            HttpServletResponse response
            ){

        if (result.hasErrors()){
            Map<String, String> errors = getErrors(result);
            return ZQJSONResult.errorMap(errors);
        }

        Users users = centerUserService.updateUserInfo(userId,userBO);

        users = setNullProperty(users);

        CookieUtils.setCookie(request,response,"user", JsonUtils.objectToJson("users"),true);

        //TODO 后续修改整合redis 添加token

        return ZQJSONResult.ok();

    }

    private Map<String,String> getErrors(BindingResult result){
        List<FieldError> errorList = result.getFieldErrors();
        Map<String,String> map = new HashMap<>();
        for (FieldError error:errorList){
            //发生错误的属性
            String field = error.getField();
            String errorMsg = error.getDefaultMessage();
            map.put(field,errorMsg);
        }
        return map;
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
