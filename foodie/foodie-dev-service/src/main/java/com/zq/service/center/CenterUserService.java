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

    /**
     * 更新用户头像
     * @param userId
     * @param faceUrl
     * @return
     */
    public Users updateUserFace(String userId, String faceUrl);

}
