package com.zq.interceptor;

import com.zq.utils.JsonUtils;
import com.zq.utils.RedisOperator;
import com.zq.utils.ZQJSONResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;


public class UserTokenInterceptor implements HandlerInterceptor {

    public static final String REDIS_USER_TOKEN = "redis_user_token";

    @Autowired
    private RedisOperator redisOperator;
    /**
     * 拦截请求
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String userId = request.getHeader("headerUserId");
        String userToken = request.getHeader("headerUserToken");
        System.out.print("===============拦截请求");

        if (StringUtils.isNotBlank(userId)&&StringUtils.isNotBlank(userToken)){
            String uniqueToken = redisOperator.get(REDIS_USER_TOKEN+":"+userId);
            if (StringUtils.isNotBlank(uniqueToken)){
                if (!uniqueToken.equals(userToken)){
                    return false;
                }
            }else {
                returnErrorResponse(response,ZQJSONResult.errorMsg("请登录"));
                return false;
            }

        }else {
            returnErrorResponse(response,ZQJSONResult.errorMsg("请登录"));
            return false;

        }

        return true;
    }


    public void returnErrorResponse(HttpServletResponse response, ZQJSONResult result) throws IOException {
        OutputStream out = null;
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/json");
            out = response.getOutputStream();
            out.write(JsonUtils.objectToJson(result).getBytes("utf-8"));
            out.flush();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (out != null){
                out.close();
            }
        }
    }


    /**
     * 请求controller 之后，渲染视图之前
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 请求controller 之后，渲染视图之后
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
