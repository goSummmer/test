package com.kedacom.entity;

import com.kedacom.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 异步请求处理Controller
 * @author tobeng
 * @date 2018/7/7 0:53
 */

@RestController
public class AsyncController {

    @Autowired
    private AsyncService asyncService;

    @RequestMapping("/async/hello")
    public String hello(){
        asyncService.hello();
        return "nihao";
    }

}
