package com.benben.yishanbang.api;

/**
 * 功能:APP接口类
 */
public class NetUrlUtils {
    //该项目接口命名，根据后台接口的真实地址，全参数命名
//    public static String BASEURL = "http://47.93.233.254:8081/api/";
    public static String BASEURL = "http://ysb.zjxtaq.com/api/";


    //注册
    public static String REGISTER = BASEURL + "v1/user/register";
    //获取验证码
    public static String GET_VERIFY_CODE = BASEURL + "v1/sms/aliSend";
    //验证码登录
    public static String VERIFY_CODE_LOGIN = BASEURL + "v1/user/mobileLogin";
    //密码登录
    public static String PASSWORD_LOGIN = BASEURL + "v1/user/login";
    //三方登录
    public static String THIRD_LOGIN = BASEURL + "v1/user/mobileThirdLogin";
    //三方登录-绑定手机号
    public static String THIRD_LOGIN_BIND_MOBILE = BASEURL + "v1/user/bindOpenId";
    //修改登录密码
    public static String CHANGE_PASSWORD_LOGIN = BASEURL + "/v1/user/changePassword";
    //忘记密码
    public static String FORGET_PASSWORD = BASEURL + "v1/user/forgetPassword";
    //更换绑定手机
    public static String CHANGE_MOBILE = BASEURL + "v1/user/changeMobile";
    //钱包-我的优惠卡 （可搜索）
    public static String WALLET_QUERY_MY_CARD_LIST = BASEURL + "userCardController/queryMyCardList";
    //钱包-我的优惠卡菜单列表
    public static String WALLET_QUERY_SHOP_CATE_GROY = BASEURL + "goodsCategoryController/queryShopCateGory";
    //钱包-搜索我的优惠卡
    public static String WALLET_QUERY_MY_CARD_SEARCH_LIST = BASEURL + "userCardController/queryPageList";
    //获取搜索优惠卡店列表
    public static String SEARCH_CARD_SHOP_LIST = BASEURL + "bbPopularSearchController/queryPageList";
    //店铺信息列表按销量或距离展示
    public static String GET_HOME_CARD_LIST = BASEURL + "bbShopController/queryCardList";
    //优惠卡商品详情
    public static String CARD_GOODS_DETAILS = BASEURL + "cardController/queryById";
    //推荐商家、推荐商品、视频广告
    public static String GET_HOME_RECOMMEND_GOODS = BASEURL + "advertController/queryPageList";
    //获取热门搜索名称
    public static String GET_HOT_SEARCH_LIST = BASEURL + "bbPopularSearchController/querySearchList";
    //获取首页轮播图
    public static String GET_BANNER_LIST = BASEURL + "bannerController/queryBanner";
    //店铺信息按类型分类
    public static String GET_STORE_CATEGORY = BASEURL + "goodsCategoryController/queryShopCateGory";
    //店铺信息
    public static String GET_STORE_INFO = BASEURL + "bbShopController/getCardShopById";
    //店铺商品分类信息
    public static String GET_STORE_CATE_INFO = BASEURL + "goodsCategoryController/queryCardCateGory";
    //店铺商品列表
    public static String GET_STORE_GOODS_LIST = BASEURL + "cardController/queryPageList";
    //答题问题列表
    public static String GET_ANSWER_LIST = BASEURL + "questionController/queryById";
    //答题结果
    public static String GET_ANSWER = BASEURL + "questionController/getAccuracy";
    //收藏店铺/商品
    public static String COLLECTION_SHOP_GOODS = BASEURL + "collectionController/add";
    //收藏的商品列表
    public static String COLLECTION_GOODS_LIST = BASEURL + "collectionController/queryCardByUserId";
    //收藏的店铺列表
    public static String COLLECTION_SHOP_LIST = BASEURL + "collectionController/queryShopById";
    //删除收藏的商品
    public static String DELETE_COLLECTION_GOODS = BASEURL + "collectionController/delete";
    //视频页面-获取店铺信息
    public static String VIDEO_GET_SHOP_INFO = BASEURL + "advertController/queryById";
    //商户中心-店铺管理
    public static String SHOP_MANAGE_INFO = BASEURL + "v1/usermag/shopManagement";
    //商户中心-修改店铺管理
    public static String UPDATE_SHOP_MANAGE = BASEURL + "v1/usermag/updateShopManagement";
    //商户中心-优惠卡查看
    public static String LOOK_COUPON_CARD = BASEURL + "v1/usermag/getCardAllByShopId";
    //商户中心-奶茶查看
    public static String LOOK_TEA = BASEURL + "goodsCategoryController/milkTeaCateGory";
    //首页-扫码-纸巾机
    public static String GET_TISSUE_MACHINE = BASEURL + "tissueController/showPaper";
    //商户中心-优惠卡-订单管理
    public static String SHOP_CENTER_CARD_ORDER_MANAGE = BASEURL + "v1/usermag/cardOrder";
    //商户中心-数据报表
    public static String SHOP_CENTER_DATA_REPORT = BASEURL + "v1/usermag/getReport";
    //商户中心-获取子账号列表
    public static String GET_SUBACCOUNT_LIST = BASEURL + "sonaccountController/getSonAcc";
    //商户中心-修改/删除子账号列表
    public static String SET_SUBACCOUNT_LIST = BASEURL + "sonaccountController/setSonAcc";
    //商户中心-添加子账号
    public static String ADD_SUBACCOUNT_LIST = BASEURL + "sonaccountController/addAcc";
    //商户中心-奶茶-订单管理
    public static String SHOP_CENTER_TEA_ORDER_MANAGE = BASEURL + "shopOrderController/getNewOrder";
    //商户中心-奶茶-订单详情-头部
    public static String SHOP_CENTER_TEA_ORDER_DETAILS_TOP = BASEURL + "v1/usermag/getUserPayCount";
    //商户中心-奶茶-订单详情-底部
    public static String SHOP_CENTER_TEA_ORDER_DETAILS_BOTTOM = BASEURL + "orderGoodsController/queryById";
    //商户中心-核销订单
    public static String SHOP_CENTER_WRITE_OFF_ORDER = BASEURL + "shopOrderController/verificationOrder";
    //优惠卡
    public static String CARD_CREATE_ORDER = BASEURL + "GoodsCartPayController/payGoodsCart";
    //微信支付
    public static String WX_PAY = BASEURL + "test/addWxOrder";
    //支付宝支付
    public static String ALIPAY = BASEURL + "wxpay/getAlipayOrder";

    //自营奶茶店铺信息列表
    public static String STORES_LIST = BASEURL + "bbShopController/queryMilkTeaList";
    //自营奶茶店铺详情
    public static String STORES_DETAILS = BASEURL + "bbShopController/queryMilkTeaByShopId";
    //自营奶茶商品分类名称
    public static String GOODS_LEFT = BASEURL + "goodsCategoryController/queryMilkTeaCateGory";
    //自营奶茶商品分类列表
    public static String GOODS_LIST = BASEURL + "goodsController/queryMilkTeGoodsByShopId";
    //自营奶茶加入购物车
    public static String ADD_SHOPPING_CAR = BASEURL + "goodsCartController/addGoodsCard";
    //自营奶茶购物车
    public static String SHOPPING_CAR = BASEURL + "goodsCartController/queryGoodsCard";
    //自营奶茶更新购物车
    public static String UPDATE_GOODS_CART = BASEURL + "goodsCartController/updateGoodsCart";
    //自營奶茶清空购物车
    public static String CLEAR_SHOPPING_CAR = BASEURL + "goodsCartController/deleteGoodsCart";
    //自营奶茶确认订单未支付接口
    public static String UNPAID_GOODS_ORDER = BASEURL + "shopOrderController/addMilkTeaOrder";
    //自营奶茶订单列表
    public static String MILK_TEA_ORDER = BASEURL + "userOrderController/queryMilkTeaOrderList";
    //个人中心-奶茶订单列表中订单详情接口
    public static String MILK_TEA_ORDER_DETAILS = BASEURL + "orderGoodsController/queryById";
    //奶茶订单-取消订单
    public static String CANCLE_GOODS_ORDER = BASEURL + "userOrderController/removeOrder";
    //奶茶订单-删除订单
    public static String DELETE_GOODS_ORDER = BASEURL + "userOrderController/deleteOrder";
    //奶茶订单-再来一单
    public static String AGAIN_ONE_ORDER = BASEURL + "userOrderController/addAgainOrder";
    //自营奶茶-订单详情已支付订单
    public static String PAYMENT_GOOD_ORDER = BASEURL + "shopOrderController/updateOrder";
    //奶茶订单-用户评价
    public static String USER_EVALUATION_ORDER = BASEURL + "userOrderController/updateMark";
    //自营奶茶微信支付
    public static String MILK_TEA_WECHAT_PAY = BASEURL + "test/addWxOrder";
    //奶茶支付成功后检查是否有抽奖弹窗
    public static String QUERY_ACTIVATY_IS_EXIST = BASEURL + "shopOrderController/queryActivatyIsExist";


    //意见反馈
    public static String FEED_BACK = BASEURL + "v1/feedback/addFeedBack";
    //我的抽奖
    public static String USER_DRAW = BASEURL + "userDrawController/queryUserDarw";
    //用户实名认证
    public static String USER_ADD_APPROVE = BASEURL + "v1/usermag/addUserApprove";
    //修改个人信息
    public static String UPDATE_USER_MAG = BASEURL + "v1/usermag/updateUserMag";
    //上传文件到本地
    public static String UPLOAD_IMAGE_LOCAL = BASEURL + "v1/common/uploadImageLocal";
    //上一张头像
    public static String OLD_USER_IMAGE = BASEURL + "v1/usermag/getOldUserImg";
    //获取用户信息
    public static String GET_USER_INFO = BASEURL + "v1/usermag/getUser";
    //商户中心-抽奖活动
    public static String QUERY_ACTIVITY = BASEURL + "activityController/queryActivityInfo";
    //商户中心-开始抽奖中奖名单
    public static String QUERY_LUCKY_USER = BASEURL + "prizeController/queryLuckyUser";
    //商户中心-开始抽奖用户池
    public static String QUERY_USER_DRAW_LIST = BASEURL + "userDrawController/queryUserDarwList";
    //商户中心-抽奖弹窗
    public static String LUCK_DRAW_POP = BASEURL + "bbUserConnectPrizeController/queryById";
    //保存抽奖用户返回中奖用户信息
    public static String LUCK_DRAW_ADD = BASEURL + "bbUserConnectPrizeController/addLuckyUser";

    //核销订单列表
    public static String AFTER_ORDER = BASEURL + "shopOrderController/verificationOrder";
    //核销奶茶
    public static String AFTER_TEA_ORDER = BASEURL + "goodsController/afterTeaOrder";
    //核销奶茶信息
    public static String WRITE_OFF_TEA_ORDER_INFO = BASEURL + "shopOrderController/teaAfter";
    //核销我的优惠卡
    public static String AFTER_MY_CARD = BASEURL + "cardController/afterMyCard";

    //收货地址展示
    public static String ADDRESS_LIST = BASEURL + "v1/restaddress/queryAddress";
    // 设置默认地址
    public static String ADDRESS_SETUP_DEFAULT = BASEURL + "v1/restaddress/editDefaultAddress";
    // 删除收货地址
    public static String ADDRESS_DELETE = BASEURL + "v1/restaddress/deleteAddress";
    //  添加地址
    public static String ADDRESS_ADD = BASEURL + "v1/restaddress/addAddress";

    //  技能帮扶全部分类
    public static String SERVE_ALL_CLASS = BASEURL + "v1/RestServeType/getServe";

    //我的橱窗订单管理
    public static String WINDOW_ORDER_MANAGE = BASEURL + "v1/RestShopCysc/orderManage";
    //创业商城商品类别展示
    public static String WINDOW_MY_GOODS_STYLE = BASEURL + "goodsCategoryController/getCuangYeShopCateGory";
    //创业商城我的商品展示
    public static String WINDOW_MY_GOODS_SHOW = BASEURL + "v1/RestShopCysc/queryCyscGoods";
    //创业商城所有商品展示
    public static String WINDOW_ALL_GOODS_SHOW = BASEURL + "RestChuanagYeShangCheng/getCyscGoodsList";
    //创业商城我的商品添加
    public static String WINDOW_MY_GOODS_ADD = BASEURL + "v1/RestShopCysc/addCyscGoods";
    //创业商城我的商品删除
    public static String WINDOW_MY_GOODS_DEL = BASEURL + "v1/RestShopCysc/deleCyscGoods";
    //创业商城我的商品详情
    public static String WINDOW_MY_GOODS_DETAIL = BASEURL + "RestChuanagYeShangCheng/queryGoodsCysc";
    //创业商城搜索商品列表
    public static String WINDOW_SEARCH_GOODS_LIST = BASEURL + "RestChuanagYeShangCheng/searchGoodsByName";

    //我的橱窗数据报表
    public static String WINDOW_DATA_LIST = BASEURL + "v1/RestShopCysc/dataReport";

    //商家入驻
    public static String SHOP_STATIONED = BASEURL + "v1/restMerchant/addMerchant";

    //获取用户主页信息
    public static String GET_USER_HOME_PAGE_INFO = BASEURL + "UserHomePageController/getUserHomeMesg";
    //获取用户动态
    public static String GET_USER_DYNAMIC = BASEURL + "UserHomePageController/getUserDynamic";
    //删除用户动态
    public static String DELETE_USER_DYNAMIC = BASEURL + "UserHomePageController/deleteUserDynamic";
    //添加用户动态
    public static String ADD_USER_DYNAMIC = BASEURL + "UserHomePageController/addUserDynamic";
    //修改用户技能
    public static String UPDATE_USER_SKILL = BASEURL + "UserHomePageController/updateUserSkill";
    //获取用户技能列表
    public static String GET_USER_SKILL_LIST = BASEURL + "UserHomePageController/getUserSkill";
    //修改用户工作时间
    public static String UPDATE_USER_WORK_TIME = BASEURL + "UserHomePageController/updateUserTime";
    //修改我的优势
    public static String UPDATE_USER_ADVANTAGE = BASEURL + "UserHomePageController/editorUserAdvantage";
    //获取优势标签列表(最多选择四个)
    public static String GET_USER_ADVANTAGE_LABEL_LIST = BASEURL + "UserHomePageController/getAdvantage";
    //上传视频
    public static String UPLOAD_VIDEO = BASEURL + "v1/common/uploadVideo";
    //获取用户短视频
    public static String GET_USER_SHORT_VIDEO = BASEURL + "UserHomePageController/getUserVido";
    //生成在线指导订单
    public static String CREATE_IM_ORDER = BASEURL + "shopOrderController/addIMorder";
    //选择意向金金额
    public static String CHOOSE_INTENTION_MONEY = BASEURL + "/v1/restSysConfig/queryServerMoney";
    //聊天过渡页返回通知用户数量
    public static String GET_IM_SERVICE_USER_LIST = BASEURL + "v1/RestServeType/getUserNumber";
    //发布服务-添加要求
    public static String ADD_SERVICE_REQUIRE = BASEURL + "v1/restUserAsk/addUserAsk";
    //发布服务-发布
    public static String RELEASE_SERVICE = BASEURL + "v1/restTask/addServerTask";
    //加入服务
    public static String JOIN_SERVICE = BASEURL + "v1/restUserServe/addUserServer";
    //社交和家政服务聊天过渡页和选择用户数据
    public static String GET_SERVICE_USER_LIST = BASEURL + "v1/restUserAsk/getServerUser";
    //选择用户
    public static String SELECT_SERVICE_USER = BASEURL + "v1/RestServeType/pitchUser";
    //设置上次选择的城市
    public static String SET_LATEST_CITY = BASEURL + "v1/usermag/setLastCity";
    //通过手机号查找用户店铺
    public static String QUERY_SHOP_BY_MOBILE = BASEURL + "/v1/user/getUserShopId";
    //创业商城商品类别展示
    public static String VENTURE_SHOP_CATE_LIST = BASEURL + "goodsCategoryController/getCuangYeShopCateGory";
    //创业商城商品根据类别展示
    public static String VENTURE_SHOP_GOODS_LIST = BASEURL + "RestChuanagYeShangCheng/getCyscGoodsList";
    //创业商城我的商品展示
    public static String VENTURE_SHOP_GOODS_DETAILS = BASEURL + "v1/RestShopCysc/queryCyscGoods";
    //创业商城我的商品添加（选定）
    public static String VENTURE_SHOP_ADD_GOODS = BASEURL + "v1/RestShopCysc/addCyscGoods";
    //创业商城我的商品删除
    public static String VENTURE_SHOP_DELETE_GOODS = BASEURL + "v1/RestShopCysc/deleCyscGoods";
    //创业商城我的购物车弹窗
    public static String VENTURE_SHOP_CART_POP = BASEURL + "v1/RestShopCysc/getCyescCard";
    //更新创业商城购物车的数量
    public static String VENTURE_SHOP_UPDATE_CART_COUNT = BASEURL + "goodsCartController/updateGoodsCart";
    //购买创业商城商品添加到购物车
    public static String VENTURE_SHOP_ADD_CART = BASEURL + "goodsCartController/queryGoodsCard";
    //创业商城清空购物车
    public static String VENTURE_SHOP_CLEAR_CART = BASEURL + "goodsCartController/deleteGoodsCart";
    //清空购物车创建订单和返回订单商品信息
    public static String VENTURE_SHOP_CREATE_ORDER = BASEURL + "v1/RestShopCysc/addOrderCysc";
    //结算购物车选择收货地址
    public static String VENTURE_SHOP_CHOOSE_ADDRESS = BASEURL + "v1/RestShopCysc/updateOrderAddress";
    //支付宝支付创业商城商品
    public static String VENTURE_SHOP_ALI_PAY = BASEURL + "wxpay/getAlipayOrder";
    //微信支付创业商城商品
    public static String VENTURE_SHOP_WX_PAY = BASEURL + "test/addWxOrder";
    //创业商城商品详情
    public static String GET_VENTURE_SHOP_GOODS_DETAILS = BASEURL + "RestChuanagYeShangCheng/queryGoodsCysc";
    //创业商城商品详情生成订单
    public static String VENTURE_SHOP_GOODS_DETAILS_CREATE_ORDER = BASEURL + "GoodsCartPayController/payCyscGoods";
    //发布服务页面 支付意向金生成订单
    public static String PAY_INTENTION_MONEY = BASEURL + "GoodsCartPayController/addJnbfOrder";
    //系统消息
    public static String SYSTEM_MSG_LIST = BASEURL + "v1/ConfigAnnouncement/queryAnnouncementList";
    //选择用户时支付剩余金额
    public static String PAY_SURPLUS_MONEY = BASEURL + "GoodsCartPayController/restReturnOrderInfo";
    //修改服务价格
    public static String UPDATE_SERVICE_PRICE = BASEURL + "v1/restUserServe/updateUserServerPrice";
    //限制聊天次数
    public static String REMAIN_CHAT_TIMES = BASEURL + "v1/userMsg/counts";
    //发送礼物
    public static String RECHARGE_GIFT = BASEURL + "v1/restUserGift/shopGift";


    //购物订单
    public static String SHOPPING_ORDER_LIST = BASEURL + "v1/RestShopCysc/myShopOrder";
    //取消订单
    public static String SHOPPING_ORDER_CANCEL = BASEURL + "v1/RestShopCysc/cancellationOrder";
    //提醒发货
    public static String SHOPPING_ORDER_REMIND = BASEURL + "v1/RestShopCysc/remindOrder";
    //查看物流
    public static String SHOPPING_ORDER_LOOK = BASEURL + "v1/RestShopCysc/selectLogistics";
    //确认收货
    public static String SHOPPING_ORDER_CONFIRM = BASEURL + "v1/RestShopCysc/confirmationOfGoods";
    //我的购物订单删除
    public static String SHOPPING_ORDER_DELETE = BASEURL + "v1/RestShopCysc/deleteMyOrder";
    //我的购物订单申请售后
    public static String SHOPPING_ORDER_SERVER = BASEURL + "v1/afterGoods/addAfterGoods";
    //我的购物订单再来一单
    public static String SHOPPING_ORDER_AGAIN = BASEURL + "v1/afterGoods/anotherList";

    //微信支付宝充值余额生成订单
    public static String RECHARGE_CREATE_ORDER_ID = BASEURL + "v1/recharge/addOrderRecharge";
    //钱包明细
    public static String WALLET_DETAIL_LIST = BASEURL + "v1/accountBill/getUserBill";
    //钱包明细
    public static String WALLET_BALANCE = BASEURL + "v1/accountBill/getUserBalance";
    //提现
    public static String WALLET_WITHDRAW = BASEURL + "v1/resztUserPay/userPayMoney";
    //否启用支付密
    public static String WALLET_IS_PASSWORD_PAY = BASEURL + "v1/accountBill/payPassword";
    //设置支付密码
    public static String SET_PAY_PASSWORD = BASEURL + "v1/accountBill/resetPayPassword";

    //服务管理列表
    public static String SERVER_ORDER_LIST_ = BASEURL + "v1/restTask/getUserServer";
    //接单
    public static String SERVER_ORDER_ALLOW = BASEURL + "v1/restTask/acceptServerOrder";
    //拒绝接单
    public static String SERVER_ORDER_DENY = BASEURL + "v1/restTask/refuseServerOrder";
    //评价接受订单用户
    public static String SERVER_ORDER_COMMENT = BASEURL + "v1/restTask/updateServerOrderMark";
    //修改我发布的服务订单状态 点击完成修改我发布服务的状态
    public static String SERVER_ORDER_FINISH = BASEURL + "v1/restTask/updateServerOrder";

    //添加技能
    public static final String SKILL_ADD = BASEURL + "userSkillController/addSkill";
    //添加技能
    public static final String SKILL_DEL = BASEURL + "userSkillController/deleteUserSkill";
}
