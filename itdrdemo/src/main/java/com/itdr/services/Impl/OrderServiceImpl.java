package com.itdr.services.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itdr.common.Const;
import com.itdr.common.ServerResponse;
import com.itdr.mappers.*;
import com.itdr.pojo.*;
import com.itdr.pojo.vo.OrderItemVO;
import com.itdr.pojo.vo.OrderMsgVO;
import com.itdr.pojo.vo.OrderVO;
import com.itdr.pojo.vo.ShippingVO;
import com.itdr.services.OrderService;
import com.itdr.utils.BigDecimalUtils;
import com.itdr.utils.DateUtils;
import com.itdr.utils.PoToVoUtil;
import com.itdr.utils.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Jwen
 * Date: 2019/9/7
 * Time: 13:43
 */

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    Order_itemMapper order_itemMapper;
    @Autowired
    CartMapper cartMapper;
    @Autowired
    ShippingMapper shippingMapper;

    /*订单列表*/
    @Override
    public ServerResponse selectAll() {
        List<Order> li = orderMapper.selectAll();
        ServerResponse sr = ServerResponse.successRS(li);
        return sr;
    }

    /*订单号查询*/
    @Override
    public ServerResponse selectOne(Long orderNo) {
        ServerResponse sr = null;
        if (orderNo == null) {
            sr = ServerResponse.defeatedRS(Const.OrderEnum.NUM_FALSE.getCode(), Const.OrderEnum.NUM_FALSE.getDesc());
            return sr;
        }
        Order o = orderMapper.selectByOrderNo(orderNo);
        sr = ServerResponse.successRS(o);
        return sr;
    }

    /*ID查询*/
    @Override
    public ServerResponse selectOneById(Integer id) {
        ServerResponse sr = null;
        if (id == null) {
            sr = ServerResponse.defeatedRS(Const.OrderEnum.ID_FALSE.getCode(), Const.OrderEnum.ID_FALSE.getDesc());
            return sr;
        }
        Order o = orderMapper.selectByPrimaryKey(id);
        sr = ServerResponse.successRS(o);
        return sr;
    }

    //创建订单
    @Override
    public ServerResponse createOrder(Integer uid, Integer shippingId) {
        //参数判断
        if (shippingId == null || shippingId <= 0) {
            return ServerResponse.defeatedRS(Const.OrderEnum.PARAMETER_NULL.getCode(),
                    Const.OrderEnum.PARAMETER_NULL.getDesc());
        }
        List<Product> productList = new ArrayList<>();

        //获取用户选定的数据
        List<Cart> cartList = cartMapper.selectByUidAll(uid);

        if (cartList.size() == 0) {
            return ServerResponse.defeatedRS(Const.OrderEnum.AT_LEAST_NOE_ITEM.getCode(),
                    Const.OrderEnum.AT_LEAST_NOE_ITEM.getDesc());
        }

        //获取用户地址信息
        Shipping shipping = shippingMapper.selectByIdAndUid(shippingId, uid);
        if (shipping == null) {
            return ServerResponse.defeatedRS(Const.OrderEnum.SHIPPING_ADD_NONE.getCode(),
                    Const.OrderEnum.SHIPPING_ADD_NONE.getDesc());
        }

        //计算订单总价
        BigDecimal payment = new BigDecimal("0");
        for (Cart cart : cartList) {
            //判断商品是否失效
            Integer productId = cart.getProductId();
            //根据商品id获取商品数据
            Product product = productMapper.selectByIdAll(productId);
            if (product == null) {
                return ServerResponse.defeatedRS(Const.OrderEnum.NO_PRODUCT.getCode(),
                        Const.OrderEnum.NO_PRODUCT.getDesc());
            }
            if (product.getStatus() != 1) {
                return ServerResponse.defeatedRS(Const.OrderEnum.PRODUCT_SHELVES.getCode(),
                        product.getName() + Const.OrderEnum.PRODUCT_SHELVES.getDesc());
            }

            //校验库存
            if (cart.getQuantity() > product.getStock()) {
                return ServerResponse.defeatedRS(Const.OrderEnum.EXCESS_INVENTORY.getCode(),
                        product.getName() + Const.OrderEnum.EXCESS_INVENTORY.getDesc());
            }

            //根据购物车购物数量和商品单价计算一条购物车信息的总价
            BigDecimal mul = BigDecimalUtils.mul(product.getPrice().doubleValue(), cart.getQuantity());
            //把每一条购物车信息总价相加，就是订单总价
            payment = BigDecimalUtils.add(payment.doubleValue(), mul.doubleValue());
            //放到集合中备用
            productList.add(product);
        }

        //创建订单，没有问题要存到数据中
        Order order = this.getOrder(uid, shippingId, payment);
        int insert = orderMapper.insert(order);
        if (insert <= 0) {
            return ServerResponse.defeatedRS(Const.OrderEnum.CREATE_ORDER_FAILED.getCode(),
                    order.getOrderNo() + Const.OrderEnum.CREATE_ORDER_FAILED.getDesc());
        }

        //创建订单详情，没有问题存到数据库中，使用批量插入
        List<Order_item> order_itemList = this.getOrderItem(uid, order.getOrderNo(), productList, cartList);
        int insertAll = order_itemMapper.insertAll(order_itemList);
        if (insertAll <= 0) {
            return ServerResponse.defeatedRS(Const.OrderEnum.CREATE_ORDER_DETAILS_FAILED.getCode(),
                    order.getOrderNo() + Const.OrderEnum.CREATE_ORDER_DETAILS_FAILED.getDesc());
        }

        //插入成功，减少商品库存
        for (Order_item item : order_itemList) {
            for (Product product : productList) {
                if (item.getProductId() == product.getId()) {
                    //注：可以在此判断库存是否为负数
                    product.setStock(product.getStock() - item.getQuantity());
                    //更新数据到数据库中（库存）
                    int i = productMapper.updateByPrimaryKeySelective(product);
                    if (i < 0) {
                        return ServerResponse.defeatedRS(Const.OrderEnum.UPDATE_INVENTORY_FAILED.getCode(),
                                Const.OrderEnum.SHIPPING_ADD_NONE.getDesc());
                    }
                }
            }
        }
        //清空购物车
        int cartDelete = cartMapper.deleteAllByIdAndUid(cartList, uid);
        if (cartDelete < 0) {
            return ServerResponse.defeatedRS(Const.OrderEnum.EMPTY_CART_FAILED.getCode(),
                    Const.OrderEnum.EMPTY_CART_FAILED.getDesc());
        }

        //创建集合
        //拼接VO类返回数据
//        List<OrderItemVO> itemVOList = new ArrayList<>();
//        for (Order_item orderItem : order_itemList) {
//            OrderItemVO orderItemVO = PoToVoUtil.orderItemToOrderItemVO(orderItem);
//            itemVOList.add(orderItemVO);
//        }
        List<OrderItemVO> itemVOList = this.getOrderItemVOList(order_itemList);

        //封装地址VO类
        ShippingVO shippingVO = PoToVoUtil.shippingToShippingVO(shipping);

        //在工具类里封装orderVO
        OrderVO orderVO = PoToVoUtil.orderToOrderVO(itemVOList, shippingVO, order, shippingId);

        return ServerResponse.successRS(orderVO);
    }


    //获取订单的商品信息
    @Override
    public ServerResponse getOrderCartProduct(Integer uid, Long orderNo) {
        //创建OrderMsgVO
        OrderMsgVO orderMsgVO = new OrderMsgVO();
        List<Order_item> itemList = null;
        List<OrderItemVO> orderItemVOList = null;
        BigDecimal payment = new BigDecimal("0");

        //根据用户id和orderNo获取对应的订单详情信息
        if (orderNo != null) {
            //根据订单号和用户id查询订单信息
            itemList = order_itemMapper.selectByUidAndOrderNo(uid, orderNo);
            orderItemVOList = this.getOrderItemVOList(itemList);

            //封装orderItemVO类
            List<OrderItemVO> orderItemVO = this.getOrderItemVOList(itemList);
            Order order = orderMapper.selectByOrderNo(orderNo);

            if (order == null) {
                return ServerResponse.defeatedRS(Const.OrderEnum.ORDER_NO.getCode(),
                        orderNo + Const.OrderEnum.ORDER_NO.getDesc());
            }
            payment = order.getPayment();

        } else {
            //没有订单id，根据用户id获取订单详情
            //获取用户选定的数据
            List<Cart> cartList = cartMapper.selectByUidAll(uid);
            List<Product> productList = new ArrayList<>();

            if (cartList.size() == 0) {
                return ServerResponse.defeatedRS(Const.OrderEnum.AT_LEAST_NOE_ITEM.getCode(),
                        Const.OrderEnum.AT_LEAST_NOE_ITEM.getDesc());
            }

            //计算订单总价
            for (Cart cart : cartList) {
                //判断商品是否失效
                Integer productId = cart.getProductId();
                //根据商品id获取商品数据
                Product product = productMapper.selectByIdAll(productId);
                if (product == null) {
                    return ServerResponse.defeatedRS(Const.OrderEnum.NO_PRODUCT.getCode(),
                            Const.OrderEnum.NO_PRODUCT.getDesc());
                }
                if (product.getStatus() != 1) {
                    return ServerResponse.defeatedRS(Const.OrderEnum.PRODUCT_SHELVES.getCode(),
                            product.getName() + Const.OrderEnum.PRODUCT_SHELVES.getDesc());
                }

                //校验库存
                if (cart.getQuantity() > product.getStock()) {
                    return ServerResponse.defeatedRS(Const.OrderEnum.EXCESS_INVENTORY.getCode(),
                            product.getName() + Const.OrderEnum.EXCESS_INVENTORY.getDesc());
                }

                //根据购物车购物数量和商品单价计算一条购物车信息的总价
                BigDecimal mul = BigDecimalUtils.mul(product.getPrice().doubleValue(), cart.getQuantity());
                //把每一条购物车信息总价相加，就是订单总价
                payment = BigDecimalUtils.add(payment.doubleValue(), mul.doubleValue());
                //放到集合中备用
                productList.add(product);
            }


            itemList = this.getOrderItem(uid, null, productList, cartList);
            orderItemVOList = this.getOrderItemVOList(itemList);

        }

        //拼装orderMsgVO
        orderMsgVO.setOrderItemVoList(orderItemVOList);
        try {
            orderMsgVO.setImageHost(PropertiesUtil.getValue("imageHost"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        orderMsgVO.setProductTotalPrice(payment);

        return ServerResponse.successRS(orderMsgVO);
    }


    //创建一个订单
    private Order getOrder(Integer uid, Integer shippingId, BigDecimal payment) {

        Order order = new Order();
        order.setUserId(uid);
        order.setOrderNo(this.getOrderNo());
        order.setShippingId(shippingId);
        order.setPayment(payment);
        order.setPaymentType(Const.Order.PAYMENTTYPE);
        order.setPostage(Const.Order.POSTAGE);
        order.setStatus(Const.Order.STATUS);

        return order;
    }

    //创建一个订单详情对象
    private List<Order_item> getOrderItem(Integer uid, Long orderNo, List<Product> productList, List<Cart> cartList) {
        List<Order_item> itemList = new ArrayList<>();

        for (Cart cart : cartList) {
            Order_item orderItem = new Order_item();
            orderItem.setQuantity(cart.getQuantity());
            for (Product product : productList) {
                if (product.getId().equals(cart.getProductId())) {
                    orderItem.setUserId(uid);
                    orderItem.setOrderNo(orderNo);
                    orderItem.setProductId(product.getId());
                    orderItem.setProductName(product.getName());
                    orderItem.setProductImage(product.getMainImage());
                    orderItem.setCurrentUnitPrice(product.getPrice());
                    //根据购物车购物数量和商品单价计算一条购物车信息的总价
                    BigDecimal mul = BigDecimalUtils.mul(product.getPrice().doubleValue(), cart.getQuantity());
                    orderItem.setTotalPrice(mul);

                    itemList.add(orderItem);
                }
            }
        }
        return itemList;
    }

    //生成订单编号
    private Long getOrderNo() {
        long l = System.currentTimeMillis();
        long orderNo = l + Math.round(Math.random() * 100);
        return orderNo;
    }

    //根据订单详情集合获取orderItemVO集合
    private List<OrderItemVO> getOrderItemVOList(List<Order_item> order_itemList) {
        List<OrderItemVO> itemVOList = new ArrayList<>();
        for (Order_item orderItem : order_itemList) {
            OrderItemVO orderItemVO = PoToVoUtil.orderItemToOrderItemVO(orderItem);
            itemVOList.add(orderItemVO);
        }
        return itemVOList;
    }


    //获取订单所有数据
    @Override
    public ServerResponse getOrderList(Integer id, Integer pageNum, Integer pageSize) {
        OrderVO orderVO = new OrderVO();
        List<OrderVO> orderVOList = new ArrayList<>();

        //获取用户的所有订单
        PageHelper.startPage(pageNum, pageSize);
        List<Order> orderList = orderMapper.selectByUid(id);

        //循环床架orderVo对象
        for (Order order : orderList) {

            List<Order_item> itemList = order_itemMapper.selectByUidAndOrderNo(id, order.getOrderNo());
            List<OrderItemVO> orderItemVOList = this.getOrderItemVOList(itemList);
            Shipping shipping = shippingMapper.selectByIdAndUid(order.getShippingId(), id);
            //封装地址VO类
            ShippingVO shippingVO = PoToVoUtil.shippingToShippingVO(shipping);

            orderVO.setOrderItemVoList(orderItemVOList);
            orderVO.setOrderNo(order.getOrderNo());
            orderVO.setShippingId(order.getShippingId());
            orderVO.setPayment(order.getPayment());
            orderVO.setPaymentType(order.getPaymentType());
            orderVO.setPostage(order.getPostage());
            orderVO.setStatus(order.getStatus());
            orderVO.setPaymentTime(DateUtils.dateToStr(order.getPaymentTime()));
            orderVO.setSendTime(DateUtils.dateToStr(order.getSendTime()));
            orderVO.setCloseTime(DateUtils.dateToStr(order.getCloseTime()));
            orderVO.setCreateTime(DateUtils.dateToStr(order.getCreateTime()));

            try {
                orderVO.setImageHost(PropertiesUtil.getValue("imageHost"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            orderVOList.add(orderVO);
        }


        PageInfo pageInfo = new PageInfo(orderList);
        pageInfo.setList(orderVOList);

        return ServerResponse.successRS(pageInfo);

    }

    //取消订单
    @Override
    public ServerResponse countermandOrder(Integer id, Long orderNo) {
        if (orderNo == null || orderNo <= 0) {
            return ServerResponse.defeatedRS(Const.OrderEnum.ILLEGALITY_PARAMETER.getCode(), Const.OrderEnum.ILLEGALITY_PARAMETER.getDesc());
        }

        //判断订单是否存在
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order == null) {
            return ServerResponse.defeatedRS(Const.OrderEnum.ORDER_NO.getCode(), Const.OrderEnum.ORDER_NO.getDesc());
        }
        //判断定时是否付款
        if (order.getStatus() != 10) {
            return ServerResponse.defeatedRS(Const.OrderEnum.ILLEGALITY_STATUS.getCode(), Const.OrderEnum.ILLEGALITY_STATUS.getDesc());
        }

        //取消订单，改变订单状态
        order.setStatus(0);
        int i = orderMapper.updateToStatus(order);
        if (i <= 0) {
            return ServerResponse.defeatedRS(Const.OrderEnum.ORDER_OFF_FAILED.getCode(), Const.OrderEnum.ORDER_OFF_FAILED.getDesc());
        }

        //取消库存锁定
        List<Order_item> itemList = order_itemMapper.selectByOrderNo(orderNo);
        for (Order_item orderItem : itemList) {
            Product product = productMapper.selectByPrimaryKey(orderItem.getProductId());
            product.setStock(product.getStock() + orderItem.getQuantity());
            int iProduct = productMapper.updateByPrimaryKey(product);
            if (iProduct <= 0) {
                return ServerResponse.defeatedRS(Const.OrderEnum.UPDATE_INVENTORY_FAILED.getCode(), Const.OrderEnum.UPDATE_INVENTORY_FAILED.getDesc());
            }
        }

        return ServerResponse.successRS(Const.OrderEnum.ORDER_OFF_SUCCESS.getCode(), Const.OrderEnum.ORDER_OFF_SUCCESS.getDesc());
    }

}
