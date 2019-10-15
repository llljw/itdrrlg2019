package com.itdr.services;

import com.itdr.common.ServerResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * User: Jwen
 * Date: 2019/9/20
 * Time: 14:13
 */

public interface AliPayService {

    ServerResponse alipay(Long orderNo, Integer id);

    ServerResponse alipayCallback(Map<String, String> newMap);

    ServerResponse payStatus(Long orderNo, Integer id);
}
