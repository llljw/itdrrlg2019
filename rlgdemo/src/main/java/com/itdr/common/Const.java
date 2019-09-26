package com.itdr.common;

public class Const {
    //成功的状态码
    public static final int SUCCESS = 0;

    //失败的状态码
    public static final int ERROR = 1;


    //用户相关状态
    public enum UserEnum {

        //        Const.UserEnum.NOT_LOGIN_BUT_HAVE.getCode(),Const.UserEnum.NOT_LOGIN_BUT_HAVE.getDesc()
        NEED_LOGIN(2, "需要登录"),
        PSD_WRONG(1, "密码错误"),
        NAME_NULL(100, "用户名不能为空"),
        PSD_NULL(100, "密码不能为空"),
        NEW_PSD_NULL(100, "新密码不能为空"),
        NO_USER(101, "用户不存在"),
        HAVE_ONE_USER(1, "用户已存在"),
        NOT_LOGIN(1, "用户未登录"),
        NOT_LOGIN_BUT_HAVE(10, "用户未登录，请登录"),
        HAVE_ONE_EMAIL(2, "邮箱已存在"),
        MSG_NULL(100, "注册信息不能为空"),
        REGISTER_FAIL(1, "注册失败"),
        REGISTER_SUCCESS(0, "注册成功"),
        VERIFY_SUCCESS(0, "校验成功"),
        UPDATE_MSG_SUCCESS(0, "更新个人信息成功"),
        UPDATE_MSG_FAIL(1, "更新失败"),
        NOT_FIND_PSD_ANSWER(1, "该用户未设置找回密码问题"),
        QUESTION_NULL(100, "问题不能为空"),
        ANSWER_NULL(100, "答案不能为空"),
        ANSWER_WRONG(1, "问题答案错误"),
        NO_TOKEN(104, "非法的token"),
        LOSE_TOKEN(103, "token已失效"),
        ALTER_PSD_FAIL(100, "修改密码失败"),
        ALTER_PSD_SUCCESS(100, "修改密码失败"),
        PARAMETER_NULL(100, "参数不能为空"),
        OLD_PSD_WRONG(1, "旧密码更新失败"),
        OLD_PSD_UPDATE_FAIL(0, "旧密码更新失败"),
        OLD_PSD_UPDATE_SUCCESS(0, "旧密码更新成功"),
        LOGIN_OUT_SUCCESS(0, "退出成功");


        //    状态信息

        private int code;
        private String desc;

        private UserEnum(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
    //用户相关常量
    public interface User {
        String LOGINUSER = "login_user";
        String USERNAME = "username";
        String EMAIL = "email";
    }


    //产品相关状态
    public enum ProductEnum {
        //        Const.ProductEnum.PRODUCT_APPEND_SUCCESS.getCode(),Const.ProductEnum.PRODUCT_APPEND_SUCCESS.getDesc()

        PARAMETER_WRONG(1, "参数错误"),
        PRODUCT_NO(1, "产品不存在"),
        AMEND_FAILED(1, "修改产品状态失败"),
        AMEND_SUCCESS(0, "修改产品状态成功"),
        UPDATE_FAILED(1, "更新产品失败"),
        UPDATE_SUCCESS(0, "更新产品成功"),
        APPEND_FAILED(1, "新增产品失败"),
        APPEND_SUCCESS(0, "新增产品成功"),
        SOLD_OUT(4, "该商品已下架"),
        NOT_PID(1, "查询id不存在");


        private int code;
        private String desc;

        private ProductEnum(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
    //产品相关常量
    public interface Product {
        String LIMITQUANTITYSUCCESS = "LIMIT_NUM_SUCCESS";
        String LIMITQUANTITYFAILED = "LIMIT_NUM_FAILED";
        Integer CHECK = 1;
        Integer UNCHECK = 0;
    }


    //购物车相关状态
    public enum CartEnum {
        //        Const.CartEnum.NO_PRODUCT.getCode(),Const.CartEnum .NO_PRODUCT.getDesc()
        CHOOSE_NULL(1, "还没有选中任何商品哦~"),
        PARAMETER_NULL(9, "参数不能为空"),
        ADD_FAIL(2, "更新数据失败"),
        NO_PRODUCT(3, "商品不存在");

        private int code;
        private String desc;

        private CartEnum(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
    //购物车相关常量
    public interface Cart {
        String LIMITQUANTITYSUCCESS = "LIMIT_NUM_SUCCESS";
        String LIMITQUANTITYFAILED = "LIMIT_NUM_FAILED";
        Integer CHECK = 1;
        Integer UNCHECK = 0;
    }


    //订单相关状态
    public enum OrderEnum {
        //Const.OrderEnum.ORDER_OFF_SUCCESS.getCode(),Const.OrderEnum.ORDER_OFF_SUCCESS.getDesc()
        PARAMETER_NULL(9, "参数不能为空"),
        ILLEGALITY_PARAMETER(1,"非法参数"),
        ID_FALSE(1, "订单ID有误"),
        NUM_FALSE(1, "订单号有误"),
        ORDER_OFF_SUCCESS(1,"订单取消成功"),
        ORDER_OFF_FAILED(1,"订单取消失败"),
        ILLEGALITY_STATUS(1,"订单状态非法"),
        CREATE_ORDER_FAILED(1,"订单创建失败"),
        CREATE_ORDER_DETAILS_FAILED(1,"订单详情创建失败"),
        ORDER_NO(1, "订单不存在"),
        AT_LEAST_NOE_ITEM(1, "至少选中一件商品"),
        PRODUCT_SHELVES(1,"商品已下架"),
        EXCESS_INVENTORY(1,"超出库存数量"),
        UPDATE_INVENTORY_FAILED(1,"更新商品库存失败"),
        NO_PRODUCT(1,"商品不存在"),
        EMPTY_CART_FAILED(1,"清空购物车失败"),
        SHIPPING_ADD_NONE(1,"用户收货地址不存在"),
        SUCCESS_DELIBERY(1, "发货成功"),
        FAILED_DELIBERY(1, "发货失败");

        private int code;
        private String desc;

        private OrderEnum(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
    //订单相关常量
    public interface Order{
        Integer PAYMENTTYPE = 1;
        Integer POSTAGE = 0;
        Integer STATUS = 10;

    }


    //支付相关状态
    public enum PaymentPlatformEnum {
        //        Const.PaymentPlatformEnum.ORDER_NOPAYMENT.getCode(),Const.PaymentPlatformEnum .ORDER_NOPAYMENT.getDesc()
        VERIFY_ORDER_FALSE(1,"不是要付款的订单"),
        PARAMETER_NULL(9, "参数不能为空"),
        NO_ORDER(7,"要支付的订单不存在"),
        ORDER_PRICE_UNMATCH(1,"订单金额不匹配"),
        ORDER_NOT_NOPAYMENT(1,"订单不是未付款状态"),
        CREATE_ORDER_FAIL(301,"支付宝生成订单失败"),
        ALIPAY(1,"支付宝"),
        ORDER_NOPAYMENT(1,"订单未支付"),
        USER_NOT_ORDER(1,"该用户并没有该订单,查询无效"),
        SAVEPAYMSG_FALSE(1,"支付失败");

        private int code;
        private String desc;

        private PaymentPlatformEnum(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
    //支付相关常量
    public interface PaymentPlatfor{
        Boolean BOOLEAN = true;
        String TRADE_SUCCESS = "TRADE_SUCCESS";
    }


    //分类相关状态
    public enum CategoryEnum {
        //        Const.CategoryEnum.CATEGORY_UPDATEPNAME_FAILED.getCode(),Const.CategoryEnum.CATEGORY_UPDATEPNAME_FAILED.getDesc()
        PID_NO(1,"未找到该品类"),
        ADD(0, "添加品类成功"),
        ADD_FAILED(1,"添加品类失败"),
        UPDATEPNAME(1,"更新品类名字成功"),
        UPDATEPNAME_FAILED(0,"更新品类名字失败");


        private int code;
        private String desc;

        private CategoryEnum(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
    //分类相关常量
    public interface Category{
    }













}
