package pers.edward.androidtool.function;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pers.edward.androidtool.tool.CommonMethod;

/**
 * Android�ؼ��Զ�������
 * 
 * @author Edward
 * 
 */
public class GetWidgetByXmlParser {
	private List<String> widgetNameList = new ArrayList<String>();
	private List<String> widgetIdList = new ArrayList<String>();
	private List<String> variableNameList = new ArrayList<String>();
	private boolean isRegListener;

	public static void main(String[] args) {
		GetWidgetByXmlParser getWidgetByXmlParser = new GetWidgetByXmlParser();
		try {
			getWidgetByXmlParser.generateWidget("C:\\MyWorkspace\\Android\\YiHuaHotel\\N18Client\\res\\layout\\fragment_home.xml",
					"C:\\MyWorkspace\\Android\\YiHuaHotel\\N18Client\\src\\cn\\zhanyun\\n18client\\aa.java", "private void initFragment()", "utf-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param xmlPath
	 *            xml�ļ�·��
	 * @param targetPath
	 *            Ŀ���ļ�·��
	 * @param methodName
	 *            ���뷽����
	 * @param codingType
	 *            ��������
	 * @throws Exception
	 */
	public void generateWidget(String xmlPath, String targetPath, String methodName, String codingType) throws Exception {
		isRegListener = true;

		// �����ļ�
		parserEmbeddedXmlFile(xmlPath);

		// д�������ؼ�����
//		statementVariable(targetPath, codingType);
//
//		bindView(methodName, targetPath, codingType);
	}

	/**
	 * ���Ĵ��룬�󶨿ؼ�����
	 * 
	 * @param methodName
	 * @param result
	 * @param targetString
	 * @throws Exception
	 */
	public void bindView(String methodName, String targetString, String condingType) throws Exception {
		String result = CommonMethod.fileToString(targetString);

		String head = result.substring(0, result.indexOf(methodName)).trim();
		String tail = result.substring(result.indexOf(methodName), result.length()).trim();
		head = head + tail.substring(0, tail.indexOf("{") + 1).trim();
		tail = tail.substring(tail.indexOf("{") + 1, tail.length()).trim();
		
		RandomAccessFile rf;
		rf = reSetUpFile(targetString);
		rf.write(head.getBytes(condingType));

		StringBuffer sb = new StringBuffer();
		sb.append("\n//���´������Զ�����������\n");
		for (int i = 0; i < widgetNameList.size(); i++) {
			sb.append(variableNameList.get(i) + "=" + "(" + widgetNameList.get(i) + ")" + "findViewById(R.id." + widgetIdList.get(i) + ");\n");
		}
		sb.append("\n");

		// ע������¼�
		if (isRegListener) {
			for (int i = 0; i < widgetNameList.size(); i++) {
				String widgetName = widgetNameList.get(i);
				if (widgetName.equals("ImageView") || widgetName.equals("TextView") || widgetName.equals("ImageButton")) {
					sb.append(variableNameList.get(i) + "." + "setOnClickListener(this);\n");
				}
			}
			sb.append("\n");
		}

		rf.write(sb.toString().getBytes(condingType));
		rf.write(tail.getBytes(condingType));
		rf.close();

		// long position = CommonMethod.seekInsertLocation(targetString,
		// methodName);
		// CommonMethod.insertContentToFile(targetString, "�ҵĲ���", codingType,
		// position);
	}

	/**
	 * ���´����ļ���ɾ���ٽ�һ�����ļ�
	 * 
	 * @param targetString
	 * @return
	 */
	public RandomAccessFile reSetUpFile(String targetString) {
		RandomAccessFile rf = null;
		try {
			File file = new File(targetString);
			if (file.exists()) {
				file.delete();
				file.createNewFile();
				rf = new RandomAccessFile(targetString, "rw");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return rf;
	}

	/**
	 * ���Ĵ��룬���������ؼ���������
	 * 
	 * @param result
	 * @param targetString
	 * @throws Exception
	 */
	public void statementVariable(String path, String codeType) throws Exception {

		StringBuffer insertContent = new StringBuffer();
		insertContent.append("\n//�ؼ�������������Android�Զ����������ɣ�\n");
		System.err.println("�ܹ���" + widgetNameList.size() + "������");
		for (int i = 0; i < widgetNameList.size(); i++) {
			insertContent.append("private " + widgetNameList.get(i) + " " + variableNameList.get(i) + ";\n");
		}
		insertContent.append("\n");

		long position = CommonMethod.seekInsertLocation(path, "{", 1);
		CommonMethod.insertContentToFile(path, insertContent.toString(), codeType, position);
	}

	/**
	 * ��ȡ�Ӳ����ļ���
	 * 
	 * @param regex
	 *            ƥ�����
	 * @param result
	 *            ��Ҫƥ����ַ���
	 * @param suffix
	 *            ƴ���ļ���׺
	 * @param isDeleRepet
	 *            �Ƿ��޳��ظ�
	 * @return
	 */
	public List<String> getChildLayout(String regex, String result, String suffix, boolean isDeleRepet) {
		List<String> list = new ArrayList<String>();

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(result);

		while (matcher.find()) {
			System.err.println("�Ӳ����ļ���" + matcher.group(1) + suffix);
			list.add(matcher.group(1) + suffix);
		}

		if (isDeleRepet) {
			// �޳��ظ�xml�ļ�
			Set<String> set = new HashSet<String>(list);
			list.clear();
			list.addAll(set);
		}

		return list;

	}

	/**
	 * ���Ĵ��룬�Ȱ���ǶXML�ļ���ȡ������
	 * 
	 * @param result
	 */
	public void parserEmbeddedXmlFile(String path) {
		String result = CommonMethod.fileToString(path);
		// ��ȡ�Ӳ����ļ�
		List<String> childFileNameList = getChildLayout("layout=\"@layout/([a-z|A-Z|0-9\\_]+)", result, ".xml", true);

		// �Ƚ�����XML�ļ�
		parserXmlFile(result, 0);

		if (childFileNameList.size() != 0) {
			for (int i = 0; i < childFileNameList.size(); i++) {
				// ��ȡ��·��
				String parentPath = path.substring(0, path.lastIndexOf("\\"));
				// ƴ�����ļ�·��
				String childFilePath = parentPath + "\\" + childFileNameList.get(i);
				System.err.println("���ļ�·����" + childFilePath);

				File file = new File(childFilePath);
				if (file.exists()) {
					String resultString = CommonMethod.fileToString(childFilePath);
					parserXmlFile(resultString, i + 1);
				} else {
					System.err.println("���ļ�������");
				}
			}
		}
	}

	/**
	 * ���ؼ����ƺ�ID�ֶ���ȡ����
	 * 
	 * @param result
	 */
	public void parserXmlFile(String result, int index) {
		// ����ֻ��Ҫid/�����ֵ����ˣ�����һ��ƥ����()������ֻҪ����a-z��A-Z����_һ�λ��ζ������������
		// ע��\s*��ʾ��ѡ�Ŀհ��ַ�
		Pattern pattern = Pattern.compile("<([a-z|A-Z|0-9\\.]+)\\s*android:id=\"@\\+id/([a-z|A-Z|0-9\\_]+)\"");
		Matcher matcher = pattern.matcher(result);

		while (matcher.find()) {
			// ��ȡ�ؼ����ƣ�����ImageView,TextView
			String widgetNameStr = matcher.group(1);
			// ����include��id�����ɿؼ�������ѭ��
			if (widgetNameStr.equals("include")) {
				continue;
			}
			// �Ƿ��Զ��ؼ�
			int customWidget = widgetNameStr.lastIndexOf(".");
			if (customWidget != -1) {
				widgetNameList.add(widgetNameStr.substring(customWidget + 1, widgetNameStr.length()));
			} else
				widgetNameList.add(widgetNameStr);

			String parserXmlResult = matcher.group(2);
			// �洢�ؼ�Id
			widgetIdList.add(parserXmlResult);
			String[] tempStrings = parserXmlResult.split("_");

			// ��ȡ�ؼ���������
			String widgetId = "m" + String.valueOf(index);
			for (int i = 0; i < tempStrings.length; i++) {
				String temp = String.valueOf(tempStrings[i].charAt(0));
				widgetId += tempStrings[i].replaceFirst(temp, temp.toUpperCase());
			}
			// �洢�ؼ�����ID����
			variableNameList.add(widgetId);
			System.out.println("��������" + widgetId + String.valueOf(index) + "     �ؼ����ƣ�" + widgetNameStr + "   R.id�ļ���" + parserXmlResult);
		}
	}
}
