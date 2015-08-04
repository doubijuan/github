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
	 * ����ʽ��GET F �ӿ�����������������ƷID��ȡ������Ʒ���� get�����ʽ:productId=��ƷID
	 */
	public static String GetCountDownByID = INTERNET_URL + "/api/Product/GetCountDownByID";

	/**
	 * Android�����Զ�������
	 * 
	 * �ӿڵ�ַ��http://120.25.218.242:9012/api/Consult/AddCommentForCityConsulting
	 * 
	 * ����ʽ��POST
	 * 
	 * �ӿ���������ͬ����Ѷ������� POST�����ʽ
	 * SP_ConsultID=��ѶID&amp;UserId=�û�ID&amp;ReviewText=��������
	 * &amp;ParentId=��ID(�Ƿ����ĳ�����۽����ٴ����ۣ����򴫱����۵�����ID�����������0)
	 */
	public static String AddCommentForCityConsulting = INTERNET_URL + "/api/Consult/AddCommentForCityConsulting";

	/**
	 * Android�����Զ�������
	 * 
	 * �ӿڵ�ַ��http://120.25.218.242:9012/api/Consult/Click_Praise
	 * 
	 * ����ʽ��POST
	 * 
	 * �ӿ����������޺�ȡ������,post����:userId=�����û�ID&amp;consultID=��ѶID
	 */
	public static String Click_Praise = INTERNET_URL + "/api/Consult/Click_Praise";

	/**
	 * Android�����Զ�������
	 * 
	 * �ӿڵ�ַ��http://120.25.218.242:9012/api/Consult/GetCityConsulting?pageindex={
	 * PAGEINDEX}&amp;pageSize={PAGESIZE}&amp;type={TYPE}
	 * 
	 * ����ʽ��GET
	 * 
	 * �ӿ���������ȡ������Ѷ����Ϣ;get����pageindex=��ǰҳ��&amp;pageSize=һҳ��ʾ����&amp;type=��Ѷ����;1����
	 * :�˲���Ƹ;2����:������Ʒ;3����:������Ϣ;4����:������Ѷ
	 */
	public static String GetCityConsulting = INTERNET_URL + "/api/Consult/GetCityConsulting";

	/**
	 * Android�����Զ�������
	 * 
	 * �ӿڵ�ַ��http://120.25.218.242:9012/api/Consult/GetCityConsultingByKey?
	 * pageindex={PAGEINDEX}&amp;pageSize={PAGESIZE}&amp;key={KEY}
	 * 
	 * ����ʽ��GET
	 * 
	 * �ӿ����������ݹؼ�������ͬ����Ϣ get����pageindex=��ǰҳ��&amp;pageSize=ÿҳ��ʾ����&amp;key=��ѯ�ؼ���
	 */
	public static String GetCityConsultingByKey = INTERNET_URL + "/api/Consult/GetCityConsultingByKey";

	/**
	 * Android�����Զ�������
	 * 
	 * �ӿڵ�ַ��http://120.25.218.242:9012/api/Consult/GetCommentByID?SP_ConsultID={
	 * SP_CONSULTID}&amp;pageindex={PAGEINDEX}&amp;pageSize={PAGESIZE}
	 * 
	 * ����ʽ��GET
	 * 
	 * �ӿ�������������ѶID��ȡ��Ѷ���� get����
	 * SP_ConsultID=��ѶID&amp;pageindex=��ǰҳ��&amp;pageSize=ÿҳ��ʾ����
	 */
	public static String GetCommentByID = INTERNET_URL + "/api/Consult/GetCommentByID";

	/**
	 * Android�����Զ�������
	 * 
	 * �ӿڵ�ַ��http://120.25.218.242:9012/api/Consult/GetRegionIDByCityName?
	 * CityName={CITYNAME}
	 * 
	 * ����ʽ��GET
	 * 
	 * �ӿ����������ݳ�������ȡ����ID get���� CityName=��������
	 */
	public static String GetRegionIDByCityName = INTERNET_URL + "/api/Consult/GetRegionIDByCityName";

	/**
	 * Android�����Զ�������
	 * 
	 * �ӿڵ�ַ��http://120.25.218.242:9012/api/Consult/GetRegionIDByFullName?
	 * fullName={FULLNAME}
	 * 
	 * ����ʽ��GET
	 * 
	 * �ӿ���������������ȫ����ȡ����ID get���� fullName=����ȫ��
	 */
	public static String GetRegionIDByFullName = INTERNET_URL + "/api/Consult/GetRegionIDByFullName";

	/**
	 * Android�����Զ�������
	 * 
	 * �ӿڵ�ַ��http://120.25.218.242:9012/api/Consult/IsUserPraise?consultID={
	 * CONSULTID}&amp;userID={USERID}
	 * 
	 * ����ʽ��GET
	 * 
	 * �ӿ��������ж��û��Ƿ���� Get���� consultID=��ѶID&amp;userID=�û�ID
	 */
	public static String IsUserPraise = INTERNET_URL + "/api/Consult/IsUserPraise";

	/**
	 * Android�����Զ�������
	 * 
	 * �ӿڵ�ַ��http://120.25.218.242:9012/api/Order/DeleteOrderByOrderId
	 * 
	 * ����ʽ��POST
	 * 
	 * �ӿ����������ݶ���IDɾ������ POST���� orderId=����ID
	 */
	public static String DeleteOrderByOrderId = INTERNET_URL + "/api/Order/DeleteOrderByOrderId";

	/**
	 * Android�����Զ�������
	 * 
	 * �ӿڵ�ַ��http://120.25.218.242:9012/api/Order/GetAllOrderByUserID?userID={
	 * USERID
	 * }&amp;orderId={ORDERID}&amp;pageIndex={PAGEINDEX}&amp;pageSize={PAGESIZE}
	 * 
	 * ����ʽ��GET
	 * 
	 * �ӿ���������ȡָ���û������ж��� get����
	 * userID=�û�ID&amp;orderId=����ID(0����鿴���û���ȫ������)&amp;pageIndex
	 * =��ǰҳ��&amp;pageSize=ÿҳ��ʾ����
	 */
	public static String GetAllOrderByUserID = INTERNET_URL + "/api/Order/GetAllOrderByUserID";

	/**
	 * Android�����Զ�������
	 * 
	 * �ӿڵ�ַ��http://120.25.218.242:9012/api/Order/GetStayCommentOrder?userId={
	 * USERID}&amp;pageNum={PAGENUM}&amp;pageSize={PAGESIZE}
	 * 
	 * ����ʽ��GET
	 * 
	 * �ӿ�������
	 * ��ҳ��ȡ�����۵Ķ�����get�����ʽ:userId=�û�ID&amp;pageNum=��ǰҳ��&amp;pageSize=һҳ��ʾ��Ʒ��
	 */
	public static String GetStayCommentOrder = INTERNET_URL + "/api/Order/GetStayCommentOrder";

}