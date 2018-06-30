package com.kedacom.entity;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

/**
 * created by yr on 2016/06/26
 */
@ApiModel(value = "user对象", description = "用户对象user")
@Table(name = "user")
@Entity
@Data
@ToString
public class UserEntity implements Serializable {
    /**
     * 主键一定要有：@GeneratedValue(strategy = GenerationType.IDENTITY)注解，
     * 否则，在新增数据时会报错。
     */
    @ApiModelProperty(value = "用户id", name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @ApiModelProperty(value = "用户名", name = "name")
    @Column(name = "name")
    private String name;

    @ApiModelProperty(value = "年龄", name = "age")
    @Column(name = "age")
    private Integer age;

    @ApiModelProperty(value = "地址", name = "address")
    @Column(name = "address")
    private String address;
}
