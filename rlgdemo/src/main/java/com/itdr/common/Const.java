package com.itdr.common;

public class Const {
    //    成功的状态码
    public static final int SUCCESS = 0;

    //    失败的状态码
//    public static final int ERROR = 11;


    //    用户相关状态
    public enum UserEnum {

        //        Const.UserEnum.NOT_LOGIN_BUT_HAVE.getCode(),Const.UserEnum.NOT_LOGIN_BUT_HAVE.getDesc()
        NEED_LOGIN(2, "需要登录"),
        PSD_WRONG(1, "密码错误"),
        NAME_NULL(100, "用户名不能为空"),
        PSD_NULL(100, "密码不能为空"),
        NEW_PSD_NULL(100, "密码不能为空"),
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

    //    产品相关状态
    public enum ProductEnum {
        //        Const.ProductEnum.NOT_PID.getCode(),Const.ProductEnum.NOT_PID.getDesc()

        PARAMETER_WRONG(1, "参数错误"),
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

    //    购物车相关状态
    public enum CartEnum {
        //        Const.CartEnum.NO_PRODUCT.getCode(),Const.CartEnum .NO_PRODUCT.getDesc()
        CHOOSE_NULL(1, "还没有选中任何商品哦~"),
        PARAMETER_NULL(9, "参数不能为空"),
        ADD_WRONG(2, "更新数据失败"),
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

    //    支付相关状态
    public enum PaymentPlatformEnum {
        //        Const.PaymentPlatformEnum.NO_PRODUCT.getCode(),Const.PaymentPlatformEnum .NO_PRODUCT.getDesc()
        VERIFY_ORDER_FALSE(1,"不是要付款的订单"),
        ALIPAY(1,"支付宝"),
        SAVEPAYMSG_FALSE(1,"支付失败"),
        NO_PRODUCT(3, "商品不存在");

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


    //    常量
    public static final String LOGINUSER = "login_user";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String TRADE_SUCCESS = "trade_success";

    public interface Cart {
        String LIMITQUANTITYSUCCESS = "LIMIT_NUM_SUCCESS";
        String LIMITQUANTITYFAILED = "LIMIT_NUM_FAILED";
        Integer CHECK = 1;
        Integer UNCHECK = 0;
    }


    //用户相关


    public static final String USER_NAME_NOT_NULL = "用户名不能为空";
    public static final String USER_PARAMETER_NULL_MSG = "参数不能为空";
    public static final String USER_DISABLE_MSG = "用户已经禁用";
    public static final String USER_ILLEGALITY_MSG = "输入的参数非法";
    public static final String USER_UPDATE_FAILED_MSG = "用户禁用更新失败";
    public static final String USER_ID_NOT_NULL = "用户ID不能为空";
    public static final String USER_NAMEORPSD_NOT_NULL = "用户名或密码不能为空";


    //    public static final String USER_NO_LOGIN = "用户未登录";
    public static final String USER_NO_LOGIN_NO_MSG = "用户未登录，无法获取当前用户信息";


    //商品相关状态
    public static final String PRODUCT_NAME_FALSE_MSG = "产品名称有误";
    public static final String PRODUCT_NO_MSG = "产品不存在";
    public static final String PRODUCT_ID_FALSE_MSG = "产品ID有误";

    public static final String PRODUCT_AMEND_FAILED_MSG = "修改产品状态失败";
    public static final String PRODUCT_AMEND_MSG = "修改产品状态成功";

    public static final String PRODUCT_UPDATE_FAILED_MSG = "更新产品失败";
    public static final String PRODUCT_UPDATE_MSG = "更新产品成功";
    public static final String PRODUCT_APPEND_FAILED_MSG = "新增产品失败";
    public static final String PRODUCT_APPEND_MSG = "新增产品成功";


    //商品分类相关状态
    public static final String CATEGORY_PID_NO_MSG = "未找到该品类";
    public static final String CATEGOR_ADD_MSG = "添加品类成功";
    public static final String CATEGOR_ADD_FAILED_MSG = "添加品类失败";
    public static final String CATEGOR_UPDATEPNAME_MSG = "更新品类名字成功";
    public static final String CATEGOR_UPDATEPNAME_FAILED_MSG = "更新品类名字失败";


    //订单相关状态
    public static final String ORDER_ID_FALSE_MSG = "订单ID有误";
    public static final String ORDER_ORDERNO_FALSE_MSG = "订单号有误";
    public static final String ORDER_PARAMETER_MSG = "参数不能为空";

    public static final String ORDER_NO_MSG = "订单不存在";
    public static final String ORDER_SUCCESSFUL_DELIBERY = "发货成功";
    public static final String ORDER_FEFEATED_DELIBERY = "发货失败";


}
