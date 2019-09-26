package com.itdr.utils;

import com.alipay.api.domain.ExtendParams;
import com.alipay.api.domain.GoodsDetail;
import com.alipay.api.domain.OrderItem;
import com.itdr.common.Const;
import com.itdr.pojo.*;
import com.itdr.pojo.pay.BizContent;
import com.itdr.pojo.vo.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Jwen
 * Date: 2019/9/18
 * Time: 11:01
 */
public class PoToVoUtil {
    public static ProductVO ProductToProductVO(Product p) throws IOException {
        ProductVO pvo = new ProductVO();
        pvo.setId(p.getId());
        pvo.setCategoryId(p.getCategoryId());
        pvo.setCreateTime(DateUtils.dateToStr(p.getCreateTime()));
        pvo.setDetail(p.getDetail());
        pvo.setIsBanner(p.getIsBanner());
        pvo.setIsHot(p.getIsHot());
        pvo.setIsNew(p.getIsNew());
        pvo.setImageHost(PropertiesUtil.getValue("imageHost"));
        pvo.setMainImage(p.getMainImage());
        pvo.setName(p.getName());
        pvo.setPrice(p.getPrice());
        pvo.setStatus(p.getStatus());
        pvo.setStock(p.getStock());
        pvo.setSubImages(p.getSubImages());
        pvo.setSubtitle(p.getSubtitle());
        pvo.setUpdateTime(DateUtils.dateToStr(p.getUpdateTime()));

        return pvo;
    }

    public static CartProductVO getOne(Cart cart, Product product) {

        //封装cartProductVo
        CartProductVO cartProductVO = new CartProductVO();
        cartProductVO.setId(cart.getId());
        cartProductVO.setUserId(cart.getUserId());
        cartProductVO.setProductId(cart.getProductId());
        cartProductVO.setProductChecked(cart.getChecked());

        //查询到的商品不能为空
        if (product != null) {
            cartProductVO.setName(product.getName());
            cartProductVO.setStatus(product.getStatus());
            cartProductVO.setMainImage(product.getMainImage());
            cartProductVO.setPrice(product.getPrice());
            cartProductVO.setStock(product.getStock());
            cartProductVO.setStatus(product.getStatus());
        }


        Integer count = 0;

        //处理库存问题
        //如果小于商品库存就等于下单数据
        if (cart.getQuantity() <= product.getStock()) {
            count = cart.getQuantity();
            cartProductVO.setLimitQuantity(Const.Cart.LIMITQUANTITYSUCCESS);
        } else {
            //超过商品库存就等于商品库存
            count = cart.getQuantity();
            cartProductVO.setLimitQuantity(Const.Cart.LIMITQUANTITYFAILED);
        }
        cartProductVO.setQuantity(count);

        //计算本条购物信息总价
        BigDecimal productTotalPrice = BigDecimalUtils.mul(product.getPrice().doubleValue(), cart.getQuantity());
        cartProductVO.setProductTotalPrice(productTotalPrice);

        return cartProductVO;
    }

    /*商品详情和支付宝商品类转换*/
    public static PGoodsDetail getNewPay(Order_item orderItem){
        PGoodsDetail info = new PGoodsDetail();
        info.setGoods_id(orderItem.getProductId().toString());
        info.setGoods_name(orderItem.getProductName());
        info.setPrice(orderItem.getCurrentUnitPrice().toString());
        info.setQuantity(orderItem.getQuantity().longValue());
        return info;
    }

    /*获取一个BizContent对象*/
    public static BizContent getBizContent(Order order, List<Order_item> orderItems){
        // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
        String outTradeNo = String.valueOf(order.getOrderNo());

        // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店当面付扫码消费”
        String subject = "睿乐GO在线平台"+order.getPayment();

        // (必填) 订单总金额，单位为元，不能超过1亿元
        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
        String totalAmount = String.valueOf(order.getPayment());

        // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
        // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
        String undiscountableAmount = "0";

        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
        String sellerId = "";

        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
        String body = "购买商品"+orderItems.size()+"件共"+order.getPayment()+"元";

        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
        String operatorId = "001";

        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
        String storeId = "001";

        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId("2088100200300400500");

        // 支付超时，定义为120分钟
        String timeoutExpress = "120m";

        // 商品明细列表，需填写购买商品详细信息，
        List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();
        for (Order_item OrderItem : orderItems) {
            // 创建一个商品信息，参数含义分别为商品id（使用国标）、名称、单价（单位为分）、数量，如果需要添加商品类别，详见GoodsDetail
            GoodsDetail goods = getNewPay(OrderItem);
            // 创建好一个商品后添加至商品明细列表
            goodsDetailList.add(goods);
        }

        BizContent biz = new BizContent();
        biz.setSubject(subject);
        biz.setTotal_amount(totalAmount);
        biz.setOut_trade_no(outTradeNo);
        biz.setUndiscountable_amount(undiscountableAmount);
        biz.setSeller_id(sellerId);
        biz.setBody(body);
        biz.setOperator_id(operatorId);
        biz.setStore_id(storeId);
        biz.setExtend_params(extendParams);
        biz.setTimeout_express(timeoutExpress);
        //支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置
        //biz.setNotify_url(Configs.getNotifyUrl_test()+"portal/order/alipay_callback.do");
        biz.setGoods_detail(goodsDetailList);

        return biz;
    }



    public static OrderItemVO orderItemToOrderItemVO(Order_item orderItem) {
        OrderItemVO orderItemVO = new OrderItemVO();
        orderItemVO.setOrderNo(orderItem.getOrderNo());
        orderItemVO.setProductId(orderItem.getProductId());
        orderItemVO.setProductName(orderItem.getProductName());
        orderItemVO.setProductImage(orderItem.getProductImage());
        orderItemVO.setCurrentUnitPrice(orderItem.getCurrentUnitPrice());
        orderItemVO.setQuantity(orderItem.getQuantity());
        orderItemVO.setTotalPrice(orderItem.getTotalPrice());
        orderItemVO.setCreateTime(DateUtils.dateToStr(orderItem.getCreateTime()));

        return orderItemVO;
    }

    public static ShippingVO shippingToShippingVO(Shipping shipping) {
        ShippingVO shippingVO = new ShippingVO();
        shippingVO.setReceiverName(shipping.getReceiverName());
        shippingVO.setReceiverPhone(shipping.getReceiverPhone());
        shippingVO.setReceiverMobile(shipping.getReceiverMobile());
        shippingVO.setReceiverProvince(shipping.getReceiverProvince());
        shippingVO.setReceiverCity(shipping.getReceiverCity());
        shippingVO.setReceiverAddress(shipping.getReceiverAddress());
        shippingVO.setReceiverDistrict(shipping.getReceiverDistrict());
        shippingVO.setReceiverZip(shipping.getReceiverZip());

        return shippingVO;
    }

    public static OrderVO orderToOrderVO(List<OrderItemVO> list,ShippingVO shippingVO,
                                         Order order,Integer shippingId){
        OrderVO orderVO = new OrderVO();
        orderVO.setOrderItemVoList(list);
        orderVO.setShippingVo(shippingVO);
        orderVO.setOrderNo(order.getOrderNo());
        orderVO.setShippingId(shippingId);
        orderVO.setPayment(order.getPayment());
        orderVO.setPaymentType(order.getPaymentType());
        orderVO.setPostage(order.getPostage());
        orderVO.setStatus(order.getStatus());
        orderVO.setPaymentTime(null);
        orderVO.setSendTime(null);
        orderVO.setCloseTime(null);
        orderVO.setCreateTime(DateUtils.dateToStr(order.getCreateTime()));
        orderVO.setEndTime(null);
        try {
            orderVO.setImageHost(PropertiesUtil.getValue("imageHost"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return orderVO;
    }

//    public static OrderMsgVO orderToOrderMsgVO(List<OrderItemVO> list) throws IOException {
//
//        OrderMsgVO orderMsgVO = new OrderMsgVO();
//        orderMsgVO.setOrderItemVoList(list);
//        orderMsgVO.setImageHost(PropertiesUtil.getValue("imageHost"));
//        orderMsgVO.setProductTotalPrice();
//
//
//        return null;
//    }
}
