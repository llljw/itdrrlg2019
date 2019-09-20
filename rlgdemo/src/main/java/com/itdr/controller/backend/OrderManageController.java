package com.itdr.controller.backend;

import com.itdr.common.ServerResponse;
import com.itdr.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * User: Jwen
 * Date: 2019/9/7
 * Time: 13:42
 */
@Controller
@ResponseBody
@RequestMapping("/manage/order/")
public class OrderManageController {

    @Autowired
    private OrderService orderService;

    /*订单列表*/
    @RequestMapping("list.do")
    public ServerResponse list() {
        ServerResponse sr = orderService.selectAll();
        return sr;
    }

    /*订单号查询*/
    @RequestMapping("search.do")
    public ServerResponse search(Long order_no) {
        ServerResponse sr = orderService.selectOne(order_no);
        return sr;
    }

    /*订单详情*/
    @RequestMapping("detail.do")
    public ServerResponse detail(Integer id){
        ServerResponse sr = orderService.selectOneById(id);
        return sr;
    }

}
