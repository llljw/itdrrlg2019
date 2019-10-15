package com.itdr.common;

import lombok.Data;

@Data
public class ResponseCode<T> {
    private Integer status;
    private T data;
    private String msg;

    //成功的时候只要返回状态码和成功获取的数据就可以了
    public static<T> ResponseCode successRS(T data){
        ResponseCode rs = new ResponseCode();
        rs.setStatus(1);
        rs.setData(data);
        return rs;
    }

    //失败的时候只要返回状态码和失败的信息就可以了
    public static<T> ResponseCode defeatedRS(String msg){
        ResponseCode rs = new ResponseCode();
        rs.setStatus(0);
        rs.setMsg(msg);
        return rs;
    }

}
