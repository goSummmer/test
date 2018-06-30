package com.kedacom;

import com.kedacom.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RedisTest {

    @Autowired
    private RedisUtil redisService;

    @Test
    public void testRedisSet(){
        log.info("向redis存储数据。");
        Assert.assertTrue(redisService.set("1", "111110"));
    }

    @Test
    public void testRedisGet(){
        String value = (String) redisService.get("1");
        log.info("redis读取数据：" + value);
    }
}
