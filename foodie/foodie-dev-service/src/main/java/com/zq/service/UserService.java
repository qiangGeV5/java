package com.zq.service;


import com.zq.pojo.Users;
import com.zq.pojo.bo.UserBO;

public interface UserService {

    public boolean queryUserNameIsExist(String username);

    public Users createUser(UserBO userBO);

    public Users queryUserForLogin(String username,String password);

}
