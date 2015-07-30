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
 * 此程序用于自动生成安卓端的网络通讯接口
 * 
 * @author Edward
 * 
 * @version 2.0 getUrlInterface
 */
public class getUrlInterface
{
	private String codingType;
	private List<Model> list = new ArrayList<Model>();
	// 存储接口实体类列表
	private List<NetworkUrlModel> storeUrlModelList;
	// 存储子接口地址列表
	private List<StoreSubInterfaceModel> storeSubInterfaceList = new ArrayList<StoreSubInterfaceModel>();

	public List<StoreSubInterfaceModel> getStoreSubInterfaceList()
	{
		return storeSubInterfaceList;
	}

	public static void main(String[] args) throws Exception
	{

		getUrlInterface main = new getUrlInterface();
		// 酒店接口地址
//		String temp = "http://120.25.218.242:9505/index.aspx";
		// 云易购
		 String temp="http://120.25.218.242:9012/";
		main.test(temp, "C:\\MyWorkspace\\JAVASE\\MyExercise\\AndroidTool\\src\\pers\\edward\\androidtool\\function\\TestUrl.java", "INTERNET_URL",
				"gbk");
	}

	/**
	 * @param interfaceAddressUrl
	 *            总地址
	 * @param saveInterfaceFile
	 *            保存文件路径
	 * @param domainNameAndPortConstant
	 *            域名和端口的常量
	 * @param codingType
	 *            编码类型
	 * @throws Exception
	 */
	public void test(String interfaceAddressUrl, String saveInterfaceFile, String domainNameAndPortConstant, String codingType) throws Exception
	{

		Pattern pattern = Pattern.compile("http://\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3}:\\d{4}");
		Matcher matcher1 = pattern.matcher(interfaceAddressUrl);
		String domainNameAndPort = null;
		// 判断子接口是否带类似http://123.123.123.123:9999
		if (matcher1.find())
		{
			System.err.println("总地址IP：" + matcher1.group());
			// 解析出IP地址
			domainNameAndPort = matcher1.group();
		}

		this.codingType = codingType;

		// 获取网络接口URL解析字符串
		parserNetworkUrl(interfaceAddressUrl, domainNameAndPort);

		// 模拟用户点击状态
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
		// System.out.println(" *接口地址：" +
		// storeUrlModelList.get(j).getUrlAddress());
		// System.out.println(" *请求方式：" +
		// storeUrlModelList.get(j).getUrlRequestWay());
		// System.out.println(" *接口描述：" +
		// storeUrlModelList.get(j).getUrlDescribe());
		// System.out.println(" */");
		//
		// }
		// }

		// 将获取的数据组装，并且写入指定文件
		// for (int i = 0; i < parserResultString.size(); i++) {
		// // System.err.println(main.parserResultString.get(i));
		// parserString(parserResultString.get(i));
		// assembleData(saveInterfaceFile, domainNameAndPortConstant,
		// domainNameAndPort);
		// }
	}

	/**
	 * 将接口数据写入目标文件
	 * 
	 * @param model
	 * @param index
	 * @param size
	 * @throws Exception
	 */
	public void inputStreamToFile(String saveInterfaceFile, Model model, int index, int size) throws Exception
	{

		// 输入生成目标文件
		String fileName = saveInterfaceFile;

		RandomAccessFile rf = new RandomAccessFile(fileName, "rw");

		rf.seek(rf.length() - 1);
		String temp = "\t/**自动生成字段" + "\n";
		String temp1 = "\t * 接口地址：" + model.getInterfaceAddress() + "\n";
		String temp2 = "\t * 请求方式：" + model.getInterfaceWay() + "\n";
		String temp3 = "\t * 接口描述：" + model.getInterfaceDetail() + "\n";
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
			String dateString = "\t //以上接口生成于" + string + "\n\n";
			rf.write(dateString.getBytes(codingType));
			rf.write("}".getBytes(codingType));
			rf.close();
			System.err.println("接口生成完毕！");
		}
	}

	/**
	 * 将地址列表输出到目标文件
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
			 * <td>查询收藏接口，get请求格式：Android/CheckCollection?mob=用户手机&amp;
			 * pageIndex=当前页面&amp;pageSize=页面大小</td>
			 * 
			 */
			// 匹配网路接口数据，匹配实例如上所示。
			// 我们的目标首先提取第一行<td></td>的数据
			// 接着提取第二title以及<a></a>中的href的网络地址和文本，
			// 最后提取最后一行<td></td>的文本。至此匹配结束
			Pattern pattern = Pattern
					.compile("<td>([^<]*)</td>\\s*<td\\s*title=\"(.*?)\">\\s*<a\\s*.*href=\"(.*?)\">([^<]*)</a>\\s*</td>\\s*<td>([^<]*)</td>\\s*");
			Matcher matcher = pattern.matcher(string);
			// 打印子接口的标题
			// System.err.println(storeSubInterfaceModel.getInterfaceTitle());
			// 此列表用于存储Url实体类
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
	 * 解析字符串
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
	 * 组装数据
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
			System.out.println(" *接口地址：" + model.getInterfaceAddress());
			System.out.println(" *请求方式：" + model.getInterfaceWay());
			System.out.println(" *接口描述：" + model.getInterfaceDetail());
			System.out.println(" */");

			String name = model.getInterfaceName();
			String address = model.getInterfaceAddress();

			address = address.replace(domainNameAndPort, "");

			// 截断?后面的参数
			int temp = address.indexOf("?");
			if (temp != -1)
			{
				address = address.substring(0, address.indexOf("?"));
			}

			if (name.indexOf("/") != -1)
			{
				// 生成接口地址
				model.setInterfaceNewAddress("public static String " + name.replace("/", "") + String.valueOf(i) + "=" + domainNameAndPortConstant
						+ "+\"" + address + "\";");
			} else
			{
				// 生成接口地址
				model.setInterfaceNewAddress("public static String " + name + String.valueOf(i) + "=" + domainNameAndPortConstant + "+\"" + address
						+ "\";");
			}
			// 将解析的数据写入文件中
			// inputStreamToFile(saveInterfaceFile, model, i, list.size());
		}
	}

	/**
	 * 解析网络接口网址数据
	 * 
	 * @param url
	 * 
	 * @param domainNameAndPort
	 * 
	 * @throws Exception
	 */
	public void parserNetworkUrl(String url, String domainNameAndPort) throws Exception
	{
		// 获取总接口地址
		Document docResult = Jsoup.connect(url).get();
		String string = docResult.toString();
		// System.err.println(string);
		// 匹配每个子接口的地址
		Pattern pattern = Pattern.compile("<a\\s*href\\s*=\\s*['|\"]([a-z|A-Z|/|0-9|:|.|_]+)['|\"]\\s*[^>]*>([^<]*)</a>");
		Matcher matcher = pattern.matcher(string);
		while (matcher.find())
		{
			// 获取子接口的后缀地址
			String getSuffixUrlString = matcher.group(1);
			// 子接口标题
			String getInterfaceTitle = matcher.group(2);
			// System.out.println(domainNameAndPort + getSuffixUrlString +
			// getInterfaceTitle);

			Pattern.compile("http://\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3}:\\d{4}");
			Matcher matcher1 = pattern.matcher(getSuffixUrlString);
			// 判断子接口是否含有前缀域名和端口http://123.123.123.123:9999
			StoreSubInterfaceModel model = new StoreSubInterfaceModel();
			if (matcher1.find())
			{
				System.err.println("特殊接口（IP地址不正确）：" + getSuffixUrlString);
				// 如果接口地址有错误，赋值为空
				model.setInterfaceUrl("");
				model.setInterfaceTitle(getInterfaceTitle);
				model.setNetworkUrlList(null);
			} else
			{
				System.err.println("子接口地址：" + domainNameAndPort + "/" + getSuffixUrlString);
				model.setInterfaceUrl(domainNameAndPort + "/" + getSuffixUrlString);
				model.setInterfaceTitle(getInterfaceTitle);
				// 解析网络子接口的数据
				parserSubNetworkUrlData(domainNameAndPort, domainNameAndPort + "/" + getSuffixUrlString, model);
			}
			storeSubInterfaceList.add(model);
		}
	}

}
