package com.kedacom.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 响应信息
 * package: com.kedacom.common
 * author: yaorui
 * date : 2018/6/29
 * Copyright @ Corporation 2018
 * 苏州科达科技   版权所有
 */
@ApiModel(value = "返回数据包装类", description = "用于包装处理返回数据")
@Data
public class ResultMessage<T> implements Serializable {

    private static final long serialVersionUID = 9055564808299721335L;

    @ApiModelProperty(value = "业务状态码", name = "code")
    protected Integer code;

    @ApiModelProperty(value = "异常信息", name = "message")
    protected String mssage;

    @ApiModelProperty(value = "成功时响应内容", name = "data")
    protected T data;

    @ApiModelProperty(value = "时间戳")
    protected long timestamp;

    @ApiModelProperty(value = "响应时间，单位毫秒")
    protected long tooktime;

}
