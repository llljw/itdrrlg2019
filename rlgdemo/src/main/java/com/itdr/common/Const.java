package com.itdr.common;

public class Const {
    //    成功的状态码
    public static final int SUCESS = 0;

    //    失败的状态码
    public static final int ERROR = 1;

    public enum UserEnum {
        NEED_LOGIN(2, "需要登录"),
        NO_LOGIN(101,"用户未登录");
//        状态信息

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

    //    常量
    public static final String LOGINUSER = "login_user";
    public static final String USERNAME = "username";


    //过滤器相关状态
    public static final String FILTER_NOT_LOGIN_MSG = "用户未登录，请登录";
    public static final String FILTER_NOT_PERMISSION = "没有权限";

    //用户相关状态
    public static final String USER_PARAMETER_MSG = "参数为空";
    public static final String USER_NO_MSG = "用户不存在";
    public static final String USER_DISABLE_MSG = "用户已经禁用";
    public static final String USER_ILLEGALITY_MSG = "输入的参数非法";
    public static final String USER_UPDATE_FAILED_MSG = "用户禁用更新失败";
    public static final String USER_ID_NOT_NULL = "用户ID不能为空";
    public static final String USER_NAMEORPSD_NOT_NULL = "用户名或密码不能为空";
    public static final String USER_PSD_NOT_NULL = "密码不能为空";
    public static final String USER_PSD_NOT_NULL_CODE = "密码不能为空";
    public static final String USER_CUO_MSG = "账户或密码错误";
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
