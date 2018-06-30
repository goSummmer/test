package com.kedacom.repository;

import com.kedacom.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.List;

/**
 * created by yr on 2018/06/26
 */
public interface UserRepository extends
        JpaRepository<UserEntity, Integer>,
        JpaSpecificationExecutor<UserEntity>,
        QueryDslPredicateExecutor<UserEntity>,
        Serializable {

    /**
     * 使用注解@Query的方式来
     * 注意：使用的sql语句对应的不是数据库表名，而是对应的实体类
     * @return List<UserEntity> 用户信息集合
     */
    @Query("select t from UserEntity t where t.age = :age")
    List<UserEntity> getByAge(@Param("age") Integer age);

    /**
     * 根据地址来查询全部信息
     * @param address
     * @return
     */
    @Query("select t from UserEntity t where t.address = :address")
    UserEntity getByAddress(@Param("address") String address);

    /**
     * 使用原始SQL语句 需要设置 nativeQuery = true
     * @param
     * @return  集合
     */
    @Query(value = "select count(id) from t_user", nativeQuery = true)
    long getTotalCount();

    /**
     * update、delete 时要加上@Modifying ，同时还要使用设计一个service来添加事物
     * @Modifying 这个注解只提供只读事物
     * @param age
     * @param id
     */
    @Modifying
    @Query("update UserEntity t set t.age = :age where t.id = :id")
    void updateUserAge(@Param("age")Integer age, @Param("id")Integer id);

    /**
     * 根据id删除用户信息
     * @param id
     */
    @Modifying
    @Query("delete from UserEntity t where t.id = :id")
    void deleteUserById(@Param("id")Integer id);

}
