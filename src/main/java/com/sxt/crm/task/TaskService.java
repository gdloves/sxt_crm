package com.sxt.crm.task;

import com.sxt.crm.service.CustomerServer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class TaskService {
    @Resource
    private CustomerServer customerServer;

    //定时流失
    // @Scheduled(cron = "0/2 * * * * ?")
    public void jobs(){
        System.out.println("定时任务开始执行-->"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        customerServer.updateCustomerState();
    }
}
