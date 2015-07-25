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
 * �˳��������Զ����ɰ�׿�˵�����ͨѶ�ӿ�
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
	 *            �ܵ�ַ
	 * @param saveInterfaceFile
	 *            �����ļ�·��
	 * @param domainNameAndPortConstant
	 *            �����Ͷ˿ڵĳ���
	 * @param codingType
	 *            ��������
	 * @throws Exception
	 */
	public void test(String interfaceAddressUrl, String saveInterfaceFile, String domainNameAndPortConstant, String codingType) throws Exception {

		Pattern pattern = Pattern.compile("http://\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3}:\\d{4}");
		Matcher matcher1 = pattern.matcher(interfaceAddressUrl);
		String domainNameAndPort = null;
		// �ж��ӽӿ��Ƿ������http://123.123.123.123:9999
		if (matcher1.find()) {
			System.err.println("��ַIP��" + matcher1.group());
			// ������IP��ַ
			domainNameAndPort = matcher1.group();
		}

		this.codingType = codingType;

		// ��ȡ����ӿ�URL�����ַ���
		parserNetworkUrlData(interfaceAddressUrl, domainNameAndPort);
		// ����ȡ��������װ������д��ָ���ļ�
		// for (int i = 0; i < parserResultString.size(); i++) {
		// // System.err.println(main.parserResultString.get(i));
		// parserString(parserResultString.get(i));
		// assembleData(saveInterfaceFile, domainNameAndPortConstant,
		// domainNameAndPort);
		// }
	}

	/**
	 * �����ַ���
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
	 * ��װ����
	 * 
	 * @param getResultData
	 * @throws Exception
	 */
	public void assembleData(String saveInterfaceFile, String domainNameAndPortConstant, String domainNameAndPort) throws Exception {

		for (int i = 0; i < list.size(); i++) {
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
			if (temp != -1) {
				address = address.substring(0, address.indexOf("?"));
			}

			if (name.indexOf("/") != -1) {
				// ���ɽӿڵ�ַ
				model.setInterfaceNewAddress("public static String " + name.replace("/", "") + String.valueOf(i) + "=" + domainNameAndPortConstant
						+ "+\"" + address + "\";");
			} else {
				// ���ɽӿڵ�ַ
				model.setInterfaceNewAddress("public static String " + name + String.valueOf(i) + "=" + domainNameAndPortConstant + "+\"" + address
						+ "\";");
			}
			// ������������д���ļ���
			inputStreamToFile(saveInterfaceFile, model, i, list.size());
		}
	}

	/**
	 * ��������ӿ���ַ����
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public void parserNetworkUrlData(String url, String domainNameAndPort) throws Exception {
		List<String> subInterface = new ArrayList<String>();
		// ��ȡ�ܽӿڵ�ַ
		Document docResult = Jsoup.connect(url).get();
		String string = docResult.toString();
//		 System.err.println(string);
		// ƥ��ÿ���ӽӿڵĵ�ַ
		Pattern pattern = Pattern.compile("<a\\s*href\\s*=\\s*['|\"]([a-z|A-Z|/|0-9|:|.|_]+)['|\"]\\s*>");
		Matcher matcher = pattern.matcher(string);

		while (matcher.find()) {
			// ��ȡ�ӽӿڵĺ�׺��ַ
			String getSuffixUrlString = matcher.group(1);
//			System.out.println(domainNameAndPort + getSuffixUrlString);
			
			
			
			Pattern.compile("http://\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3}:\\d{4}");
			Matcher matcher1 = pattern.matcher(getSuffixUrlString);
			// �ж��ӽӿ��Ƿ���ǰ׺�����Ͷ˿�http://123.123.123.123:9999
			if (matcher1.find()) {
				subInterface.add(matcher.group(1));
				System.err.println("����ӿڣ�IP��ַ����ȷ����" + matcher.group(1));
			} else {
				subInterface.add(domainNameAndPort + "/" + matcher.group(1));
				System.out.println(domainNameAndPort + "/" + matcher.group(1));
			}
		}

		// ����ÿ���ӽӿڵ�URLȥ�������������
		for (int i = 0; i < subInterface.size(); i++) {
			// �����ӽӿڵ�ַ
			// System.err.println("����"+subInterface.get(i));
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
	 * ���ӿ�����д��Ŀ���ļ�
	 * 
	 * @param model
	 * @param index
	 * @param size
	 * @throws Exception
	 */
	public void inputStreamToFile(String saveInterfaceFile, Model model, int index, int size) throws Exception {

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

		if (index == size - 1) {
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
}
