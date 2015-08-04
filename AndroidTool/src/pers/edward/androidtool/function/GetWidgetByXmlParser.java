package pers.edward.androidtool.function;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pers.edward.androidtool.model.FileLayoutVariableModel;
import pers.edward.androidtool.model.RecordSelectedIndexModel;
import pers.edward.androidtool.model.VariableDataModel;
import pers.edward.androidtool.tool.CommonMethod;

/**
 * Android控件自动生成器
 * 
 * @author Edward
 * 
 */
public class GetWidgetByXmlParser
{
	private List<String> widgetNameList = new ArrayList<String>();
	private List<String> widgetIdList = new ArrayList<String>();
	private List<String> variableNameList = new ArrayList<String>();
	// store current file method list(存储当前文件列表)
	private List<String> listMethod = new ArrayList<String>();

	private boolean isRegListener;
	// 存储布局控件
	private List<FileLayoutVariableModel> fileLayoutVariableList;

	public List<FileLayoutVariableModel> getFileLayoutVariableList()
	{
		return fileLayoutVariableList;
	}

	public void setFileLayoutVariableList(List<FileLayoutVariableModel> fileLayoutVariableList)
	{
		this.fileLayoutVariableList = fileLayoutVariableList;
	}

	public static void main(String[] args)
	{
		GetWidgetByXmlParser getWidgetByXmlParser = new GetWidgetByXmlParser();
		try
		{
			// C:\\MyWorkspace\\Android\\YiHuaHotel\\N18Client\\res\\layout\\fragment_home.xml
			// C:\\MyWorkspace\\Android\\YiHuaHotel\\N18Client\\src\\cn\\zhanyun\\n18client\\ApplicationActivity.java
			getWidgetByXmlParser.generateWidget("C:\\MyWorkspace\\Android\\YiHuaHotel\\MyTest\\res\\layout\\one.xml",
					"C:\\MyWorkspace\\Android\\YiHuaHotel\\MyTest\\src\\com\\example\\mytest\\_Main1.java", "utf-8");
		} catch (Exception e)
		{
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
	public void generateWidget(String xmlPath, String targetPath, String codingType) throws Exception
	{
		isRegListener = true;

		fileLayoutVariableList = new ArrayList<FileLayoutVariableModel>();

		// 解析文件
		parserEmbeddedXmlFile(xmlPath);

		// System.out.println();
		// System.out.println();
		// for (int i = 0; i < fileLayoutVariableList.size(); i++)
		// {
		// File file = new File(fileLayoutVariableList.get(i).getFileName());
		// System.err.println(file.getName());
		// List<VariableDataModel> list =
		// fileLayoutVariableList.get(i).getVariableList();
		// for (int j = 0; j < list.size(); j++)
		// {
		// System.out.println(list.get(j).getVariableType() + "   " +
		// list.get(j).getVariableName());
		// }
		//
		// }

		// 写入声明控件变量
		// generateStatementVariable(targetPath,
		// "String test(String basd){",null, codingType);

		// getTargetFileMethodList(targetPath);

		//
		// bindView(methodName, targetPath, codingType);
	}

	/**
	 * 核心代码，绑定控件数据
	 * 
	 * @param methodName
	 * 
	 * @param result
	 * 
	 * @param targetString
	 * 
	 * @throws Exception
	 */
	public void bindView(String methodName, String targetString, String condingType) throws Exception
	{
		String result = CommonMethod.fileToString(targetString);

		String head = result.substring(0, result.indexOf(methodName)).trim();
		String tail = result.substring(result.indexOf(methodName), result.length()).trim();
		head = head + tail.substring(0, tail.indexOf("{") + 1).trim();
		tail = tail.substring(tail.indexOf("{") + 1, tail.length()).trim();

		RandomAccessFile rf;
		rf = reSetUpFile(targetString);
		rf.write(head.getBytes(condingType));

		StringBuffer sb = new StringBuffer();
		sb.append("\n//以下代码由自动生成器生成\n".getBytes(condingType));
		for (int i = 0; i < widgetNameList.size(); i++)
		{
			sb.append(variableNameList.get(i) + "=" + "(" + widgetNameList.get(i) + ")" + "findViewById(R.id." + widgetIdList.get(i) + ");\n");
		}
		sb.append("\n");

		// 注册监听事件
		if (isRegListener)
		{
			for (int i = 0; i < widgetNameList.size(); i++)
			{
				String widgetName = widgetNameList.get(i);
				if (widgetName.equals("ImageView") || widgetName.equals("TextView") || widgetName.equals("ImageButton"))
				{
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
	public RandomAccessFile reSetUpFile(String targetString)
	{
		RandomAccessFile rf = null;
		try
		{
			File file = new File(targetString);
			if (file.exists())
			{
				file.delete();
				file.createNewFile();
				rf = new RandomAccessFile(targetString, "rw");
			}
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return rf;
	}

	/**
	 * 核心代码，生成声明控件变量，实例化控件代码
	 * 
	 * @param result
	 * 
	 * @param targetString
	 * 
	 * @throws Exception
	 */
	public void generateStatementAndBindviewVariable(String targetFilePath, String selectedMethodName, List<RecordSelectedIndexModel> list,
			String codeType) throws Exception
	{
		selectedMethodName = selectedMethodName.replace("(", "\\(*");
		selectedMethodName = selectedMethodName.replace(")", "\\)*");
		selectedMethodName = selectedMethodName.replace("{", "\\{*");
		String targetFileString = CommonMethod.fileToString(targetFilePath);
		// System.out.println(targetFileString);
		// 根据匹配规则，根据public class XXXXX extends XXXXX {和传入进来的方法例如public void
		// methodTest(String a) {的条件，将此文件的内容分割成三份
		Pattern pattern = Pattern.compile("([\\s\\S]*public\\s*class\\s*[\\w]*\\s*extends\\s*[^{]*\\{)([\\s\\S]*\\s*" + selectedMethodName
				+ ")([\\s\\S]*)");
		Matcher matcher = pattern.matcher(targetFileString);

		StringBuffer sb = new StringBuffer();

		if (matcher.find())
		{
			// sb.append(matcher.group(1) + "\n");
			// sb.append(matcher.group(2));

			System.out.println(matcher.group(1));
			sb.append(matcher.group(1) + "\n");
			System.err.println("-------------------------------------------------------------------------->");
			for (int i = 0; i < list.size(); i++)
			{
				List<Integer> tempList = list.get(i).getSubListIndex();
				List<VariableDataModel> tempVariable = fileLayoutVariableList.get(i).getVariableList();
				for (int j = 0; j < tempList.size(); j++)
				{
					String variableTypeString = tempVariable.get(j).getVariableType();
					int result = variableTypeString.lastIndexOf(".");
					if (result != -1)
					{
						System.out.println(variableTypeString.substring(result + 1, variableTypeString.length()) + " "
								+ tempVariable.get(j).getVariableName() + ";");
						sb.append(variableTypeString.substring(result + 1, variableTypeString.length()) + " " + tempVariable.get(j).getVariableName()
								+ ";\n");
					} else
					{
						System.out.println(variableTypeString + " " + tempVariable.get(j).getVariableName() + ";");
						sb.append(variableTypeString + " " + tempVariable.get(j).getVariableName() + ";\n");
					}
				}
			}
			System.out.println(matcher.group(2));
			sb.append(matcher.group(2) + "\n");
			System.err.println("------------------------------------------------------------------------->");
			for (int i = 0; i < list.size(); i++)
			{
				List<Integer> tempList = list.get(i).getSubListIndex();
				List<VariableDataModel> tempVariable = fileLayoutVariableList.get(i).getVariableList();
				for (int j = 0; j < tempList.size(); j++)
				{
					String variableTypeString = tempVariable.get(j).getVariableType();

					String tempType = null, tempName = null;

					int result = variableTypeString.lastIndexOf(".");
					if (result != -1)
					{
						tempType = variableTypeString.substring(result + 1, variableTypeString.length());
						tempName = tempVariable.get(j).getVariableName();
					} else
					{
						tempType = variableTypeString;
						tempName = tempVariable.get(j).getVariableName();
					}
					System.out.println(tempName + " = (" + tempType + ") findViewById(R.id." + tempName + ");");
					sb.append(tempName + " = (" + tempType + ") findViewById(R.id." + tempName + ");\n");
				}
			}
			System.out.println(matcher.group(3));
			sb.append(matcher.group(3));
		} else
		{

			System.out.println("not find for example public class XXXXX extends XXXXX {");
		}

		//将数据写入指定文件中
		inputDataToTargetFile(targetFilePath, sb.toString(), codeType);

		// 实例化控件变量
		// bindView("public void insertString(String a)", targetFileString);

		// int insertIndex = targetFileString.indexOf("{");
		//
		// CommonMethod.insertContentToFile(targetFilePath, "111", codeType,
		// insertIndex + 1);

		// StringBuffer insertContent = new StringBuffer();
		// insertContent.append("\n//控件变量声明（由Android自动生成器生成）\n");
		// System.err.println("总共有" + widgetNameList.size() + "个变量");
		// for (int i = 0; i < widgetNameList.size(); i++)
		// {
		// insertContent.append("private " + widgetNameList.get(i) + " " +
		// variableNameList.get(i) + ";\n");
		// }
		// insertContent.append("\n");
		//
		// long position = CommonMethod.seekInsertLocation(path, "{", 1);
		// CommonMethod.insertContentToFile(path, insertContent.toString(),
		// codeType, position);
	}

	/**
	 * 绑定数据
	 * 
	 * @param methodName
	 */
	public void bindView(String methodName, String result)
	{
		System.out.println(methodName);
		methodName = methodName.replace("(", "\\(");
		methodName = methodName.replace(")", "\\)");
		// ([\\s\\S]*public void insertString\\(\\)\\s*\\{)([\\s\\S]*)

		Pattern pattern = Pattern.compile("([\\s\\S]*\\s*" + methodName + "\\s*\\{)([\\s\\S]*)");
		Matcher matcher = pattern.matcher(result);

		if (matcher.find())
		{
			System.out.println(matcher.group(1));
			System.err.println("------------------------------------------------------------------------->");
			System.out.println(matcher.group(2));
		}
	}

	/**
	 * 获取指定文件路径的方法列表。
	 * 
	 * @param targetFilepPath
	 */
	public List<String> getTargetFileMethodList(String targetFilepPath) throws Exception
	{
		String result = CommonMethod.fileToString(targetFilepPath);

		// 根据指定规则提取目标文件方法列表
		Pattern pattern = Pattern.compile("([public|private|protected]*\\s+[\\w]+\\s+[\\w]+\\([^)]*\\)\\s*\\{)");
		Matcher matcher = pattern.matcher(result);

		while (matcher.find())
		{
			listMethod.add(matcher.group(1).trim());
			// String temp=matcher.group(1).trim();
			// System.out.println(temp.substring(0, temp.length()-1));
		}

		return listMethod;
		// inputVariableInstanceData(targetFilepPath,
		// "public void insertString(String a) {");
	}

	/**
	 * 将数据写入到指定的文件中
	 * 
	 * @param targetFilePath
	 * 
	 * @param methodName
	 */
	public void inputDataToTargetFile(String targetFilePath, String variableString, String codingType)
	{
		try
		{
			RandomAccessFile rf = reSetUpFile(targetFilePath);
			rf.write(variableString.getBytes(codingType));
			rf.close();
		} catch (IOException e)
		{
			System.err.println("写入控件变量异常！！！");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	public List<String> getChildLayout(String regex, String result, String suffix, boolean isDeleRepet)
	{
		List<String> list = new ArrayList<String>();

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(result);

		while (matcher.find())
		{
			System.err.println("子布局文件：" + matcher.group(1) + suffix);
			list.add(matcher.group(1) + suffix);
		}

		if (isDeleRepet)
		{
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
	/**
	 * @param path
	 */
	public void parserEmbeddedXmlFile(String path)
	{
		if (path.isEmpty())
		{
			System.out.println("xml file path is null!!!(xml文件为空)");
			return;
		}

		Queue<String> queue = new LinkedList<String>();
		File file = new File(path);
		if (file.exists())
		{
			// 将第一个文件入栈
			queue.add(path);
		} else
		{
			System.err.println("input file path error!(输入文件路径错误)");
			return;
		}

		// 如果队列不为空
		while (!queue.isEmpty())
		{
			// 输出当前XML文件路径
			String currentPath = queue.poll();

			// 用于获取当前路径的父路径
			file = new File(currentPath);

			// 将该路径文件转换为String
			String fileResultString = CommonMethod.fileToString(currentPath);
			// System.err.println(fileResultString);

			// 搜索子文件
			searchChildFileLayout(queue, file, fileResultString);

			System.out.println(currentPath);

			// 存储当先文件的路径
			FileLayoutVariableModel model = new FileLayoutVariableModel();
			model.setFileName(currentPath);
			// 将存储变量的列表存储起来
			model.setVariableList(getVariableTypeAndNameList(fileResultString));

			fileLayoutVariableList.add(model);
		}

		// String result = CommonMethod.fileToString(path);
		// // 获取子布局文件
		// List<String> childFileNameList =
		// getChildLayout("layout=\"@layout/([a-z|A-Z|0-9\\_]+)", result,
		// ".xml", true);
		//
		// // 先解析主XML文件
		// parserXmlFile(result, 0);
		//
		// // 解析子布局的XML文件
		// if (childFileNameList.size() != 0)
		// {
		// for (int i = 0; i < childFileNameList.size(); i++)
		// {
		// // 提取父路径
		// String parentPath = path.substring(0, path.lastIndexOf("\\"));
		// // 拼接子文件路径
		// String childFilePath = parentPath + "\\" + childFileNameList.get(i);
		// System.err.println("子文件路径：" + childFilePath);
		//
		// File file = new File(childFilePath);
		// if (file.exists())
		// {
		// String resultString = CommonMethod.fileToString(childFilePath);
		// parserXmlFile(resultString, i + 1);
		// } else
		// {
		// System.err.println("子文件不存在");
		// }
		// }
		// }
	}

	/**
	 * 此函数用来搜索子文件布局
	 * 
	 * @param queue
	 * 
	 * @param file
	 * 
	 * @param fileResultString
	 */
	public void searchChildFileLayout(Queue<String> queue, File file, String fileResultString)
	{
		// 此规则用于提取子布局文件
		Pattern pattern = Pattern.compile("layout=\"@layout/([^\"]*)");
		Matcher matcher = pattern.matcher(fileResultString);

		// 寻找此XML文件下有多少个子文件
		while (matcher.find())
		{
			String childFilePath = file.getParent() + "\\" + matcher.group(1) + ".xml";

			file = new File(childFilePath);
			// 如果文件已存在，则将其存入队列中
			if (file.exists())
			{
				queue.add(childFilePath);
			} else
			{
				System.err.println("subfile inexistence!!!(子文件不存在)");
			}
			// System.out.println(file.getParent() + "\\" + matcher.group(1)
			// + ".xml");
		}
	}

	/**
	 * 获取变量类型和名称列表
	 */
	public List<VariableDataModel> getVariableTypeAndNameList(String fileResultString)
	{
		// <([\\w|.]*)\\s*.*\\s*android:id=\"@\\+id/([^\"]*)\"
		// 此处算法需要优化-------------------------------------------------------------------------------------------------------------->
		Pattern pattern = Pattern.compile("<([\\w|.]*)\\s*.*\\s*android:id=\"@\\+id/([^\"]*)\"");
		Matcher matcher = pattern.matcher(fileResultString);

		List<VariableDataModel> tempList = new ArrayList<VariableDataModel>();
		while (matcher.find())
		{
			VariableDataModel variableDataModel = new VariableDataModel();
			variableDataModel.setVariableType(matcher.group(1));
			variableDataModel.setVariableName(matcher.group(2));
			tempList.add(variableDataModel);
			System.err.println(matcher.group(1) + "       " + matcher.group(2));
		}

		return tempList;
	}

	/**
	 * 将控件名称和ID字段提取出来
	 * 
	 * @param result
	 */
	public void parserXmlFile(String result, int index)
	{
		// 由于只需要id/后面的值。因此，加上一个匹配组()。组内只要出现a-z，A-Z或者_一次或多次都算符合条件。
		// 注意\s*表示可选的空白字符
		Pattern pattern = Pattern.compile("<([a-z|A-Z|0-9\\.]+)\\s*android:id=\"@\\+id/([a-z|A-Z|0-9\\_]+)\"");
		Matcher matcher = pattern.matcher(result);

		while (matcher.find())
		{
			// 获取控件名称，例如ImageView,TextView
			String widgetNameStr = matcher.group(1);
			// 含有include的id构不成控件，跳出循环
			if (widgetNameStr.equals("include"))
			{
				continue;
			}
			// 是否自定控件
			int customWidget = widgetNameStr.lastIndexOf(".");
			if (customWidget != -1)
			{
				widgetNameList.add(widgetNameStr.substring(customWidget + 1, widgetNameStr.length()));
			} else
				widgetNameList.add(widgetNameStr);

			String parserXmlResult = matcher.group(2);
			// 存储控件Id
			widgetIdList.add(parserXmlResult);
			String[] tempStrings = parserXmlResult.split("_");

			// 获取控件变量名称
			String widgetId = "m" + String.valueOf(index);
			for (int i = 0; i < tempStrings.length; i++)
			{
				String temp = String.valueOf(tempStrings[i].charAt(0));
				widgetId += tempStrings[i].replaceFirst(temp, temp.toUpperCase());
			}
			// 存储控件变量ID名称
			variableNameList.add(widgetId);
			System.out.println("变量名：" + widgetId + String.valueOf(index) + "     控件名称：" + widgetNameStr + "   R.id文件：" + parserXmlResult);
		}
	}
}
