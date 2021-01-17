package com.zq.service.center;


import com.zq.pojo.Users;


public interface CenterUserService {

    /**
     * 用户信息
     * @param userId
     * @return
     */
    public Users queryUserInfo(String userId);

}
