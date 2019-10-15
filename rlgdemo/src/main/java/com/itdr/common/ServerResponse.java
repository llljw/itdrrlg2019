package com.itdr.common;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * User: Jwen
 * Date: 2019/9/10
 * Time: 15:23
 */

@Getter
@Setter
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ServerResponse<T> implements Serializable {
    private Integer status;
    private T data;
    private String msg;


    //获取成功的对象(状态码和数据
    private ServerResponse(Integer status, T data) {
        this.status=status;
        this.data = data;
    }

    //获取成功对象(状态码数据、信息
    private ServerResponse(Integer status, T data, String msg) {
        this.status=status;
        this.data = data;
        this.msg = msg;
    }

    //获取失败对象(状态码和信息
    private ServerResponse(Integer status, String msg) {
        this.status=status;
        this.msg = msg;
    }

    //获取失败对象(信息
    private ServerResponse(String msg) {
        this.msg = msg;
    }


    //成功的时候只传入数据
    public static <T> ServerResponse successRS(T data) {
        return new ServerResponse(Const.SUCCESS,data);
    }

    //成功的时候只传入状态信息
    public static <T> ServerResponse successRS(String msg) {
        return new ServerResponse(Const.SUCCESS,msg);
    }

    //成功的时候传入数据、信息
    public static <T> ServerResponse successRS(T data, String msg) {
        return new ServerResponse(Const.SUCCESS, data, msg);
    }

    //失败的时候只要传入状态码和信息
    public static <T> ServerResponse defeatedRS(Integer status, String msg) {
        return new ServerResponse(status, msg);
    }

    //失败的时候只要传入信息
    public static <T> ServerResponse defeatedRS(String msg) {
        return new ServerResponse(msg);
    }

    //判断
//    @JsonIgnore
//    public Boolean isSuccess(){
//        return Const.SUCCESS == 0;
//    }

}
