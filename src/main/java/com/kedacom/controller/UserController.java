package com.kedacom.controller;

import com.kedacom.common.ResultMessage;
import com.kedacom.repository.UserRepository;
import com.kedacom.entity.UserEntity;
import com.kedacom.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * created by yr on 2018/06/26
 */
@Api(value = "用户Controller", tags = "用户信息操作接口")
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @ApiOperation(value = "获取所有用户信息", notes = "获取所有用户信息")
    @RequestMapping(value = "/user",method = RequestMethod.GET )
    public List<UserEntity> findAll(){
        return userService.findAll();
    }

    @ApiOperation(value = "获取用户信息", notes = "根据url的id获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户id", required = true , dataType = "Integer")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResultMessage<UserEntity> findOne(@PathVariable("id")Integer id){
        ResultMessage<UserEntity> resultMessage = new ResultMessage<UserEntity>();

        UserEntity user = userService.findOneById(id);
        if(null == user){
            resultMessage.setMssage("未查询到用用户信息！");
            resultMessage.setCode(500);
            return resultMessage;
        }
        resultMessage.setData(user);
        return resultMessage;
    }

    @ApiOperation(value = "新增或修改用户信息", notes = "根据传过来的userEntity信息进行更新或新增")
    @ApiImplicitParam(name = "userEntity", value = "用户详细实体类userEntity", required = true, dataType = "UserEntity")
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public List<UserEntity> saveOrUpdate(UserEntity userEntity){
        userRepository.save(userEntity);
        return userRepository.findAll();
    }

    @ApiOperation(value = "删除用户的信息", notes = "根据url的id删除用户的信息")
    @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "Integer")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public List<UserEntity> delete(@PathVariable("id") Integer id){
        userService.delete(id);
        return userRepository.findAll();
    }

    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    public UserEntity getUser(@Param("name") String name){
        return userService.findOne(name);
    }


}
