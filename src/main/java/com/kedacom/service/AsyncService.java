package com.kedacom.service;

import org.springframework.stereotype.Service;

/**
 * 异步定时任务
 * @author tobeng
 * @date 2018/7/7 0:36
 */

@Service
public class AsyncService {

    public void hello(){
        try {
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("q请求数据处理中...");
    }

}
