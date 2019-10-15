package com.itdr.controller.portal;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.itdr.common.Const;
import com.itdr.common.ServerResponse;
import com.itdr.pojo.Users;
import com.itdr.pojo.pay.Configs;
import com.itdr.services.AliPayService;
import jdk.nashorn.internal.runtime.regexp.joni.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * User: Jwen
 * Date: 2019/9/20
 * Time: 14:12
 */

@RestController
@RequestMapping("/portal/pay/")
public class AliPayController {
    @Autowired
    AliPayService aliPayService;

    //用户支付
    @RequestMapping("alipay.do")
    private ServerResponse alipay(Long orderNo, HttpSession session) {
        Users users = (Users) session.getAttribute(Const.User.LOGINUSER);
        if (users == null) {
            return ServerResponse.defeatedRS(Const.UserEnum.NOT_LOGIN_BUT_HAVE.getCode(), Const.UserEnum.NOT_LOGIN_BUT_HAVE.getDesc());
        }
        return aliPayService.alipay(orderNo, users.getId());
    }

    //查询订单支付状态
    @RequestMapping("query_order_pay_status.do")
    private ServerResponse payStatus(Long orderNo, HttpSession session) {
        Users users = (Users) session.getAttribute(Const.User.LOGINUSER);
        if (users == null) {
            return ServerResponse.defeatedRS(Const.UserEnum.NOT_LOGIN_BUT_HAVE.getCode(), Const.UserEnum.NOT_LOGIN_BUT_HAVE.getDesc());
        }
        return aliPayService.payStatus(orderNo, users.getId());
    }

    //支付宝回调
    @RequestMapping("alipay_callback.do")
    private String alipayCallback(HttpServletRequest request) {

        //获取支付宝返回的参数，返回一个map集合
        Map<String, String[]> map = request.getParameterMap();
        //获取上面集合的键的集合
        Set<String> strings = map.keySet();
        //使用迭代器遍历键集合获得值
        Iterator<String> iterator = strings.iterator();
        //创建接受参数的集合
        Map<String, String> newMap = new HashMap<>();

        //遍历迭代器，重新组装参数
        while (iterator.hasNext()) {
            //根据键获取map中的值
            String key = iterator.next();
            String[] strings1 = map.get(key);
            //遍历值的数组，重新拼装数据
            StringBuffer stringBuffer = new StringBuffer("");
            for (int i = 0; i < strings1.length; i++) {
                stringBuffer = (i == strings1.length - 1) ? stringBuffer.append(strings1[i]) : stringBuffer.append(strings1[i] + ",");
            }
            //把新的数据以键值对的方式放入一个新集合中
            newMap.put(key, stringBuffer.toString());
            System.out.println(key+stringBuffer);
        }

        //去除不必要参数
        newMap.remove("sign_type");
        try {
            //使用官方验签方法验证
            boolean b = AlipaySignature.rsaCheckV2(newMap, Configs.getAlipayPublicKey(), "utf-8", Configs.getSignType());

            //验签失败，返回错误信息
            if(!b){
                return "{'msg':'验签失败'}";
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return "{'msg':'验签失败'}";
        }

        //验签通过,去业务层
        ServerResponse sr = aliPayService.alipayCallback(newMap);

        if(sr.getStatus()==0){
            return "SUCCESS";
        }else {
            return "FAILED";
        }
    }

}
