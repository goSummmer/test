package com.kedacom;


import com.kedacom.entity.UserEntity;
import com.kedacom.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testUserServiceDelete(){
        Integer id = 3;
        log.info("删除用户信息操作：id="+id);
        userService.deleteUserById(id);
    }

    @Test
    public void testUserServiceUpdate(){
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);
        userEntity.setAge(21);
        log.info("修改用户信息：id = "+userEntity.getId());
        userService.updateUserAge(userEntity.getId(), userEntity.getAge());
    }

    @Test
    public void testFindAll(){
        log.info("查询user表中所有记录：");
        List<UserEntity> list = userService.findAll();
        for (UserEntity user :
                list) {
            System.out.println(user.toString());
        }
    }

    @Test
    public void testFindOneByUserName(){
        log.info("根据name查询表中一条记录：");
        String name = "zhangsan";
        UserEntity userEntity = userService.findOne(name);
        Assert.assertEquals(name, userEntity.getName());
    }

    @Test
    public void testFindOneByUserNameAndAddress(){
        String name = "zhangsan";
        String address = "shanghai";
        UserEntity user = userService.findOneByUserNameAndAddress(name, address);
        log.info("根据用户名和地址查询一条信息："+ user.toString());
    }

    @Test
    public void testindUserByGroup(){
        List<UserEntity> list = userService.findUserByGroup();
        log.info("Group By使用：" + list.toString());
    }

    @Test
    public void testFindAllAndPager(){
        int offset = 1;
        int pageSize = 2;
        Page<UserEntity> page = userService.findAllAndPager(offset, pageSize);
        for (UserEntity user :
                page) {
            System.out.println(user.toString());
        }
        log.info("分页查询：" + page.toString());
    }
}
