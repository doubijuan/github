package pers.edward.androidtool.function;

import java.io.RandomAccessFile;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import pers.edward.androidtool.model.Model;
import pers.edward.androidtool.model.NetworkUrlModel;
import pers.edward.androidtool.model.RecordSelectedIndexModel;
import pers.edward.androidtool.model.StoreSubInterfaceModel;
import pers.edward.androidtool.tool.CommonMethod;

/**
 * �˳��������Զ����ɰ�׿�˵�����ͨѶ�ӿ�
 * 
 * @author Edward
 * 
 * @version 2.0 getUrlInterface
 */
public class getUrlInterface
{
	// �洢�ӿ�ʵ�����б�
	private List<NetworkUrlModel> storeUrlModelList;
	// �洢�ӽӿڵ�ַ�б�
	private List<StoreSubInterfaceModel> storeSubInterfaceList = new ArrayList<StoreSubInterfaceModel>();

	private String newDomainNameAndPort;

	public List<StoreSubInterfaceModel> getStoreSubInterfaceList()
	{
		return storeSubInterfaceList;
	}

	public static void main(String[] args) throws Exception
	{

		getUrlInterface main = new getUrlInterface();
		// �Ƶ�ӿڵ�ַ
		String temp = "http://120.25.218.242:9505/index.aspx";
		// ���׹�
		// String temp = "http://120.25.218.242:9012/";
		main.test(temp, "C:\\MyWorkspace\\JAVASE\\MyExercise\\AndroidTool\\src\\pers\\edward\\androidtool\\function\\TestUrl.java", "INTERNET_URL",
				"gbk");

		// String result = CommonMethod.fileToString(
		// "C:\\MyWorkspace\\JAVASE\\github\\AndroidTool\\src\\pers\\edward\\androidtool\\function\\TestUrl.java",
		// "gbk");
		//
		// Pattern pattern = Pattern.compile("([\\s\\S]*)(\\s*})");
		// Matcher matcher = pattern.matcher(result);
		// StringBuffer sb = new StringBuffer();
		// if (matcher.find())
		// {
		// sb.append(matcher.group(1));
		// sb.append("----------------------------------------->\n");
		// sb.append(matcher.group(2));
		// }
		//
		// System.out.println(sb.toString());
	}

	/**
	 * @param interfaceAddressUrl
	 *            �ܵ�ַ
	 * @param saveInterfaceFile
	 *            �����ļ�·��
	 * @param domainNameAndPortConstant
	 *            �����Ͷ˿ڵĳ���
	 * @param codingType
	 *            ��������
	 * @throws Exception
	 */
	public void test(String interfaceAddressUrl, String saveInterfaceFile, String domainNameAndPortConstant, String codingType) throws Exception
	{

		Pattern pattern = Pattern.compile("http://\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3}:\\d{4}");
		Matcher matcher1 = pattern.matcher(interfaceAddressUrl);
		String newDomainNameAndPort = null;
		// �ж��ӽӿ��Ƿ������http://123.123.123.123:9999
		if (matcher1.find())
		{
			System.err.println("�ܵ�ַIP��" + matcher1.group());
			// ������IP��ַ
			this.newDomainNameAndPort = newDomainNameAndPort = matcher1.group();
		}

		// ��ȡ����ӿ�URL�����ַ���
		parserNetworkUrl(interfaceAddressUrl, newDomainNameAndPort);

	}

	/**
	 * ���ӿ�����д��Ŀ���ļ�
	 * 
	 * @param model
	 * 
	 * @param index
	 * 
	 * @param size
	 * 
	 * @throws Exception
	 */
	public void inputStreamToFile(String saveInterfaceFile, String outputString, String codingType) throws Exception
	{

		// ��������Ŀ���ļ�
		String fileName = saveInterfaceFile;

		RandomAccessFile rf = new RandomAccessFile(fileName, "rw");
		// ��ָ���Ƶ��ļ���β��
		rf.seek(rf.length() - 1);
		rf.write(outputString.getBytes(codingType));
		rf.write("}".getBytes(codingType));
		rf.close();

	}

	/**
	 * ����ַ�б������Ŀ���ļ�
	 * 
	 * @param list
	 * 
	 * @param targetFilePath
	 *            Ŀ���ļ�·��
	 * 
	 * @param domainNameAndPortConstant
	 * 
	 * @param domainNameAndPort
	 */
	public void outputUrlToTargetFile(List<RecordSelectedIndexModel> list, String targetFilePath, String domainNameAndPortConstant,
			String domainNameAndPort, String condingType) throws Exception
	{
		StringBuffer sb = new StringBuffer();
		String result = CommonMethod.fileToString(targetFilePath, condingType);

		Pattern pattern = Pattern.compile("([\\s\\S]*)(\\s*})");
		Matcher matcher = pattern.matcher(result);
		if (matcher.find())
		{
			sb.append(matcher.group(1));

			for (int i = 0; i < list.size(); i++)
			{
				RecordSelectedIndexModel model = list.get(i);
				String temp = storeSubInterfaceList.get(model.getIndex()).getInterfaceTitle();
				System.err.println(temp);
				List<NetworkUrlModel> networkList = storeSubInterfaceList.get(model.getIndex()).getNetworkUrlList();
				List<Integer> listTemp = model.getSubListIndex();
				for (int j = 0; j < listTemp.size(); j++)
				{
					NetworkUrlModel tempModel = networkList.get(listTemp.get(j));

					// System.out.println("/**");
					// System.out.println(" *Android�����Զ�������");
					// System.out.println(" *");
					// System.out.println(" *�ӿڵ�ַ��" +
					// tempModel.getUrlAddress());
					// System.out.println(" *");
					// System.out.println(" *����ʽ��" +
					// tempModel.getUrlRequestWay());
					// System.out.println(" *");
					// System.out.println(" *�ӿ�������" +
					// tempModel.getUrlDescribe());
					// System.out.println(" */");
					// System.out.println(newUrl);

					sb.append("/**\n");
					sb.append(" *Android�����Զ�������\n");
					sb.append(" *\n");
					sb.append(" *�ӿڵ�ַ��" + tempModel.getUrlAddress() + "\n");
					sb.append(" *\n");
					sb.append(" *����ʽ��" + tempModel.getUrlRequestWay() + "\n");
					sb.append(" *\n");
					sb.append(" *�ӿ�������" + tempModel.getUrlDescribe() + "\n");
					sb.append(" */\n");
					// ƴ�ӵ�ַ
					String newUrl = jointUrlAddress(tempModel.getUrlName(), tempModel.getUrlAddress(), domainNameAndPortConstant, domainNameAndPort);
					sb.append(newUrl + "\n\n");
				}
			}
			sb.append(matcher.group(2));
		}
		// ���ӿ�����д��Ŀ���ļ�
		CommonMethod.inputDataToTargetFile(targetFilePath, sb.toString(), condingType);

	}

	/**
	 * �����ַ����
	 * 
	 * @param addressName
	 * 
	 * @return
	 */
	public String dealwithAddressName(String addressName)
	{
		addressName = addressName.replaceAll("/", "");
		return addressName;
	}

	/**
	 * ƴ�������ַ
	 * 
	 * @param addressName
	 * 
	 * @param address
	 * 
	 * @param domainNameAndPortConstant
	 * 
	 * @param domainNameAndPort
	 * 
	 * @return
	 */
	public String jointUrlAddress(String addressName, String address, String domainNameAndPortConstant, String domainNameAndPort)
	{
		// �ض�?����Ĳ���
		int temp = address.indexOf("?");
		if (temp != -1)
		{
			address = address.substring(0, address.indexOf("?"));
		}

		String tempString = null;
		// �����ַ����
		addressName = dealwithAddressName(addressName);
		System.out.println("�����ĵ�ַ���ƣ�" + addressName);

		int tempIndex = address.indexOf(newDomainNameAndPort);
		// System.out.println(domainNameAndPort + "               " +
		// domainNameAndPortConstant + "               " + address);

		// �滻��ַ����
		if (tempIndex != -1)
		{
			address = address.replace(newDomainNameAndPort, domainNameAndPort + "+\"/");
			// ���ɽӿڵ�ַ
			tempString = "public static String " + addressName + "=" + address + "\";";
		} else
		{
			// ���ɽӿڵ�ַ
			tempString = "public static String " + addressName + "=\"" + address + "\";";
			System.err.println("���ʵ�ַ�滻����");
		}

		System.out.println("���ɽӿڵ�ַ��" + tempString);
		return tempString;
	}

	/**
	 * 
	 * 
	 * @throws Exception
	 */
	public void parserSubNetworkUrlData(String domainNameAndPort, String subUrl, StoreSubInterfaceModel storeSubInterfaceModel) throws Exception
	{
		if (!"".equals(subUrl))
		{
			Document doc = Jsoup.connect(subUrl).get();
			String string = doc.toString();
			// System.err.println(string);

			/**
			 * <td>Android/CheckCollection</td>
			 * 
			 * <td title="http://xxx.xxx.xxx.xxx:xxxx/app/AndroidServices/Android/CheckCollection">
			 * <a rel="operation" href="help/operations/CheckCollection">GET</a>
			 * </td>
			 * 
			 * <td>��ѯ�ղؽӿڣ�get�����ʽ��Android/CheckCollection?mob=�û��ֻ�&amp;
			 * pageIndex=��ǰҳ��&amp;pageSize=ҳ���С</td>
			 * 
			 */
			// ƥ����·�ӿ����ݣ�ƥ��ʵ��������ʾ��
			// ���ǵ�Ŀ��������ȡ��һ��<td></td>������
			// ������ȡ�ڶ�title�Լ�<a></a>�е�href�������ַ���ı���
			// �����ȡ���һ��<td></td>���ı�������ƥ�����
			Pattern pattern = Pattern
					.compile("<td>([^<]*)</td>\\s*<td\\s*title=\"(.*?)\">\\s*<a\\s*.*href=\"(.*?)\">([^<]*)</a>\\s*</td>\\s*<td>([^<]*)</td>\\s*");
			Matcher matcher = pattern.matcher(string);
			// ��ӡ�ӽӿڵı���
			// System.err.println(storeSubInterfaceModel.getInterfaceTitle());
			// ���б����ڴ洢Urlʵ����
			storeUrlModelList = new ArrayList<NetworkUrlModel>();
			while (matcher.find())
			{
				// System.out.println(matcher.group(1) + "   " +
				// matcher.group(2) + "   " + matcher.group(3) + "   " +
				// matcher.group(4) + "   "
				// + matcher.group(5));
				NetworkUrlModel model = new NetworkUrlModel();
				model.setUrlName(matcher.group(1));
				model.setUrlAddress(matcher.group(2));
				model.setUrlRequestWay(matcher.group(4));
				model.setUrlDescribe(matcher.group(5));
				storeUrlModelList.add(model);
			}
			storeSubInterfaceModel.setNetworkUrlList(storeUrlModelList);
		}
	}

	/**
	 * ��������ӿ���ַ����
	 * 
	 * @param url
	 * 
	 * @param newDomainNameAndPort
	 * 
	 * @throws Exception
	 */
	public void parserNetworkUrl(String url, String newDomainNameAndPort) throws Exception
	{
		// ��ȡ�ܽӿڵ�ַ(����10����ʱ������timeout�쳣)
		Document docResult = Jsoup.connect(url).timeout(10 * 1000).get();
		String string = docResult.toString();
		// System.err.println(string);
		// ƥ��ÿ���ӽӿڵĵ�ַ
		Pattern pattern = Pattern.compile("<a\\s*href\\s*=\\s*['|\"]([a-z|A-Z|/|0-9|:|.|_]+)['|\"]\\s*[^>]*>([^<]*)</a>");
		Matcher matcher = pattern.matcher(string);
		while (matcher.find())
		{
			// ��ȡ�ӽӿڵĺ�׺��ַ
			String getSuffixUrlString = matcher.group(1);
			// �ӽӿڱ���
			String getInterfaceTitle = matcher.group(2);
			// System.out.println(domainNameAndPort + getSuffixUrlString +
			// getInterfaceTitle);

			Pattern.compile("http://\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3}:\\d{4}");
			Matcher matcher1 = pattern.matcher(getSuffixUrlString);
			// �ж��ӽӿ��Ƿ���ǰ׺�����Ͷ˿ں�http://123.123.123.123:9999
			StoreSubInterfaceModel model = new StoreSubInterfaceModel();
			if (matcher1.find())
			{
				System.err.println("����ӿڣ�IP��ַ����ȷ����" + getSuffixUrlString);
				// ����ӿڵ�ַ�д��󣬸�ֵΪ��
				model.setInterfaceUrl("");
				model.setInterfaceTitle(getInterfaceTitle);
				model.setNetworkUrlList(null);
			} else
			{
				System.err.println("�ӽӿڵ�ַ��" + newDomainNameAndPort + "/" + getSuffixUrlString);
				model.setInterfaceUrl(newDomainNameAndPort + "/" + getSuffixUrlString);
				model.setInterfaceTitle(getInterfaceTitle);
				// ���������ӽӿڵ�����
				parserSubNetworkUrlData(newDomainNameAndPort, newDomainNameAndPort + "/" + getSuffixUrlString, model);
			}
			storeSubInterfaceList.add(model);
		}
	}

}
