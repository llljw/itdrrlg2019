package com.itdr.services.Impl;

import com.alipay.api.domain.Car;
import com.itdr.common.Const;
import com.itdr.common.ServerResponse;
import com.itdr.mappers.CartMapper;
import com.itdr.mappers.ProductMapper;
import com.itdr.pojo.Cart;
import com.itdr.pojo.Product;
import com.itdr.pojo.Users;
import com.itdr.pojo.vo.CartProductVO;
import com.itdr.pojo.vo.CartVO;
import com.itdr.services.CartService;
import com.itdr.utils.BigDecimalUtils;
import com.itdr.utils.PoToVoUtil;
import com.itdr.utils.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * User: Jwen
 * Date: 2019/9/12
 * Time: 21:48
 */

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartMapper cartMapper;
    @Autowired
    ProductMapper productMapper;

    //购物车列表
    @Override
    public ServerResponse<CartVO> list(Integer id) {
//        List<Cart> carts = cartMapper.selectAll();
//        if (carts == null) {
//            return ServerResponse.defeatedRS(Const.CartEnum.CHOOSE_NULL.getCode(), Const.CartEnum.CHOOSE_NULL.getDesc());
//        }
//        return ServerResponse.successRS(carts);
        CartVO cartVo = this.getCartVo(id);
        return ServerResponse.successRS(cartVo);
    }

    //增加商品
    @Override
    public ServerResponse<CartVO> insert(Integer productId, Integer count, Integer uid) {
        //参数非空判断
        if (productId == null || productId <= 0 || count == null || count <= 0) {
            return ServerResponse.defeatedRS(Const.CartEnum.PARAMETER_NULL.getCode(), Const.CartEnum.PARAMETER_NULL.getDesc());
        }

        //如果有购物信息就执行更新，没有的话执行增加
        Cart cart2 = cartMapper.selectByProductIdAndUid(productId, uid);

        if (cart2 != null) {
            //更新数据
            cart2.setQuantity(cart2.getQuantity() + count);
            int insert = cartMapper.updateByPrimaryKeySelective(cart2);
            if (insert <= 0) {
                return ServerResponse.defeatedRS(Const.CartEnum.ADD_FAIL.getCode(), Const.CartEnum.ADD_FAIL.getDesc());
            }
        } else {
            //插入数据
            //创建cart对象
            Cart cart = new Cart();
            cart.setUserId(uid);
            cart.setProductId(productId);
            cart.setQuantity(count);
            int insert = cartMapper.insert(cart);
        }
        return list(uid);

//        CartVO cartVo = getCartVo(uid);
//        return ServerResponse.successRS(cartVo);
    }
    private CartVO getCartVo(Integer uid) {
        //创建cartVO对象
        CartVO cartVO = new CartVO();

        //存储购物车总价
        BigDecimal cartTotalPrice = new BigDecimal("0");


        //用来存放CartProductVo对象的集合
        List<CartProductVO> lists = new ArrayList<CartProductVO>();

        //根据用户id查询所有的购物信息
        List<Cart> list = cartMapper.selectByUid(uid);

        //从购物信息集合中拿出每一条数据，根据其中商品id查询需要的商品信息
        if (list.size() != 0) {
            for (Cart cart : list) {
                //根据购物信息中的商品id查询商品数据
                Product product = productMapper.selectById(cart.getProductId());

                //使用工具类进行数据封装
                CartProductVO cartProductVO = PoToVoUtil.getOne(cart, product);

                //更新购物车有效库存
                Cart cartForQuantity = new Cart();
                cartForQuantity.setId(cart.getId());
                cartForQuantity.setQuantity(cartProductVO.getQuantity());
                cartMapper.updateByPrimaryKeySelective(cartForQuantity);

                //计算购物车总价
                if (cart.getChecked() == Const.Cart.CHECK) {
                    cartTotalPrice = BigDecimalUtils.add(cartTotalPrice.doubleValue(), cartProductVO.getProductTotalPrice().doubleValue());
                }

                //把对象放到集合中
                lists.add(cartProductVO);
            }
        }
        //封装cartVO数据
        cartVO.setCartProductVoList(lists);
        cartVO.setAllChecked(this.checkAll(uid));
        cartVO.setCartTotalPrice(cartTotalPrice);


        try {
            cartVO.setImageHost(PropertiesUtil.getValue("imageHost"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cartVO;
    }

    //判断用户购物车是否全选
    public Boolean checkAll(Integer uid) {
        int i = cartMapper.selectByUidCheckAll(uid, Const.Cart.UNCHECK);
        if (i == 0) {
            return true;
        } else {
            return false;
        }
    }

    //更新商品数量
    @Override
    public ServerResponse<CartVO> update(Integer productId, Integer count, Integer uid) {


        if (productId == null || productId <= 0 || count == null || count <= 0) {
            return ServerResponse.defeatedRS(Const.CartEnum.PARAMETER_NULL.getCode(), Const.CartEnum.PARAMETER_NULL.getDesc());
        }

        Cart cart2 = cartMapper.selectByProductIdAndUid(productId, uid);

        //更新数量
        cart2.setQuantity(count);
        int insert = cartMapper.updateByPrimaryKeySelective(cart2);
        if (insert <= 0) {
            return ServerResponse.defeatedRS(Const.CartEnum.ADD_FAIL.getCode(), Const.CartEnum.ADD_FAIL.getDesc());
        }

        List<Cart> list = cartMapper.selectAll();
        return ServerResponse.successRS(list);
    }

    //移除商品
    @Override
    public ServerResponse<CartVO> deleteByProductId(String productIds, Integer id) {
        if (productIds == null || "".equals(productIds)) {
            return ServerResponse.defeatedRS(Const.CartEnum.PARAMETER_NULL.getCode(), Const.CartEnum.PARAMETER_NULL.getDesc());
        }

        //把字符串中数据放到集合里
        String[] split = productIds.split(",");
        List<String> strings = Arrays.asList(split);

        int i = cartMapper.deleteByProductId(strings,id);


        return list(id);
    }

    //查询在购物车里的产品数量
    @Override
    public ServerResponse<Integer> getCartProductCount(Integer id) {
        List<Cart> list = cartMapper.selectByUid(id);
        return ServerResponse.successRS(list.size());
    }

    //改变购物车中商品选择状态
    @Override
    public ServerResponse<CartVO> selectOrUnSelect(Integer id, Integer check, Integer productId) {
        int i = cartMapper.selectOrUnSelect(id, check, productId);
        return list(id);
    }


}
