package com.zq.service.impl.center;

import com.zq.enums.Sex;
import com.zq.mapper.UsersMapper;
import com.zq.pojo.Users;
import com.zq.pojo.bo.UserBO;
import com.zq.pojo.bo.center.CenterUserBO;
import com.zq.service.UserService;
import com.zq.service.center.CenterUserService;
import com.zq.utils.DateUtil;
import com.zq.utils.MD5Utils;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

@Service
public class CenterUserServiceImpl implements CenterUserService {
    @Autowired
    private UsersMapper usersMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserInfo(String userId) {

        Users users = usersMapper.selectByPrimaryKey(userId);

        users.setPassword("");
        return users;
    }

    @Override
    public Users updateUserInfo(String userId, CenterUserBO centerUserBO) {
        Users users = new Users();
        BeanUtils.copyProperties(centerUserBO,users);
        users.setId(userId);
        users.setUpdatedTime(new Date());

        usersMapper.updateByPrimaryKeySelective(users);

        return queryUserInfo(userId);

    }
}



































