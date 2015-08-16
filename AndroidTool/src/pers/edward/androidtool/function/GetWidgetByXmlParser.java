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
	// store current file method list(存储当前文件列表)
	private List<String> listMethod;

	// 存储布局控件
	private List<FileLayoutVariableModel> fileLayoutVariableList = new ArrayList<FileLayoutVariableModel>();

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
		// 解析文件
		parserEmbeddedXmlFile(xmlPath, codingType);
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
			String codeType, String modifierType, boolean isAddListener) throws Exception
	{
		selectedMethodName = selectedMethodName.replace("(", "\\(*");
		selectedMethodName = selectedMethodName.replace(")", "\\)*");
		selectedMethodName = selectedMethodName.replace("{", "\\{*");

		String targetFileString = CommonMethod.fileToString(targetFilePath, codeType);

		// System.out.println(targetFileString);
		// 根据匹配规则，根据public class XXXXX extends XXXXX {和传入进来的方法例如public void
		// methodTest(String a) {的条件，将此文件的内容分割成三份
		Pattern pattern = Pattern.compile("([\\s\\S]*public\\s*class\\s*[\\w]*\\s*extends\\s*[^{]*\\{)([\\s\\S]*\\s*" + selectedMethodName
				+ ")([\\s\\S]*)");
		Matcher matcher = pattern.matcher(targetFileString);

		StringBuffer sb = new StringBuffer();

		if (matcher.find())
		{

			System.out.println(matcher.group(1));
			sb.append(matcher.group(1) + "\n");
			System.err.println("-------------------------------------------------------------------------->");
			// 声明控件
			generateStatementVariable(list, modifierType, sb);
			System.out.println(matcher.group(2));
			sb.append(matcher.group(2) + "\n");
			System.err.println("-------------------------------------------------------------------------->");
			// 绑定控件
			generateBindViewVariable(list, sb);
			sb.append("\n");
			// 判断是否应该添加点击事件
			if (isAddListener)
			{
				addListener(list, sb);
			}
			System.out.println(matcher.group(3));
			sb.append(matcher.group(3));
		} else
		{
			System.out.println("not find for example public class XXXXX extends XXXXX {");
		}
		
		// 将数据写入指定文件中
		CommonMethod.inputDataToTargetFile(targetFilePath, sb.toString(), codeType);
	}

	/**
	 * 绑定控件数据
	 * 
	 * @param list
	 * 
	 * @param sb
	 */
	public void generateStatementVariable(List<RecordSelectedIndexModel> list, String modifierType, StringBuffer sb)
	{
		// 生成 声明控件代码。
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
					System.out.println(modifierType + " " + variableTypeString.substring(result + 1, variableTypeString.length()) + " m" + i
							+ tempVariable.get(j).getVariableName() + ";");
					sb.append(modifierType + " " + variableTypeString.substring(result + 1, variableTypeString.length()) + " m" + i
							+ tempVariable.get(j).getVariableName() + ";\n");
				} else
				{
					System.out.println(modifierType + " " + variableTypeString + " m" + i + tempVariable.get(j).getVariableName() + ";");
					sb.append(modifierType + " " + variableTypeString + " m" + i + tempVariable.get(j).getVariableName() + ";\n");
				}
			}
		}
	}

	/**
	 * 生成声明控件代码
	 * 
	 * @param list
	 * 
	 * @param sb
	 */
	public void generateBindViewVariable(List<RecordSelectedIndexModel> list, StringBuffer sb)
	{
		// 生成实例化代码
		for (int i = 0; i < list.size(); i++)
		{
			List<Integer> tempList = list.get(i).getSubListIndex();
			List<VariableDataModel> tempVariable = fileLayoutVariableList.get(i).getVariableList();
			for (int j = 0; j < tempList.size(); j++)
			{
				String variableTypeString = tempVariable.get(j).getVariableType();

				String tempType = null, tempName = null;
				// 判断变量类型是否自定义控件，如果是自定义控件就有 xxx.xxx的规则，根据规则截取.最后一段作为此变量名
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
				System.out.println("m" + i + tempName + " = (" + tempType + ") findViewById(R.id." + tempName + ");");
				sb.append("m" + i + tempName + " = (" + tempType + ") findViewById(R.id." + tempName + ");\n");
			}
		}
	}

	/**
	 * 为控件添加监听事件
	 * 
	 * @param list
	 * 
	 * @param sb
	 */
	public void addListener(List<RecordSelectedIndexModel> list, StringBuffer sb)
	{
		for (int i = 0; i < list.size(); i++)
		{
			List<Integer> tempList = list.get(i).getSubListIndex();
			List<VariableDataModel> tempVariable = fileLayoutVariableList.get(i).getVariableList();
			for (int j = 0; j < tempList.size(); j++)
			{
				String variableTypeString = tempVariable.get(j).getVariableType();

				if (variableTypeString.equals("TextView") || variableTypeString.equals("ImageView") || variableTypeString.equals("LinearLayout")
						|| variableTypeString.equals("RelativeLayout") || variableTypeString.equals("ImageButton"))
				{
					System.out.println("m" + i + tempVariable.get(j).getVariableName() + ".setOnClickListener(this);");
					sb.append("m" + i + tempVariable.get(j).getVariableName() + ".setOnClickListener(this);\n");
				}
			}
		}
	}

	/**
	 * 获取指定文件路径的方法列表。
	 * 
	 * @param targetFilepPath
	 */
	public List<String> getTargetFileMethodList(String targetFilepPath, String codingType) throws Exception
	{
		String result = CommonMethod.fileToString(targetFilepPath, codingType);
		listMethod = new ArrayList<String>();

		// 根据指定规则提取目标文件方法列表
		Pattern pattern = Pattern.compile("([public|private|protected]*\\s+[\\w]+\\s+[\\w]+\\([^)]*\\)\\s*\\{)");
		Matcher matcher = pattern.matcher(result);

		while (matcher.find())
		{
			listMethod.add(matcher.group(1).trim());
			// String temp=matcher.group(1).trim();
		}

		return listMethod;
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
	public void parserEmbeddedXmlFile(String path, String codingType)
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
			String fileResultString = CommonMethod.fileToString(currentPath, codingType);
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

}
