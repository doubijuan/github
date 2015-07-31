package pers.edward.androidtool.function;

public class TestUrl
{
	private static String INTERNET_URL = "";

	/**
	 * Android�����Զ�������
	 * 
	 * �ӿڵ�ַ��http://120.25.218.242:9012/api/Advertisements/GetSecondAdvertisement
	 * 
	 * ����ʽ��GET
	 * 
	 * �ӿ������� ��ȡ��ҳ����������Ϣ,get�����ʽ
	 */
	public static String GetSecondAdvertisement = INTERNET_URL + "/api/Advertisements/GetSecondAdvertisement";

	/**
	 * Android�����Զ�������
	 * 
	 * �ӿڵ�ַ��http://120.25.218.242:9012/api/Advertisements/GetSPAdvertisementList
	 * 
	 * ����ʽ��GET
	 * 
	 * �ӿ���������ȡ�����С���� Get����
	 */
	public static String GetSPAdvertisementList = INTERNET_URL + "/api/Advertisements/GetSPAdvertisementList";

	/**
	 * Android�����Զ�������
	 * 
	 * �ӿڵ�ַ��http://120.25.218.242:9012/api/Order/DeleteShoppingCartProduct
	 * 
	 * ����ʽ��POST
	 * 
	 * �ӿ������� ɾ�����ﳵ��Ʒ��POST�����ʽ:userId=�û�ID&amp;productId=��ƷID(���Ҫɾ�������Ӣ�Ķ��Ÿ���);
	 * ��productId&lt;0������Ϊ��ɾ�����ﳵ�����в�Ʒ����չ��ﳵ��
	 */
	public static String DeleteShoppingCartProduct = INTERNET_URL + "/api/Order/DeleteShoppingCartProduct";

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

	/**
	 * Android�����Զ�������
	 * 
	 * �ӿڵ�ַ��http://120.25.218.242:9012/api/Order/GetAffirmOrederProduct?userId={
	 * USERID}&amp;productIds={PRODUCTIDS}
	 * 
	 * ����ʽ��GET
	 * 
	 * �ӿ�������
	 * ��ȡȷ�϶�����Ϣ����Ʒ��get�����ʽ:userId=�û�ID&amp;productIds=��Ʒ��ID(����ж���ö��Ÿ���(Ҫ��Ӣ�ĵĶ���))
	 */
	public static String GetAffirmOrederProduct = INTERNET_URL + "/api/Order/GetAffirmOrederProduct";

	/**
	 * Android�����Զ�������
	 * 
	 * �ӿڵ�ַ��http://120.25.218.242:9012/api/Order/GetOrderItem?orderId={ORDERID}
	 * 
	 * ����ʽ��GET
	 * 
	 * �ӿ����������ݶ���ID��ȡ�������� Get���� orderId=����ID
	 */
	public static String GetOrderItem = INTERNET_URL + "/api/Order/GetOrderItem";

	/**
	 * Android�����Զ�������
	 * 
	 * �ӿڵ�ַ��http://120.25.218.242:9012/api/Order/GetStayReceivingOrStaySendGoods
	 * ?userId={USERID}&amp;pageNum={PAGENUM}&amp;pageSize={PAGESIZE}&amp;
	 * shippingStatus={SHIPPINGSTATUS}
	 * 
	 * ����ʽ��GET
	 * 
	 * �ӿ������� ��ҳ��ȡ���������ߴ��ջ�������get�����ʽ:userId=�û�ID&amp;pageNum=��ǰҳ��&amp;pageSize=
	 * һҳ��ʾ��Ʒ��&amp;shippingStatus=����״̬(0��������2�ѷ���)
	 */
	public static String GetStayReceivingOrStaySendGoods = INTERNET_URL + "/api/Order/GetStayReceivingOrStaySendGoods";

	/**
	 * Android�����Զ�������
	 * 
	 * �ӿڵ�ַ��http://120.25.218.242:9012/api/Order/UpdateOrderStatusByOrderId
	 * 
	 * ����ʽ��POST
	 * 
	 * �ӿ��������޸Ķ���״̬Ϊ������ȷ�ϡ� POST���� orderId=����ID
	 */
	public static String UpdateOrderStatusByOrderId = INTERNET_URL + "/api/Order/UpdateOrderStatusByOrderId";

	/**
	 * Android�����Զ�������
	 * 
	 * �ӿڵ�ַ��http://120.25.218.242:9012/api/Account/AddShippingAddres
	 * 
	 * ����ʽ��POST
	 * 
	 * �ӿ�������������ջ���ַ��post�����ʽ:shipName=�ջ���&amp;fullName=����ȫ��ַ(���������ö��Ÿ����磺�㶫ʡ,������,
	 * �����
	 * )&amp;address=��ϸ��ַ&amp;celPhone=�ջ����ֻ�����&amp;zipcode=�ʱ�&amp;userId=�û���ID
	 * &amp;isDefault=�Ƿ���ΪĬ�ϵ�ַ(1������ΪĬ�ϣ�0������ΪĬ��))
	 */
	public static String AddShippingAddres = INTERNET_URL + "/api/Account/AddShippingAddres";

	/**
	 * Android�����Զ�������
	 * 
	 * �ӿڵ�ַ��http://120.25.218.242:9012/api/Account/DeleteUserAddressByID?
	 * shippingId={SHIPPINGID}
	 * 
	 * ����ʽ��GET
	 * 
	 * �ӿ����������ݵ�ַIDɾ���û��ջ���ַ��get����,shippingId=�û��ջ���ַ��ʶID
	 */
	public static String DeleteUserAddressByID = INTERNET_URL + "/api/Account/DeleteUserAddressByID";

	/**
	 * Android�����Զ�������
	 * 
	 * �ӿڵ�ַ��http://120.25.218.242:9012/api/Account/GetShippingAddresByID?
	 * shippingID={SHIPPINGID}
	 * 
	 * ����ʽ��GET
	 * 
	 * �ӿ����������ݵ�ַID��ȡ��ַ����,Get����,shippingID=��ַID
	 */
	public static String GetShippingAddresByID = INTERNET_URL + "/api/Account/GetShippingAddresByID";

	/**
	 * Android�����Զ�������
	 * 
	 * �ӿڵ�ַ��http://120.25.218.242:9012/api/Account/GetUserAddressByUserID?UserID
	 * ={USERID}
	 * 
	 * ����ʽ��GET
	 * 
	 * �ӿ���������ȡָ���û��ջ���ַ�б�,Get����,UserID=�û�ID
	 */
	public static String GetUserAddressByUserID = INTERNET_URL + "/api/Account/GetUserAddressByUserID";

	/**
	 * Android�����Զ�������
	 * 
	 * �ӿڵ�ַ��http://120.25.218.242:9012/api/Account/Register
	 * 
	 * ����ʽ��POST
	 * 
	 * �ӿ��������û�ע�ᣬpost�����ʽ:Name=�û���&amp;Pwd=����&amp;MobliePhoneCode=��֤��)
	 */
	public static String Register = INTERNET_URL + "/api/Account/Register";

	/**
	 * Android�����Զ�������
	 * 
	 * �ӿڵ�ַ��http://120.25.218.242:9012/api/Account/UpdateDefdaultAddress
	 * 
	 * ����ʽ��POST
	 * 
	 * �ӿ������������û���ַΪĬ���ջ���ַ,post�����ʽ:userID=�û�ID&amp;shippingId=��ַ��ʶID
	 */
	public static String UpdateDefdaultAddress = INTERNET_URL + "/api/Account/UpdateDefdaultAddress";

	/**
	 * Android�����Զ�������
	 * 
	 * �ӿڵ�ַ��http://120.25.218.242:9012/api/Account/UpdateShippingAddres
	 * 
	 * ����ʽ��POST
	 * 
	 * �ӿ������������ջ���ַ��post�����ʽ:shipName=�ջ���&amp;regionId=����ID&amp;address=��ϸ��ַ&amp;
	 * celPhone=�ջ����ֻ�����&amp;zipcode=�ʱ�&amp;ShippingId=�ջ���ַID)
	 */
	public static String UpdateShippingAddres = INTERNET_URL + "/api/Account/UpdateShippingAddres";

	/**
	 * Android�����Զ�������
	 * 
	 * �ӿڵ�ַ��http://120.25.218.242:9012/api/Account/UpdateUserPwdByUserId
	 * 
	 * ����ʽ��POST
	 * 
	 * �ӿ��������޸��û����� POST���� userId=�û�ID&amp;password=������&amp;affirmPwd=ȷ������
	 */
	public static String UpdateUserPwdByUserId = INTERNET_URL + "/api/Account/UpdateUserPwdByUserId";

	/**
	 * Android�����Զ�������
	 * 
	 * �ӿڵ�ַ��http://120.25.218.242:9012/api/Account/UserLogin
	 * 
	 * ����ʽ��POST
	 * 
	 * �ӿ��������û���¼��post�����ʽ:Name=�û���&amp;Password=����)
	 */
	public static String UserLogin = INTERNET_URL + "/api/Account/UserLogin";

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
	 * �ӿڵ�ַ��http://120.25.218.242:9012/api/Supermarket/GetSPProductCategory
	 * 
	 * ����ʽ��GET
	 * 
	 * �ӿ���������á����С���Ʒ�����б� get����
	 */
	public static String GetSPProductCategory = INTERNET_URL + "/api/Supermarket/GetSPProductCategory";

}