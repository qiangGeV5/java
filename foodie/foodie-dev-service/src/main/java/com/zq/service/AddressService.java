package com.zq.service;

import com.zq.pojo.Carousel;
import com.zq.pojo.UserAddress;
import com.zq.pojo.bo.AddressBO;

import java.util.List;

public interface AddressService {

    /**
     * 查询所有收货地址列表
     * @param userId
     * @return
     */
    public List<UserAddress> queryAll(String userId);

    /**
     * 用户新增收货地址
     * @param addressBO
     */
    public void addNewUserAddress(AddressBO addressBO);

    /**
     * 修改用户收货地址
     * @param addressBO
     */
    public void updateNewUserAddress(AddressBO addressBO);

    /**
     * 删除地址
     * @param userId
     * @param addressId
     */
    public void deleteAddress(String userId,String addressId);

    /**
     * 设置默认地址
     * @param userId
     * @param addressId
     */
    public void updateUserAddressToBeDefault(String userId,String addressId);

    /**
     * 根据用户id和地址id查询用户地址信息
     * @param userId
     * @param addressId
     * @return
     */
    public UserAddress queryUserAddress(String userId,String addressId);
}
