package com.zq.exception;

import com.zq.utils.ZQJSONResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler
    public ZQJSONResult handlerMaxUploadFile(Exception exception){
        return ZQJSONResult.errorMsg("文件上传大小不能超过500k");
    }

}
