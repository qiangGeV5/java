package com.zq.service.center;


import com.zq.pojo.Users;
import com.zq.pojo.bo.center.CenterUserBO;


public interface CenterUserService {

    /**
     * 用户信息
     * @param userId
     * @return
     */
    public Users queryUserInfo(String userId);

    /**
     * 更新用户信息
     * @param userId
     * @param centerUserBO
     */
    public Users updateUserInfo(String userId, CenterUserBO centerUserBO);

}
