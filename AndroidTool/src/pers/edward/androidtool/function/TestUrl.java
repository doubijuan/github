package pers.edward.androidtool.function;

public class TestUrl
{
	private static String INTERNET_URL = "";

	/**
	 * Android代码自动生成器
	 * 
	 * 接口地址：http://120.25.218.242:9012/api/Advertisements/GetSecondAdvertisement
	 * 
	 * 请求方式：GET
	 * 
	 * 接口描述： 获取首页二级广告的信息,get请求格式
	 */
	public static String GetSecondAdvertisement = INTERNET_URL + "/api/Advertisements/GetSecondAdvertisement";

	/**
	 * Android代码自动生成器
	 * 
	 * 接口地址：http://120.25.218.242:9012/api/Advertisements/GetSPAdvertisementList
	 * 
	 * 请求方式：GET
	 * 
	 * 接口描述：获取【超市】广告 Get请求
	 */
	public static String GetSPAdvertisementList = INTERNET_URL + "/api/Advertisements/GetSPAdvertisementList";

	/**
	 * Android代码自动生成器
	 * 
	 * 接口地址：http://120.25.218.242:9012/api/Order/DeleteShoppingCartProduct
	 * 
	 * 请求方式：POST
	 * 
	 * 接口描述： 删除购物车商品，POST请求格式:userId=用户ID&amp;productId=产品ID(如果要删除多个用英文逗号隔开);
	 * 若productId&lt;0，则认为是删除购物车中所有产品（清空购物车）
	 */
	public static String DeleteShoppingCartProduct = INTERNET_URL + "/api/Order/DeleteShoppingCartProduct";

	/**
	 * Android代码自动生成器
	 * 
	 * 接口地址：http://120.25.218.242:9012/api/Order/GetStayCommentOrder?userId={
	 * USERID}&amp;pageNum={PAGENUM}&amp;pageSize={PAGESIZE}
	 * 
	 * 请求方式：GET
	 * 
	 * 接口描述：
	 * 分页获取待评论的订单，get请求格式:userId=用户ID&amp;pageNum=当前页码&amp;pageSize=一页显示商品数
	 */
	public static String GetStayCommentOrder = INTERNET_URL + "/api/Order/GetStayCommentOrder";

	/**
	 * Android代码自动生成器
	 * 
	 * 接口地址：http://120.25.218.242:9012/api/Order/GetAffirmOrederProduct?userId={
	 * USERID}&amp;productIds={PRODUCTIDS}
	 * 
	 * 请求方式：GET
	 * 
	 * 接口描述：
	 * 获取确认订单信息的商品，get请求格式:userId=用户ID&amp;productIds=产品的ID(如果有多个用逗号隔开(要是英文的逗号))
	 */
	public static String GetAffirmOrederProduct = INTERNET_URL + "/api/Order/GetAffirmOrederProduct";

	/**
	 * Android代码自动生成器
	 * 
	 * 接口地址：http://120.25.218.242:9012/api/Order/GetOrderItem?orderId={ORDERID}
	 * 
	 * 请求方式：GET
	 * 
	 * 接口描述：根据订单ID获取订单详情 Get请求 orderId=订单ID
	 */
	public static String GetOrderItem = INTERNET_URL + "/api/Order/GetOrderItem";

	/**
	 * Android代码自动生成器
	 * 
	 * 接口地址：http://120.25.218.242:9012/api/Order/GetStayReceivingOrStaySendGoods
	 * ?userId={USERID}&amp;pageNum={PAGENUM}&amp;pageSize={PAGESIZE}&amp;
	 * shippingStatus={SHIPPINGSTATUS}
	 * 
	 * 请求方式：GET
	 * 
	 * 接口描述： 分页获取待发货或者待收货订单，get请求格式:userId=用户ID&amp;pageNum=当前页码&amp;pageSize=
	 * 一页显示商品数&amp;shippingStatus=发货状态(0待发货，2已发货)
	 */
	public static String GetStayReceivingOrStaySendGoods = INTERNET_URL + "/api/Order/GetStayReceivingOrStaySendGoods";

	/**
	 * Android代码自动生成器
	 * 
	 * 接口地址：http://120.25.218.242:9012/api/Order/UpdateOrderStatusByOrderId
	 * 
	 * 请求方式：POST
	 * 
	 * 接口描述：修改订单状态为：【已确认】 POST请求 orderId=订单ID
	 */
	public static String UpdateOrderStatusByOrderId = INTERNET_URL + "/api/Order/UpdateOrderStatusByOrderId";

	/**
	 * Android代码自动生成器
	 * 
	 * 接口地址：http://120.25.218.242:9012/api/Account/AddShippingAddres
	 * 
	 * 请求方式：POST
	 * 
	 * 接口描述：添加用收货地址，post请求格式:shipName=收货人&amp;fullName=区域全地址(各个区域用逗号隔开如：广东省,广州市,
	 * 天河区
	 * )&amp;address=详细地址&amp;celPhone=收货人手机号码&amp;zipcode=邮编&amp;userId=用户的ID
	 * &amp;isDefault=是否设为默认地址(1代表设为默认，0代表不设为默认))
	 */
	public static String AddShippingAddres = INTERNET_URL + "/api/Account/AddShippingAddres";

	/**
	 * Android代码自动生成器
	 * 
	 * 接口地址：http://120.25.218.242:9012/api/Account/DeleteUserAddressByID?
	 * shippingId={SHIPPINGID}
	 * 
	 * 请求方式：GET
	 * 
	 * 接口描述：根据地址ID删除用户收货地址，get请求,shippingId=用户收货地址标识ID
	 */
	public static String DeleteUserAddressByID = INTERNET_URL + "/api/Account/DeleteUserAddressByID";

	/**
	 * Android代码自动生成器
	 * 
	 * 接口地址：http://120.25.218.242:9012/api/Account/GetShippingAddresByID?
	 * shippingID={SHIPPINGID}
	 * 
	 * 请求方式：GET
	 * 
	 * 接口描述：根据地址ID获取地址详情,Get请求,shippingID=地址ID
	 */
	public static String GetShippingAddresByID = INTERNET_URL + "/api/Account/GetShippingAddresByID";

	/**
	 * Android代码自动生成器
	 * 
	 * 接口地址：http://120.25.218.242:9012/api/Account/GetUserAddressByUserID?UserID
	 * ={USERID}
	 * 
	 * 请求方式：GET
	 * 
	 * 接口描述：获取指定用户收货地址列表,Get请求,UserID=用户ID
	 */
	public static String GetUserAddressByUserID = INTERNET_URL + "/api/Account/GetUserAddressByUserID";

	/**
	 * Android代码自动生成器
	 * 
	 * 接口地址：http://120.25.218.242:9012/api/Account/Register
	 * 
	 * 请求方式：POST
	 * 
	 * 接口描述：用户注册，post请求格式:Name=用户名&amp;Pwd=密码&amp;MobliePhoneCode=验证码)
	 */
	public static String Register = INTERNET_URL + "/api/Account/Register";

	/**
	 * Android代码自动生成器
	 * 
	 * 接口地址：http://120.25.218.242:9012/api/Account/UpdateDefdaultAddress
	 * 
	 * 请求方式：POST
	 * 
	 * 接口描述：设置用户地址为默认收货地址,post请求格式:userID=用户ID&amp;shippingId=地址标识ID
	 */
	public static String UpdateDefdaultAddress = INTERNET_URL + "/api/Account/UpdateDefdaultAddress";

	/**
	 * Android代码自动生成器
	 * 
	 * 接口地址：http://120.25.218.242:9012/api/Account/UpdateShippingAddres
	 * 
	 * 请求方式：POST
	 * 
	 * 接口描述：更新收货地址，post请求格式:shipName=收货人&amp;regionId=区域ID&amp;address=详细地址&amp;
	 * celPhone=收货人手机号码&amp;zipcode=邮编&amp;ShippingId=收货地址ID)
	 */
	public static String UpdateShippingAddres = INTERNET_URL + "/api/Account/UpdateShippingAddres";

	/**
	 * Android代码自动生成器
	 * 
	 * 接口地址：http://120.25.218.242:9012/api/Account/UpdateUserPwdByUserId
	 * 
	 * 请求方式：POST
	 * 
	 * 接口描述：修改用户密码 POST请求 userId=用户ID&amp;password=新密码&amp;affirmPwd=确认密码
	 */
	public static String UpdateUserPwdByUserId = INTERNET_URL + "/api/Account/UpdateUserPwdByUserId";

	/**
	 * Android代码自动生成器
	 * 
	 * 接口地址：http://120.25.218.242:9012/api/Account/UserLogin
	 * 
	 * 请求方式：POST
	 * 
	 * 接口描述：用户登录，post请求格式:Name=用户名&amp;Password=密码)
	 */
	public static String UserLogin = INTERNET_URL + "/api/Account/UserLogin";

	/**
	 * Android代码自动生成器
	 * 
	 * 接口地址：http://120.25.218.242:9012/api/Consult/GetCityConsultingByKey?
	 * pageindex={PAGEINDEX}&amp;pageSize={PAGESIZE}&amp;key={KEY}
	 * 
	 * 请求方式：GET
	 * 
	 * 接口描述：根据关键字搜索同城信息 get请求：pageindex=当前页码&amp;pageSize=每页显示数量&amp;key=查询关键字
	 */
	public static String GetCityConsultingByKey = INTERNET_URL + "/api/Consult/GetCityConsultingByKey";

	/**
	 * Android代码自动生成器
	 * 
	 * 接口地址：http://120.25.218.242:9012/api/Consult/GetRegionIDByFullName?
	 * fullName={FULLNAME}
	 * 
	 * 请求方式：GET
	 * 
	 * 接口描述：根据区域全名获取区域ID get请求 fullName=区域全名
	 */
	public static String GetRegionIDByFullName = INTERNET_URL + "/api/Consult/GetRegionIDByFullName";

	/**
	 * Android代码自动生成器
	 * 
	 * 接口地址：http://120.25.218.242:9012/api/Supermarket/GetSPProductCategory
	 * 
	 * 请求方式：GET
	 * 
	 * 接口描述：获得【超市】商品类型列表 get请求
	 */
	public static String GetSPProductCategory = INTERNET_URL + "/api/Supermarket/GetSPProductCategory";

}