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
 * Android控件自动生成器
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
	 *            xml文件路径
	 * @param targetPath
	 *            目标文件路径
	 * @param methodName
	 *            插入方法名
	 * @param codingType
	 *            编码类型
	 * @throws Exception
	 */
	public void generateWidget(String xmlPath, String targetPath, String methodName, String codingType) throws Exception {
		isRegListener = true;

		// 解析文件
		parserEmbeddedXmlFile(xmlPath);

		// 写入声明控件变量
//		statementVariable(targetPath, codingType);
//
//		bindView(methodName, targetPath, codingType);
	}

	/**
	 * 核心代码，绑定控件数据
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
		sb.append("\n//以下代码由自动生成器生成\n");
		for (int i = 0; i < widgetNameList.size(); i++) {
			sb.append(variableNameList.get(i) + "=" + "(" + widgetNameList.get(i) + ")" + "findViewById(R.id." + widgetIdList.get(i) + ");\n");
		}
		sb.append("\n");

		// 注册监听事件
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
		// CommonMethod.insertContentToFile(targetString, "我的测试", codingType,
		// position);
	}

	/**
	 * 重新创建文件，删除再建一个新文件
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
	 * 核心代码，生成声明控件变量代码
	 * 
	 * @param result
	 * @param targetString
	 * @throws Exception
	 */
	public void statementVariable(String path, String codeType) throws Exception {

		StringBuffer insertContent = new StringBuffer();
		insertContent.append("\n//控件变量声明（由Android自动生成器生成）\n");
		System.err.println("总共有" + widgetNameList.size() + "个变量");
		for (int i = 0; i < widgetNameList.size(); i++) {
			insertContent.append("private " + widgetNameList.get(i) + " " + variableNameList.get(i) + ";\n");
		}
		insertContent.append("\n");

		long position = CommonMethod.seekInsertLocation(path, "{", 1);
		CommonMethod.insertContentToFile(path, insertContent.toString(), codeType, position);
	}

	/**
	 * 获取子布局文件名
	 * 
	 * @param regex
	 *            匹配规则
	 * @param result
	 *            需要匹配的字符串
	 * @param suffix
	 *            拼接文件后缀
	 * @param isDeleRepet
	 *            是否剔除重复
	 * @return
	 */
	public List<String> getChildLayout(String regex, String result, String suffix, boolean isDeleRepet) {
		List<String> list = new ArrayList<String>();

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(result);

		while (matcher.find()) {
			System.err.println("子布局文件：" + matcher.group(1) + suffix);
			list.add(matcher.group(1) + suffix);
		}

		if (isDeleRepet) {
			// 剔除重复xml文件
			Set<String> set = new HashSet<String>(list);
			list.clear();
			list.addAll(set);
		}

		return list;

	}

	/**
	 * 核心代码，先把内嵌XML文件提取出来。
	 * 
	 * @param result
	 */
	public void parserEmbeddedXmlFile(String path) {
		String result = CommonMethod.fileToString(path);
		// 获取子布局文件
		List<String> childFileNameList = getChildLayout("layout=\"@layout/([a-z|A-Z|0-9\\_]+)", result, ".xml", true);

		// 先解析主XML文件
		parserXmlFile(result, 0);

		if (childFileNameList.size() != 0) {
			for (int i = 0; i < childFileNameList.size(); i++) {
				// 提取父路径
				String parentPath = path.substring(0, path.lastIndexOf("\\"));
				// 拼接子文件路径
				String childFilePath = parentPath + "\\" + childFileNameList.get(i);
				System.err.println("子文件路径：" + childFilePath);

				File file = new File(childFilePath);
				if (file.exists()) {
					String resultString = CommonMethod.fileToString(childFilePath);
					parserXmlFile(resultString, i + 1);
				} else {
					System.err.println("子文件不存在");
				}
			}
		}
	}

	/**
	 * 将控件名称和ID字段提取出来
	 * 
	 * @param result
	 */
	public void parserXmlFile(String result, int index) {
		// 由于只需要id/后面的值。因此，加上一个匹配组()。组内只要出现a-z，A-Z或者_一次或多次都算符合条件。
		// 注意\s*表示可选的空白字符
		Pattern pattern = Pattern.compile("<([a-z|A-Z|0-9\\.]+)\\s*android:id=\"@\\+id/([a-z|A-Z|0-9\\_]+)\"");
		Matcher matcher = pattern.matcher(result);

		while (matcher.find()) {
			// 获取控件名称，例如ImageView,TextView
			String widgetNameStr = matcher.group(1);
			// 含有include的id构不成控件，跳出循环
			if (widgetNameStr.equals("include")) {
				continue;
			}
			// 是否自定控件
			int customWidget = widgetNameStr.lastIndexOf(".");
			if (customWidget != -1) {
				widgetNameList.add(widgetNameStr.substring(customWidget + 1, widgetNameStr.length()));
			} else
				widgetNameList.add(widgetNameStr);

			String parserXmlResult = matcher.group(2);
			// 存储控件Id
			widgetIdList.add(parserXmlResult);
			String[] tempStrings = parserXmlResult.split("_");

			// 获取控件变量名称
			String widgetId = "m" + String.valueOf(index);
			for (int i = 0; i < tempStrings.length; i++) {
				String temp = String.valueOf(tempStrings[i].charAt(0));
				widgetId += tempStrings[i].replaceFirst(temp, temp.toUpperCase());
			}
			// 存储控件变量ID名称
			variableNameList.add(widgetId);
			System.out.println("变量名：" + widgetId + String.valueOf(index) + "     控件名称：" + widgetNameStr + "   R.id文件：" + parserXmlResult);
		}
	}
}
