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
	 * 请求方式：GET
	 * 
	 * 接口描述：根据抢购产品ID获取抢购产品详情 get请求格式:productId=产品ID
	 */
	public static String GetCountDownByID = INTERNET_URL + "/api/Product/GetCountDownByID";

}