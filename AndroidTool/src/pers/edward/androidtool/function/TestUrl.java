package pers.edward.androidtool.function;

public class TestUrl
{
	private static String INTERNET_URL = "";

	/**
	 * Android�����Զ�������
	 * 
	 * �ӿڵ�ַ��http://120.25.218.242:9012/api/DiningRoom/GetDiningRoom?pageindex={
	 * PAGEINDEX}&amp;pageSize={PAGESIZE}
	 * 
	 * ����ʽ��GET
	 * 
	 * �ӿ���������ҳ��ȡ������ʳ get pageindex=��ǰҳ��&amp;pageSize=ÿҳ��ʾ����
	 */
	public static String GetDiningRoom = INTERNET_URL + "/api/DiningRoom/GetDiningRoom";

	/**
	 * Android�����Զ�������
	 * 
	 * �ӿڵ�ַ��http://120.25.218.242:9012/api/Product/GetCountDownByID?productId={
	 * PRODUCTID}
	 * 
	 * ����ʽ��GET
	 * 
	 * �ӿ�����������������ƷID��ȡ������Ʒ���� get�����ʽ:productId=��ƷID
	 */
	public static String GetCountDownByID = INTERNET_URL + "/api/Product/GetCountDownByID";

}