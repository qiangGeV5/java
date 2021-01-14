package com.zq.service.impl;

import com.zq.enums.YesOrNo;
import com.zq.mapper.UserAddressMapper;
import com.zq.pojo.UserAddress;
import com.zq.pojo.bo.AddressBO;
import com.zq.service.AddressService;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;
import java.util.Date;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private UserAddressMapper userAddressMapper;

    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<UserAddress> queryAll(String userId) {
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);

        List<UserAddress> list = userAddressMapper.select(userAddress);
        return list;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void addNewUserAddress(AddressBO addressBO) {
        //判断用户有没有地址，没有把新地址设置为默认地址
        Integer isDefault = 0;
        List<UserAddress> userAddresses = this.queryAll(addressBO.getUserId());
        if (userAddresses == null || userAddresses.isEmpty()||userAddresses.size() == 0){
            isDefault = 1;
        }
        String addressId = sid.nextShort();

        //保存数据到数据库
        UserAddress userAddress = new UserAddress();
        BeanUtils.copyProperties(addressBO,userAddress);
        userAddress.setId(addressId);
        userAddress.setIsDefault(isDefault);
        userAddress.setCreatedTime(new Date());
        userAddress.setUpdatedTime(new Date());

        int insert = userAddressMapper.insert(userAddress);

    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateNewUserAddress(AddressBO addressBO) {
        String addressId = addressBO.getAddressId();

        UserAddress updateAddress = new UserAddress();
        BeanUtils.copyProperties(addressBO,updateAddress);
        updateAddress.setId(addressId);
        updateAddress.setUpdatedTime(new Date());

        userAddressMapper.updateByPrimaryKeySelective(updateAddress);

    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteAddress(String userId, String addressId) {
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);
        userAddress.setId(addressId);

        userAddressMapper.delete(userAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateUserAddressToBeDefault(String userId, String addressId) {
//        查找默认地址设置为不默认
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);
        userAddress.setIsDefault(YesOrNo.YES.type);
        List<UserAddress> userAddressList = userAddressMapper.select(userAddress);
        for (UserAddress ua : userAddressList){
            ua.setIsDefault(YesOrNo.NO.type);
            userAddressMapper.updateByPrimaryKeySelective(ua);
        }

        //        设置默认地址
        UserAddress defaultAddress = new UserAddress();
        defaultAddress.setUserId(userId);
        defaultAddress.setId(addressId);
        defaultAddress.setIsDefault(YesOrNo.YES.type);
        userAddressMapper.updateByPrimaryKeySelective(defaultAddress);

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public UserAddress queryUserAddress(String userId, String addressId) {
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);
        userAddress.setId(addressId);

        return userAddressMapper.selectOne(userAddress);
    }


}
