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
 * Android�ؼ��Զ�������
 * 
 * @author Edward
 * 
 */
public class GetWidgetByXmlParser
{
	private List<String> widgetNameList = new ArrayList<String>();
	private List<String> widgetIdList = new ArrayList<String>();
	private List<String> variableNameList = new ArrayList<String>();
	// store current file method list(�洢��ǰ�ļ��б�)
	private List<String> listMethod = new ArrayList<String>();

	private boolean isRegListener;
	// �洢���ֿؼ�
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
	 *            xml�ļ�·��
	 * @param targetPath
	 *            Ŀ���ļ�·��
	 * @param methodName
	 *            ���뷽����
	 * @param codingType
	 *            ��������
	 * @throws Exception
	 */
	public void generateWidget(String xmlPath, String targetPath, String codingType) throws Exception
	{
		isRegListener = true;

		fileLayoutVariableList = new ArrayList<FileLayoutVariableModel>();

		// �����ļ�
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

		// д�������ؼ�����
		// generateStatementVariable(targetPath,
		// "String test(String basd){",null, codingType);

		// getTargetFileMethodList(targetPath);

		//
		// bindView(methodName, targetPath, codingType);
	}

	/**
	 * ���Ĵ��룬�󶨿ؼ�����
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
		sb.append("\n//���´������Զ�����������\n".getBytes(condingType));
		for (int i = 0; i < widgetNameList.size(); i++)
		{
			sb.append(variableNameList.get(i) + "=" + "(" + widgetNameList.get(i) + ")" + "findViewById(R.id." + widgetIdList.get(i) + ");\n");
		}
		sb.append("\n");

		// ע������¼�
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
		// CommonMethod.insertContentToFile(targetString, "�ҵĲ���", codingType,
		// position);
	}

	/**
	 * ���´����ļ���ɾ���ٽ�һ�����ļ�
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
	 * ���Ĵ��룬���������ؼ�������ʵ�����ؼ�����
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
		// ����ƥ����򣬸���public class XXXXX extends XXXXX {�ʹ�������ķ�������public void
		// methodTest(String a) {�������������ļ������ݷָ������
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

		//������д��ָ���ļ���
		inputDataToTargetFile(targetFilePath, sb.toString(), codeType);

		// ʵ�����ؼ�����
		// bindView("public void insertString(String a)", targetFileString);

		// int insertIndex = targetFileString.indexOf("{");
		//
		// CommonMethod.insertContentToFile(targetFilePath, "111", codeType,
		// insertIndex + 1);

		// StringBuffer insertContent = new StringBuffer();
		// insertContent.append("\n//�ؼ�������������Android�Զ����������ɣ�\n");
		// System.err.println("�ܹ���" + widgetNameList.size() + "������");
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
	 * ������
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
	 * ��ȡָ���ļ�·���ķ����б�
	 * 
	 * @param targetFilepPath
	 */
	public List<String> getTargetFileMethodList(String targetFilepPath) throws Exception
	{
		String result = CommonMethod.fileToString(targetFilepPath);

		// ����ָ��������ȡĿ���ļ������б�
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
	 * ������д�뵽ָ�����ļ���
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
			System.err.println("д��ؼ������쳣������");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	public List<String> getChildLayout(String regex, String result, String suffix, boolean isDeleRepet)
	{
		List<String> list = new ArrayList<String>();

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(result);

		while (matcher.find())
		{
			System.err.println("�Ӳ����ļ���" + matcher.group(1) + suffix);
			list.add(matcher.group(1) + suffix);
		}

		if (isDeleRepet)
		{
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
	/**
	 * @param path
	 */
	public void parserEmbeddedXmlFile(String path)
	{
		if (path.isEmpty())
		{
			System.out.println("xml file path is null!!!(xml�ļ�Ϊ��)");
			return;
		}

		Queue<String> queue = new LinkedList<String>();
		File file = new File(path);
		if (file.exists())
		{
			// ����һ���ļ���ջ
			queue.add(path);
		} else
		{
			System.err.println("input file path error!(�����ļ�·������)");
			return;
		}

		// ������в�Ϊ��
		while (!queue.isEmpty())
		{
			// �����ǰXML�ļ�·��
			String currentPath = queue.poll();

			// ���ڻ�ȡ��ǰ·���ĸ�·��
			file = new File(currentPath);

			// ����·���ļ�ת��ΪString
			String fileResultString = CommonMethod.fileToString(currentPath);
			// System.err.println(fileResultString);

			// �������ļ�
			searchChildFileLayout(queue, file, fileResultString);

			System.out.println(currentPath);

			// �洢�����ļ���·��
			FileLayoutVariableModel model = new FileLayoutVariableModel();
			model.setFileName(currentPath);
			// ���洢�������б�洢����
			model.setVariableList(getVariableTypeAndNameList(fileResultString));

			fileLayoutVariableList.add(model);
		}

		// String result = CommonMethod.fileToString(path);
		// // ��ȡ�Ӳ����ļ�
		// List<String> childFileNameList =
		// getChildLayout("layout=\"@layout/([a-z|A-Z|0-9\\_]+)", result,
		// ".xml", true);
		//
		// // �Ƚ�����XML�ļ�
		// parserXmlFile(result, 0);
		//
		// // �����Ӳ��ֵ�XML�ļ�
		// if (childFileNameList.size() != 0)
		// {
		// for (int i = 0; i < childFileNameList.size(); i++)
		// {
		// // ��ȡ��·��
		// String parentPath = path.substring(0, path.lastIndexOf("\\"));
		// // ƴ�����ļ�·��
		// String childFilePath = parentPath + "\\" + childFileNameList.get(i);
		// System.err.println("���ļ�·����" + childFilePath);
		//
		// File file = new File(childFilePath);
		// if (file.exists())
		// {
		// String resultString = CommonMethod.fileToString(childFilePath);
		// parserXmlFile(resultString, i + 1);
		// } else
		// {
		// System.err.println("���ļ�������");
		// }
		// }
		// }
	}

	/**
	 * �˺��������������ļ�����
	 * 
	 * @param queue
	 * 
	 * @param file
	 * 
	 * @param fileResultString
	 */
	public void searchChildFileLayout(Queue<String> queue, File file, String fileResultString)
	{
		// �˹���������ȡ�Ӳ����ļ�
		Pattern pattern = Pattern.compile("layout=\"@layout/([^\"]*)");
		Matcher matcher = pattern.matcher(fileResultString);

		// Ѱ�Ҵ�XML�ļ����ж��ٸ����ļ�
		while (matcher.find())
		{
			String childFilePath = file.getParent() + "\\" + matcher.group(1) + ".xml";

			file = new File(childFilePath);
			// ����ļ��Ѵ��ڣ�������������
			if (file.exists())
			{
				queue.add(childFilePath);
			} else
			{
				System.err.println("subfile inexistence!!!(���ļ�������)");
			}
			// System.out.println(file.getParent() + "\\" + matcher.group(1)
			// + ".xml");
		}
	}

	/**
	 * ��ȡ�������ͺ������б�
	 */
	public List<VariableDataModel> getVariableTypeAndNameList(String fileResultString)
	{
		// <([\\w|.]*)\\s*.*\\s*android:id=\"@\\+id/([^\"]*)\"
		// �˴��㷨��Ҫ�Ż�-------------------------------------------------------------------------------------------------------------->
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
	 * ���ؼ����ƺ�ID�ֶ���ȡ����
	 * 
	 * @param result
	 */
	public void parserXmlFile(String result, int index)
	{
		// ����ֻ��Ҫid/�����ֵ����ˣ�����һ��ƥ����()������ֻҪ����a-z��A-Z����_һ�λ��ζ������������
		// ע��\s*��ʾ��ѡ�Ŀհ��ַ�
		Pattern pattern = Pattern.compile("<([a-z|A-Z|0-9\\.]+)\\s*android:id=\"@\\+id/([a-z|A-Z|0-9\\_]+)\"");
		Matcher matcher = pattern.matcher(result);

		while (matcher.find())
		{
			// ��ȡ�ؼ����ƣ�����ImageView,TextView
			String widgetNameStr = matcher.group(1);
			// ����include��id�����ɿؼ�������ѭ��
			if (widgetNameStr.equals("include"))
			{
				continue;
			}
			// �Ƿ��Զ��ؼ�
			int customWidget = widgetNameStr.lastIndexOf(".");
			if (customWidget != -1)
			{
				widgetNameList.add(widgetNameStr.substring(customWidget + 1, widgetNameStr.length()));
			} else
				widgetNameList.add(widgetNameStr);

			String parserXmlResult = matcher.group(2);
			// �洢�ؼ�Id
			widgetIdList.add(parserXmlResult);
			String[] tempStrings = parserXmlResult.split("_");

			// ��ȡ�ؼ���������
			String widgetId = "m" + String.valueOf(index);
			for (int i = 0; i < tempStrings.length; i++)
			{
				String temp = String.valueOf(tempStrings[i].charAt(0));
				widgetId += tempStrings[i].replaceFirst(temp, temp.toUpperCase());
			}
			// �洢�ؼ�����ID����
			variableNameList.add(widgetId);
			System.out.println("��������" + widgetId + String.valueOf(index) + "     �ؼ����ƣ�" + widgetNameStr + "   R.id�ļ���" + parserXmlResult);
		}
	}
}
