package pers.edward.androidtool.function;

public class TestUrl
{
	private static String INTERNET_URL = "";

	/**
	 * Android代码自动生成器
	 * 
	 * 接口地址：http://120.25.218.242:9012/api/DiningRoom/GetDiningRoom?pageindex={
	 * PAGEINDEX}&amp;pageSize={PAGESIZE}
	 * 
	 * 请求方式：GET
	 * 
	 * 接口描述：分页获取餐厅美食 get pageindex=当前页码&amp;pageSize=每页显示数量
	 */
	public static String GetDiningRoom = INTERNET_URL + "/api/DiningRoom/GetDiningRoom";

	/**
	 * Android代码自动生成器
	 * 
	 * 接口地址：http://120.25.218.242:9012/api/Product/GetCountDownByID?productId={
	 * PRODUCTID}
	 * 
	 * 请求方式：GET F 接口描述：根据抢购产品ID获取抢购产品详情 get请求格式:productId=产品ID
	 */
	public static String GetCountDownByID = INTERNET_URL + "/api/Product/GetCountDownByID";

	/**
	 * Android代码自动生成器
	 * 
	 * 接口地址：http://120.25.218.242:9012/api/Consult/AddCommentForCityConsulting
	 * 
	 * 请求方式：POST
	 * 
	 * 接口描述：对同城资讯添加评论 POST请求格式
	 * SP_ConsultID=资讯ID&amp;UserId=用户ID&amp;ReviewText=评论内容
	 * &amp;ParentId=父ID(是否针对某条评论进行再次评论，是则传被评论的评论ID，如果不是则传0)
	 */
	public static String AddCommentForCityConsulting = INTERNET_URL + "/api/Consult/AddCommentForCityConsulting";

	/**
	 * Android代码自动生成器
	 * 
	 * 接口地址：http://120.25.218.242:9012/api/Consult/Click_Praise
	 * 
	 * 请求方式：POST
	 * 
	 * 接口描述：点赞和取消点赞,post请求:userId=点赞用户ID&amp;consultID=资讯ID
	 */
	public static String Click_Praise = INTERNET_URL + "/api/Consult/Click_Praise";

	/**
	 * Android代码自动生成器
	 * 
	 * 接口地址：http://120.25.218.242:9012/api/Consult/GetCityConsulting?pageindex={
	 * PAGEINDEX}&amp;pageSize={PAGESIZE}&amp;type={TYPE}
	 * 
	 * 请求方式：GET
	 * 
	 * 接口描述：获取所有资讯的信息;get请求，pageindex=当前页码&amp;pageSize=一页显示数量&amp;type=资讯类型;1代表
	 * :人才招聘;2代表:二手物品;3代表:房屋信息;4代表:本地资讯
	 */
	public static String GetCityConsulting = INTERNET_URL + "/api/Consult/GetCityConsulting";

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
	 * 接口地址：http://120.25.218.242:9012/api/Consult/GetCommentByID?SP_ConsultID={
	 * SP_CONSULTID}&amp;pageindex={PAGEINDEX}&amp;pageSize={PAGESIZE}
	 * 
	 * 请求方式：GET
	 * 
	 * 接口描述：根据资讯ID获取资讯评论 get请求
	 * SP_ConsultID=资讯ID&amp;pageindex=当前页码&amp;pageSize=每页显示数量
	 */
	public static String GetCommentByID = INTERNET_URL + "/api/Consult/GetCommentByID";

	/**
	 * Android代码自动生成器
	 * 
	 * 接口地址：http://120.25.218.242:9012/api/Consult/GetRegionIDByCityName?
	 * CityName={CITYNAME}
	 * 
	 * 请求方式：GET
	 * 
	 * 接口描述：根据城市名获取区域ID get请求 CityName=城市名称
	 */
	public static String GetRegionIDByCityName = INTERNET_URL + "/api/Consult/GetRegionIDByCityName";

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
	 * 接口地址：http://120.25.218.242:9012/api/Consult/IsUserPraise?consultID={
	 * CONSULTID}&amp;userID={USERID}
	 * 
	 * 请求方式：GET
	 * 
	 * 接口描述：判断用户是否点赞 Get请求 consultID=资讯ID&amp;userID=用户ID
	 */
	public static String IsUserPraise = INTERNET_URL + "/api/Consult/IsUserPraise";

	/**
	 * Android代码自动生成器
	 * 
	 * 接口地址：http://120.25.218.242:9012/api/Order/DeleteOrderByOrderId
	 * 
	 * 请求方式：POST
	 * 
	 * 接口描述：根据订单ID删除订单 POST请求 orderId=订单ID
	 */
	public static String DeleteOrderByOrderId = INTERNET_URL + "/api/Order/DeleteOrderByOrderId";

	/**
	 * Android代码自动生成器
	 * 
	 * 接口地址：http://120.25.218.242:9012/api/Order/GetAllOrderByUserID?userID={
	 * USERID
	 * }&amp;orderId={ORDERID}&amp;pageIndex={PAGEINDEX}&amp;pageSize={PAGESIZE}
	 * 
	 * 请求方式：GET
	 * 
	 * 接口描述：获取指定用户的所有订单 get请求
	 * userID=用户ID&amp;orderId=订单ID(0代表查看该用户的全部订单)&amp;pageIndex
	 * =当前页码&amp;pageSize=每页显示数量
	 */
	public static String GetAllOrderByUserID = INTERNET_URL + "/api/Order/GetAllOrderByUserID";

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

}