package com.zq.service.impl;

import com.zq.enums.Sex;
import com.zq.mapper.UsersMapper;
import com.zq.pojo.Users;
import com.zq.pojo.bo.UserBO;
import com.zq.service.UserService;
import com.zq.utils.DateUtil;
import com.zq.utils.MD5Utils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private Sid sid;

    public static final String USER_FACE = "";

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUserNameIsExist(String username) {

        Example userExample = new Example(Users.class);
        Example.Criteria userCriteria = userExample.createCriteria();
        userCriteria.andEqualTo("username",username);

        Users users = usersMapper.selectOneByExample(userExample);


        return users == null ? false : true;

    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users createUser(UserBO userBO){
        String userid = new Sid().nextShort();

        Users users = new Users();
        users.setId(userid);
        users.setUsername(userBO.getUsername());
        try{
            users.setPassword(MD5Utils.getMD5Str(userBO.getPassword()));
        }catch (Exception e){
            e.printStackTrace();
        }

        users.setNickname(userBO.getUsername());
        users.setFace(USER_FACE);
        users.setBirthday(DateUtil.stringToDate("1900-01-01"));
        //默认性别保密
        users.setSex(Sex.secret.type);
        users.setCreatedTime(new Date());
        users.setUpdatedTime(new Date());

        int insert = usersMapper.insert(users);

        return users;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserForLogin(String username, String password) {

        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();

        criteria.andEqualTo("username",username);
        criteria.andEqualTo("password",password);

        Users users = usersMapper.selectOneByExample(example);

        return users;
    }

}



































