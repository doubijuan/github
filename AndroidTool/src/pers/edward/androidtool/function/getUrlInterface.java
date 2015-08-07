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
 * 此程序用于自动生成安卓端的网络通讯接口
 * 
 * @author Edward
 * 
 * @version 2.0 getUrlInterface
 */
public class getUrlInterface
{
	// 存储接口实体类列表
	private List<NetworkUrlModel> storeUrlModelList;
	// 存储子接口地址列表
	private List<StoreSubInterfaceModel> storeSubInterfaceList = new ArrayList<StoreSubInterfaceModel>();

	private String newDomainNameAndPort;

	public List<StoreSubInterfaceModel> getStoreSubInterfaceList()
	{
		return storeSubInterfaceList;
	}

	public static void main(String[] args) throws Exception
	{

		getUrlInterface main = new getUrlInterface();
		// 酒店接口地址
		String temp = "http://120.25.218.242:9505/index.aspx";
		// 云易购
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
		String newDomainNameAndPort = null;
		// 判断子接口是否带类似http://123.123.123.123:9999
		if (matcher1.find())
		{
			System.err.println("总地址IP：" + matcher1.group());
			// 解析出IP地址
			this.newDomainNameAndPort = newDomainNameAndPort = matcher1.group();
		}

		// 获取网络接口URL解析字符串
		parserNetworkUrl(interfaceAddressUrl, newDomainNameAndPort);

	}

	/**
	 * 将接口数据写入目标文件
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

		// 输入生成目标文件
		String fileName = saveInterfaceFile;

		RandomAccessFile rf = new RandomAccessFile(fileName, "rw");
		// 将指针移到文件最尾部
		rf.seek(rf.length() - 1);
		rf.write(outputString.getBytes(codingType));
		rf.write("}".getBytes(codingType));
		rf.close();

	}

	/**
	 * 将地址列表输出到目标文件
	 * 
	 * @param list
	 * 
	 * @param targetFilePath
	 *            目标文件路径
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
					// System.out.println(" *Android代码自动生成器");
					// System.out.println(" *");
					// System.out.println(" *接口地址：" +
					// tempModel.getUrlAddress());
					// System.out.println(" *");
					// System.out.println(" *请求方式：" +
					// tempModel.getUrlRequestWay());
					// System.out.println(" *");
					// System.out.println(" *接口描述：" +
					// tempModel.getUrlDescribe());
					// System.out.println(" */");
					// System.out.println(newUrl);

					sb.append("/**\n");
					sb.append(" *Android代码自动生成器\n");
					sb.append(" *\n");
					sb.append(" *接口地址：" + tempModel.getUrlAddress() + "\n");
					sb.append(" *\n");
					sb.append(" *请求方式：" + tempModel.getUrlRequestWay() + "\n");
					sb.append(" *\n");
					sb.append(" *接口描述：" + tempModel.getUrlDescribe() + "\n");
					sb.append(" */\n");
					// 拼接地址
					String newUrl = jointUrlAddress(tempModel.getUrlName(), tempModel.getUrlAddress(), domainNameAndPortConstant, domainNameAndPort);
					sb.append(newUrl + "\n\n");
				}
			}
			sb.append(matcher.group(2));
		}
		// 将接口数据写入目标文件
		CommonMethod.inputDataToTargetFile(targetFilePath, sb.toString(), condingType);

	}

	/**
	 * 处理地址名称
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
	 * 拼接网络地址
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
		// 截断?后面的参数
		int temp = address.indexOf("?");
		if (temp != -1)
		{
			address = address.substring(0, address.indexOf("?"));
		}

		String tempString = null;
		// 处理地址名称
		addressName = dealwithAddressName(addressName);
		System.out.println("处理后的地址名称：" + addressName);

		int tempIndex = address.indexOf(newDomainNameAndPort);
		// System.out.println(domainNameAndPort + "               " +
		// domainNameAndPortConstant + "               " + address);

		// 替换地址常量
		if (tempIndex != -1)
		{
			address = address.replace(newDomainNameAndPort, domainNameAndPort + "+\"/");
			// 生成接口地址
			tempString = "public static String " + addressName + "=" + address + "\";";
		} else
		{
			// 生成接口地址
			tempString = "public static String " + addressName + "=\"" + address + "\";";
			System.err.println("访问地址替换出错！");
		}

		System.out.println("生成接口地址：" + tempString);
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
	 * 解析网络接口网址数据
	 * 
	 * @param url
	 * 
	 * @param newDomainNameAndPort
	 * 
	 * @throws Exception
	 */
	public void parserNetworkUrl(String url, String newDomainNameAndPort) throws Exception
	{
		// 获取总接口地址(设置10秒延时，避免timeout异常)
		Document docResult = Jsoup.connect(url).timeout(10 * 1000).get();
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
			// 判断子接口是否含有前缀域名和端口号http://123.123.123.123:9999
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
				System.err.println("子接口地址：" + newDomainNameAndPort + "/" + getSuffixUrlString);
				model.setInterfaceUrl(newDomainNameAndPort + "/" + getSuffixUrlString);
				model.setInterfaceTitle(getInterfaceTitle);
				// 解析网络子接口的数据
				parserSubNetworkUrlData(newDomainNameAndPort, newDomainNameAndPort + "/" + getSuffixUrlString, model);
			}
			storeSubInterfaceList.add(model);
		}
	}

}
