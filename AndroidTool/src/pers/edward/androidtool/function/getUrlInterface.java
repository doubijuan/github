package pers.edward.androidtool.function;

import java.io.RandomAccessFile;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import pers.edward.androidtool.model.Model;

/**
 * 此程序用于自动生成安卓端的网络通讯接口
 * 
 * @author Edward
 * 
 * @version 2.0 getUrlInterface
 */
public class getUrlInterface {
	private String codingType;
	private List<String> parserResultString = new ArrayList<String>();
	private List<Model> list = new ArrayList<Model>();

	public static void main(String[] args) throws Exception {

		getUrlInterface main = new getUrlInterface();

		main.test("http://120.25.218.242:9012/",
				"C:\\MyWorkspace\\JAVASE\\MyExercise\\AndroidTool\\src\\pers\\edward\\androidtool\\function\\TestUrl.java", "INTERNET_URL", "gbk");
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
	public void test(String interfaceAddressUrl, String saveInterfaceFile, String domainNameAndPortConstant, String codingType) throws Exception {

		Pattern pattern = Pattern.compile("http://\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3}:\\d{4}");
		Matcher matcher1 = pattern.matcher(interfaceAddressUrl);
		String domainNameAndPort = null;
		// 判断子接口是否带类似http://123.123.123.123:9999
		if (matcher1.find()) {
			System.err.println("地址IP：" + matcher1.group());
			// 解析出IP地址
			domainNameAndPort = matcher1.group();
		}

		this.codingType = codingType;

		// 获取网络接口URL解析字符串
		parserNetworkUrlData(interfaceAddressUrl, domainNameAndPort);
		// 将获取的数据组装，并且写入指定文件
		// for (int i = 0; i < parserResultString.size(); i++) {
		// // System.err.println(main.parserResultString.get(i));
		// parserString(parserResultString.get(i));
		// assembleData(saveInterfaceFile, domainNameAndPortConstant,
		// domainNameAndPort);
		// }
	}

	/**
	 * 解析字符串
	 * 
	 * @param getResultData
	 */
	public void parserString(String getResultData) {
		String[] string = getResultData.split("\n");
		list = new ArrayList<Model>();
		for (int i = 0; i < string.length; i++) {

			if (i % 4 == 0 && i != 0) {
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
	public void assembleData(String saveInterfaceFile, String domainNameAndPortConstant, String domainNameAndPort) throws Exception {

		for (int i = 0; i < list.size(); i++) {
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
			if (temp != -1) {
				address = address.substring(0, address.indexOf("?"));
			}

			if (name.indexOf("/") != -1) {
				// 生成接口地址
				model.setInterfaceNewAddress("public static String " + name.replace("/", "") + String.valueOf(i) + "=" + domainNameAndPortConstant
						+ "+\"" + address + "\";");
			} else {
				// 生成接口地址
				model.setInterfaceNewAddress("public static String " + name + String.valueOf(i) + "=" + domainNameAndPortConstant + "+\"" + address
						+ "\";");
			}
			// 将解析的数据写入文件中
			inputStreamToFile(saveInterfaceFile, model, i, list.size());
		}
	}

	/**
	 * 解析网络接口网址数据
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public void parserNetworkUrlData(String url, String domainNameAndPort) throws Exception {
		List<String> subInterface = new ArrayList<String>();
		// 获取总接口地址
		Document docResult = Jsoup.connect(url).get();
		String string = docResult.toString();
//		 System.err.println(string);
		// 匹配每个子接口的地址
		Pattern pattern = Pattern.compile("<a\\s*href\\s*=\\s*['|\"]([a-z|A-Z|/|0-9|:|.|_]+)['|\"]\\s*>");
		Matcher matcher = pattern.matcher(string);

		while (matcher.find()) {
			// 获取子接口的后缀地址
			String getSuffixUrlString = matcher.group(1);
//			System.out.println(domainNameAndPort + getSuffixUrlString);
			
			
			
			Pattern.compile("http://\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3}:\\d{4}");
			Matcher matcher1 = pattern.matcher(getSuffixUrlString);
			// 判断子接口是否含有前缀域名和端口http://123.123.123.123:9999
			if (matcher1.find()) {
				subInterface.add(matcher.group(1));
				System.err.println("特殊接口（IP地址不正确）：" + matcher.group(1));
			} else {
				subInterface.add(domainNameAndPort + "/" + matcher.group(1));
				System.out.println(domainNameAndPort + "/" + matcher.group(1));
			}
		}

		// 根据每个子接口的URL去解析里面的数据
		for (int i = 0; i < subInterface.size(); i++) {
			// 访问子接口地址
			// System.err.println("测试"+subInterface.get(i));
			Document doc = Jsoup.connect(subInterface.get(i)).get();
			StringBuffer sb = new StringBuffer();
			doc.select("tr").get(0).remove();
			Elements temp = doc.getElementsByTag("tr");
			for (Element element : temp) {
				Elements temp1 = element.getElementsByTag("td");
				for (Element element2 : temp1) {
					if (!"".equals(element2.attr("title"))) {
						sb.append(element2.attr("title") + "\n");
					}
					if (!"".equals(element2.getElementsByTag("td").last().text())) {
						sb.append(element2.getElementsByTag("td").last().text() + "\n");
					}

				}
			}
			parserResultString.add(sb.toString());
		}

	}

	/**
	 * 将接口数据写入目标文件
	 * 
	 * @param model
	 * @param index
	 * @param size
	 * @throws Exception
	 */
	public void inputStreamToFile(String saveInterfaceFile, Model model, int index, int size) throws Exception {

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

		if (index == size - 1) {
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
}
