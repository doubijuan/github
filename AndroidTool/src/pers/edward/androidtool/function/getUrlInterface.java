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

/**
 * �˳��������Զ����ɰ�׿�˵�����ͨѶ�ӿ�
 * 
 * @author Edward
 * 
 * @version 2.0 getUrlInterface
 */
public class getUrlInterface
{
	private String codingType;
	private List<Model> list = new ArrayList<Model>();
	// �洢�ӿ�ʵ�����б�
	private List<NetworkUrlModel> storeUrlModelList;
	// �洢�ӽӿڵ�ַ�б�
	private List<StoreSubInterfaceModel> storeSubInterfaceList = new ArrayList<StoreSubInterfaceModel>();

	public List<StoreSubInterfaceModel> getStoreSubInterfaceList()
	{
		return storeSubInterfaceList;
	}

	public static void main(String[] args) throws Exception
	{

		getUrlInterface main = new getUrlInterface();
		// �Ƶ�ӿڵ�ַ
//		String temp = "http://120.25.218.242:9505/index.aspx";
		// ���׹�
		 String temp="http://120.25.218.242:9012/";
		main.test(temp, "C:\\MyWorkspace\\JAVASE\\MyExercise\\AndroidTool\\src\\pers\\edward\\androidtool\\function\\TestUrl.java", "INTERNET_URL",
				"gbk");
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
		String domainNameAndPort = null;
		// �ж��ӽӿ��Ƿ������http://123.123.123.123:9999
		if (matcher1.find())
		{
			System.err.println("�ܵ�ַIP��" + matcher1.group());
			// ������IP��ַ
			domainNameAndPort = matcher1.group();
		}

		this.codingType = codingType;

		// ��ȡ����ӿ�URL�����ַ���
		parserNetworkUrl(interfaceAddressUrl, domainNameAndPort);

		// ģ���û����״̬
//		List<RecordSelectedIndexModel> listTemp = new ArrayList<RecordSelectedIndexModel>();
//		for (int i = 0; i < storeSubInterfaceList.size(); i++)
//		{
//			RecordSelectedIndexModel model = new RecordSelectedIndexModel();
//			model.setIndex(i);
//			List<Integer> list = new ArrayList<Integer>();
//			storeUrlModelList = storeSubInterfaceList.get(i).getNetworkUrlList();
//			for (int j = 0; j < storeUrlModelList.size(); j++)
//			{
//				list.add(j);
//			}
//			model.setSubListIndex(list);
//
//			listTemp.add(model);
//		}

		// outputUrlToTargetFile(listTemp);

		// for (int i = 0; i < storeSubInterfaceList.size(); i++) {
		// System.err.println(storeSubInterfaceList.get(i).getInterfaceTitle() +
		// "    " + storeSubInterfaceList.get(i).getInterfaceUrl());
		// storeUrlModelList = storeSubInterfaceList.get(i).getNetworkUrlList();
		// for (int j = 0; j < storeUrlModelList.size(); j++) {
		// System.out.println("/**");
		// System.out.println(" *�ӿڵ�ַ��" +
		// storeUrlModelList.get(j).getUrlAddress());
		// System.out.println(" *����ʽ��" +
		// storeUrlModelList.get(j).getUrlRequestWay());
		// System.out.println(" *�ӿ�������" +
		// storeUrlModelList.get(j).getUrlDescribe());
		// System.out.println(" */");
		//
		// }
		// }

		// ����ȡ��������װ������д��ָ���ļ�
		// for (int i = 0; i < parserResultString.size(); i++) {
		// // System.err.println(main.parserResultString.get(i));
		// parserString(parserResultString.get(i));
		// assembleData(saveInterfaceFile, domainNameAndPortConstant,
		// domainNameAndPort);
		// }
	}

	/**
	 * ���ӿ�����д��Ŀ���ļ�
	 * 
	 * @param model
	 * @param index
	 * @param size
	 * @throws Exception
	 */
	public void inputStreamToFile(String saveInterfaceFile, Model model, int index, int size) throws Exception
	{

		// ��������Ŀ���ļ�
		String fileName = saveInterfaceFile;

		RandomAccessFile rf = new RandomAccessFile(fileName, "rw");

		rf.seek(rf.length() - 1);
		String temp = "\t/**�Զ������ֶ�" + "\n";
		String temp1 = "\t * �ӿڵ�ַ��" + model.getInterfaceAddress() + "\n";
		String temp2 = "\t * ����ʽ��" + model.getInterfaceWay() + "\n";
		String temp3 = "\t * �ӿ�������" + model.getInterfaceDetail() + "\n";
		String temp4 = "\t */" + "\n";
		rf.write(temp.getBytes(codingType));
		rf.write(temp3.getBytes(codingType));
		rf.write("\t *\n".getBytes(codingType));
		rf.write(temp1.getBytes(codingType));
		rf.write("\t *\n".getBytes(codingType));
		rf.write(temp2.getBytes(codingType));
		rf.write(temp4.getBytes(codingType));
		String temp5 = "\t" + model.getInterfaceNewAddress() + "\n\n\n";
		rf.write(temp5.getBytes(codingType));

		if (index == size - 1)
		{
			rf.seek(rf.length() - 1);
			Date date = new Date();
			String string;
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			string = sdf.format(date);
			String dateString = "\t //���Ͻӿ�������" + string + "\n\n";
			rf.write(dateString.getBytes(codingType));
			rf.write("}".getBytes(codingType));
			rf.close();
			System.err.println("�ӿ�������ϣ�");
		}
	}

	/**
	 * ����ַ�б������Ŀ���ļ�
	 * 
	 * @param list
	 */
	public void outputUrlToTargetFile(List<RecordSelectedIndexModel> list)
	{
		for (int i = 0; i < list.size(); i++)
		{
			RecordSelectedIndexModel model = list.get(i);
			String temp = storeSubInterfaceList.get(model.getIndex()).getInterfaceTitle();
			System.err.println(temp);
			List<NetworkUrlModel> networkList = storeSubInterfaceList.get(model.getIndex()).getNetworkUrlList();
			List<Integer> listTemp = model.getSubListIndex();
			for (int j = 0; j < listTemp.size(); j++)
			{
				System.out.println(networkList.get(listTemp.get(j)).getUrlDescribe());
			}
		}
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
	 * �����ַ���
	 * 
	 * @param getResultData
	 */
	public void parserString(String getResultData)
	{
		String[] string = getResultData.split("\n");
		list = new ArrayList<Model>();
		for (int i = 0; i < string.length; i++)
		{

			if (i % 4 == 0 && i != 0)
			{
				Model model = new Model();
				model.setInterfaceName(string[i - 4]);
				model.setInterfaceAddress(string[i - 3]);
				model.setInterfaceWay(string[i - 2]);
				model.setInterfaceDetail(string[i - 1]);
				list.add(model);
			}
		}
	}

	/**
	 * ��װ����
	 * 
	 * @param getResultData
	 * @throws Exception
	 */
	public void assembleData(String saveInterfaceFile, String domainNameAndPortConstant, String domainNameAndPort) throws Exception
	{

		for (int i = 0; i < list.size(); i++)
		{
			Model model = list.get(i);
			System.out.println("/**");
			System.out.println(" *�ӿڵ�ַ��" + model.getInterfaceAddress());
			System.out.println(" *����ʽ��" + model.getInterfaceWay());
			System.out.println(" *�ӿ�������" + model.getInterfaceDetail());
			System.out.println(" */");

			String name = model.getInterfaceName();
			String address = model.getInterfaceAddress();

			address = address.replace(domainNameAndPort, "");

			// �ض�?����Ĳ���
			int temp = address.indexOf("?");
			if (temp != -1)
			{
				address = address.substring(0, address.indexOf("?"));
			}

			if (name.indexOf("/") != -1)
			{
				// ���ɽӿڵ�ַ
				model.setInterfaceNewAddress("public static String " + name.replace("/", "") + String.valueOf(i) + "=" + domainNameAndPortConstant
						+ "+\"" + address + "\";");
			} else
			{
				// ���ɽӿڵ�ַ
				model.setInterfaceNewAddress("public static String " + name + String.valueOf(i) + "=" + domainNameAndPortConstant + "+\"" + address
						+ "\";");
			}
			// ������������д���ļ���
			// inputStreamToFile(saveInterfaceFile, model, i, list.size());
		}
	}

	/**
	 * ��������ӿ���ַ����
	 * 
	 * @param url
	 * 
	 * @param domainNameAndPort
	 * 
	 * @throws Exception
	 */
	public void parserNetworkUrl(String url, String domainNameAndPort) throws Exception
	{
		// ��ȡ�ܽӿڵ�ַ
		Document docResult = Jsoup.connect(url).get();
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
			// �ж��ӽӿ��Ƿ���ǰ׺�����Ͷ˿�http://123.123.123.123:9999
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
				System.err.println("�ӽӿڵ�ַ��" + domainNameAndPort + "/" + getSuffixUrlString);
				model.setInterfaceUrl(domainNameAndPort + "/" + getSuffixUrlString);
				model.setInterfaceTitle(getInterfaceTitle);
				// ���������ӽӿڵ�����
				parserSubNetworkUrlData(domainNameAndPort, domainNameAndPort + "/" + getSuffixUrlString, model);
			}
			storeSubInterfaceList.add(model);
		}
	}

}
