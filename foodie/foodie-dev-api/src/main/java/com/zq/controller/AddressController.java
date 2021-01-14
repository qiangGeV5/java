package com.zq.controller;

import com.zq.pojo.UserAddress;
import com.zq.pojo.bo.AddressBO;
import com.zq.pojo.bo.ShopcartBO;
import com.zq.service.AddressService;
import com.zq.utils.MobileEmailUtils;
import com.zq.utils.ZQJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(value = "地址相关",tags = {"地址相关接口"})
@RestController
@RequestMapping("address")
@Log4j
public class AddressController {

    @Autowired
    private AddressService addressService;
    /**
     *查询用户收货地址
     * @param userId
     * @return
     */
    @ApiOperation(value = "根据用户id查询收货地址", notes = "根据用户id查询收货地址",httpMethod = "POST")
    @PostMapping("/list")
    public ZQJSONResult list(
            @ApiParam(name = "userId",value = "用户ID",required = true)
            @RequestParam String userId){
        if (StringUtils.isBlank(userId)){
            return ZQJSONResult.errorMsg("");
        }
        List<UserAddress> userAddresses = addressService.queryAll(userId);
        return ZQJSONResult.ok(userAddresses);
    }
    /**
     * 用户添加地址
     * @param addressBO
     * @return
     */
    @ApiOperation(value = "添加收货地址", notes = "添加收货地址",httpMethod = "POST")
    @PostMapping("/add")
    public ZQJSONResult add(
            @ApiParam(name = "addressBO",value = "addressBO",required = true)
            @RequestBody AddressBO addressBO){

        ZQJSONResult zqjsonResult = checkAddress(addressBO);
        if (zqjsonResult.getStatus()!= 200){
            return zqjsonResult;
        }
        addressService.addNewUserAddress(addressBO);
        return ZQJSONResult.ok();
    }

    private ZQJSONResult checkAddress(AddressBO addressBO) {
        String receiver = addressBO.getReceiver();
        if (StringUtils.isBlank(receiver)) {
            return ZQJSONResult.errorMsg("收货人不能为空");
        }
        if (receiver.length() > 12) {
            return ZQJSONResult.errorMsg("收货人姓名不能太长");
        }

        String mobile = addressBO.getMobile();
        if (StringUtils.isBlank(mobile)) {
            return ZQJSONResult.errorMsg("收货人手机号不能为空");
        }
        if (mobile.length() != 11) {
            return ZQJSONResult.errorMsg("收货人手机号长度不正确");
        }
        boolean isMobileOk = MobileEmailUtils.checkMobileIsOk(mobile);
        if (!isMobileOk) {
            return ZQJSONResult.errorMsg("收货人手机号格式不正确");
        }

        String province = addressBO.getProvince();
        String city = addressBO.getCity();
        String district = addressBO.getDistrict();
        String detail = addressBO.getDetail();
        if (StringUtils.isBlank(province) ||
                StringUtils.isBlank(city) ||
                StringUtils.isBlank(district) ||
                StringUtils.isBlank(detail)) {
            return ZQJSONResult.errorMsg("收货地址信息不能为空");
        }

        return ZQJSONResult.ok();
    }

    /**
     * 修改收货地址
     * @param addressBO
     * @return
     */
    @ApiOperation(value = "修改收货地址", notes = "修改收货地址",httpMethod = "POST")
    @PostMapping("/update")
    public ZQJSONResult update(
            @ApiParam(name = "addressBO",value = "addressBO",required = true)
            @RequestBody AddressBO addressBO){

        if (StringUtils.isBlank(addressBO.getAddressId())){
            return ZQJSONResult.errorMsg("");
        }

        ZQJSONResult zqjsonResult = checkAddress(addressBO);
        if (zqjsonResult.getStatus()!= 200){
            return zqjsonResult;
        }
        addressService.updateNewUserAddress(addressBO);
        return ZQJSONResult.ok();
    }

    /**
     * 删除收货地址
     * @param userId
     * @return
     */
    @ApiOperation(value = "删除收货地址", notes = "删除收货地址",httpMethod = "POST")
    @PostMapping("/delete")
    public ZQJSONResult delete(
            @ApiParam(name = "userId",value = "userId",required = true)
            @RequestParam String userId,
            @ApiParam(name = "addressId",value = "addressId",required = true)
            @RequestParam String addressId){

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)){
            return ZQJSONResult.errorMsg("");
        }

        addressService.deleteAddress(userId,addressId);
        return ZQJSONResult.ok();
    }

    /**
     * 设置默认地址
     * @param userId
     * @return
     */

    @ApiOperation(value = "删除收货地址", notes = "删除收货地址",httpMethod = "POST")
    @PostMapping("/setDefalut")
    public ZQJSONResult setDefault(
            @ApiParam(name = "userId",value = "userId",required = true)
            @RequestParam String userId,
            @ApiParam(name = "addressId",value = "addressId",required = true)
            @RequestParam String addressId){

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)){
            return ZQJSONResult.errorMsg("");
        }

        addressService.updateUserAddressToBeDefault(userId,addressId);
        return ZQJSONResult.ok();
    }





}
