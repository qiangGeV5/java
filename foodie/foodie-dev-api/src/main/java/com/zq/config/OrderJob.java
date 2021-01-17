package com.zq.config;

import com.zq.service.OrderService;
import com.zq.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//定时任务
@Component
public class OrderJob {

    @Autowired
    private OrderService orderService;

    //定时任务执行时间 表达式设置网址：https://cron.qqe2.com
    @Scheduled(cron = "0 0 0 1/1 * ?")
    public void autoCloseOrder(){

        orderService.closeOrder();
        System.out.print("===========定时任务执行时间"+
                DateUtil.getCurrentDateString(DateUtil.DATETIME_PATTERN));
    }
}
